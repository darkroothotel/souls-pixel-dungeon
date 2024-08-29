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
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class FrogSprite extends MobSprite {

	public Animation stab;
	public Animation prep;
	public Animation leap;

	private boolean alt = Random.Int(2) == 0;

	public FrogSprite() {
		super();
		
		texture( Assets.Sprites.FROG );
		
		TextureFilm frames = new TextureFilm( texture, 16, 15 );
		
		idle = new Animation( 2, true );
		idle.frames( frames, 0, 0, 0, 1 );
		
		run = new Animation( 10, true );
		run.frames( frames, 6, 7, 8, 9, 10 );
		
		attack = new Animation( 15, false );
		attack.frames( frames, 2, 3, 4, 5, 0 );
		
		die = new Animation( 10, false );
		die.frames( frames, 11, 12, 13, 14 );

		prep = new Animation( 1, true );
		prep.frames( frames, 10 );

		stab = new Animation( 15, false );
		stab.frames( frames, 2, 3, 2, 4, 5, 4, 5, 0 );

		leap = new Animation( 1, true );
		leap.frames( frames, 8 );
		
		play( idle );
	}

	public void leapPrep( int cell ){
		turnTo( ch.pos, cell );
		play( prep );
	}

	@Override
	public void jump( int from, int to, float height, float duration,  Callback callback ) {
		super.jump( from, to, height, duration, callback );
		play( leap );
	}

	@Override
	public void attack( int cell ) {
		super.attack( cell );
		if (alt) {
			play( stab );
		}
		alt = !alt;
	}

	@Override
	public void onComplete( Animation anim ) {
		super.onComplete( anim == stab ? attack : anim );
	}
}
