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

package com.soulspixel.soulspixeldungeon.items;

import com.soulspixel.soulspixeldungeon.Dungeon;
import com.soulspixel.soulspixeldungeon.items.armor.Armor;
import com.soulspixel.soulspixeldungeon.items.armor.ClothArmor;
import com.soulspixel.soulspixeldungeon.items.armor.DuelistArmor;
import com.soulspixel.soulspixeldungeon.items.armor.HuntressArmor;
import com.soulspixel.soulspixeldungeon.items.armor.LeatherArmor;
import com.soulspixel.soulspixeldungeon.items.armor.MageArmor;
import com.soulspixel.soulspixeldungeon.items.armor.MailArmor;
import com.soulspixel.soulspixeldungeon.items.armor.PlateArmor;
import com.soulspixel.soulspixeldungeon.items.armor.RogueArmor;
import com.soulspixel.soulspixeldungeon.items.armor.ScaleArmor;
import com.soulspixel.soulspixeldungeon.items.armor.WarriorArmor;
import com.soulspixel.soulspixeldungeon.items.artifacts.AlchemistsToolkit;
import com.soulspixel.soulspixeldungeon.items.artifacts.Artifact;
import com.soulspixel.soulspixeldungeon.items.artifacts.ChaliceOfBlood;
import com.soulspixel.soulspixeldungeon.items.artifacts.CloakOfShadows;
import com.soulspixel.soulspixeldungeon.items.artifacts.DriedRose;
import com.soulspixel.soulspixeldungeon.items.artifacts.EtherealChains;
import com.soulspixel.soulspixeldungeon.items.artifacts.HornOfPlenty;
import com.soulspixel.soulspixeldungeon.items.artifacts.MasterThievesArmband;
import com.soulspixel.soulspixeldungeon.items.artifacts.SandalsOfNature;
import com.soulspixel.soulspixeldungeon.items.artifacts.TalismanOfForesight;
import com.soulspixel.soulspixeldungeon.items.artifacts.TimekeepersHourglass;
import com.soulspixel.soulspixeldungeon.items.artifacts.UnstableSpellbook;
import com.soulspixel.soulspixeldungeon.items.bombs.Bomb;
import com.soulspixel.soulspixeldungeon.items.food.Food;
import com.soulspixel.soulspixeldungeon.items.food.MysteryMeat;
import com.soulspixel.soulspixeldungeon.items.food.Pasty;
import com.soulspixel.soulspixeldungeon.items.potions.Potion;
import com.soulspixel.soulspixeldungeon.items.potions.PotionOfExperience;
import com.soulspixel.soulspixeldungeon.items.potions.PotionOfFrost;
import com.soulspixel.soulspixeldungeon.items.potions.PotionOfHaste;
import com.soulspixel.soulspixeldungeon.items.potions.PotionOfHealing;
import com.soulspixel.soulspixeldungeon.items.potions.PotionOfInvisibility;
import com.soulspixel.soulspixeldungeon.items.potions.PotionOfLevitation;
import com.soulspixel.soulspixeldungeon.items.potions.PotionOfLiquidFlame;
import com.soulspixel.soulspixeldungeon.items.potions.PotionOfMindVision;
import com.soulspixel.soulspixeldungeon.items.potions.PotionOfParalyticGas;
import com.soulspixel.soulspixeldungeon.items.potions.PotionOfPurity;
import com.soulspixel.soulspixeldungeon.items.potions.PotionOfStrength;
import com.soulspixel.soulspixeldungeon.items.potions.PotionOfToxicGas;
import com.soulspixel.soulspixeldungeon.items.potions.brews.Brew;
import com.soulspixel.soulspixeldungeon.items.potions.elixirs.Elixir;
import com.soulspixel.soulspixeldungeon.items.potions.exotic.ExoticPotion;
import com.soulspixel.soulspixeldungeon.items.rings.Ring;
import com.soulspixel.soulspixeldungeon.items.rings.RingOfAccuracy;
import com.soulspixel.soulspixeldungeon.items.rings.RingOfAntiMagic;
import com.soulspixel.soulspixeldungeon.items.rings.RingOfArcana;
import com.soulspixel.soulspixeldungeon.items.rings.RingOfElements;
import com.soulspixel.soulspixeldungeon.items.rings.RingOfEnergy;
import com.soulspixel.soulspixeldungeon.items.rings.RingOfEvasion;
import com.soulspixel.soulspixeldungeon.items.rings.RingOfForce;
import com.soulspixel.soulspixeldungeon.items.rings.RingOfFuror;
import com.soulspixel.soulspixeldungeon.items.rings.RingOfHaste;
import com.soulspixel.soulspixeldungeon.items.rings.RingOfMight;
import com.soulspixel.soulspixeldungeon.items.rings.RingOfSharpshooting;
import com.soulspixel.soulspixeldungeon.items.rings.RingOfTenacity;
import com.soulspixel.soulspixeldungeon.items.rings.RingOfWealth;
import com.soulspixel.soulspixeldungeon.items.scrolls.Scroll;
import com.soulspixel.soulspixeldungeon.items.scrolls.ScrollOfIdentify;
import com.soulspixel.soulspixeldungeon.items.scrolls.ScrollOfLullaby;
import com.soulspixel.soulspixeldungeon.items.scrolls.ScrollOfMagicMapping;
import com.soulspixel.soulspixeldungeon.items.scrolls.ScrollOfMirrorImage;
import com.soulspixel.soulspixeldungeon.items.scrolls.ScrollOfRage;
import com.soulspixel.soulspixeldungeon.items.scrolls.ScrollOfRecharging;
import com.soulspixel.soulspixeldungeon.items.scrolls.ScrollOfRemoveCurse;
import com.soulspixel.soulspixeldungeon.items.scrolls.ScrollOfRetribution;
import com.soulspixel.soulspixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.soulspixel.soulspixeldungeon.items.scrolls.ScrollOfTerror;
import com.soulspixel.soulspixeldungeon.items.scrolls.ScrollOfTransmutation;
import com.soulspixel.soulspixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.soulspixel.soulspixeldungeon.items.scrolls.exotic.ExoticScroll;
import com.soulspixel.soulspixeldungeon.items.spells.Spell;
import com.soulspixel.soulspixeldungeon.items.stones.Runestone;
import com.soulspixel.soulspixeldungeon.items.stones.StoneOfAggression;
import com.soulspixel.soulspixeldungeon.items.stones.StoneOfAugmentation;
import com.soulspixel.soulspixeldungeon.items.stones.StoneOfBlast;
import com.soulspixel.soulspixeldungeon.items.stones.StoneOfBlink;
import com.soulspixel.soulspixeldungeon.items.stones.StoneOfClairvoyance;
import com.soulspixel.soulspixeldungeon.items.stones.StoneOfDeepSleep;
import com.soulspixel.soulspixeldungeon.items.stones.StoneOfDisarming;
import com.soulspixel.soulspixeldungeon.items.stones.StoneOfEnchantment;
import com.soulspixel.soulspixeldungeon.items.stones.StoneOfFear;
import com.soulspixel.soulspixeldungeon.items.stones.StoneOfFlock;
import com.soulspixel.soulspixeldungeon.items.stones.StoneOfIntuition;
import com.soulspixel.soulspixeldungeon.items.stones.StoneOfShock;
import com.soulspixel.soulspixeldungeon.items.trinkets.DimensionalSundial;
import com.soulspixel.soulspixeldungeon.items.trinkets.ExoticCrystals;
import com.soulspixel.soulspixeldungeon.items.trinkets.EyeOfNewt;
import com.soulspixel.soulspixeldungeon.items.trinkets.MimicTooth;
import com.soulspixel.soulspixeldungeon.items.trinkets.MossyClump;
import com.soulspixel.soulspixeldungeon.items.trinkets.ParchmentScrap;
import com.soulspixel.soulspixeldungeon.items.trinkets.PetrifiedSeed;
import com.soulspixel.soulspixeldungeon.items.trinkets.RatSkull;
import com.soulspixel.soulspixeldungeon.items.trinkets.ThirteenLeafClover;
import com.soulspixel.soulspixeldungeon.items.trinkets.TrapMechanism;
import com.soulspixel.soulspixeldungeon.items.trinkets.Trinket;
import com.soulspixel.soulspixeldungeon.items.trinkets.TrinketCatalyst;
import com.soulspixel.soulspixeldungeon.items.trinkets.WondrousResin;
import com.soulspixel.soulspixeldungeon.items.wands.Wand;
import com.soulspixel.soulspixeldungeon.items.wands.WandOfBlastWave;
import com.soulspixel.soulspixeldungeon.items.wands.WandOfCorrosion;
import com.soulspixel.soulspixeldungeon.items.wands.WandOfCorruption;
import com.soulspixel.soulspixeldungeon.items.wands.WandOfDisintegration;
import com.soulspixel.soulspixeldungeon.items.wands.WandOfFireblast;
import com.soulspixel.soulspixeldungeon.items.wands.WandOfFrost;
import com.soulspixel.soulspixeldungeon.items.wands.WandOfLightning;
import com.soulspixel.soulspixeldungeon.items.wands.WandOfLivingEarth;
import com.soulspixel.soulspixeldungeon.items.wands.WandOfMagicMissile;
import com.soulspixel.soulspixeldungeon.items.wands.WandOfPrismaticLight;
import com.soulspixel.soulspixeldungeon.items.wands.WandOfRegrowth;
import com.soulspixel.soulspixeldungeon.items.wands.WandOfTransfusion;
import com.soulspixel.soulspixeldungeon.items.wands.WandOfWarding;
import com.soulspixel.soulspixeldungeon.items.weapon.melee.AssassinsBlade;
import com.soulspixel.soulspixeldungeon.items.weapon.melee.BattleAxe;
import com.soulspixel.soulspixeldungeon.items.weapon.melee.Crossbow;
import com.soulspixel.soulspixeldungeon.items.weapon.melee.Dagger;
import com.soulspixel.soulspixeldungeon.items.weapon.melee.Dirk;
import com.soulspixel.soulspixeldungeon.items.weapon.melee.Flail;
import com.soulspixel.soulspixeldungeon.items.weapon.melee.Gauntlet;
import com.soulspixel.soulspixeldungeon.items.weapon.melee.Glaive;
import com.soulspixel.soulspixeldungeon.items.weapon.melee.Gloves;
import com.soulspixel.soulspixeldungeon.items.weapon.melee.Greataxe;
import com.soulspixel.soulspixeldungeon.items.weapon.melee.Greatshield;
import com.soulspixel.soulspixeldungeon.items.weapon.melee.Greatsword;
import com.soulspixel.soulspixeldungeon.items.weapon.melee.HandAxe;
import com.soulspixel.soulspixeldungeon.items.weapon.melee.Katana;
import com.soulspixel.soulspixeldungeon.items.weapon.melee.Longsword;
import com.soulspixel.soulspixeldungeon.items.weapon.melee.Mace;
import com.soulspixel.soulspixeldungeon.items.weapon.melee.MagesStaff;
import com.soulspixel.soulspixeldungeon.items.weapon.melee.MeleeWeapon;
import com.soulspixel.soulspixeldungeon.items.weapon.melee.Quarterstaff;
import com.soulspixel.soulspixeldungeon.items.weapon.melee.Rapier;
import com.soulspixel.soulspixeldungeon.items.weapon.melee.RoundShield;
import com.soulspixel.soulspixeldungeon.items.weapon.melee.RunicBlade;
import com.soulspixel.soulspixeldungeon.items.weapon.melee.Sai;
import com.soulspixel.soulspixeldungeon.items.weapon.melee.Scimitar;
import com.soulspixel.soulspixeldungeon.items.weapon.melee.Shortsword;
import com.soulspixel.soulspixeldungeon.items.weapon.melee.Sickle;
import com.soulspixel.soulspixeldungeon.items.weapon.melee.Spear;
import com.soulspixel.soulspixeldungeon.items.weapon.melee.Sword;
import com.soulspixel.soulspixeldungeon.items.weapon.melee.WarHammer;
import com.soulspixel.soulspixeldungeon.items.weapon.melee.WarScythe;
import com.soulspixel.soulspixeldungeon.items.weapon.melee.Whip;
import com.soulspixel.soulspixeldungeon.items.weapon.melee.WornShortsword;
import com.soulspixel.soulspixeldungeon.items.weapon.missiles.Bolas;
import com.soulspixel.soulspixeldungeon.items.weapon.missiles.FishingSpear;
import com.soulspixel.soulspixeldungeon.items.weapon.missiles.ForceCube;
import com.soulspixel.soulspixeldungeon.items.weapon.missiles.HeavyBoomerang;
import com.soulspixel.soulspixeldungeon.items.weapon.missiles.Javelin;
import com.soulspixel.soulspixeldungeon.items.weapon.missiles.Kunai;
import com.soulspixel.soulspixeldungeon.items.weapon.missiles.MissileWeapon;
import com.soulspixel.soulspixeldungeon.items.weapon.missiles.Shuriken;
import com.soulspixel.soulspixeldungeon.items.weapon.missiles.ThrowingClub;
import com.soulspixel.soulspixeldungeon.items.weapon.missiles.ThrowingHammer;
import com.soulspixel.soulspixeldungeon.items.weapon.missiles.ThrowingKnife;
import com.soulspixel.soulspixeldungeon.items.weapon.missiles.ThrowingSpear;
import com.soulspixel.soulspixeldungeon.items.weapon.missiles.ThrowingSpike;
import com.soulspixel.soulspixeldungeon.items.weapon.missiles.ThrowingStone;
import com.soulspixel.soulspixeldungeon.items.weapon.missiles.Tomahawk;
import com.soulspixel.soulspixeldungeon.items.weapon.missiles.Trident;
import com.soulspixel.soulspixeldungeon.plants.Blindweed;
import com.soulspixel.soulspixeldungeon.plants.Earthroot;
import com.soulspixel.soulspixeldungeon.plants.Fadeleaf;
import com.soulspixel.soulspixeldungeon.plants.Firebloom;
import com.soulspixel.soulspixeldungeon.plants.Icecap;
import com.soulspixel.soulspixeldungeon.plants.Mageroyal;
import com.soulspixel.soulspixeldungeon.plants.Plant;
import com.soulspixel.soulspixeldungeon.plants.Rotberry;
import com.soulspixel.soulspixeldungeon.plants.Sorrowmoss;
import com.soulspixel.soulspixeldungeon.plants.Starflower;
import com.soulspixel.soulspixeldungeon.plants.Stormvine;
import com.soulspixel.soulspixeldungeon.plants.Sungrass;
import com.soulspixel.soulspixeldungeon.plants.Swiftthistle;
import com.watabou.utils.Bundle;
import com.watabou.utils.GameMath;
import com.watabou.utils.Random;
import com.watabou.utils.Reflection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Generator {

	public enum Category {
		TRINKET ( 0, 0, Trinket.class),

		WEAPON	( 2, 2, MeleeWeapon.class),
		WEP_T1	( 0, 0, MeleeWeapon.class),
		WEP_T2	( 0, 0, MeleeWeapon.class),
		WEP_T3	( 0, 0, MeleeWeapon.class),
		WEP_T4	( 0, 0, MeleeWeapon.class),
		WEP_T5	( 0, 0, MeleeWeapon.class),
		
		ARMOR	( 2, 1, Armor.class ),
		
		MISSILE ( 1, 2, MissileWeapon.class ),
		MIS_T1  ( 0, 0, MissileWeapon.class ),
		MIS_T2  ( 0, 0, MissileWeapon.class ),
		MIS_T3  ( 0, 0, MissileWeapon.class ),
		MIS_T4  ( 0, 0, MissileWeapon.class ),
		MIS_T5  ( 0, 0, MissileWeapon.class ),
		
		WAND	( 1, 1, Wand.class ),
		RING	( 1, 0, Ring.class ),
		ARTIFACT( 0, 1, Artifact.class),
		
		FOOD	( 0, 0, Food.class ),
		
		POTION	( 8, 8, Potion.class ),
		SEED	( 1, 1, Plant.Seed.class ),
		
		SCROLL	( 8, 8, Scroll.class ),
		STONE   ( 1, 1, Runestone.class),
		
		GOLD	( 10, 10,   Gold.class );
		
		public Class<?>[] classes;

		//some item types use a deck-based system, where the probs decrement as items are picked
		// until they are all 0, and then they reset. Those generator classes should define
		// defaultProbs. If defaultProbs is null then a deck system isn't used.
		//Artifacts in particular don't reset, no duplicates!
		public float[] probs;
		public float[] defaultProbs = null;

		//some items types have two decks and swap between them
		// this enforces more consistency while still allowing for better precision
		public float[] defaultProbs2 = null;
		public boolean using2ndProbs = false;
		//but in such cases we still need a reference to the full deck in case of non-deck generation
		public float[] defaultProbsTotal = null;

		//These variables are used as a part of the deck system, to ensure that drops are consistent
		// regardless of when they occur (either as part of seeded levelgen, or random item drops)
		public Long seed = null;
		public int dropped = 0;

		//game has two decks of 35 items for overall category probs
		//one deck has a ring and extra armor, the other has an artifact and extra thrown weapon
		//Note that pure random drops only happen as part of levelgen atm, so no seed is needed here
		public float firstProb;
		public float secondProb;
		public Class<? extends Item> superClass;
		
		private Category( float firstProb, float secondProb, Class<? extends Item> superClass ) {
			this.firstProb = firstProb;
			this.secondProb = secondProb;
			this.superClass = superClass;
		}

		//some generator categories can have ordering within that category as well
		// note that sub category ordering doesn't need to always include items that belong
		// to that categories superclass, e.g. bombs are ordered within thrown weapons
		private static HashMap<Class, ArrayList<Class>> subOrderings = new HashMap<>();
		static {
			subOrderings.put(Trinket.class, new ArrayList<>(Arrays.asList(Trinket.class, TrinketCatalyst.class)));
			subOrderings.put(MissileWeapon.class, new ArrayList<>(Arrays.asList(MissileWeapon.class, Bomb.class)));
			subOrderings.put(Potion.class, new ArrayList<>(Arrays.asList(Potion.class, ExoticPotion.class, Brew.class, Elixir.class, LiquidMetal.class)));
			subOrderings.put(Scroll.class, new ArrayList<>(Arrays.asList(Scroll.class, ExoticScroll.class, Spell.class, ArcaneResin.class)));
		}

		//in case there are multiple matches, this will return the latest match
		public static int order( Item item ) {
			int catResult = -1, subResult = 0;
			for (int i=0; i < values().length; i++) {
				ArrayList<Class> subOrdering = subOrderings.get(values()[i].superClass);
				if (subOrdering != null){
					for (int j=0; j < subOrdering.size(); j++){
						if (subOrdering.get(j).isInstance(item)){
							catResult = i;
							subResult = j;
						}
					}
				} else {
					if (values()[i].superClass.isInstance(item)) {
						catResult = i;
						subResult = 0;
					}
				}
			}
			if (catResult != -1) return catResult*100 + subResult;

			//items without a category-defined order are sorted based on the spritesheet
			return Short.MAX_VALUE+item.image();
		}

		static {
			GOLD.classes = new Class<?>[]{
					Gold.class };
			GOLD.probs = new float[]{ 1 };
			
			POTION.classes = new Class<?>[]{
					PotionOfStrength.class, //2 drop every chapter, see Dungeon.posNeeded()
					PotionOfHealing.class,
					PotionOfMindVision.class,
					PotionOfFrost.class,
					PotionOfLiquidFlame.class,
					PotionOfToxicGas.class,
					PotionOfHaste.class,
					PotionOfInvisibility.class,
					PotionOfLevitation.class,
					PotionOfParalyticGas.class,
					PotionOfPurity.class,
					PotionOfExperience.class};
			POTION.defaultProbs  = new float[]{ 0, 3, 2, 1, 2, 1, 1, 1, 1, 1, 1, 1 };
			POTION.defaultProbs2 = new float[]{ 0, 3, 2, 2, 1, 2, 1, 1, 1, 1, 1, 0 };
			POTION.probs = POTION.defaultProbs.clone();
			
			SEED.classes = new Class<?>[]{
					Rotberry.Seed.class, //quest item
					Sungrass.Seed.class,
					Fadeleaf.Seed.class,
					Icecap.Seed.class,
					Firebloom.Seed.class,
					Sorrowmoss.Seed.class,
					Swiftthistle.Seed.class,
					Blindweed.Seed.class,
					Stormvine.Seed.class,
					Earthroot.Seed.class,
					Mageroyal.Seed.class,
					Starflower.Seed.class};
			SEED.defaultProbs = new float[]{ 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1 };
			SEED.probs = SEED.defaultProbs.clone();
			
			SCROLL.classes = new Class<?>[]{
					ScrollOfUpgrade.class, //3 drop every chapter, see Dungeon.souNeeded()
					ScrollOfIdentify.class,
					ScrollOfRemoveCurse.class,
					ScrollOfMirrorImage.class,
					ScrollOfRecharging.class,
					ScrollOfTeleportation.class,
					ScrollOfLullaby.class,
					ScrollOfMagicMapping.class,
					ScrollOfRage.class,
					ScrollOfRetribution.class,
					ScrollOfTerror.class,
					ScrollOfTransmutation.class
			};
			SCROLL.defaultProbs  = new float[]{ 0, 3, 2, 1, 2, 1, 1, 1, 1, 1, 1, 1 };
			SCROLL.defaultProbs2 = new float[]{ 0, 3, 2, 2, 1, 2, 1, 1, 1, 1, 1, 0 };
			SCROLL.probs = SCROLL.defaultProbs.clone();
			
			STONE.classes = new Class<?>[]{
					StoneOfEnchantment.class,   //1 is guaranteed to drop on floors 6-19
					StoneOfIntuition.class,     //1 additional stone is also dropped on floors 1-3
					StoneOfDisarming.class,
					StoneOfFlock.class,
					StoneOfShock.class,
					StoneOfBlink.class,
					StoneOfDeepSleep.class,
					StoneOfClairvoyance.class,
					StoneOfAggression.class,
					StoneOfBlast.class,
					StoneOfFear.class,
					StoneOfAugmentation.class  //1 is sold in each shop
			};
			STONE.defaultProbs = new float[]{ 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0 };
			STONE.probs = STONE.defaultProbs.clone();

			WAND.classes = new Class<?>[]{
					WandOfMagicMissile.class,
					WandOfLightning.class,
					WandOfDisintegration.class,
					WandOfFireblast.class,
					WandOfCorrosion.class,
					WandOfBlastWave.class,
					WandOfLivingEarth.class,
					WandOfFrost.class,
					WandOfPrismaticLight.class,
					WandOfWarding.class,
					WandOfTransfusion.class,
					WandOfCorruption.class,
					WandOfRegrowth.class };
			WAND.defaultProbs = new float[]{ 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 };
			WAND.probs = WAND.defaultProbs.clone();
			
			//see generator.randomWeapon
			WEAPON.classes = new Class<?>[]{};
			WEAPON.probs = new float[]{};
			
			WEP_T1.classes = new Class<?>[]{
					WornShortsword.class,
					MagesStaff.class,
					Dagger.class,
					Gloves.class,
					Rapier.class
			};
			WEP_T1.defaultProbs = new float[]{ 2, 0, 2, 2, 2 };
			WEP_T1.probs = WEP_T1.defaultProbs.clone();
			
			WEP_T2.classes = new Class<?>[]{
					Shortsword.class,
					HandAxe.class,
					Spear.class,
					Quarterstaff.class,
					Dirk.class,
					Sickle.class
			};
			WEP_T2.defaultProbs = new float[]{ 2, 2, 2, 2, 2, 2 };
			WEP_T2.probs = WEP_T2.defaultProbs.clone();
			
			WEP_T3.classes = new Class<?>[]{
					Sword.class,
					Mace.class,
					Scimitar.class,
					RoundShield.class,
					Sai.class,
					Whip.class
			};
			WEP_T3.defaultProbs = new float[]{ 2, 2, 2, 2, 2, 2 };
			WEP_T3.probs = WEP_T1.defaultProbs.clone();
			
			WEP_T4.classes = new Class<?>[]{
					Longsword.class,
					BattleAxe.class,
					Flail.class,
					RunicBlade.class,
					AssassinsBlade.class,
					Crossbow.class,
					Katana.class
			};
			WEP_T4.defaultProbs = new float[]{ 2, 2, 2, 2, 2, 2, 2 };
			WEP_T4.probs = WEP_T4.defaultProbs.clone();
			
			WEP_T5.classes = new Class<?>[]{
					Greatsword.class,
					WarHammer.class,
					Glaive.class,
					Greataxe.class,
					Greatshield.class,
					Gauntlet.class,
					WarScythe.class
			};
			WEP_T5.defaultProbs = new float[]{ 2, 2, 2, 2, 2, 2, 2 };
			WEP_T5.probs = WEP_T5.defaultProbs.clone();
			
			//see Generator.randomArmor
			ARMOR.classes = new Class<?>[]{
					ClothArmor.class,
					LeatherArmor.class,
					MailArmor.class,
					ScaleArmor.class,
					PlateArmor.class,
					WarriorArmor.class,
					MageArmor.class,
					RogueArmor.class,
					HuntressArmor.class,
					DuelistArmor.class
			};
			ARMOR.probs = new float[]{ 1, 1, 1, 1, 1, 0, 0, 0, 0, 0 };
			
			//see Generator.randomMissile
			MISSILE.classes = new Class<?>[]{};
			MISSILE.probs = new float[]{};
			
			MIS_T1.classes = new Class<?>[]{
					ThrowingStone.class,
					ThrowingKnife.class,
					ThrowingSpike.class
			};
			MIS_T1.defaultProbs = new float[]{ 3, 3, 3 };
			MIS_T1.probs = MIS_T1.defaultProbs.clone();
			
			MIS_T2.classes = new Class<?>[]{
					FishingSpear.class,
					ThrowingClub.class,
					Shuriken.class
			};
			MIS_T2.defaultProbs = new float[]{ 3, 3, 3 };
			MIS_T2.probs = MIS_T2.defaultProbs.clone();
			
			MIS_T3.classes = new Class<?>[]{
					ThrowingSpear.class,
					Kunai.class,
					Bolas.class
			};
			MIS_T3.defaultProbs = new float[]{ 3, 3, 3 };
			MIS_T3.probs = MIS_T3.defaultProbs.clone();
			
			MIS_T4.classes = new Class<?>[]{
					Javelin.class,
					Tomahawk.class,
					HeavyBoomerang.class
			};
			MIS_T4.defaultProbs = new float[]{ 3, 3, 3 };
			MIS_T4.probs = MIS_T4.defaultProbs.clone();
			
			MIS_T5.classes = new Class<?>[]{
					Trident.class,
					ThrowingHammer.class,
					ForceCube.class
			};
			MIS_T5.defaultProbs = new float[]{ 3, 3, 3 };
			MIS_T5.probs = MIS_T5.defaultProbs.clone();
			
			FOOD.classes = new Class<?>[]{
					Food.class,
					Pasty.class,
					MysteryMeat.class };
			FOOD.defaultProbs = new float[]{ 4, 1, 0 };
			FOOD.probs = FOOD.defaultProbs.clone();
			
			RING.classes = new Class<?>[]{
					RingOfAccuracy.class,
					RingOfArcana.class,
					RingOfElements.class,
					RingOfEnergy.class,
					RingOfEvasion.class,
					RingOfForce.class,
					RingOfFuror.class,
					RingOfHaste.class,
					RingOfMight.class,
					RingOfSharpshooting.class,
					RingOfTenacity.class,
					RingOfWealth.class,
					RingOfAntiMagic.class};
			RING.defaultProbs = new float[]{ 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 };
			RING.probs = RING.defaultProbs.clone();
			
			ARTIFACT.classes = new Class<?>[]{
					AlchemistsToolkit.class,
					ChaliceOfBlood.class,
					CloakOfShadows.class,
					DriedRose.class,
					EtherealChains.class,
					HornOfPlenty.class,
					MasterThievesArmband.class,
					SandalsOfNature.class,
					TalismanOfForesight.class,
					TimekeepersHourglass.class,
					UnstableSpellbook.class
			};
			ARTIFACT.defaultProbs = new float[]{ 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1 };
			ARTIFACT.probs = ARTIFACT.defaultProbs.clone();

			//Trinkets are unique like artifacts, but unlike them you can only have one at once
			//So we don't need the same enforcement of uniqueness
			TRINKET.classes = new Class<?>[]{
					RatSkull.class,
					ParchmentScrap.class,
					PetrifiedSeed.class,
					ExoticCrystals.class,
					MossyClump.class,
					DimensionalSundial.class,
					ThirteenLeafClover.class,
					TrapMechanism.class,
					MimicTooth.class,
					WondrousResin.class,
					EyeOfNewt.class
			};
			TRINKET.defaultProbs = new float[]{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
			TRINKET.probs = TRINKET.defaultProbs.clone();

			for (Category cat : Category.values()){
				if (cat.defaultProbs2 != null){
					cat.defaultProbsTotal = new float[cat.defaultProbs.length];
					for (int i = 0; i < cat.defaultProbs.length; i++){
						cat.defaultProbsTotal[i] = cat.defaultProbs[i] + cat.defaultProbs2[i];
					}
				}
			}
		}
	}

	private static final float[][] floorSetTierProbs = new float[][] {
			{0, 75, 20,  4,  1},
			{0, 25, 50, 20,  5},
			{0,  0, 40, 50, 10},
			{0,  0, 20, 40, 40},
			{0,  0,  0, 20, 80}
	};

	private static boolean usingFirstDeck = false;
	private static HashMap<Category,Float> defaultCatProbs = new LinkedHashMap<>();
	private static HashMap<Category,Float> categoryProbs = new LinkedHashMap<>();

	public static void fullReset() {
		usingFirstDeck = Random.Int(2) == 0;
		generalReset();
		for (Category cat : Category.values()) {
			cat.using2ndProbs =  cat.defaultProbs2 != null && Random.Int(2) == 0;
			reset(cat);
			if (cat.defaultProbs != null) {
				cat.seed = Random.Long();
				cat.dropped = 0;
			}
		}
	}

	public static void generalReset(){
		for (Category cat : Category.values()) {
			categoryProbs.put( cat, usingFirstDeck ? cat.firstProb : cat.secondProb );
			defaultCatProbs.put( cat, cat.firstProb + cat.secondProb );
		}
	}

	public static void reset(Category cat){
		if (cat.defaultProbs != null) {
			if (cat.defaultProbs2 != null){
				cat.using2ndProbs = !cat.using2ndProbs;
				cat.probs = cat.using2ndProbs ? cat.defaultProbs2.clone() : cat.defaultProbs.clone();
			} else {
				cat.probs = cat.defaultProbs.clone();
			}
		}
	}

	//reverts changes to drop chances generates by this item
	//equivalent of shuffling the card back into the deck, does not preserve order!
	public static void undoDrop(Item item){
		undoDrop(item.getClass());
	}

	public static void undoDrop(Class cls){
		for (Category cat : Category.values()){
			if (cls.isAssignableFrom(cat.superClass)){
				if (cat.defaultProbs == null) continue;
				for (int i = 0; i < cat.classes.length; i++){
					if (cls == cat.classes[i]){
						cat.probs[i]++;
					}
				}
			}
		}
	}
	
	public static Item random() {
		Category cat = Random.chances( categoryProbs );
		if (cat == null){
			usingFirstDeck = !usingFirstDeck;
			generalReset();
			cat = Random.chances( categoryProbs );
		}
		categoryProbs.put( cat, categoryProbs.get( cat ) - 1);

		if (cat == Category.SEED) {
			//We specifically use defaults for seeds here because, unlike other item categories
			// their predominant source of drops is grass, not levelgen. This way the majority
			// of seed drops still use a deck, but the few that are spawned by levelgen are consistent
			return randomUsingDefaults(cat);
		} else {
			return random(cat);
		}
	}

	public static Item randomUsingDefaults(){
		return randomUsingDefaults(Random.chances( defaultCatProbs ));
	}
	
	public static Item random( Category cat ) {
		switch (cat) {
			case ARMOR:
				return randomArmor();
			case WEAPON:
				return randomWeapon();
			case MISSILE:
				return randomMissile();
			case ARTIFACT:
				Item item = randomArtifact();
				//if we're out of artifacts, return a ring instead.
				return item != null ? item : random(Category.RING);
			default:
				if (cat.defaultProbs != null && cat.seed != null){
					Random.pushGenerator(cat.seed);
					for (int i = 0; i < cat.dropped; i++) Random.Long();
				}

				int i = Random.chances(cat.probs);
				if (i == -1) {
					reset(cat);
					i = Random.chances(cat.probs);
				}
				if (cat.defaultProbs != null) cat.probs[i]--;
				Class<?> itemCls = cat.classes[i];

				if (cat.defaultProbs != null && cat.seed != null){
					Random.popGenerator();
					cat.dropped++;
				}

				if (ExoticPotion.regToExo.containsKey(itemCls)){
					if (Random.Float() < ExoticCrystals.consumableExoticChance()){
						itemCls = ExoticPotion.regToExo.get(itemCls);
					}
				} else if (ExoticScroll.regToExo.containsKey(itemCls)){
					if (Random.Float() < ExoticCrystals.consumableExoticChance()){
						itemCls = ExoticScroll.regToExo.get(itemCls);
					}
				}

				return ((Item) Reflection.newInstance(itemCls)).random();
		}
	}

	//overrides any deck systems and always uses default probs
	// except for artifacts, which must always use a deck
	public static Item randomUsingDefaults( Category cat ){
		if (cat == Category.WEAPON){
			return randomWeapon(true);
		} else if (cat == Category.MISSILE){
			return randomMissile(true);
		} else if (cat.defaultProbs == null || cat == Category.ARTIFACT) {
			return random(cat);
		} else if (cat.defaultProbsTotal != null){
			return ((Item) Reflection.newInstance(cat.classes[Random.chances(cat.defaultProbsTotal)])).random();
		} else {
			return ((Item) Reflection.newInstance(cat.classes[Random.chances(cat.defaultProbs)])).random();
		}
	}
	
	public static Item random( Class<? extends Item> cl ) {
		return Reflection.newInstance(cl).random();
	}

	public static Armor randomArmor(){
		return randomArmor(Dungeon.depth / 5);
	}
	
	public static Armor randomArmor(int floorSet) {

		floorSet = (int)GameMath.gate(0, floorSet, floorSetTierProbs.length-1);
		
		Armor a = (Armor)Reflection.newInstance(Category.ARMOR.classes[Random.chances(floorSetTierProbs[floorSet])]);
		a.random();
		return a;
	}

	public static final Category[] wepTiers = new Category[]{
			Category.WEP_T1,
			Category.WEP_T2,
			Category.WEP_T3,
			Category.WEP_T4,
			Category.WEP_T5
	};

	public static MeleeWeapon randomWeapon(){
		return randomWeapon(Dungeon.depth / 5);
	}

	public static MeleeWeapon randomWeapon(int floorSet) {
		return randomWeapon(floorSet, false);
	}

	public static MeleeWeapon randomWeapon(boolean useDefaults) {
		return randomWeapon(Dungeon.depth / 5, useDefaults);
	}
	
	public static MeleeWeapon randomWeapon(int floorSet, boolean useDefaults) {

		floorSet = (int)GameMath.gate(0, floorSet, floorSetTierProbs.length-1);

		MeleeWeapon w;
		if (useDefaults){
			w = (MeleeWeapon) randomUsingDefaults(wepTiers[Random.chances(floorSetTierProbs[floorSet])]);
		} else {
			w = (MeleeWeapon) random(wepTiers[Random.chances(floorSetTierProbs[floorSet])]);
		}
		return w;
	}
	
	public static final Category[] misTiers = new Category[]{
			Category.MIS_T1,
			Category.MIS_T2,
			Category.MIS_T3,
			Category.MIS_T4,
			Category.MIS_T5
	};
	
	public static MissileWeapon randomMissile(){
		return randomMissile(Dungeon.depth / 5);
	}

	public static MissileWeapon randomMissile(int floorSet) {
		return randomMissile(floorSet, false);
	}

	public static MissileWeapon randomMissile(boolean useDefaults) {
		return randomMissile(Dungeon.depth / 5, useDefaults);
	}

	public static MissileWeapon randomMissile(int floorSet, boolean useDefaults) {
		
		floorSet = (int)GameMath.gate(0, floorSet, floorSetTierProbs.length-1);

		MissileWeapon w;
		if (useDefaults){
			w = (MissileWeapon)randomUsingDefaults(misTiers[Random.chances(floorSetTierProbs[floorSet])]);
		} else {
			w = (MissileWeapon)random(misTiers[Random.chances(floorSetTierProbs[floorSet])]);
		}
		return w;
	}

	//enforces uniqueness of artifacts throughout a run.
	public static Artifact randomArtifact() {

		Category cat = Category.ARTIFACT;

		if (cat.defaultProbs != null && cat.seed != null){
			Random.pushGenerator(cat.seed);
			for (int i = 0; i < cat.dropped; i++) Random.Long();
		}

		int i = Random.chances( cat.probs );

		if (cat.defaultProbs != null && cat.seed != null){
			Random.popGenerator();
			cat.dropped++;
		}

		//if no artifacts are left, return null
		if (i == -1){
			return null;
		}

		cat.probs[i]--;
		return (Artifact) Reflection.newInstance((Class<? extends Artifact>) cat.classes[i]).random();

	}

	public static boolean removeArtifact(Class<?extends Artifact> artifact) {
		Category cat = Category.ARTIFACT;
		for (int i = 0; i < cat.classes.length; i++){
			if (cat.classes[i].equals(artifact) && cat.probs[i] > 0) {
				cat.probs[i] = 0;
				return true;
			}
		}
		return false;
	}

	private static final String FIRST_DECK = "first_deck";
	private static final String GENERAL_PROBS = "general_probs";
	private static final String CATEGORY_PROBS = "_probs";
	private static final String CATEGORY_USING_PROBS2 = "_using_probs2";
	private static final String CATEGORY_SEED = "_seed";
	private static final String CATEGORY_DROPPED = "_dropped";

	public static void storeInBundle(Bundle bundle) {
		bundle.put(FIRST_DECK, usingFirstDeck);

		Float[] genProbs = categoryProbs.values().toArray(new Float[0]);
		float[] storeProbs = new float[genProbs.length];
		for (int i = 0; i < storeProbs.length; i++){
			storeProbs[i] = genProbs[i];
		}
		bundle.put( GENERAL_PROBS, storeProbs);

		for (Category cat : Category.values()){
			if (cat.defaultProbs == null) continue;

			bundle.put(cat.name().toLowerCase() + CATEGORY_PROBS, cat.probs);

			if (cat.defaultProbs2 != null){
				bundle.put(cat.name().toLowerCase() + CATEGORY_USING_PROBS2, cat.using2ndProbs);
			}

			if (cat.seed != null) {
				bundle.put(cat.name().toLowerCase() + CATEGORY_SEED, cat.seed);
				bundle.put(cat.name().toLowerCase() + CATEGORY_DROPPED, cat.dropped);
			}
		}
	}

	public static void restoreFromBundle(Bundle bundle) {
		fullReset();

		usingFirstDeck = bundle.getBoolean(FIRST_DECK);

		if (bundle.contains(GENERAL_PROBS)){
			float[] probs = bundle.getFloatArray(GENERAL_PROBS);
			if (probs.length == Category.values().length) {
				for (int i = 0; i < probs.length; i++) {
					categoryProbs.put(Category.values()[i], probs[i]);
				}
			}
		}

		for (Category cat : Category.values()){
			if (bundle.contains(cat.name().toLowerCase() + CATEGORY_PROBS)){
				float[] probs = bundle.getFloatArray(cat.name().toLowerCase() + CATEGORY_PROBS);
				if (cat.defaultProbs != null && probs.length == cat.defaultProbs.length){
					cat.probs = probs;
				}
				if (bundle.contains(cat.name().toLowerCase() + CATEGORY_USING_PROBS2)){
					cat.using2ndProbs = bundle.getBoolean(cat.name().toLowerCase() + CATEGORY_USING_PROBS2);
				} else {
					cat.using2ndProbs = false;
				}
				if (bundle.contains(cat.name().toLowerCase() + CATEGORY_SEED)){
					cat.seed = bundle.getLong(cat.name().toLowerCase() + CATEGORY_SEED);
					cat.dropped = bundle.getInt(cat.name().toLowerCase() + CATEGORY_DROPPED);
				}
			}
		}
		
	}
}
