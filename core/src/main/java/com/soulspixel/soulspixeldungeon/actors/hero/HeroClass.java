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

package com.soulspixel.soulspixeldungeon.actors.hero;

import com.soulspixel.soulspixeldungeon.Assets;
import com.soulspixel.soulspixeldungeon.Badges;
import com.soulspixel.soulspixeldungeon.Challenges;
import com.soulspixel.soulspixeldungeon.Dungeon;
import com.soulspixel.soulspixeldungeon.QuickSlot;
import com.soulspixel.soulspixeldungeon.SPDSettings;
import com.soulspixel.soulspixeldungeon.actors.hero.abilities.ArmorAbility;
import com.soulspixel.soulspixeldungeon.actors.hero.abilities.duelist.Challenge;
import com.soulspixel.soulspixeldungeon.actors.hero.abilities.duelist.ElementalStrike;
import com.soulspixel.soulspixeldungeon.actors.hero.abilities.duelist.Feint;
import com.soulspixel.soulspixeldungeon.actors.hero.abilities.huntress.NaturesPower;
import com.soulspixel.soulspixeldungeon.actors.hero.abilities.huntress.SpectralBlades;
import com.soulspixel.soulspixeldungeon.actors.hero.abilities.huntress.SpiritHawk;
import com.soulspixel.soulspixeldungeon.actors.hero.abilities.mage.ElementalBlast;
import com.soulspixel.soulspixeldungeon.actors.hero.abilities.mage.WarpBeacon;
import com.soulspixel.soulspixeldungeon.actors.hero.abilities.mage.WildMagic;
import com.soulspixel.soulspixeldungeon.actors.hero.abilities.rogue.DeathMark;
import com.soulspixel.soulspixeldungeon.actors.hero.abilities.rogue.ShadowClone;
import com.soulspixel.soulspixeldungeon.actors.hero.abilities.rogue.SmokeBomb;
import com.soulspixel.soulspixeldungeon.actors.hero.abilities.warrior.Endure;
import com.soulspixel.soulspixeldungeon.actors.hero.abilities.warrior.HeroicLeap;
import com.soulspixel.soulspixeldungeon.actors.hero.abilities.warrior.Shockwave;
import com.soulspixel.soulspixeldungeon.items.BrokenSeal;
import com.soulspixel.soulspixeldungeon.items.Item;
import com.soulspixel.soulspixeldungeon.items.Waterskin;
import com.soulspixel.soulspixeldungeon.items.armor.ClothArmor;
import com.soulspixel.soulspixeldungeon.items.artifacts.CloakOfShadows;
import com.soulspixel.soulspixeldungeon.items.bags.VelvetPouch;
import com.soulspixel.soulspixeldungeon.items.food.Food;
import com.soulspixel.soulspixeldungeon.items.potions.PotionOfHealing;
import com.soulspixel.soulspixeldungeon.items.potions.PotionOfInvisibility;
import com.soulspixel.soulspixeldungeon.items.potions.PotionOfLiquidFlame;
import com.soulspixel.soulspixeldungeon.items.potions.PotionOfMindVision;
import com.soulspixel.soulspixeldungeon.items.potions.PotionOfStrength;
import com.soulspixel.soulspixeldungeon.items.rings.RingOfAccuracy;
import com.soulspixel.soulspixeldungeon.items.rings.RingOfAntiMagic;
import com.soulspixel.soulspixeldungeon.items.scrolls.ScrollOfIdentify;
import com.soulspixel.soulspixeldungeon.items.scrolls.ScrollOfLullaby;
import com.soulspixel.soulspixeldungeon.items.scrolls.ScrollOfMagicMapping;
import com.soulspixel.soulspixeldungeon.items.scrolls.ScrollOfMirrorImage;
import com.soulspixel.soulspixeldungeon.items.scrolls.ScrollOfRage;
import com.soulspixel.soulspixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.soulspixel.soulspixeldungeon.items.wands.WandOfMagicMissile;
import com.soulspixel.soulspixeldungeon.items.weapon.SpiritBow;
import com.soulspixel.soulspixeldungeon.items.weapon.enchantments.Blazing;
import com.soulspixel.soulspixeldungeon.items.weapon.enchantments.Blocking;
import com.soulspixel.soulspixeldungeon.items.weapon.enchantments.Blooming;
import com.soulspixel.soulspixeldungeon.items.weapon.enchantments.Chilling;
import com.soulspixel.soulspixeldungeon.items.weapon.enchantments.Corrupting;
import com.soulspixel.soulspixeldungeon.items.weapon.enchantments.Elastic;
import com.soulspixel.soulspixeldungeon.items.weapon.enchantments.Grim;
import com.soulspixel.soulspixeldungeon.items.weapon.enchantments.Kinetic;
import com.soulspixel.soulspixeldungeon.items.weapon.enchantments.Lucky;
import com.soulspixel.soulspixeldungeon.items.weapon.enchantments.Projecting;
import com.soulspixel.soulspixeldungeon.items.weapon.enchantments.Shocking;
import com.soulspixel.soulspixeldungeon.items.weapon.enchantments.Unstable;
import com.soulspixel.soulspixeldungeon.items.weapon.enchantments.Vampiric;
import com.soulspixel.soulspixeldungeon.items.weapon.enchantments.Vortex;
import com.soulspixel.soulspixeldungeon.items.weapon.melee.Dagger;
import com.soulspixel.soulspixeldungeon.items.weapon.melee.Gloves;
import com.soulspixel.soulspixeldungeon.items.weapon.melee.MagesStaff;
import com.soulspixel.soulspixeldungeon.items.weapon.melee.Rapier;
import com.soulspixel.soulspixeldungeon.items.weapon.melee.Shortsword;
import com.soulspixel.soulspixeldungeon.items.weapon.melee.WornShortsword;
import com.soulspixel.soulspixeldungeon.items.weapon.missiles.ThrowingKnife;
import com.soulspixel.soulspixeldungeon.items.weapon.missiles.ThrowingSpike;
import com.soulspixel.soulspixeldungeon.items.weapon.missiles.ThrowingStone;
import com.soulspixel.soulspixeldungeon.messages.Messages;
import com.watabou.utils.DeviceCompat;

public enum HeroClass {

	WARRIOR( HeroSubClass.BERSERKER, HeroSubClass.GLADIATOR ),
	MAGE( HeroSubClass.BATTLEMAGE, HeroSubClass.WARLOCK ),
	ROGUE( HeroSubClass.ASSASSIN, HeroSubClass.FREERUNNER ),
	HUNTRESS( HeroSubClass.SNIPER, HeroSubClass.WARDEN ),
	DUELIST( HeroSubClass.CHAMPION, HeroSubClass.MONK );

	private HeroSubClass[] subClasses;

	HeroClass( HeroSubClass...subClasses ) {
		this.subClasses = subClasses;
	}

	public void initHero( Hero hero ) {

		hero.heroClass = this;
		Talent.initClassTalents(hero);

		Item i = new ClothArmor().identify();
		if (!Challenges.isItemBlocked(i)) hero.belongings.armor = (ClothArmor)i;

		i = new Food();
		if (!Challenges.isItemBlocked(i)) i.collect();

		new VelvetPouch().collect();
		Dungeon.LimitedDrops.VELVET_POUCH.drop();

		Waterskin waterskin = new Waterskin();
		waterskin.collect();

		new ScrollOfIdentify().identify();

		new ScrollOfMagicMapping().identify().collect();
		new ScrollOfMagicMapping().identify().collect();
		new ScrollOfMagicMapping().identify().collect();
		new ScrollOfMagicMapping().identify().collect();
		new ScrollOfMagicMapping().identify().collect();
		new ScrollOfMagicMapping().identify().collect();
		new ScrollOfMagicMapping().identify().collect();
		new PotionOfHealing().identify().collect();
		new PotionOfHealing().identify().collect();
		new PotionOfHealing().identify().collect();
		new PotionOfHealing().identify().collect();
		new PotionOfHealing().identify().collect();

		/**WornShortsword shortsword = new WornShortsword();
		shortsword.identify();
		shortsword.enchant(new Blazing());
		shortsword.collect();

		WornShortsword shortsword1 = new WornShortsword();
		shortsword1.identify();
		shortsword1.enchant(new Blocking());
		shortsword1.collect();

		WornShortsword shortsword2 = new WornShortsword();
		shortsword2.identify();
		shortsword2.enchant(new Blooming());
		shortsword2.collect();

		WornShortsword shortsword3 = new WornShortsword();
		shortsword3.identify();
		shortsword3.enchant(new Chilling());
		shortsword3.collect();

		WornShortsword shortsword4 = new WornShortsword();
		shortsword4.identify();
		shortsword4.enchant(new Corrupting());
		shortsword4.collect();

		WornShortsword shortsword5 = new WornShortsword();
		shortsword5.identify();
		shortsword5.enchant(new Elastic());
		shortsword5.collect();

		WornShortsword shortsword6 = new WornShortsword();
		shortsword6.identify();
		shortsword6.enchant(new Grim());
		shortsword6.collect();

		WornShortsword shortsword7 = new WornShortsword();
		shortsword7.identify();
		shortsword7.enchant(new Kinetic());
		shortsword7.collect();

		WornShortsword shortsword8 = new WornShortsword();
		shortsword8.identify();
		shortsword8.enchant(new Lucky());
		shortsword8.collect();

		WornShortsword shortsword9 = new WornShortsword();
		shortsword9.identify();
		shortsword9.enchant(new Projecting());
		shortsword9.collect();

		WornShortsword shortsword10 = new WornShortsword();
		shortsword10.identify();
		shortsword10.enchant(new Shocking());
		shortsword10.collect();

		WornShortsword shortsword11 = new WornShortsword();
		shortsword11.identify();
		shortsword11.enchant(new Unstable());
		shortsword11.collect();

		WornShortsword shortsword12 = new WornShortsword();
		shortsword12.identify();
		shortsword12.enchant(new Vampiric());
		shortsword12.collect();

		WornShortsword shortsword13 = new WornShortsword();
		shortsword13.identify();
		shortsword13.enchant(new Vortex());
		shortsword13.collect();

		new RingOfAntiMagic().upgrade(1).identify().collect();
		new RingOfAccuracy().upgrade(1).identify().collect();**/

		switch (this) {
			case WARRIOR:
				initWarrior( hero );
				break;

			case MAGE:
				initMage( hero );
				break;

			case ROGUE:
				initRogue( hero );
				break;

			case HUNTRESS:
				initHuntress( hero );
				break;

			case DUELIST:
				initDuelist( hero );
				break;
		}

		if (SPDSettings.quickslotWaterskin()) {
			for (int s = 0; s < QuickSlot.SIZE; s++) {
				if (Dungeon.quickslot.getItem(s) == null) {
					Dungeon.quickslot.setSlot(s, waterskin);
					break;
				}
			}
		}

	}

	public Badges.Badge masteryBadge() {
		switch (this) {
			case WARRIOR:
				return Badges.Badge.MASTERY_WARRIOR;
			case MAGE:
				return Badges.Badge.MASTERY_MAGE;
			case ROGUE:
				return Badges.Badge.MASTERY_ROGUE;
			case HUNTRESS:
				return Badges.Badge.MASTERY_HUNTRESS;
			case DUELIST:
				return Badges.Badge.MASTERY_DUELIST;
		}
		return null;
	}

	private static void initWarrior( Hero hero ) {
		(hero.belongings.weapon = new WornShortsword()).identify();
		ThrowingStone stones = new ThrowingStone();
		stones.quantity(3).collect();
		Dungeon.quickslot.setSlot(0, stones);

		if (hero.belongings.armor != null){
			hero.belongings.armor.affixSeal(new BrokenSeal());
		}

		new PotionOfHealing().identify();
		new ScrollOfRage().identify();
	}

	private static void initMage( Hero hero ) {
		MagesStaff staff;

		staff = new MagesStaff(new WandOfMagicMissile());

		(hero.belongings.weapon = staff).identify();
		hero.belongings.weapon.activate(hero);

		Dungeon.quickslot.setSlot(0, staff);

		new ScrollOfUpgrade().identify();
		new PotionOfLiquidFlame().identify();
	}

	private static void initRogue( Hero hero ) {
		(hero.belongings.weapon = new Dagger()).identify();

		CloakOfShadows cloak = new CloakOfShadows();
		(hero.belongings.artifact = cloak).identify();
		hero.belongings.artifact.activate( hero );

		ThrowingKnife knives = new ThrowingKnife();
		knives.quantity(3).collect();

		Dungeon.quickslot.setSlot(0, cloak);
		Dungeon.quickslot.setSlot(1, knives);

		new ScrollOfMagicMapping().identify();
		new PotionOfInvisibility().identify();
	}

	private static void initHuntress( Hero hero ) {

		(hero.belongings.weapon = new Gloves()).identify();
		SpiritBow bow = new SpiritBow();
		bow.identify().collect();

		Dungeon.quickslot.setSlot(0, bow);

		new PotionOfMindVision().identify();
		new ScrollOfLullaby().identify();
	}

	private static void initDuelist( Hero hero ) {

		(hero.belongings.weapon = new Rapier()).identify();
		hero.belongings.weapon.activate(hero);

		ThrowingSpike spikes = new ThrowingSpike();
		spikes.quantity(2).collect();

		Dungeon.quickslot.setSlot(0, hero.belongings.weapon);
		Dungeon.quickslot.setSlot(1, spikes);

		new PotionOfStrength().identify();
		new ScrollOfMirrorImage().identify();
	}

	public String title() {
		return Messages.get(HeroClass.class, name());
	}

	public String desc(){
		return Messages.get(HeroClass.class, name()+"_desc");
	}

	public String shortDesc(){
		return Messages.get(HeroClass.class, name()+"_desc_short");
	}

	public HeroSubClass[] subClasses() {
		return subClasses;
	}

	public ArmorAbility[] armorAbilities(){
		switch (this) {
			case WARRIOR: default:
				return new ArmorAbility[]{new HeroicLeap(), new Shockwave(), new Endure()};
			case MAGE:
				return new ArmorAbility[]{new ElementalBlast(), new WildMagic(), new WarpBeacon()};
			case ROGUE:
				return new ArmorAbility[]{new SmokeBomb(), new DeathMark(), new ShadowClone()};
			case HUNTRESS:
				return new ArmorAbility[]{new SpectralBlades(), new NaturesPower(), new SpiritHawk()};
			case DUELIST:
				return new ArmorAbility[]{new Challenge(), new ElementalStrike(), new Feint()};
		}
	}

	public String spritesheet() {
		switch (this) {
			case WARRIOR: default:
				return Assets.Sprites.WARRIOR;
			case MAGE:
				return Assets.Sprites.MAGE;
			case ROGUE:
				return Assets.Sprites.ROGUE;
			case HUNTRESS:
				return Assets.Sprites.HUNTRESS;
			case DUELIST:
				return Assets.Sprites.DUELIST;
		}
	}

	public String splashArt(){
		switch (this) {
			case WARRIOR: default:
				return Assets.Splashes.WARRIOR;
			case MAGE:
				return Assets.Splashes.MAGE;
			case ROGUE:
				return Assets.Splashes.ROGUE;
			case HUNTRESS:
				return Assets.Splashes.HUNTRESS;
			case DUELIST:
				return Assets.Splashes.DUELIST;
		}
	}
	
	public boolean isUnlocked(){
		//always unlock on debug builds
		if (DeviceCompat.isDebug()) return true;

		switch (this){
			case WARRIOR: default:
				return true;
			case MAGE:
				return Badges.isUnlocked(Badges.Badge.UNLOCK_MAGE);
			case ROGUE:
				return Badges.isUnlocked(Badges.Badge.UNLOCK_ROGUE);
			case HUNTRESS:
				return Badges.isUnlocked(Badges.Badge.UNLOCK_HUNTRESS);
			case DUELIST:
				return Badges.isUnlocked(Badges.Badge.UNLOCK_DUELIST);
		}
	}
	
	public String unlockMsg() {
		return shortDesc() + "\n\n" + Messages.get(HeroClass.class, name()+"_unlock");
	}

}
