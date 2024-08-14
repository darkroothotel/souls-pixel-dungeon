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

import com.soulspixel.soulspixeldungeon.actors.hero.Hero;
import com.soulspixel.soulspixeldungeon.items.potions.PotionOfHealing;
import com.soulspixel.soulspixeldungeon.items.potions.PotionOfLiquidFlame;
import com.soulspixel.soulspixeldungeon.sprites.ItemSpriteSheet;

public class SpellOfEmber extends Spell {
	
	{
		image = ItemSpriteSheet.SPELL_OF_EMBER;

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
		hero.undoUndead();
		PotionOfHealing.heal(hero);
	}

	public static class Recipe extends com.soulspixel.soulspixeldungeon.items.Recipe.SimpleRecipe {
		
		{
			inputs =  new Class[]{PotionOfHealing.class, PotionOfLiquidFlame.class};
			inQuantity = new int[]{1, 1};
			
			cost = 12;
			
			output = SpellOfEmber.class;
			outQuantity = 1;
		}
		
	}
}
