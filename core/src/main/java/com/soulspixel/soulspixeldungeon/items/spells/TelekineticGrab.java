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

package com.soulspixel.soulspixeldungeon.items.spells;

import com.soulspixel.soulspixeldungeon.Assets;
import com.soulspixel.soulspixeldungeon.Dungeon;
import com.soulspixel.soulspixeldungeon.actors.Actor;
import com.soulspixel.soulspixeldungeon.actors.Char;
import com.soulspixel.soulspixeldungeon.actors.buffs.PinCushion;
import com.soulspixel.soulspixeldungeon.actors.hero.Hero;
import com.soulspixel.soulspixeldungeon.actors.mobs.DwarfKing;
import com.soulspixel.soulspixeldungeon.effects.MagicMissile;
import com.soulspixel.soulspixeldungeon.items.Heap;
import com.soulspixel.soulspixeldungeon.items.Item;
import com.soulspixel.soulspixeldungeon.items.LiquidMetal;
import com.soulspixel.soulspixeldungeon.mechanics.Ballistica;
import com.soulspixel.soulspixeldungeon.messages.Messages;
import com.soulspixel.soulspixeldungeon.sprites.ItemSpriteSheet;
import com.soulspixel.soulspixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;

public class TelekineticGrab extends TargetedSpell {

	{
		image = ItemSpriteSheet.TELE_GRAB;

		talentChance = 1/(float)Recipe.OUT_QUANTITY;
	}

	@Override
	protected void fx(Ballistica bolt, Callback callback) {
		MagicMissile.boltFromChar( curUser.sprite.parent,
				MagicMissile.BEACON,
				curUser.sprite,
				bolt.collisionPos,
				callback);
		Sample.INSTANCE.play( Assets.Sounds.ZAP );
	}

	@Override
	protected void affectTarget(Ballistica bolt, Hero hero) {
		Char ch = Actor.findChar(bolt.collisionPos);

		//special logic for DK when he is on his throne
		if (ch == null && bolt.path.size() > bolt.dist+1){
			ch = Actor.findChar(bolt.path.get(bolt.dist+1));
			if (!(ch instanceof DwarfKing && Dungeon.level.solid[ch.pos])){
				ch = null;
			}
		}

		if (ch != null && ch.buff(PinCushion.class) != null){

			while (ch.buff(PinCushion.class) != null) {
				Item item = ch.buff(PinCushion.class).grabOne();

				if (item.doPickUp(hero, ch.pos)) {
					hero.spend(-Item.TIME_TO_PICK_UP); //casting the spell already takes a turn
					GLog.i( Messages.capitalize(Messages.get(hero, "you_now_have", item.name())) );

				} else {
					GLog.w(Messages.get(this, "cant_grab"));
					Dungeon.level.drop(item, ch.pos).sprite.drop();
					return;
				}

			}

		} else if (Dungeon.level.heaps.get(bolt.collisionPos) != null){

			Heap h = Dungeon.level.heaps.get(bolt.collisionPos);

			if (h.type != Heap.Type.HEAP){
				GLog.w(Messages.get(this, "cant_grab"));
				h.sprite.drop();
				return;
			}

			while (!h.isEmpty()) {
				Item item = h.peek();
				if (item.doPickUp(hero, h.pos)) {
					h.pickUp();
					hero.spend(-Item.TIME_TO_PICK_UP); //casting the spell already takes a turn
					GLog.i( Messages.capitalize(Messages.get(hero, "you_now_have", item.name())) );

				} else {
					GLog.w(Messages.get(this, "cant_grab"));
					h.sprite.drop();
					return;
				}
			}

		} else {
			GLog.w(Messages.get(this, "no_target"));
		}

	}

	@Override
	public int value() {
		return (int)(60 * (quantity/(float)Recipe.OUT_QUANTITY));
	}

	@Override
	public int energyVal() {
		return (int)(12 * (quantity/(float)Recipe.OUT_QUANTITY));
	}

	public static class Recipe extends com.soulspixel.soulspixeldungeon.items.Recipe.SimpleRecipe {

		private static final int OUT_QUANTITY = 8;

		{
			inputs =  new Class[]{LiquidMetal.class};
			inQuantity = new int[]{10};

			cost = 10;

			output = TelekineticGrab.class;
			outQuantity = OUT_QUANTITY;
		}

	}

}
