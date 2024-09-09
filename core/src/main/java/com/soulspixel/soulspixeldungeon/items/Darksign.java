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

package com.soulspixel.soulspixeldungeon.items;

import com.soulspixel.soulspixeldungeon.actors.hero.Hero;
import com.soulspixel.soulspixeldungeon.messages.Messages;
import com.soulspixel.soulspixeldungeon.scenes.GameScene;
import com.soulspixel.soulspixeldungeon.sprites.ItemSprite.Glowing;
import com.soulspixel.soulspixeldungeon.sprites.ItemSpriteSheet;
import com.soulspixel.soulspixeldungeon.windows.WndReturnDarksign;
import com.watabou.noosa.Game;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;

import java.util.ArrayList;

public class Darksign extends Item {

	public static final String AC_KILL = "KILL";

	{
		image = ItemSpriteSheet.DARKSIGN;

		bones = false;
	}

	private boolean blessed = false;

	private int blessedCharges = 0;
	
	@Override
	public boolean isUpgradable() {
		return false;
	}
	
	@Override
	public boolean isIdentified() {
		return true;
	}

	@Override
	public ArrayList<String> actions( Hero hero ) {
		ArrayList<String> actions = new ArrayList<>();
		if (isBlessed()){
			actions.add( AC_KILL );
		}
		return actions;
	}

	@Override
	public void execute( final Hero hero, String action ) {

		super.execute( hero, action );

		if (action.equals( AC_KILL )) {
			Game.runOnRenderThread(new Callback() {
				@Override
				public void call() {
					GameScene.show(new WndReturnDarksign());
				}
			});
		}
	}

	@Override
	public boolean collect() {
		if(curUser == null){
			return super.collect();
		}
		for (Darksign i : curUser.belongings.getAllItems(Darksign.class)){
			if(i != null){
				i.addBlessedCharges(1);
				return true;
			} else {
				return super.collect();
			}
		}
		return super.collect();
	}

	@Override
	public String desc() {
		if (blessed)
			return Messages.get(this, "desc_blessed");
		else
			return super.desc();
	}

	public boolean isBlessed(){
		return blessed;
	}

	public void bless(){
		blessed = true;
	}

	public int getBlessedCharges(){
		return blessedCharges;
	}

	public void addBlessedCharges(int charges){
		blessedCharges += charges;
		updateQuickslot();
	}

	public void setBlessedCharges(int charges){
		blessedCharges = charges;
		updateQuickslot();
	}

	public void subtractBlessedCharges(int charges){
		blessedCharges -= charges;
		if(blessedCharges <= 0){
			blessed = false;
		}
		updateQuickslot();
	}

	private static final Glowing WHITE = new Glowing( 0xFFFFCC );

	@Override
	public Glowing glowing() {
		return isBlessed() ? WHITE : null;
	}

	private static final String BLESSED = "blessed";
	private static final String CHARGES = "charges";

	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );
		bundle.put( BLESSED, blessed );
		bundle.put( CHARGES, blessedCharges );
	}

	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		blessed	= bundle.getBoolean( BLESSED );
		blessedCharges = bundle.getInt( CHARGES );
	}
	
	@Override
	public int value() {
		return 200 * quantity * blessedCharges;
	}

	@Override
	public String status() {
		return blessedCharges+"";
	}
}
