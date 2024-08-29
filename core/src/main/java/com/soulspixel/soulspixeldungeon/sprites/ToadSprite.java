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
import com.watabou.utils.Random;

public class ToadSprite extends FrogSprite {

	public ToadSprite() {
		super();
		
		texture( Assets.Sprites.FROG );
		
		TextureFilm frames = new TextureFilm( texture, 16, 15 );

		idle = new Animation( 2, true );
		idle.frames( frames, 16, 16, 16, 17 );

		run = new Animation( 10, true );
		run.frames( frames, 22, 23, 24, 25, 26 );

		attack = new Animation( 15, false );
		attack.frames( frames, 18, 19, 20, 21 );

		die = new Animation( 10, false );
		die.frames( frames, 27, 28, 29, 30 );

		prep = new Animation( 1, true );
		prep.frames( frames, 26 );

		stab = new Animation( 15, false );
		stab.frames( frames, 18, 19, 18, 20, 21, 20, 21, 16 );

		leap = new Animation( 1, true );
		leap.frames( frames, 24);
		
		play( idle );
	}
}
