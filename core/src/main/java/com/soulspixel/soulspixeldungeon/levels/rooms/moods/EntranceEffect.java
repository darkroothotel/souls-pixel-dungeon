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

import com.soulspixel.soulspixeldungeon.Assets;
import com.soulspixel.soulspixeldungeon.Dungeon;
import com.soulspixel.soulspixeldungeon.SPDSettings;
import com.soulspixel.soulspixeldungeon.SoulsPixelDungeon;
import com.soulspixel.soulspixeldungeon.actors.Char;
import com.soulspixel.soulspixeldungeon.actors.buffs.AtEase;
import com.soulspixel.soulspixeldungeon.actors.buffs.Buff;
import com.soulspixel.soulspixeldungeon.actors.buffs.Cripple;
import com.soulspixel.soulspixeldungeon.actors.buffs.Light;
import com.soulspixel.soulspixeldungeon.actors.buffs.Vertigo;
import com.soulspixel.soulspixeldungeon.actors.hero.Hero;
import com.soulspixel.soulspixeldungeon.actors.mobs.Mob;
import com.soulspixel.soulspixeldungeon.effects.CellEmitter;
import com.soulspixel.soulspixeldungeon.effects.Speck;
import com.soulspixel.soulspixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.soulspixel.soulspixeldungeon.levels.RegularLevel;
import com.soulspixel.soulspixeldungeon.plants.Earthroot;
import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Sample;

public class EntranceEffect {

    public static void getEffect(int type, Char ch, RegularLevel regularLevel){
        switch (type){
            case -1:
                //shiftingVision();
                break;
            case -2:
                detect(ch);
                break;
            case -3:
                trip(ch);
                break;
            case -4:
                teleport(ch);
                break;
            case -5:
                confuse(ch);
                break;
            case -6:
                light(ch);
                break;
            case -7:
                plantArmor(ch);
                break;
            case -8:
                atEase(ch);
                break;
        }
    }

    private static void shiftingVision(){
        SoulsPixelDungeon.seamlessResetScene(new Game.SceneChangeCallback() {
            @Override
            public void beforeCreate() {
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

    private static void detect(Char ch){
        for (Mob mob : Dungeon.level.mobs) {
            mob.beckon( ch.pos );
        }
        if (Dungeon.level.heroFOV[ch.pos]) {
            CellEmitter.center( ch.pos ).start( Speck.factory( Speck.SCREAM ), 0.3f, 3 );
        }
        Sample.INSTANCE.play( Assets.Sounds.ALERT );
    }

    private static void trip(Char ch){
        if(ch instanceof Hero){
            ((Hero) ch).belongings.weapon.doDrop((Hero) ch);
        } else{
            Buff.affect(ch, Cripple.class, 6f);
        }
    }

    private static void teleport(Char ch){
        ScrollOfTeleportation.teleportCharPreferringUnseen(ch);
    }

    private static void confuse(Char ch){
        Buff.affect(ch, Vertigo.class, 12f);
    }

    private static void light(Char ch){
        Buff.affect(ch, Light.class, 24f);
    }

    private static void plantArmor(Char ch){
        Earthroot.Armor armor = ch.buff( Earthroot.Armor.class );
        if (armor != null) {
            Buff.affect( ch, Earthroot.Armor.class ).level(ch.HP);
        }
    }

    private static void atEase(Char ch){
        Buff.affect(ch, AtEase.class, AtEase.DURATION);
    }

}
