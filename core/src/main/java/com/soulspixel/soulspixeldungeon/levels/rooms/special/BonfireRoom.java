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

import com.soulspixel.soulspixeldungeon.Dungeon;
import com.soulspixel.soulspixeldungeon.actors.blobs.Foliage;
import com.soulspixel.soulspixeldungeon.actors.mobs.Piranha;
import com.soulspixel.soulspixeldungeon.actors.mobs.npcs.Bonfire;
import com.soulspixel.soulspixeldungeon.levels.Level;
import com.soulspixel.soulspixeldungeon.levels.Terrain;
import com.soulspixel.soulspixeldungeon.levels.painters.Painter;
import com.soulspixel.soulspixeldungeon.plants.BlandfruitBush;
import com.soulspixel.soulspixeldungeon.plants.Sungrass;
import com.watabou.utils.Random;

public class BonfireRoom extends SpecialRoom {

	@Override
	public int minWidth() {
		return 3;
	}

	@Override
	public int minHeight() {
		return 3;
	}

	public void paint( Level level ) {

		Painter.fill( level, this, Terrain.WALL );
		Painter.fill( level, this, 1, Terrain.EMPTY );
		Painter.fill( level, this, 2, Terrain.GRASS );

		for (Door door : connected.values()) {
			door.set( Door.Type.EMPTY );
		}
		
		Foliage light = (Foliage)level.blobs.get( Foliage.class );
		if (light == null) {
			light = new Foliage();
		}
		for (int i=top + 1; i < bottom; i++) {
			for (int j=left + 1; j < right; j++) {
				light.seed( level, j + level.width() * i, 1 );
			}
		}
		level.blobs.put( Foliage.class, light );

		Bonfire bonfire = new Bonfire();
		bonfire.pos = level.pointToCell(center());
		bonfire.spawn(Dungeon.depth);
		if(level.map[bonfire.pos] == Terrain.WATER || level.findMob( bonfire.pos ) != null){
			do {
				bonfire.pos = level.pointToCell(random());
			} while (level.map[bonfire.pos] == Terrain.WATER || level.findMob( bonfire.pos ) != null);
		}
		level.mobs.add( bonfire );
	}
}
