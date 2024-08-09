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
import com.soulspixel.soulspixeldungeon.effects.Speck;
import com.soulspixel.soulspixeldungeon.effects.particles.ShaftParticle;
import com.soulspixel.soulspixeldungeon.ui.Window;
import com.watabou.glwrap.Blending;
import com.watabou.noosa.TextureFilm;

public class BonfireSprite extends MobSprite {

	public BonfireSprite() {
		super();
		
		texture( Assets.Sprites.BONFIRE );
		
		TextureFilm frames = new TextureFilm( texture, 16, 16 );
		
		idle = new Animation( 5, true );
		idle.frames( frames, 0, 1, 2, 3, 4, 5 );
		
		run = new Animation( 5, true );
		run.frames( frames, 0, 1, 2, 3, 4, 5 );

		attack = new Animation( 5, false );
		attack.frames( frames, 0, 1, 2, 3, 4, 5 );

		die = new Animation( 5, false );
		die.frames( frames, 0, 1, 2, 3, 4, 5 );
		
		play( idle );
	}
	
	@Override
	public void draw() {
		Blending.setLightMode();
		super.draw();
		Blending.setNormalMode();
	}
	
	@Override
	public void die() {
		super.die();
		emitter().start( ShaftParticle.FACTORY, 0.3f, 4 );
		emitter().start( Speck.factory( Speck.LIGHT ), 0.2f, 3 );
	}
	
	@Override
	public int blood() {
		return Window.SPX_COLOR;
	}
}
