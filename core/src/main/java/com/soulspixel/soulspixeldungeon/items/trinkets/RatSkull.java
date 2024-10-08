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

package com.soulspixel.soulspixeldungeon.items.trinkets;

import com.soulspixel.soulspixeldungeon.messages.Messages;
import com.soulspixel.soulspixeldungeon.sprites.ItemSpriteSheet;

public class RatSkull extends Trinket {

	{
		image = ItemSpriteSheet.RAT_SKULL;
	}

	@Override
	protected int upgradeEnergyCost() {
		//5 -> 8(13) -> 10(23) -> 12(35)
		return 6+2*level();
	}

	@Override
	public String desc() {
		return Messages.get(this, "desc", (int)(exoticChanceMultiplier(buffedLvl())));
	}

	public static float exoticChanceMultiplier(){
		return exoticChanceMultiplier(trinketLevel(RatSkull.class));
	}

	public static float exoticChanceMultiplier( int level ){
		if (level == -1){
			return 1f;
		} else {
			return 2f + 1f*level;
		}
	}

}
