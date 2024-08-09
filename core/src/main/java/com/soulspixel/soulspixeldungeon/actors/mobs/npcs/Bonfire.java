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

package com.soulspixel.soulspixeldungeon.actors.mobs.npcs;

import com.soulspixel.soulspixeldungeon.Dungeon;
import com.soulspixel.soulspixeldungeon.Statistics;
import com.soulspixel.soulspixeldungeon.actors.Char;
import com.soulspixel.soulspixeldungeon.actors.buffs.AscensionChallenge;
import com.soulspixel.soulspixeldungeon.actors.buffs.Buff;
import com.soulspixel.soulspixeldungeon.actors.hero.Hero;
import com.soulspixel.soulspixeldungeon.actors.mobs.Elemental;
import com.soulspixel.soulspixeldungeon.actors.mobs.Mob;
import com.soulspixel.soulspixeldungeon.actors.mobs.RotHeart;
import com.soulspixel.soulspixeldungeon.items.Generator;
import com.soulspixel.soulspixeldungeon.items.Heap;
import com.soulspixel.soulspixeldungeon.items.Item;
import com.soulspixel.soulspixeldungeon.items.quest.CeremonialCandle;
import com.soulspixel.soulspixeldungeon.items.quest.CorpseDust;
import com.soulspixel.soulspixeldungeon.items.quest.Embers;
import com.soulspixel.soulspixeldungeon.items.wands.Wand;
import com.soulspixel.soulspixeldungeon.journal.Notes;
import com.soulspixel.soulspixeldungeon.levels.Level;
import com.soulspixel.soulspixeldungeon.levels.RegularLevel;
import com.soulspixel.soulspixeldungeon.levels.Terrain;
import com.soulspixel.soulspixeldungeon.levels.rooms.Room;
import com.soulspixel.soulspixeldungeon.levels.rooms.quest.MassGraveRoom;
import com.soulspixel.soulspixeldungeon.levels.rooms.quest.RitualSiteRoom;
import com.soulspixel.soulspixeldungeon.levels.rooms.quest.RotGardenRoom;
import com.soulspixel.soulspixeldungeon.levels.traps.Trap;
import com.soulspixel.soulspixeldungeon.messages.Messages;
import com.soulspixel.soulspixeldungeon.plants.Rotberry;
import com.soulspixel.soulspixeldungeon.scenes.BonfireScene;
import com.soulspixel.soulspixeldungeon.scenes.GameScene;
import com.soulspixel.soulspixeldungeon.sprites.BonfireSprite;
import com.soulspixel.soulspixeldungeon.sprites.WandmakerSprite;
import com.soulspixel.soulspixeldungeon.windows.WndBlacksmith;
import com.soulspixel.soulspixeldungeon.windows.WndInfoTrap;
import com.soulspixel.soulspixeldungeon.windows.WndQuest;
import com.soulspixel.soulspixeldungeon.windows.WndRest;
import com.soulspixel.soulspixeldungeon.windows.WndWandmaker;
import com.watabou.noosa.Game;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.Point;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Bonfire extends NPC {

	{
		spriteClass = BonfireSprite.class;

		properties.add(Property.IMMOVABLE);
		properties.add(Property.NOT_A_MOB);
		properties.add(Property.STATIC);
	}

	private int depth = 1;

	public int getDepth(){
		return depth;
	}

	public void spawn( int depth ) {
		this.depth = depth;
	}
	
	@Override
	protected boolean act() {
		if (Dungeon.level.visited[pos]){
			Notes.add( Notes.Landmark.BONFIRE );
		}
		return super.act();
	}
	
	@Override
	public int defenseSkill( Char enemy ) {
		return INFINITE_EVASION;
	}

	@Override
	public void damage( int dmg, Object src ) {
		//do nothing
	}

	@Override
	public boolean add( Buff buff ) {
		return false;
	}
	
	@Override
	public boolean reset() {
		return true;
	}
	
	@Override
	public boolean interact(Char c) {
        if (c == Dungeon.hero) {
			if (((Hero) c).getBonfireDepth() != Bonfire.this.getDepth()) {
				Game.runOnRenderThread(new Callback() {
					@Override
					public void call() {
						GameScene.show(new WndRest(Bonfire.this));
					}
				});
				return true;
			} else {
				Game.switchScene(BonfireScene.class);
			}
			return true;
        }
        return true;
    }
}
