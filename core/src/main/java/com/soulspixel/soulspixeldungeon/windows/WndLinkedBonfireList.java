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

package com.soulspixel.soulspixeldungeon.windows;

import com.soulspixel.soulspixeldungeon.Dungeon;
import com.soulspixel.soulspixeldungeon.SoulsPixelDungeon;
import com.soulspixel.soulspixeldungeon.actors.mobs.npcs.Bonfire;
import com.soulspixel.soulspixeldungeon.messages.Messages;
import com.soulspixel.soulspixeldungeon.scenes.BonfireScene;
import com.soulspixel.soulspixeldungeon.sprites.BonfireSprite;
import com.soulspixel.soulspixeldungeon.ui.ScrollingListPane;
import com.soulspixel.soulspixeldungeon.ui.Window;

public class WndLinkedBonfireList extends Window {

	private ScrollingListPane sl;

	public WndLinkedBonfireList(Bonfire bonfire) {

		BonfireSprite bs = new BonfireSprite();

		sl = new ScrollingListPane();
		add(sl);

		sl.addTitle(Messages.get(BonfireScene.class, "locations"));

		for(Bonfire b : Dungeon.hero.getLinkedbonfires().valueList()){
			if(b != bonfire){
				ScrollingListPane.ListItem li = new ScrollingListPane.ListItem(bs, Messages.get(BonfireScene.class, "title", Dungeon.getFloorName())){

					final Bonfire bonfire = b;

					@Override
					public boolean onClick(float x, float y) {
						if (inside( x, y )) {
							SoulsPixelDungeon.scene().addToFront(new WndInfoBonfire(bonfire));
							return true;
						}
						return false;
					}
				};
				sl.addItem(li);
			}
		}

		resize(120, (int)sl.content().height());
		sl.setRect(0, 0, width, height);
	}
}
