/*
 *
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2024 Evan Debenham
 *
 * Souls Pixel Dungeon
 * Copyright (C) 2024 Bartolomeo Cold
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.soulspixel.soulspixeldungeon.actors.mobs.npcs;

import com.soulspixel.soulspixeldungeon.Dungeon;
import com.soulspixel.soulspixeldungeon.SoulsPixelDungeon;
import com.soulspixel.soulspixeldungeon.Statistics;
import com.soulspixel.soulspixeldungeon.actors.Char;
import com.soulspixel.soulspixeldungeon.actors.blobs.Blob;
import com.soulspixel.soulspixeldungeon.actors.buffs.AscensionChallenge;
import com.soulspixel.soulspixeldungeon.actors.buffs.BlobImmunity;
import com.soulspixel.soulspixeldungeon.actors.buffs.Buff;
import com.soulspixel.soulspixeldungeon.effects.CellEmitter;
import com.soulspixel.soulspixeldungeon.effects.Speck;
import com.soulspixel.soulspixeldungeon.effects.particles.ElmoParticle;
import com.soulspixel.soulspixeldungeon.items.Heap;
import com.soulspixel.soulspixeldungeon.items.Item;
import com.soulspixel.soulspixeldungeon.items.armor.Armor;
import com.soulspixel.soulspixeldungeon.journal.Notes;
import com.soulspixel.soulspixeldungeon.messages.Messages;
import com.soulspixel.soulspixeldungeon.scenes.GameScene;
import com.soulspixel.soulspixeldungeon.scenes.PixelScene;
import com.soulspixel.soulspixeldungeon.sprites.ItemSprite;
import com.soulspixel.soulspixeldungeon.sprites.ShopkeeperSprite;
import com.soulspixel.soulspixeldungeon.utils.GLog;
import com.soulspixel.soulspixeldungeon.windows.WndBag;
import com.soulspixel.soulspixeldungeon.windows.WndOptions;
import com.soulspixel.soulspixeldungeon.windows.WndTitledMessage;
import com.soulspixel.soulspixeldungeon.windows.WndTradeItem;
import com.watabou.noosa.Game;
import com.watabou.noosa.Image;
import com.watabou.utils.BArray;
import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;

import java.util.ArrayList;

public class Shopkeeper extends NPC {

	{
		spriteClass = ShopkeeperSprite.class;

		properties.add(Property.IMMOVABLE);
	}

	public static int MAX_BUYBACK_HISTORY = 3;
	public ArrayList<Item> buybackItems = new ArrayList<>();

	private int turnsSinceHarmed = -1;
	
	@Override
	protected boolean act() {

		if (Dungeon.level.visited[pos]){
			Notes.add(Notes.Landmark.SHOP);
		}

		if (turnsSinceHarmed >= 0){
			turnsSinceHarmed ++;
		}

		sprite.turnTo( pos, Dungeon.hero.pos );
		spend( TICK );
		return super.act();
	}
	
	@Override
	public void damage(int dmg, Object src, DamageType damageType) {
		processHarm();
	}
	
	@Override
	public boolean add( Buff buff ) {
		if (buff.type == Buff.buffType.NEGATIVE){
			processHarm();
		}
		return false;
	}

	public void processHarm(){

		//do nothing if the shopkeeper is out of the hero's FOV
		if (!Dungeon.level.heroFOV[pos]){
			return;
		}

		if (turnsSinceHarmed == -1){
			turnsSinceHarmed = 0;
			yell(Messages.get(this, "warn"));

			//cleanses all harmful blobs in the shop
			ArrayList<Blob> blobs = new ArrayList<>();
			for (Class c : new BlobImmunity().immunities()){
				Blob b = Dungeon.level.blobs.get(c);
				if (b != null && b.volume > 0){
					blobs.add(b);
				}
			}

			PathFinder.buildDistanceMap( pos, BArray.not( Dungeon.level.solid, null ), 4 );

			for (int i=0; i < Dungeon.level.length(); i++) {
				if (PathFinder.distance[i] < Integer.MAX_VALUE) {

					boolean affected = false;
					for (Blob blob : blobs) {
						if (blob.cur[i] > 0) {
							blob.clear(i);
							affected = true;
						}
					}

					if (affected && Dungeon.level.heroFOV[i]) {
						CellEmitter.get( i ).burst( Speck.factory( Speck.DISCOVER ), 2 );
					}

				}
			}

		//There is a 1 turn buffer before more damage/debuffs make the shopkeeper flee
		//This is mainly to prevent stacked effects from causing an instant flee
		} else if (turnsSinceHarmed >= 1) {
			flee();
		}
	}
	
	public void flee() {
		destroy();

		Notes.remove(Notes.Landmark.SHOP);

		if (sprite != null) {
			sprite.killAndErase();
			CellEmitter.get(pos).burst(ElmoParticle.FACTORY, 6);
		}
	}
	
	@Override
	public void destroy() {
		super.destroy();
		for (Heap heap: Dungeon.level.heaps.valueList()) {
			if (heap.type == Heap.Type.FOR_SALE) {
				if (SoulsPixelDungeon.scene() instanceof GameScene) {
					CellEmitter.get(heap.pos).burst(ElmoParticle.FACTORY, 4);
				}
				if (heap.size() == 1) {
					heap.destroy();
				} else {
					heap.items.remove(heap.size()-1);
					heap.type = Heap.Type.HEAP;
				}
			}
		}
	}
	
	@Override
	public boolean reset() {
		return true;
	}

	//shopkeepers are greedy!
	public static int sellPrice(Item item){
		return item.value() * 5 * (Dungeon.depth / 5 + 1);
	}
	
	public static WndBag sell() {
		return GameScene.selectItem( itemSelector );
	}

	public static boolean canSell(Item item){
		if (item.value() <= 0)                                              return false;
		if (item.unique && !item.stackable)                                 return false;
		if (item instanceof Armor && ((Armor) item).checkSeal() != null)    return false;
		if (item.isEquipped(Dungeon.hero) && item.cursed)                   return false;
		return true;
	}

	private static WndBag.ItemSelector itemSelector = new WndBag.ItemSelector() {
		@Override
		public String textPrompt() {
			return Messages.get(Shopkeeper.class, "sell");
		}

		@Override
		public boolean itemSelectable(Item item) {
			return Shopkeeper.canSell(item);
		}

		@Override
		public void onSelect( Item item ) {
			if (item != null) {
				WndBag parentWnd = sell();
				GameScene.show( new WndTradeItem( item, parentWnd ) );
			}
		}
	};

	@Override
	public boolean interact(Char c) {
		if (c != Dungeon.hero) {
			return true;
		}
		Game.runOnRenderThread(new Callback() {
			@Override
			public void call() {
				String[] options = new String[2+ buybackItems.size()];
				int maxLen = PixelScene.landscape() ? 30 : 25;
				int i = 0;
				options[i++] = Messages.get(Shopkeeper.this, "sell");
				options[i++] = Messages.get(Shopkeeper.this, "talk");
				for (Item item : buybackItems){
					options[i] = Messages.get(Heap.class, "for_sale", item.value(), Messages.titleCase(item.title()));
					if (options[i].length() > maxLen) options[i] = options[i].substring(0, maxLen-3) + "...";
					i++;
				}
				GameScene.show(new WndOptions(sprite(), Messages.titleCase(name()), description(), options){
					@Override
					protected void onSelect(int index) {
						super.onSelect(index);
						if (index == 0){
							sell();
						} else if (index == 1){
							GameScene.show(new WndTitledMessage(sprite(), Messages.titleCase(name()), chatText()));
						} else if (index > 1){
							GLog.i(Messages.get(Shopkeeper.this, "buyback"));
							Item returned = buybackItems.remove(index-2);
							Dungeon.souls -= returned.value();
							Statistics.goldCollected -= returned.value();
							if (!returned.doPickUp(Dungeon.hero)){
								Dungeon.level.drop(returned, Dungeon.hero.pos);
							}
						}
					}

					@Override
					protected boolean enabled(int index) {
						if (index > 1){
							return Dungeon.souls >= buybackItems.get(index-2).value();
						} else {
							return super.enabled(index);
						}
					}

					@Override
					protected boolean hasIcon(int index) {
						return index > 1;
					}

					@Override
					protected Image getIcon(int index) {
						if (index > 1){
							return new ItemSprite(buybackItems.get(index-2));
						}
						return null;
					}
				});
			}
		});
		return true;
	}

	public String chatText(){
		if (Dungeon.hero.buff(AscensionChallenge.class) != null){
			return Messages.get(this, "talk_ascent");
		}
		switch (Dungeon.depth){
			case 6: default:
				return Messages.get(this, "talk_prison_intro") + "\n\n" + Messages.get(this, "talk_prison_" + Dungeon.hero.heroClass.name());
			case 11:
				return Messages.get(this, "talk_caves");
			case 16:
				return Messages.get(this, "talk_city");
			case 20:
				return Messages.get(this, "talk_halls");
		}
	}

	public static String BUYBACK_ITEMS = "buyback_items";

	public static String TURNS_SINCE_HARMED = "turns_since_harmed";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(BUYBACK_ITEMS, buybackItems);
		bundle.put(TURNS_SINCE_HARMED, turnsSinceHarmed);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		buybackItems.clear();
		if (bundle.contains(BUYBACK_ITEMS)){
			for (Bundlable i : bundle.getCollection(BUYBACK_ITEMS)){
				buybackItems.add((Item) i);
			}
		}
		turnsSinceHarmed = bundle.contains(TURNS_SINCE_HARMED) ? bundle.getInt(TURNS_SINCE_HARMED) : -1;
	}
}
