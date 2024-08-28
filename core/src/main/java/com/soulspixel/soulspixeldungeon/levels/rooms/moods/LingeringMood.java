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

package com.soulspixel.soulspixeldungeon.levels.rooms.moods;

import com.soulspixel.soulspixeldungeon.actors.Char;
import com.soulspixel.soulspixeldungeon.actors.buffs.Blindness;
import com.soulspixel.soulspixeldungeon.actors.buffs.Buff;
import com.soulspixel.soulspixeldungeon.actors.buffs.Carcinisation;
import com.soulspixel.soulspixeldungeon.actors.buffs.Invisibility;
import com.soulspixel.soulspixeldungeon.actors.buffs.Sick;
import com.soulspixel.soulspixeldungeon.actors.buffs.Silenced;
import com.soulspixel.soulspixeldungeon.actors.buffs.Stickyfloor;
import com.soulspixel.soulspixeldungeon.actors.buffs.Weakness;
import com.soulspixel.soulspixeldungeon.levels.RegularLevel;

public class LingeringMood {

    public static void getEffect(int type, Char ch, RegularLevel regularLevel){
        switch (type){
            case 1:
                blindness(ch);
                break;
            case 2:
                crabArmor(ch);
                break;
            case 3:
                vomitNoEat(ch);
                break;
            case 4:
                silence(ch);
                break;
            case 5:
                weak(ch);
                break;
            case 6:
                invisibility(ch);
                break;
            case 7:
                stickyfloor(ch);
                break;
        }
    }

    private static void blindness(Char ch){
        if(ch.buff( Blindness.class ) == null){
            Buff.affect(ch, Blindness.class, 12f);
        }
    }

    private static void crabArmor(Char ch){
        if(ch.buff( Carcinisation.class ) == null){
            Buff.affect(ch, Carcinisation.class, 12f);
        }
    }

    public static void vomitNoEat(Char ch){
        if(ch.buff( Sick.class ) == null){
            Buff.affect(ch, Sick.class, 12f);
        }
    }

    public static void silence(Char ch){
        if(ch.buff( Silenced.class ) == null){
            Buff.affect(ch, Silenced.class, 12f);
        }
    }

    public static void weak(Char ch){
        if(ch.buff( Weakness.class ) == null){
            Buff.prolong(ch, Weakness.class, 12f);
        }
    }

    public static void invisibility(Char ch){
        if(ch.buff( Invisibility.class ) == null){
            Buff.affect(ch, Invisibility.class, 6f);
        }
    }

    public static void stickyfloor(Char ch){
        if(ch.buff( Stickyfloor.class ) == null){
            Buff.affect(ch, Stickyfloor.class, 6f);
        }
    }

}
