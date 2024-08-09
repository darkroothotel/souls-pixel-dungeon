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

package com.soulspixel.soulspixeldungeon.ui.changelist;

import com.soulspixel.soulspixeldungeon.ui.Icons;
import com.soulspixel.soulspixeldungeon.ui.Window;

import java.util.ArrayList;

public class souls_v0_1_X_Changes {
	
	public static void addAllChanges( ArrayList<ChangeInfo> changeInfos ){
		
		ChangeInfo changes = new ChangeInfo( "v0.1.X", true, "");
		changes.hardlight( Window.SPX_COLOR);
		changeInfos.add(changes);
		
		add_v0_1_0_Changes(changeInfos);
	}
	
	public static void add_v0_1_0_Changes( ArrayList<ChangeInfo> changeInfos ){
		
		ChangeInfo changes = new ChangeInfo("v0.1.0", false, "");
		changes.hardlight(Window.SPX_COLOR);
		changeInfos.add(changes);
		
		changes.addButton( new ChangeButton(Icons.get(Icons.DARKROOT), "Developer Commentary",
				"_-_ Released August 8th, 2024\n" +
				"_-_ During development of v0.1.0\n" +
				"\n" +
				"v0.1.0 was the first fork of Shattered Pixel Dungeon."));
	}
	
}
