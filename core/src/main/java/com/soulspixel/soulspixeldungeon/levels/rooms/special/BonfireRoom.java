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

import com.soulspixel.soulspixeldungeon.Assets;
import com.soulspixel.soulspixeldungeon.Dungeon;
import com.soulspixel.soulspixeldungeon.actors.blobs.BonfireLight;
import com.soulspixel.soulspixeldungeon.actors.mobs.npcs.Bonfire;
import com.soulspixel.soulspixeldungeon.levels.Level;
import com.soulspixel.soulspixeldungeon.levels.Terrain;
import com.soulspixel.soulspixeldungeon.levels.painters.Painter;
import com.soulspixel.soulspixeldungeon.levels.rooms.quest.RitualSiteRoom;
import com.soulspixel.soulspixeldungeon.messages.Messages;
import com.soulspixel.soulspixeldungeon.tiles.CustomTilemap;
import com.watabou.noosa.Tilemap;
import com.watabou.utils.Point;

public class BonfireRoom extends SpecialRoom {

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

		BonfireLight light = (BonfireLight)level.blobs.get( BonfireLight.class );
		if (light == null) {
			light = new BonfireLight();
		}
		Bonfire bonfire = new Bonfire();
		bonfire.pos = level.pointToCell(center());
		bonfire.spawn(Dungeon.depth);
		if(level.map[bonfire.pos] == Terrain.WATER || level.findMob( bonfire.pos ) != null){
			do {
				bonfire.pos = level.pointToCell(random());
			} while (level.map[bonfire.pos] == Terrain.WATER || level.findMob( bonfire.pos ) != null);
		}
		level.mobs.add( bonfire );
		for (int i=top + 1; i < bottom; i++) {
			for (int j=left + 1; j < right; j++) {
				light.seed( level, j + level.width() * i, 1 );
			}
		}
		level.blobs.put( BonfireLight.class, light );

		BonfireTiles bv = new BonfireTiles();
		bv.pos(left+1, top);

		level.customTiles.add(bv);
	}

	public static class BonfireTiles extends CustomTilemap {

		{
			switch (Dungeon.depth){
				default:
				case 1:
					texture = Assets.Environment.BONFIRE_FLOOR_1;
					break;
			}
			tileW = 3;
			tileH = 4;
		}

		final int TEX_WIDTH = 48;

		@Override
		public Tilemap create() {
			Tilemap v = super.create();
			v.map(mapSimpleImage(0, 0, TEX_WIDTH), 3);
			return v;
		}

		@Override
		public String name(int tileX, int tileY) {
			return Messages.get(this, "name");
		}

		@Override
		public String desc(int tileX, int tileY) {
			return Messages.get(this, "desc");
		}
	}
}
