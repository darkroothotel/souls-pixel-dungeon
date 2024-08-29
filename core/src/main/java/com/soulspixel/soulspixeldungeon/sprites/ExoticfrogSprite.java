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

public class ExoticfrogSprite extends FrogSprite {

	public ExoticfrogSprite() {
		super();
		
		texture( Assets.Sprites.FROG );
		
		TextureFilm frames = new TextureFilm( texture, 16, 15 );

		idle = new Animation( 2, true );
		idle.frames( frames, 32, 32, 32, 33);

		run = new Animation( 10, true );
		run.frames( frames, 38, 39, 40, 41, 42);

		attack = new Animation( 15, false );
		attack.frames( frames, 34, 35, 36, 37);

		die = new Animation( 10, false );
		die.frames( frames, 43, 44, 45, 46);

		prep = new Animation( 1, true );
		prep.frames( frames, 42);

		stab = new Animation( 15, false );
		stab.frames( frames, 34, 35, 34, 36, 37, 36, 37, 32);

		leap = new Animation( 1, true );
		leap.frames( frames, 40);
		
		play( idle );
	}
}
