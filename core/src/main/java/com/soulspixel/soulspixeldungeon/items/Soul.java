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

package com.soulspixel.soulspixeldungeon.items;

import com.soulspixel.soulspixeldungeon.Assets;
import com.soulspixel.soulspixeldungeon.Badges;
import com.soulspixel.soulspixeldungeon.Dungeon;
import com.soulspixel.soulspixeldungeon.Statistics;
import com.soulspixel.soulspixeldungeon.actors.hero.Hero;
import com.soulspixel.soulspixeldungeon.effects.FloatingText;
import com.soulspixel.soulspixeldungeon.messages.Messages;
import com.soulspixel.soulspixeldungeon.scenes.GameScene;
import com.soulspixel.soulspixeldungeon.sprites.CharSprite;
import com.soulspixel.soulspixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Soul extends Item {

	{
		image = ItemSpriteSheet.SMALL_SOUL;
		stackable = true;
	}

	private static final Map<Integer, Integer> imageMap = new TreeMap<>();

	static {
		imageMap.put(0, ItemSpriteSheet.SMALL_SOUL);      // For values <= 800
		imageMap.put(801, ItemSpriteSheet.MEDIUM_SOUL);   // For values > 800
	}

	private static final Map<Integer, String> nameMap = new TreeMap<>();

	static {
		nameMap.put(ItemSpriteSheet.SMALL_SOUL, Messages.get(Soul.class, "name_0"));
		nameMap.put(ItemSpriteSheet.MEDIUM_SOUL, Messages.get(Soul.class, "name_1"));
	}

	private static final Map<Integer, String> descMap = new TreeMap<>();

	static {
		descMap.put(ItemSpriteSheet.SMALL_SOUL, Messages.get(Soul.class, "desc_0"));
		descMap.put(ItemSpriteSheet.MEDIUM_SOUL, Messages.get(Soul.class, "desc_1"));
	}

	private Integer floorKey(int value) {
		Integer closestKey = null;
		for (Integer key : imageMap.keySet()) {
			if (key <= value) {
				closestKey = key;
			} else {
				break;
			}
		}
		return closestKey;
	}

	private Integer getImageForValue(int value) {
		Integer floorKey = floorKey(value);
		if (floorKey != null) {
			return imageMap.get(floorKey);
		}
		return ItemSpriteSheet.SMALL_SOUL;
	}
	
	public Soul() {
		this( 1 );
	}
	
	public Soul(int value ) {
		this.quantity = value;
		this.image = getImageForValue(quantity);
	}
	
	@Override
	public ArrayList<String> actions( Hero hero ) {
		return new ArrayList<>();
	}
	
	@Override
	public boolean doPickUp(Hero hero, int pos) {
		
		Dungeon.souls += quantity;
		Statistics.soulsCollected += quantity;
		Badges.validateGoldCollected();

		GameScene.pickUp( this, pos );
		hero.sprite.showStatusWithIcon( CharSprite.NEUTRAL, Integer.toString(quantity), FloatingText.SOUL);
		hero.spendAndNext( TIME_TO_PICK_UP );
		
		Sample.INSTANCE.play( Assets.Sounds.BURNING, 1, 1, Random.Float( 0.9f, 1.1f ) );
		updateQuickslot();
		
		return true;
	}
	
	@Override
	public boolean isUpgradable() {
		return false;
	}
	
	@Override
	public boolean isIdentified() {
		return true;
	}
	
	@Override
	public Item random() {
		quantity = Random.IntRange( 30 + Dungeon.depth * 100, 60 + Dungeon.depth * 200 );
		return this;
	}

	@Override
	public String name() {
		return nameMap.get(image);
	}

	@Override
	public String desc() {
		return descMap.get(image);
	}
}
