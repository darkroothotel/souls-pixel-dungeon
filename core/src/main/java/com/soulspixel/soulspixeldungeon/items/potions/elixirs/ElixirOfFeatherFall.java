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

package com.soulspixel.soulspixeldungeon.items.potions.elixirs;

import com.soulspixel.soulspixeldungeon.actors.buffs.Buff;
import com.soulspixel.soulspixeldungeon.actors.buffs.FlavourBuff;
import com.soulspixel.soulspixeldungeon.actors.hero.Hero;
import com.soulspixel.soulspixeldungeon.effects.Speck;
import com.soulspixel.soulspixeldungeon.items.potions.PotionOfLevitation;
import com.soulspixel.soulspixeldungeon.messages.Messages;
import com.soulspixel.soulspixeldungeon.sprites.ItemSpriteSheet;
import com.soulspixel.soulspixeldungeon.ui.BuffIndicator;
import com.soulspixel.soulspixeldungeon.utils.GLog;
import com.watabou.noosa.Image;

public class ElixirOfFeatherFall extends Elixir {

	{
		image = ItemSpriteSheet.ELIXIR_FEATHER;

		talentChance = 1/(float)Recipe.OUT_QUANTITY;
	}

	@Override
	public void apply(Hero hero) {
		Buff.append(hero, FeatherBuff.class, FeatherBuff.DURATION);

		hero.sprite.emitter().burst(Speck.factory(Speck.JET), 20);
		GLog.p(Messages.get(this, "light"));
	}

	public static class FeatherBuff extends FlavourBuff {
		//does nothing, just waits to be triggered by chasm falling
		{
			type = buffType.POSITIVE;
		}

		public static final float DURATION	= 50f;

		@Override
		public int icon() {
			return BuffIndicator.LEVITATION;
		}

		@Override
		public void tintIcon(Image icon) {
			icon.hardlight(1f, 2f, 1.25f);
		}

		@Override
		public float iconFadePercent() {
			return Math.max(0, (DURATION - visualcooldown()) / DURATION);
		}
	}

	@Override
	public int value() {
		return (int)(60 * (quantity/(float) Recipe.OUT_QUANTITY));
	}

	@Override
	public int energyVal() {
		return (int)(12 * (quantity/(float) Recipe.OUT_QUANTITY));
	}

	public static class Recipe extends com.soulspixel.soulspixeldungeon.items.Recipe.SimpleRecipe {

		private static final int OUT_QUANTITY = 2;

		{
			inputs =  new Class[]{PotionOfLevitation.class};
			inQuantity = new int[]{1};

			cost = 16;

			output = ElixirOfFeatherFall.class;
			outQuantity = OUT_QUANTITY;
		}

	}

}
