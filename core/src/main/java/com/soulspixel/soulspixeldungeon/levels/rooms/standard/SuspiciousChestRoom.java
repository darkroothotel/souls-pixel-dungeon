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

package com.soulspixel.soulspixeldungeon.levels.rooms.standard;

import com.soulspixel.soulspixeldungeon.actors.mobs.Mimic;
import com.soulspixel.soulspixeldungeon.items.Heap;
import com.soulspixel.soulspixeldungeon.items.Item;
import com.soulspixel.soulspixeldungeon.items.Soul;
import com.soulspixel.soulspixeldungeon.items.trinkets.MimicTooth;
import com.soulspixel.soulspixeldungeon.levels.Level;
import com.soulspixel.soulspixeldungeon.levels.Terrain;
import com.soulspixel.soulspixeldungeon.levels.painters.Painter;
import com.watabou.utils.Random;

public class SuspiciousChestRoom extends StandardRoom {

	@Override
	public int minWidth() {
		return Math.max(5, super.minWidth());
	}

	@Override
	public int minHeight() {
		return Math.max(5, super.minHeight());
	}

	@Override
	public void paint(Level level) {
		Painter.fill( level, this, Terrain.WALL );
		Painter.fill( level, this, 1 , Terrain.EMPTY );

		for (Door door : connected.values()) {
			door.set( Door.Type.REGULAR );
		}

		Item i = level.findPrizeItem();

		if ( i == null ){
			i = new Soul().random();
		}

		int center = level.pointToCell(center());

		Painter.set(level, center, Terrain.PEDESTAL);

		float mimicChance = 1/3f * MimicTooth.mimicChanceMultiplier();
		if (Random.Float() < mimicChance) {
			level.mobs.add(Mimic.spawnAt(center, i));
		} else {
			level.drop(i, center).type = Heap.Type.CHEST;
		}
	}
}
