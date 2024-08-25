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

package com.soulspixel.soulspixeldungeon.items.rings;


import com.soulspixel.soulspixeldungeon.Badges;
import com.soulspixel.soulspixeldungeon.Challenges;
import com.soulspixel.soulspixeldungeon.Dungeon;
import com.soulspixel.soulspixeldungeon.actors.Char;
import com.soulspixel.soulspixeldungeon.actors.buffs.Barrier;
import com.soulspixel.soulspixeldungeon.actors.buffs.Buff;
import com.soulspixel.soulspixeldungeon.actors.hero.Hero;
import com.soulspixel.soulspixeldungeon.effects.FloatingText;
import com.soulspixel.soulspixeldungeon.items.Item;
import com.soulspixel.soulspixeldungeon.items.potions.PotionOfHealing;
import com.soulspixel.soulspixeldungeon.items.weapon.Weapon;
import com.soulspixel.soulspixeldungeon.messages.Messages;
import com.soulspixel.soulspixeldungeon.sprites.CharSprite;
import com.soulspixel.soulspixeldungeon.sprites.ItemSpriteSheet;
import com.soulspixel.soulspixeldungeon.utils.GLog;

public class RingOfAntiMagic extends Ring implements Hero.Doom {

	{
		icon = ItemSpriteSheet.Icons.RING_ANTI_MAGIC;
	}
	
	public String statsInfo() {
		float b = 2*((float) Math.pow( 2.0, getBuffedBonus(Dungeon.hero, AntiMagicField.class)));
		if (isIdentified()){
			String info = Messages.get(this, "stats",
					soloBonus(), (int) b);
			if (isEquipped(Dungeon.hero) && soloBuffedBonus() != combinedBuffedBonus(Dungeon.hero)){
				info += "\n\n" + Messages.get(this, "combined_stats",
						getBonus(Dungeon.hero, AntiMagicField.class), (int)(Math.pow(2.0, combinedBuffedBonus(Dungeon.hero)) - 1));
			}
			return info;
		} else {
			return Messages.get(this, "typical_stats", 1, 2);
		}
	}

	@Override
	protected RingBuff buff() {
		return new AntiMagicField();
	}

	public static boolean isTriggered(Hero user){
		return (float) Math.pow( 2.0, getBuffedBonus(user, AntiMagicField.class)) > 0;
	}

	public static boolean isEnchanted(Hero hero){
		return  ((Weapon) hero.belongings.weapon).hasGoodEnchant() || ((Weapon) hero.belongings.weapon).hasCurseEnchant();
	}

	public static void antiMagicFieldChar( Hero user, Char enemy){
		if(Char.hasProp(enemy, Char.Property.MAGIC) || user.belongings.armor.glyph != null || isEnchanted(user)){
			float b = ((float) Math.pow( 2.0, getBuffedBonus(user, AntiMagicField.class)));
			if(b < 0){
				user.damage((int) (-1*b), RingOfAntiMagic.class);
			} else {
				Buff.affect(user, Barrier.class).setShield((int) b);
				user.sprite.showStatusWithIcon( CharSprite.POSITIVE, Integer.toString((int) b), FloatingText.SHIELDING );
			}
		}
	}

	@Override
	public void onDeath() {
		Badges.validateDeathFromFriendlyMagic();

		Dungeon.fail( this );
		GLog.n( Messages.get(this, "ondeath") );
	}

	public class AntiMagicField extends RingBuff {
	}
}

