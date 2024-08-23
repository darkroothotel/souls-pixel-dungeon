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
import com.soulspixel.soulspixeldungeon.actors.mobs.npcs.Bonfire;
import com.soulspixel.soulspixeldungeon.levels.Level;
import com.soulspixel.soulspixeldungeon.messages.Messages;
import com.soulspixel.soulspixeldungeon.scenes.BonfireScene;
import com.soulspixel.soulspixeldungeon.scenes.InterlevelScene;
import com.soulspixel.soulspixeldungeon.scenes.PixelScene;
import com.soulspixel.soulspixeldungeon.sprites.BonfireSprite;
import com.soulspixel.soulspixeldungeon.ui.RedButton;
import com.soulspixel.soulspixeldungeon.ui.RenderedTextBlock;
import com.soulspixel.soulspixeldungeon.ui.Window;
import com.watabou.noosa.Game;

public class WndInfoBonfire extends Window {

	private static final float GAP	= 2;

	private static final int WIDTH_MIN = 120;
	private static final int WIDTH_MAX = 220;

	//only one WndInfoBonfire can appear at a time
	private static WndInfoBonfire INSTANCE;

	public WndInfoBonfire(Bonfire bonfire) {

		super();

		if (INSTANCE != null){
			INSTANCE.hide();
		}
		INSTANCE = this;

		fillFields(bonfire);
	}

	@Override
	public void hide() {
		super.hide();
		if (INSTANCE == this){
			INSTANCE = null;
		}
	}

	private void fillFields(Bonfire bonfire) {
		
		IconTitle titlebar = new IconTitle( new BonfireSprite(), Messages.get(BonfireScene.class, "title", Dungeon.getFloorName()));
		titlebar.color( TITLE_COLOR );
		
		RenderedTextBlock txtInfo = PixelScene.renderTextBlock(Dungeon.getFloorDesc(), 6 );

		layoutFields(titlebar, txtInfo, bonfire);
	}

	private void layoutFields(IconTitle title, RenderedTextBlock info, Bonfire bonfire){
		int width = WIDTH_MIN;

		info.maxWidth(width);

		//window can go out of the screen on landscape, so widen it as appropriate
		while (PixelScene.landscape()
				&& info.height() > 100
				&& width < WIDTH_MAX){
			width += 20;
			info.maxWidth(width);
		}

		title.setRect( 0, 0, width, 0 );
		add( title );

		info.setPos(title.left(), title.bottom() + GAP);
		add( info );

		RedButton travel = new RedButton(Messages.get(BonfireScene.class, "travel")){
			@Override
			protected void onClick() {
				Level.beforeTransition();
				InterlevelScene.mode = InterlevelScene.Mode.RETURN;
				InterlevelScene.returnDepth = bonfire.getDepth();
				InterlevelScene.returnBranch = 0;
				InterlevelScene.returnPos = bonfire.pos;
				Game.switchScene( InterlevelScene.class );
			}
		};
		travel.setRect(0, info.bottom() + GAP, width, 20);
		add(travel);

		resize( width, (int)(travel.bottom() + 2) );
	}
}
