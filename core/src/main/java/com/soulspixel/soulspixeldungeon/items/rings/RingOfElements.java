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

package com.soulspixel.soulspixeldungeon.items.rings;

import com.soulspixel.soulspixeldungeon.Dungeon;
import com.soulspixel.soulspixeldungeon.actors.Char;
import com.soulspixel.soulspixeldungeon.actors.blobs.Electricity;
import com.soulspixel.soulspixeldungeon.actors.blobs.ToxicGas;
import com.soulspixel.soulspixeldungeon.actors.buffs.Burning;
import com.soulspixel.soulspixeldungeon.actors.buffs.Chill;
import com.soulspixel.soulspixeldungeon.actors.buffs.Corrosion;
import com.soulspixel.soulspixeldungeon.actors.buffs.Frost;
import com.soulspixel.soulspixeldungeon.actors.buffs.Ooze;
import com.soulspixel.soulspixeldungeon.actors.buffs.Paralysis;
import com.soulspixel.soulspixeldungeon.actors.buffs.Poison;
import com.soulspixel.soulspixeldungeon.items.armor.glyphs.AntiMagic;
import com.soulspixel.soulspixeldungeon.messages.Messages;
import com.soulspixel.soulspixeldungeon.sprites.ItemSpriteSheet;

import java.util.HashSet;

public class RingOfElements extends Ring {

	{
		icon = ItemSpriteSheet.Icons.RING_ELEMENTS;
	}

	public String statsInfo() {
		if (isIdentified()){
			String info = Messages.get(this, "stats",
					Messages.decimalFormat("#.##", 100f * (1f - Math.pow(0.825f, soloBuffedBonus()))));
			if (isEquipped(Dungeon.hero) && soloBuffedBonus() != combinedBuffedBonus(Dungeon.hero)){
				info += "\n\n" + Messages.get(this, "combined_stats",
						Messages.decimalFormat("#.##", 100f * (1f - Math.pow(0.825f, combinedBuffedBonus(Dungeon.hero)))));
			}
			return info;
		} else {
			return Messages.get(this, "typical_stats", Messages.decimalFormat("#.##", 17.5f));
		}
	}
	
	@Override
	protected RingBuff buff( ) {
		return new Resistance();
	}

	public static final HashSet<Class> RESISTS = new HashSet<>();
	static {
		RESISTS.add( Burning.class );
		RESISTS.add( Chill.class );
		RESISTS.add( Frost.class );
		RESISTS.add( Ooze.class );
		RESISTS.add( Paralysis.class );
		RESISTS.add( Poison.class );
		RESISTS.add( Corrosion.class );

		RESISTS.add( ToxicGas.class );
		RESISTS.add( Electricity.class );

		RESISTS.addAll( AntiMagic.RESISTS );
	}
	
	public static float resist( Char target, Class effect ){
		if (getBuffedBonus(target, Resistance.class) == 0) return 1f;
		
		for (Class c : RESISTS){
			if (c.isAssignableFrom(effect)){
				return (float)Math.pow(0.825, getBuffedBonus(target, Resistance.class));
			}
		}
		
		return 1f;
	}
	
	public class Resistance extends RingBuff {
	
	}
}
