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

import com.soulspixel.soulspixeldungeon.Badges;
import com.soulspixel.soulspixeldungeon.Statistics;
import com.soulspixel.soulspixeldungeon.actors.buffs.Degrade;
import com.soulspixel.soulspixeldungeon.actors.hero.Hero;
import com.soulspixel.soulspixeldungeon.items.Item;
import com.soulspixel.soulspixeldungeon.items.armor.Armor;
import com.soulspixel.soulspixeldungeon.items.potions.PotionOfHealing;
import com.soulspixel.soulspixeldungeon.items.potions.PotionOfLiquidFlame;
import com.soulspixel.soulspixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.soulspixel.soulspixeldungeon.items.wands.Wand;
import com.soulspixel.soulspixeldungeon.items.weapon.Weapon;
import com.soulspixel.soulspixeldungeon.messages.Messages;
import com.soulspixel.soulspixeldungeon.sprites.ItemSpriteSheet;
import com.soulspixel.soulspixeldungeon.utils.GLog;

public class PotionOfEmber extends Spell {
	
	{
		image = ItemSpriteSheet.POTION_OF_EMBER;

		unique = true;

		talentFactor = 2;
	}

	@Override
	public int value() {
		return 200 * quantity;
	}

	@Override
	public int energyVal() {
		return 30 * quantity;
	}

	@Override
	protected void onCast(Hero hero) {

	}

	public static class Recipe extends com.soulspixel.soulspixeldungeon.items.Recipe.SimpleRecipe {
		
		{
			inputs =  new Class[]{PotionOfHealing.class, PotionOfLiquidFlame.class};
			inQuantity = new int[]{1, 1};
			
			cost = 12;
			
			output = PotionOfEmber.class;
			outQuantity = 1;
		}
		
	}
}
