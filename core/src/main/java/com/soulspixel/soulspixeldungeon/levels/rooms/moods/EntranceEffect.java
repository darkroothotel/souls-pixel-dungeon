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

import com.soulspixel.soulspixeldungeon.SPDSettings;
import com.soulspixel.soulspixeldungeon.SoulsPixelDungeon;
import com.soulspixel.soulspixeldungeon.actors.buffs.Blindness;
import com.soulspixel.soulspixeldungeon.actors.buffs.Buff;
import com.soulspixel.soulspixeldungeon.actors.hero.Hero;
import com.soulspixel.soulspixeldungeon.levels.RegularLevel;
import com.watabou.noosa.Game;
import com.watabou.utils.Random;

public class EntranceEffect {

    public static void getEffect(int type, Hero hero, RegularLevel regularLevel){
        switch (type){
            case -1:
                shiftingVision(hero);
                break;
        }
    }

    private static void shiftingVision(Hero hero){
        SoulsPixelDungeon.seamlessResetScene(new Game.SceneChangeCallback() {
            @Override
            public void beforeCreate() {
                //TODO: INCREMENT WITH SHADERS
                int shaderId = (SPDSettings.shader()+1)%3;
                SPDSettings.shader();
                SPDSettings.shader(shaderId);
                Game.setShaderId(shaderId);
            }
            @Override
            public void afterCreate() {
                //do nothing
            }
        });
    }

}
