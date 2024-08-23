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

package com.soulspixel.soulspixeldungeon.items.armor.glyphs;

import com.soulspixel.soulspixeldungeon.actors.Char;
import com.soulspixel.soulspixeldungeon.actors.buffs.Charm;
import com.soulspixel.soulspixeldungeon.actors.buffs.Degrade;
import com.soulspixel.soulspixeldungeon.actors.buffs.Hex;
import com.soulspixel.soulspixeldungeon.actors.buffs.MagicalSleep;
import com.soulspixel.soulspixeldungeon.actors.buffs.Vulnerable;
import com.soulspixel.soulspixeldungeon.actors.buffs.Weakness;
import com.soulspixel.soulspixeldungeon.actors.hero.abilities.duelist.ElementalStrike;
import com.soulspixel.soulspixeldungeon.actors.hero.abilities.mage.ElementalBlast;
import com.soulspixel.soulspixeldungeon.actors.hero.abilities.mage.WarpBeacon;
import com.soulspixel.soulspixeldungeon.actors.mobs.CrystalWisp;
import com.soulspixel.soulspixeldungeon.actors.mobs.DM100;
import com.soulspixel.soulspixeldungeon.actors.mobs.Eye;
import com.soulspixel.soulspixeldungeon.actors.mobs.Shaman;
import com.soulspixel.soulspixeldungeon.actors.mobs.Warlock;
import com.soulspixel.soulspixeldungeon.actors.mobs.YogFist;
import com.soulspixel.soulspixeldungeon.items.armor.Armor;
import com.soulspixel.soulspixeldungeon.items.bombs.ArcaneBomb;
import com.soulspixel.soulspixeldungeon.items.bombs.HolyBomb;
import com.soulspixel.soulspixeldungeon.items.scrolls.ScrollOfRetribution;
import com.soulspixel.soulspixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.soulspixel.soulspixeldungeon.items.scrolls.exotic.ScrollOfPsionicBlast;
import com.soulspixel.soulspixeldungeon.items.wands.CursedWand;
import com.soulspixel.soulspixeldungeon.items.wands.WandOfBlastWave;
import com.soulspixel.soulspixeldungeon.items.wands.WandOfDisintegration;
import com.soulspixel.soulspixeldungeon.items.wands.WandOfFireblast;
import com.soulspixel.soulspixeldungeon.items.wands.WandOfFrost;
import com.soulspixel.soulspixeldungeon.items.wands.WandOfLightning;
import com.soulspixel.soulspixeldungeon.items.wands.WandOfLivingEarth;
import com.soulspixel.soulspixeldungeon.items.wands.WandOfMagicMissile;
import com.soulspixel.soulspixeldungeon.items.wands.WandOfPrismaticLight;
import com.soulspixel.soulspixeldungeon.items.wands.WandOfTransfusion;
import com.soulspixel.soulspixeldungeon.items.wands.WandOfWarding;
import com.soulspixel.soulspixeldungeon.items.weapon.enchantments.Blazing;
import com.soulspixel.soulspixeldungeon.items.weapon.enchantments.Grim;
import com.soulspixel.soulspixeldungeon.items.weapon.enchantments.Shocking;
import com.soulspixel.soulspixeldungeon.items.weapon.enchantments.Vortex;
import com.soulspixel.soulspixeldungeon.items.weapon.missiles.darts.HolyDart;
import com.soulspixel.soulspixeldungeon.levels.traps.DisintegrationTrap;
import com.soulspixel.soulspixeldungeon.levels.traps.GrimTrap;
import com.soulspixel.soulspixeldungeon.sprites.ItemSprite;

import java.util.HashSet;

public class AntiMagic extends Armor.Glyph {

	private static ItemSprite.Glowing TEAL = new ItemSprite.Glowing( 0x88EEFF );
	
	public static final HashSet<Class> RESISTS = new HashSet<>();
	static {
		RESISTS.add( MagicalSleep.class );
		RESISTS.add( Charm.class );
		RESISTS.add( Weakness.class );
		RESISTS.add( Vulnerable.class );
		RESISTS.add( Hex.class );
		RESISTS.add( Degrade.class );
		
		RESISTS.add( DisintegrationTrap.class );
		RESISTS.add( GrimTrap.class );

		RESISTS.add( ArcaneBomb.class );
		RESISTS.add( HolyBomb.HolyDamage.class );
		RESISTS.add( ScrollOfRetribution.class );
		RESISTS.add( ScrollOfPsionicBlast.class );
		RESISTS.add( ScrollOfTeleportation.class );
		RESISTS.add( HolyDart.class );

		RESISTS.add( ElementalBlast.class );
		RESISTS.add( CursedWand.class );
		RESISTS.add( WandOfBlastWave.class );
		RESISTS.add( WandOfDisintegration.class );
		RESISTS.add( WandOfFireblast.class );
		RESISTS.add( WandOfFrost.class );
		RESISTS.add( WandOfLightning.class );
		RESISTS.add( WandOfLivingEarth.class );
		RESISTS.add( WandOfMagicMissile.class );
		RESISTS.add( WandOfPrismaticLight.class );
		RESISTS.add( WandOfTransfusion.class );
		RESISTS.add( WandOfWarding.Ward.class );

		RESISTS.add( ElementalStrike.class );
		RESISTS.add( Blazing.class );
		RESISTS.add( Vortex.class );
		RESISTS.add( WandOfFireblast.FireBlastOnHit.class );
		RESISTS.add( Shocking.class );
		RESISTS.add( WandOfLightning.LightningOnHit.class );
		RESISTS.add( Grim.class );

		RESISTS.add( WarpBeacon.class );
		
		RESISTS.add( DM100.LightningBolt.class );
		RESISTS.add( Shaman.EarthenBolt.class );
		RESISTS.add( CrystalWisp.LightBeam.class );
		RESISTS.add( Warlock.DarkBolt.class );
		RESISTS.add( Eye.DeathGaze.class );
		RESISTS.add( YogFist.BrightFist.LightBeam.class );
		RESISTS.add( YogFist.DarkFist.DarkBolt.class );
	}
	
	@Override
	public int proc(Armor armor, Char attacker, Char defender, int damage) {
		//no proc effect, see:
		// Hero.damage
		// GhostHero.damage
		// Shadowclone.damage
		// ArmoredStatue.damage
		// PrismaticImage.damage
		return damage;
	}
	
	public static int drRoll( Char ch, int level ){
		return Char.combatRoll(
				Math.round(level * genericProcChanceMultiplier(ch)),
				Math.round((3 + (level*1.5f)) * genericProcChanceMultiplier(ch)));
	}

	@Override
	public ItemSprite.Glowing glowing() {
		return TEAL;
	}

}