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

package com.soulspixel.soulspixeldungeon.levels.rooms.special;

import com.soulspixel.soulspixeldungeon.levels.Level;
import com.soulspixel.soulspixeldungeon.levels.Terrain;
import com.soulspixel.soulspixeldungeon.levels.features.LevelTransition;
import com.soulspixel.soulspixeldungeon.levels.painters.Painter;

public class SecretExitRoom extends SpecialRoom {

	@Override
	public int maxHeight() {
		return 5;
	}

	@Override
	public int maxWidth() {
		return 5;
	}

	public void paint(Level level ) {

		Painter.fill( level, this, Terrain.WALL );
		Painter.fill( level, this, 1, Terrain.EMPTY );

		for (Door door : connected.values()) {
			door.set( Door.Type.EMPTY );
		}

		int p = level.pointToCell(center());
		Painter.set(level, p, Terrain.SECRET_EXIT);
		entrance().set(Door.Type.HIDDEN);

		level.transitions.add(new LevelTransition(level, p, LevelTransition.Type.SECRET_EXIT));
	}
}
