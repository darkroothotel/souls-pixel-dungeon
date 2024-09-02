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

package com.soulspixel.soulspixeldungeon.items.armor;

import com.soulspixel.soulspixeldungeon.actors.Char;
import com.soulspixel.soulspixeldungeon.sprites.ItemSpriteSheet;

import java.util.ArrayList;
import java.util.Arrays;

public class ClothArmor extends Armor {

	{
		image = ItemSpriteSheet.ARMOR_CLOTH;

		bones = false; //Finding them in bones would be semi-frequent and disappointing.

		damageTypeWeak = new ArrayList<Char.DamageType>(){{
			add(Char.DamageType.SLASH);
			add(Char.DamageType.PIERCE);
			add(Char.DamageType.STAB);
			add(Char.DamageType.FIRE);
		}};
	}
	
	public ClothArmor() {
		super( 1 );
	}

}
