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

package com.soulspixel.soulspixeldungeon.items.food;

import com.soulspixel.soulspixeldungeon.Assets;
import com.soulspixel.soulspixeldungeon.Badges;
import com.soulspixel.soulspixeldungeon.Challenges;
import com.soulspixel.soulspixeldungeon.Dungeon;
import com.soulspixel.soulspixeldungeon.Statistics;
import com.soulspixel.soulspixeldungeon.actors.buffs.Buff;
import com.soulspixel.soulspixeldungeon.actors.buffs.Hunger;
import com.soulspixel.soulspixeldungeon.actors.buffs.Sick;
import com.soulspixel.soulspixeldungeon.actors.hero.Hero;
import com.soulspixel.soulspixeldungeon.actors.hero.Talent;
import com.soulspixel.soulspixeldungeon.effects.SpellSprite;
import com.soulspixel.soulspixeldungeon.items.Item;
import com.soulspixel.soulspixeldungeon.items.artifacts.Artifact;
import com.soulspixel.soulspixeldungeon.items.artifacts.HornOfPlenty;
import com.soulspixel.soulspixeldungeon.messages.Messages;
import com.soulspixel.soulspixeldungeon.sprites.ItemSpriteSheet;
import com.soulspixel.soulspixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;

import java.util.ArrayList;

public class Food extends Item {

	public static final float TIME_TO_EAT	= 3f;
	
	public static final String AC_EAT	= "EAT";
	
	public float energy = Hunger.HUNGRY;
	
	{
		stackable = true;
		image = ItemSpriteSheet.RATION;

		defaultAction = AC_EAT;

		bones = true;
	}
	
	@Override
	public ArrayList<String> actions( Hero hero ) {
		ArrayList<String> actions = super.actions( hero );
		if(hero.buffs(Sick.class) == null){
			actions.add( AC_EAT );
		}
		return actions;
	}
	
	@Override
	public void execute( Hero hero, String action ) {

		super.execute( hero, action );

		if (action.equals( AC_EAT )) {
			
			detach( hero.belongings.backpack );
			
			satisfy(hero);
			GLog.i( Messages.get(this, "eat_msg") );
			
			hero.sprite.operate( hero.pos );
			hero.busy();
			SpellSprite.show( hero, SpellSprite.FOOD );
			eatSFX();
			
			hero.spend( eatingTime() );

			Talent.onFoodEaten(hero, energy, this);
			
			Statistics.foodEaten++;
			Badges.validateFoodEaten();
			
		}
	}

	protected void eatSFX(){
		Sample.INSTANCE.play( Assets.Sounds.EAT );
	}

	protected float eatingTime(){
		if (Dungeon.hero.hasTalent(Talent.IRON_STOMACH)
			|| Dungeon.hero.hasTalent(Talent.ENERGIZING_MEAL)
			|| Dungeon.hero.hasTalent(Talent.MYSTICAL_MEAL)
			|| Dungeon.hero.hasTalent(Talent.INVIGORATING_MEAL)
			|| Dungeon.hero.hasTalent(Talent.FOCUSED_MEAL)){
			return TIME_TO_EAT - 2;
		} else {
			return TIME_TO_EAT;
		}
	}
	
	protected void satisfy( Hero hero ){
		float foodVal = energy;
		if (Dungeon.isChallenged(Challenges.NO_FOOD)){
			foodVal /= 3f;
		}

		Artifact.ArtifactBuff buff = hero.buff( HornOfPlenty.hornRecharge.class );
		if (buff != null && buff.isCursed()){
			foodVal *= 0.67f;
			GLog.n( Messages.get(Hunger.class, "cursedhorn") );
		}

		Buff.affect(hero, Hunger.class).satisfy(foodVal);
	}
	
	@Override
	public boolean isUpgradable() {
		return false;
	}
	
	@Override
	public boolean isIdentified() {
		return true;
	}
	
	@Override
	public int value() {
		return 10 * quantity;
	}
}
