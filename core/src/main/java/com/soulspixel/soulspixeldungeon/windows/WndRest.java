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
import com.soulspixel.soulspixeldungeon.actors.mobs.Mob;
import com.soulspixel.soulspixeldungeon.actors.mobs.npcs.Bonfire;
import com.soulspixel.soulspixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.soulspixel.soulspixeldungeon.messages.Messages;
import com.soulspixel.soulspixeldungeon.scenes.BonfireScene;
import com.soulspixel.soulspixeldungeon.scenes.PixelScene;
import com.soulspixel.soulspixeldungeon.ui.RedButton;
import com.soulspixel.soulspixeldungeon.ui.RenderedTextBlock;
import com.soulspixel.soulspixeldungeon.ui.Window;
import com.watabou.noosa.Game;

import java.util.ArrayList;

public class WndRest extends Window {

	private static final int WIDTH		= 160;
	private static final int BTN_HEIGHT	= 20;
	private static final float GAP		= 2;
	private static final float BTN_GAP  = 10;

	RedButton btnRest;
	RedButton btnDontRest;

	public WndRest(Bonfire bonfire) {
		
		super();
		
		IconTitle titlebar = new IconTitle();
		titlebar.icon( bonfire.sprite() );
		titlebar.label( Messages.titleCase(Messages.get(BonfireScene.class, "title", Dungeon.getFloorName())));
		titlebar.setRect( 0, 0, WIDTH, 0 );
		add( titlebar );
		
		RenderedTextBlock message = PixelScene.renderTextBlock(Messages.get(this, "message"), 6 );
		message.maxWidth(WIDTH);
		message.setPos(0, titlebar.bottom() + GAP);
		add( message );
		
		btnRest = new RedButton( Messages.get(this, "rest") ) {
			@Override
			protected void onClick() {
				Dungeon.hero.setBonfireDepth(bonfire.getDepth());
				Dungeon.hero.setBonfirePos(bonfire.pos);
				bonfire.sitDown(Dungeon.hero);
				hide();
			}
		};
		btnRest.setRect( 0, message.bottom() + BTN_GAP, WIDTH, BTN_HEIGHT );
		add( btnRest );

		btnDontRest = new RedButton( Messages.get(this, "dont_rest") ) {
			@Override
			protected void onClick() {
				hide();
            }
		};
		btnDontRest.setRect( 0, btnRest.bottom(), WIDTH, BTN_HEIGHT );
		add( btnDontRest );

		resize( WIDTH, (int)btnDontRest.bottom() );
	}
	
	@Override
	public void destroy() {
		super.destroy();
	}
	
	@Override
	public void onBackPressed() {
	}
}
