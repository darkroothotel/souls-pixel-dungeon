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

package com.soulspixel.soulspixeldungeon.effects.particles;

import com.watabou.noosa.particles.Emitter;
import com.watabou.noosa.particles.Emitter.Factory;
import com.watabou.noosa.particles.PixelParticle;
import com.watabou.utils.Random;

public class BonfireLightShaftParticle extends PixelParticle {

	public static final Factory FACTORY = new Factory() {
		@Override
		public void emit( Emitter emitter, int index, float x, float y ) {
			((BonfireLightShaftParticle)emitter.recycle( BonfireLightShaftParticle.class )).reset( x, y );
		}
		@Override
		public boolean lightMode() {
			return true;
		}
	};

	public static final Factory FACTORY_SCENE = new Factory() {
		@Override
		public void emit( Emitter emitter, int index, float x, float y ) {
			((BonfireLightShaftParticle)emitter.recycle( BonfireLightShaftParticle.class )).reset( x, y, true );
		}
		@Override
		public boolean lightMode() {
			return true;
		}
	};


	public BonfireLightShaftParticle() {
		super();
		
		lifespan = 1.2f;
		speed.set( 0, -6 );

		color(0xFFFF00);
	}
	
	private float offs;
	
	public void reset( float x, float y ) {
		revive();
		
		this.x = x;
		this.y = y;
		
		offs = -Random.Float( lifespan );
		left = lifespan - offs;
	}

    public void reset( float x, float y, boolean offsetX ) {
		revive();

		offs = -Random.Float( lifespan );
		left = lifespan - offs;

		if(offsetX){
            this.x = x+Random.Float(-10f, 10f);
        } else {
			this.x = x;
        }
        this.y = y;
    }
	
	@Override
	public void update() {
		super.update();
		
		float p = left / lifespan;
		am = p < 0.5f ? p : 1 - p;
		scale.x = (1 - p) * 4;
		scale.y = 16 + (1 - p) * 16;
	}
}