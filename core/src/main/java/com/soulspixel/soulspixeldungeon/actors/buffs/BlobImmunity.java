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

package com.soulspixel.soulspixeldungeon.actors.buffs;

import com.soulspixel.soulspixeldungeon.actors.blobs.Blizzard;
import com.soulspixel.soulspixeldungeon.actors.blobs.ConfusionGas;
import com.soulspixel.soulspixeldungeon.actors.blobs.CorrosiveGas;
import com.soulspixel.soulspixeldungeon.actors.blobs.Electricity;
import com.soulspixel.soulspixeldungeon.actors.blobs.Fire;
import com.soulspixel.soulspixeldungeon.actors.blobs.Freezing;
import com.soulspixel.soulspixeldungeon.actors.blobs.Inferno;
import com.soulspixel.soulspixeldungeon.actors.blobs.ParalyticGas;
import com.soulspixel.soulspixeldungeon.actors.blobs.Regrowth;
import com.soulspixel.soulspixeldungeon.actors.blobs.SmokeScreen;
import com.soulspixel.soulspixeldungeon.actors.blobs.StenchGas;
import com.soulspixel.soulspixeldungeon.actors.blobs.StormCloud;
import com.soulspixel.soulspixeldungeon.actors.blobs.ToxicGas;
import com.soulspixel.soulspixeldungeon.actors.blobs.Web;
import com.soulspixel.soulspixeldungeon.actors.mobs.Tengu;
import com.soulspixel.soulspixeldungeon.levels.rooms.special.MagicalFireRoom;
import com.soulspixel.soulspixeldungeon.ui.BuffIndicator;

public class BlobImmunity extends FlavourBuff {
	
	{
		type = buffType.POSITIVE;
	}
	
	public static final float DURATION	= 20f;
	
	@Override
	public int icon() {
		return BuffIndicator.IMMUNITY;
	}

	@Override
	public float iconFadePercent() {
		return Math.max(0, (DURATION - visualcooldown()) / DURATION);
	}

	{
		//all harmful blobs
		immunities.add( Blizzard.class );
		immunities.add( ConfusionGas.class );
		immunities.add( CorrosiveGas.class );
		immunities.add( Electricity.class );
		immunities.add( Fire.class );
		immunities.add( MagicalFireRoom.EternalFire.class );
		immunities.add( Freezing.class );
		immunities.add( Inferno.class );
		immunities.add( ParalyticGas.class );
		immunities.add( Regrowth.class );
		immunities.add( SmokeScreen.class );
		immunities.add( StenchGas.class );
		immunities.add( StormCloud.class );
		immunities.add( ToxicGas.class );
		immunities.add( Web.class );

		immunities.add(Tengu.FireAbility.FireBlob.class);
	}

}
