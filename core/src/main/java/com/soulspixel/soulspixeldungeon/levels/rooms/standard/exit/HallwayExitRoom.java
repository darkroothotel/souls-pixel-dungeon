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

package com.soulspixel.soulspixeldungeon.levels.rooms.standard.exit;

import com.soulspixel.soulspixeldungeon.Dungeon;
import com.soulspixel.soulspixeldungeon.levels.Level;
import com.soulspixel.soulspixeldungeon.levels.Terrain;
import com.soulspixel.soulspixeldungeon.levels.features.LevelTransition;
import com.soulspixel.soulspixeldungeon.levels.painters.Painter;
import com.soulspixel.soulspixeldungeon.levels.rooms.standard.HallwayRoom;
import com.watabou.utils.Point;

public class HallwayExitRoom extends HallwayRoom {

	@Override
	public boolean isExit() {
		return true;
	}

	@Override
	public void paint(Level level) {
		super.paint(level);

		int exit = -1;
		for ( Point p : getPoints()){
			if (level.map[level.pointToCell(p)] == Terrain.STATUE_SP){
				exit = level.pointToCell(p);
				break;
			}
		}
		if(!Dungeon.hasNoExit()){
			Painter.set( level, exit, Terrain.EXIT );
			level.transitions.add(new LevelTransition(level, exit, LevelTransition.Type.REGULAR_EXIT));
		}

	}

}
