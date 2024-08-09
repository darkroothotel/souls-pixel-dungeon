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

package com.soulspixel.soulspixeldungeon.items.scrolls.exotic;

import com.soulspixel.soulspixeldungeon.actors.buffs.Buff;
import com.soulspixel.soulspixeldungeon.actors.buffs.MagicImmune;
import com.soulspixel.soulspixeldungeon.effects.Flare;
import com.soulspixel.soulspixeldungeon.sprites.ItemSpriteSheet;

public class ScrollOfAntiMagic extends ExoticScroll {
	
	{
		icon = ItemSpriteSheet.Icons.SCROLL_ANTIMAGIC;
	}
	
	@Override
	public void doRead() {

		detach(curUser.belongings.backpack);
		Buff.affect( curUser, MagicImmune.class, MagicImmune.DURATION );
		new Flare( 5, 32 ).color( 0x00FF00, true ).show( curUser.sprite, 2f );

		identify();
		
		readAnimation();
	}
}
