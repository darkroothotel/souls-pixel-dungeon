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

import com.soulspixel.soulspixeldungeon.actors.buffs.Blindness;
import com.soulspixel.soulspixeldungeon.actors.buffs.Buff;
import com.soulspixel.soulspixeldungeon.actors.buffs.Carcinisation;
import com.soulspixel.soulspixeldungeon.actors.buffs.Sick;
import com.soulspixel.soulspixeldungeon.actors.hero.Hero;
import com.soulspixel.soulspixeldungeon.levels.RegularLevel;

public class LingeringMood {

    public static void getEffect(int type, Hero hero, RegularLevel regularLevel){
        switch (type){
            case 1:
                blindness(hero);
                break;
            case 2:
                crabArmor(hero);
                break;
            case 3:
                vomitNoEat(hero);
                break;
        }
    }

    private static void blindness(Hero hero){
        if(hero.buff( Blindness.class ) == null){
            Buff.affect(hero, Blindness.class, 12f);
        }
    }

    private static void crabArmor(Hero hero){
        if(hero.buff( Carcinisation.class ) == null){
            Buff.affect(hero, Carcinisation.class, 12f);
        }
    }

    public static void vomitNoEat(Hero hero){
        if(hero.buff( Sick.class ) == null){
            Buff.affect(hero, Sick.class, 12f);
        }
    }

}
