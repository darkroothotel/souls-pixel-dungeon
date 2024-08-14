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

package com.soulspixel.soulspixeldungeon.ui;

import com.soulspixel.soulspixeldungeon.SoulsPixelDungeon;
import com.soulspixel.soulspixeldungeon.messages.Messages;
import com.soulspixel.soulspixeldungeon.scenes.TitleScene;
import com.soulspixel.soulspixeldungeon.windows.WndKeyBindings;
import com.watabou.input.GameAction;
import com.watabou.noosa.Game;

public class BonfireHeroButton extends IconButton {

	public BonfireHeroButton() {
		super(Icons.TALENT.get());

		width = 20;
		height = 20;
	}

	@Override
	public GameAction keyAction() {
		return GameAction.MIDDLE_CLICK;
	}

	@Override
	protected String hoverText() {
		return Messages.titleCase(Messages.get(this, "msg"));
	}
}
