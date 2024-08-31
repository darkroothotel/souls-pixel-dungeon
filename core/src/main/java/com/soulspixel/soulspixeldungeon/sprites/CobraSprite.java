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

package com.soulspixel.soulspixeldungeon.sprites;

import com.soulspixel.soulspixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class CobraSprite extends MobSprite {

	public CobraSprite() {
		super();
		
		texture( Assets.Sprites.SNAKE );
		
		TextureFilm frames = new TextureFilm( texture, 12, 11 );
		
		//many frames here as we want the rising/falling to be slow but the tongue to be fast
		idle = new Animation( 10, true );
		idle.frames( frames, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14,
		                     15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 16, 17, 16, 15, 15);
		
		run = new Animation( 8, true );
		run.frames( frames, 4+14, 5+14, 6+14, 7+14 );
		
		attack = new Animation( 15, false );
		attack.frames( frames, 8+14, 9+14, 10+14, 9+14, 0+14);
		
		die = new Animation( 10, false );
		die.frames( frames, 11+14, 12+14, 13+14 );
		
		play(idle);
	}
	
}
