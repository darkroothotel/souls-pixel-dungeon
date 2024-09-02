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

import com.soulspixel.soulspixeldungeon.Dungeon;
import com.soulspixel.soulspixeldungeon.SPDSettings;
import com.soulspixel.soulspixeldungeon.actors.hero.Hero;
import com.soulspixel.soulspixeldungeon.scenes.GameScene;
import com.soulspixel.soulspixeldungeon.ui.BuffIndicator;
import com.watabou.utils.Bundle;

public class Silenced extends FlavourBuff {

	public static final float DURATION = 10f;

	private boolean wasTurnedOff = false;

	private static final String TURNED_OFF    = "turned_off";

	{
		type = buffType.NEGATIVE;
	}
	
	@Override
	public int icon() {
		return BuffIndicator.SILENCED;
	}

	@Override
	public float iconFadePercent() {
		return Math.max(0, (DURATION - visualcooldown()) / DURATION);
	}

	@Override
	protected void onAdd() {
		if(target instanceof Hero){
			if(SPDSettings.soundFx() && SPDSettings.music()){
				wasTurnedOff = true;
				SPDSettings.soundFx(false);
				SPDSettings.music(false);
			}
		}
		super.onAdd();
	}

	@Override
	protected void onRemove() {
		if(wasTurnedOff){
			wasTurnedOff = false;
			SPDSettings.soundFx(true);
			SPDSettings.music(true);
		}
		super.onRemove();
	}

	@Override
	public void detach() {
		super.detach();
		Dungeon.observe();
		GameScene.updateFog();
	}

	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );
		bundle.put( TURNED_OFF, wasTurnedOff );
	}

	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		wasTurnedOff = bundle.getBoolean( TURNED_OFF );
	}
}
