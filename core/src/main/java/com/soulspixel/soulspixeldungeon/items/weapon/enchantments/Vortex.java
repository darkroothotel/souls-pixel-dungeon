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

package com.soulspixel.soulspixeldungeon.items.weapon.enchantments;

import com.soulspixel.soulspixeldungeon.actors.Char;
import com.soulspixel.soulspixeldungeon.actors.buffs.Buff;
import com.soulspixel.soulspixeldungeon.actors.buffs.Vertigo;
import com.soulspixel.soulspixeldungeon.effects.particles.RainbowParticle;
import com.soulspixel.soulspixeldungeon.items.weapon.Weapon;
import com.soulspixel.soulspixeldungeon.sprites.ItemSprite.Glowing;
import com.watabou.utils.Random;
import com.watabou.utils.Reflection;

public class Vortex extends Weapon.Enchantment {

	private static Glowing PURPLE = new Glowing( 0xB03FD0 );
	
	@Override
	public int proc( Weapon weapon, Char attacker, Char defender, int damage ) {
		int level = Math.max( 0, weapon.buffedLvl() );

		// lvl 0 - 33%
		// lvl 1 - 50%
		// lvl 2 - 60%
		float procChance = (level+1f)/(level+3f) * procChanceMultiplier(attacker);
		if (Random.Float() < procChance) {

			float powerMulti = Math.max(1f, procChance);

			if (defender.buff(Vertigo.class) == null){
				Buff.affect(defender, Vertigo.class, Vertigo.DURATION*(level+1));
				powerMulti -= 1;
			}

			if (powerMulti > 0){
				int conservedDamage = 0;
				if (attacker.buff(Kinetic.ConservedDamage.class) != null) {
					conservedDamage = attacker.buff(Kinetic.ConservedDamage.class).damageBonus();
					attacker.buff(Kinetic.ConservedDamage.class).detach();
				}

				damage = Reflection.newInstance(Random.oneOf(Unstable.randomEnchants)).proc( weapon, attacker, defender, damage );

				return damage + conservedDamage;
			}
			
			defender.sprite.emitter().burst( RainbowParticle.BURST, level + 1 );
			
		}

		return damage;

	}
	
	@Override
	public Glowing glowing() {
		return PURPLE;
	}
}
