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

package com.soulspixel.soulspixeldungeon.levels.rooms.standard.entrance;

import com.soulspixel.soulspixeldungeon.Dungeon;
import com.soulspixel.soulspixeldungeon.levels.Level;
import com.soulspixel.soulspixeldungeon.levels.Terrain;
import com.soulspixel.soulspixeldungeon.levels.features.LevelTransition;
import com.soulspixel.soulspixeldungeon.levels.painters.Painter;
import com.soulspixel.soulspixeldungeon.levels.rooms.standard.PillarsRoom;
import com.watabou.utils.PathFinder;

public class PillarsEntranceRoom extends PillarsRoom {

	@Override
	public float[] sizeCatProbs() {
		return new float[]{3, 1, 0};
	}

	@Override
	public boolean isEntrance() {
		return true;
	}

	@Override
	public void paint(Level level) {
		super.paint(level);

		int entrance;
		boolean valid;
		do {
			entrance = level.pointToCell(random(2));
			valid = true;

			for (int i : PathFinder.NEIGHBOURS4){
				if (i == -level.width()) continue;
				if (level.map[entrance+i] == Terrain.WALL){
					valid = false;
				}
			}

		} while (level.findMob(entrance) != null || level.map[entrance] == Terrain.WALL || !valid);
		if(!Dungeon.hasNoEntrance()){
			Painter.set( level, entrance, Terrain.ENTRANCE );
			level.transitions.add(new LevelTransition(level, entrance, LevelTransition.Type.REGULAR_ENTRANCE));
		}
	}
}
