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
import com.soulspixel.soulspixeldungeon.actors.Char;
import com.soulspixel.soulspixeldungeon.actors.buffs.Buff;
import com.soulspixel.soulspixeldungeon.actors.buffs.MindVision;
import com.soulspixel.soulspixeldungeon.actors.buffs.Recharging;
import com.soulspixel.soulspixeldungeon.actors.buffs.Terror;
import com.soulspixel.soulspixeldungeon.actors.hero.Hero;
import com.soulspixel.soulspixeldungeon.actors.mobs.Mob;
import com.soulspixel.soulspixeldungeon.effects.particles.EnergyParticle;
import com.soulspixel.soulspixeldungeon.items.food.Food;
import com.soulspixel.soulspixeldungeon.items.food.UndeadFlesh;
import com.soulspixel.soulspixeldungeon.items.potions.PotionOfHealing;
import com.soulspixel.soulspixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.soulspixel.soulspixeldungeon.journal.Notes;
import com.soulspixel.soulspixeldungeon.scenes.BonfireScene;
import com.soulspixel.soulspixeldungeon.scenes.GameScene;
import com.soulspixel.soulspixeldungeon.sprites.BonfireSprite;
import com.soulspixel.soulspixeldungeon.windows.WndRest;
import com.watabou.noosa.Game;
import com.watabou.noosa.particles.Emitter;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;

import java.util.ArrayList;

public class Bonfire extends NPC {

	{
		spriteClass = BonfireSprite.class;

		properties.add(Property.IMMOVABLE);
		properties.add(Property.NOT_A_MOB);
		properties.add(Property.STATIC);
		alignment = Alignment.ALLY;
	}

	private int depth = -1;
	private boolean discovered = false;
	private int level = 0;

	public int getDepth(){
		return depth;
	}

	public boolean getIsDiscovered(){
		return discovered;
	}

	public void spawn( int depth ) {
		this.depth = depth;
	}

	public int getLevel(){
		return level;
	}

	public void setLevel(int level){
		this.level = level;
	}
	
	@Override
	protected boolean act() {
		if (Dungeon.level.visited[pos]){
			Notes.add( Notes.Landmark.BONFIRE );
		}
		for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
			if (Dungeon.level.adjacent(pos, mob.pos)) {
				Buff.affect( mob, Terror.class, Terror.DURATION ).object = this.id();
			}
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

	public void sitDown(Hero c){
		Game.switchScene(BonfireScene.class);
		ArrayList<Mob> targets = new ArrayList<>();
		for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
			if (Dungeon.level.heroFOV[mob.pos]) {
				targets.add(mob);
			}
		}

		for (Mob mob : targets){
			mob.bonfireReset();
			mob.clearEnemy();
		}

		if(!discovered){
			c.undoUndead();
			PotionOfHealing.heal(c);
			discovered = true;
		}

		int chp;
		switch (level){
			case 0:
				//nothing
				break;
			case 1:
                chp = (c.HP / 6) * (level+1);
                if(c.HP < chp) c.HP = chp;
				break;
			case 2:
				chp = (c.HP / 6) * (level+1);
				if(c.HP < chp) c.HP = chp;

				Buff.affect( c, MindVision.class, (MindVision.DURATION/6) * (level-1) );
				Dungeon.observe();
				break;
			case 3:
				chp = (c.HP / 6) * (level+1);
				if(c.HP < chp) c.HP = chp;

				Buff.affect( c, MindVision.class, (MindVision.DURATION/6) * (level-1) );
				Dungeon.observe();

				Buff.affect(c, Recharging.class, (Recharging.DURATION/6)*(level-2));
				charge(c);
				break;
			case 4:
				chp = (c.HP / 6) * (level+1);
				if(c.HP < chp) c.HP = chp;

				Buff.affect( c, MindVision.class, (MindVision.DURATION/6) * (level-1) );
				Dungeon.observe();

				Buff.affect(c, Recharging.class, (Recharging.DURATION/6)*(level-2));
				charge(c);

				new UndeadFlesh(0.25f).execute(c, Food.AC_EAT);
				break;
			default: //5
				chp = (c.HP / 6) * (level+1);
				if(c.HP < chp) c.HP = chp;

				Buff.affect( c, MindVision.class, (MindVision.DURATION/6) * (level-1) );
				Dungeon.observe();

				Buff.affect(c, Recharging.class, (Recharging.DURATION/6)*(level-2));
				charge(c);

				new UndeadFlesh(0.5f).execute(c, Food.AC_EAT);
				break;
		}
	}

	public void charge( Char user ) {
		if (user.sprite != null) {
			Emitter e = user.sprite.centerEmitter();
			if (e != null) e.burst(EnergyParticle.FACTORY, 15);
		}
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
				sitDown((Hero) c);
			}
			return true;
        }
        return true;
    }

	private static final String DISCOVERED	= "discovered";
	private static final String DEPTH		= "depth";
	private static final String LEVEL		= "level";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(DISCOVERED, discovered);
		bundle.put(DEPTH, depth);
		bundle.put(LEVEL, level);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		discovered = bundle.getBoolean(DISCOVERED);
		depth = bundle.getInt(DEPTH);
		level = bundle.getInt(LEVEL);
	}
}
