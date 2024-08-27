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
import com.soulspixel.soulspixeldungeon.items.food.Food;
import com.soulspixel.soulspixeldungeon.items.food.MysteryMeat;
import com.soulspixel.soulspixeldungeon.items.food.Pasty;
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
import com.soulspixel.soulspixeldungeon.items.trinkets.WondrousResin;
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
import com.soulspixel.soulspixeldungeon.plants.Rotberry;
import com.soulspixel.soulspixeldungeon.plants.Sorrowmoss;
import com.soulspixel.soulspixeldungeon.plants.Starflower;
import com.soulspixel.soulspixeldungeon.plants.Stormvine;
import com.soulspixel.soulspixeldungeon.plants.Sungrass;
import com.soulspixel.soulspixeldungeon.plants.Swiftthistle;
import com.soulspixel.soulspixeldungeon.sprites.ItemSpriteSheet;

import java.util.HashMap;

public class ShowItems {

	public static HashMap<Class<?>, Integer> showableItems = new HashMap<>();

	public static void loadShowableItems(){

		         showableItems.put(PotionOfStrength.class, ItemSpriteSheet.POTION_TURQUOISE);
				 showableItems.put(PotionOfHealing.class, ItemSpriteSheet.POTION_SILVER);
				 showableItems.put(PotionOfMindVision.class, ItemSpriteSheet.POTION_MAGENTA);
				 showableItems.put(PotionOfFrost.class, ItemSpriteSheet.POTION_JADE);
				 showableItems.put(PotionOfLiquidFlame.class, ItemSpriteSheet.POTION_IVORY);
				 showableItems.put(PotionOfToxicGas.class, ItemSpriteSheet.POTION_INDIGO);
				 showableItems.put(PotionOfHaste.class, ItemSpriteSheet.POTION_GOLDEN);
				 showableItems.put(PotionOfInvisibility.class, ItemSpriteSheet.POTION_CRIMSON);
				 showableItems.put(PotionOfLevitation.class, ItemSpriteSheet.POTION_CHARCOAL);
				 showableItems.put(PotionOfParalyticGas.class, ItemSpriteSheet.POTION_BISTRE);
				 showableItems.put(PotionOfPurity.class, ItemSpriteSheet.POTION_AZURE);
				 showableItems.put(PotionOfExperience.class, ItemSpriteSheet.POTION_AMBER);
				 showableItems.put(Rotberry.class, ItemSpriteSheet.SEED_ROTBERRY);
				 showableItems.put(Sungrass.class, ItemSpriteSheet.SEED_SUNGRASS);
				 showableItems.put(Fadeleaf.class, ItemSpriteSheet.SEED_FADELEAF);
				 showableItems.put(Icecap.class, ItemSpriteSheet.SEED_ICECAP);
				 showableItems.put(Firebloom.class, ItemSpriteSheet.SEED_FIREBLOOM);
				 showableItems.put(Sorrowmoss.class, ItemSpriteSheet.SEED_SORROWMOSS);
				 showableItems.put(Swiftthistle.class, ItemSpriteSheet.SEED_SWIFTTHISTLE);
				 showableItems.put(Blindweed.class, ItemSpriteSheet.SEED_BLINDWEED);
				 showableItems.put(Stormvine.class, ItemSpriteSheet.SEED_STORMVINE);
				 showableItems.put(Earthroot.class, ItemSpriteSheet.SEED_EARTHROOT);
				 showableItems.put(Mageroyal.class, ItemSpriteSheet.SEED_MAGEROYAL);
				 showableItems.put(Starflower.class, ItemSpriteSheet.SEED_STARFLOWER);
				 showableItems.put(ScrollOfUpgrade.class, ItemSpriteSheet.SCROLL_YNGVI);
				 showableItems.put(ScrollOfIdentify.class, ItemSpriteSheet.SCROLL_TIWAZ);
				 showableItems.put(ScrollOfRemoveCurse.class, ItemSpriteSheet.SCROLL_SOWILO);
				 showableItems.put(ScrollOfMirrorImage.class, ItemSpriteSheet.SCROLL_RAIDO);
				 showableItems.put(ScrollOfRecharging.class, ItemSpriteSheet.SCROLL_ODAL);
				 showableItems.put(ScrollOfTeleportation.class, ItemSpriteSheet.SCROLL_NAUDIZ);
				 showableItems.put(ScrollOfLullaby.class, ItemSpriteSheet.SCROLL_MANNAZ);
				 showableItems.put(ScrollOfMagicMapping.class, ItemSpriteSheet.SCROLL_LAGUZ);
				 showableItems.put(ScrollOfRage.class, ItemSpriteSheet.SCROLL_KAUNAN);
				 showableItems.put(ScrollOfRetribution.class, ItemSpriteSheet.SCROLL_ISAZ);
				 showableItems.put(ScrollOfTerror.class, ItemSpriteSheet.SCROLL_GYFU);
				 showableItems.put(ScrollOfTransmutation.class, ItemSpriteSheet.SCROLL_BERKANAN);
				 showableItems.put(StoneOfEnchantment.class, ItemSpriteSheet.STONE_ENCHANT);
				 showableItems.put(StoneOfIntuition.class, ItemSpriteSheet.STONE_INTUITION);
				 showableItems.put(StoneOfDisarming.class, ItemSpriteSheet.STONE_DISARM);
				 showableItems.put(StoneOfFlock.class, ItemSpriteSheet.STONE_FLOCK);
				 showableItems.put(StoneOfShock.class, ItemSpriteSheet.STONE_SHOCK);
				 showableItems.put(StoneOfBlink.class, ItemSpriteSheet.STONE_BLINK);
				 showableItems.put(StoneOfDeepSleep.class, ItemSpriteSheet.STONE_SLEEP);
				 showableItems.put(StoneOfClairvoyance.class, ItemSpriteSheet.STONE_CLAIRVOYANCE);
				 showableItems.put(StoneOfAggression.class, ItemSpriteSheet.STONE_AGGRESSION);
				 showableItems.put(StoneOfBlast.class, ItemSpriteSheet.STONE_BLAST);
				 showableItems.put(StoneOfFear.class, ItemSpriteSheet.STONE_FEAR);
				 showableItems.put(StoneOfAugmentation.class, ItemSpriteSheet.STONE_AUGMENTATION);
				 showableItems.put(WandOfMagicMissile.class, ItemSpriteSheet.WAND_MAGIC_MISSILE);
				 showableItems.put(WandOfLightning.class, ItemSpriteSheet.WAND_LIGHTNING);
				 showableItems.put(WandOfDisintegration.class, ItemSpriteSheet.WAND_DISINTEGRATION);
				 showableItems.put(WandOfFireblast.class, ItemSpriteSheet.WAND_FIREBOLT);
				 showableItems.put(WandOfCorrosion.class, ItemSpriteSheet.WAND_CORROSION);
				 showableItems.put(WandOfBlastWave.class, ItemSpriteSheet.WAND_BLAST_WAVE);
				 showableItems.put(WandOfLivingEarth.class, ItemSpriteSheet.WAND_LIVING_EARTH);
				 showableItems.put(WandOfFrost.class, ItemSpriteSheet.WAND_FROST);
				 showableItems.put(WandOfPrismaticLight.class, ItemSpriteSheet.WAND_PRISMATIC_LIGHT);
				 showableItems.put(WandOfWarding.class, ItemSpriteSheet.WAND_WARDING);
				 showableItems.put(WandOfTransfusion.class, ItemSpriteSheet.WAND_TRANSFUSION);
				 showableItems.put(WandOfCorruption.class, ItemSpriteSheet.WAND_CORRUPTION);
				 showableItems.put(WandOfRegrowth.class, ItemSpriteSheet.WAND_REGROWTH);
				 showableItems.put(WornShortsword.class, ItemSpriteSheet.WORN_SHORTSWORD);
				 showableItems.put(MagesStaff.class, ItemSpriteSheet.MAGES_STAFF);
				 showableItems.put(Dagger.class, ItemSpriteSheet.DAGGER);
				 showableItems.put(Gloves.class, ItemSpriteSheet.GLOVES);
				 showableItems.put(Rapier.class, ItemSpriteSheet.RAPIER);
				 showableItems.put(Shortsword.class, ItemSpriteSheet.SHORTSWORD);
				 showableItems.put(HandAxe.class, ItemSpriteSheet.HAND_AXE);
				 showableItems.put(Spear.class, ItemSpriteSheet.SPEAR);
				 showableItems.put(Quarterstaff.class, ItemSpriteSheet.QUARTERSTAFF);
				 showableItems.put(Dirk.class, ItemSpriteSheet.DIRK);
				 showableItems.put(Sickle.class, ItemSpriteSheet.SICKLE);
				 showableItems.put(Sword.class, ItemSpriteSheet.SWORD);
				 showableItems.put(Mace.class, ItemSpriteSheet.MACE);
				 showableItems.put(Scimitar.class, ItemSpriteSheet.SCIMITAR);
				 showableItems.put(RoundShield.class, ItemSpriteSheet.ROUND_SHIELD);
				 showableItems.put(Sai.class, ItemSpriteSheet.SAI);
				 showableItems.put(Whip.class, ItemSpriteSheet.WHIP);
				 showableItems.put(Longsword.class, ItemSpriteSheet.LONGSWORD);
				 showableItems.put(BattleAxe.class, ItemSpriteSheet.BATTLE_AXE);
				 showableItems.put(Flail.class, ItemSpriteSheet.FLAIL);
				 showableItems.put(RunicBlade.class, ItemSpriteSheet.RUNIC_BLADE);
				 showableItems.put(AssassinsBlade.class, ItemSpriteSheet.ASSASSINS_BLADE);
				 showableItems.put(Crossbow.class, ItemSpriteSheet.CROSSBOW);
				 showableItems.put(Katana.class, ItemSpriteSheet.KATANA);
				 showableItems.put(Greatsword.class, ItemSpriteSheet.GREATSWORD);
				 showableItems.put(WarHammer.class, ItemSpriteSheet.WAR_HAMMER);
				 showableItems.put(Glaive.class, ItemSpriteSheet.GLAIVE);
				 showableItems.put(Greataxe.class, ItemSpriteSheet.GREATAXE);
				 showableItems.put(Greatshield.class, ItemSpriteSheet.GREATSHIELD);
				 showableItems.put(Gauntlet.class, ItemSpriteSheet.GAUNTLETS);
				 showableItems.put(WarScythe.class, ItemSpriteSheet.WAR_SCYTHE);
				 showableItems.put(ClothArmor.class, ItemSpriteSheet.ARMOR_CLOTH);
				 showableItems.put(LeatherArmor.class, ItemSpriteSheet.ARMOR_LEATHER);
				 showableItems.put(MailArmor.class, ItemSpriteSheet.ARMOR_MAIL);
				 showableItems.put(ScaleArmor.class, ItemSpriteSheet.ARMOR_SCALE);
				 showableItems.put(PlateArmor.class, ItemSpriteSheet.ARMOR_PLATE);
				 showableItems.put(WarriorArmor.class, ItemSpriteSheet.ARMOR_WARRIOR);
				 showableItems.put(MageArmor.class, ItemSpriteSheet.ARMOR_MAGE);
				 showableItems.put(RogueArmor.class, ItemSpriteSheet.ARMOR_ROGUE);
				 showableItems.put(HuntressArmor.class, ItemSpriteSheet.ARMOR_HUNTRESS);
				 showableItems.put(DuelistArmor.class, ItemSpriteSheet.ARMOR_DUELIST);
				 showableItems.put(ThrowingStone.class, ItemSpriteSheet.THROWING_STONE);
				 showableItems.put(ThrowingKnife.class, ItemSpriteSheet.THROWING_KNIFE);
				 showableItems.put(ThrowingSpike.class, ItemSpriteSheet.THROWING_SPIKE);
				 showableItems.put(FishingSpear.class, ItemSpriteSheet.FISHING_SPEAR);
				 showableItems.put(ThrowingClub.class, ItemSpriteSheet.THROWING_CLUB);
				 showableItems.put(Shuriken.class, ItemSpriteSheet.SHURIKEN);
				 showableItems.put(ThrowingSpear.class, ItemSpriteSheet.THROWING_SPEAR);
				 showableItems.put(Kunai.class, ItemSpriteSheet.KUNAI);
				 showableItems.put(Bolas.class, ItemSpriteSheet.BOLAS);
				 showableItems.put(Javelin.class, ItemSpriteSheet.JAVELIN);
				 showableItems.put(Tomahawk.class, ItemSpriteSheet.TOMAHAWK);
				 showableItems.put(HeavyBoomerang.class, ItemSpriteSheet.BOOMERANG);
				 showableItems.put(Trident.class, ItemSpriteSheet.TRIDENT);
				 showableItems.put(ThrowingHammer.class, ItemSpriteSheet.THROWING_HAMMER);
				 showableItems.put(ForceCube.class, ItemSpriteSheet.FORCE_CUBE);
				 showableItems.put(Food.class, ItemSpriteSheet.RATION);
				 showableItems.put(Pasty.class, ItemSpriteSheet.PASTY);
				 showableItems.put(MysteryMeat.class, ItemSpriteSheet.MEAT);
				 showableItems.put(RingOfAccuracy.class, ItemSpriteSheet.RING_TOURMALINE);
				 showableItems.put(RingOfArcana.class, ItemSpriteSheet.RING_TOPAZ);
				 showableItems.put(RingOfElements.class, ItemSpriteSheet.RING_SAPPHIRE);
				 showableItems.put(RingOfEnergy.class, ItemSpriteSheet.RING_RUBY);
				 showableItems.put(RingOfEvasion.class, ItemSpriteSheet.RING_QUARTZ);
				 showableItems.put(RingOfForce.class, ItemSpriteSheet.RING_OPAL);
				 showableItems.put(RingOfFuror.class, ItemSpriteSheet.RING_ONYX);
				 showableItems.put(RingOfHaste.class, ItemSpriteSheet.RING_GARNET);
				 showableItems.put(RingOfMight.class, ItemSpriteSheet.RING_EMERALD);
				 showableItems.put(RingOfSharpshooting.class, ItemSpriteSheet.RING_DIAMOND);
				 showableItems.put(RingOfTenacity.class, ItemSpriteSheet.RING_AMETHYST);
				 showableItems.put(RingOfWealth.class, ItemSpriteSheet.RING_AGATE);
				 showableItems.put(RingOfAntiMagic.class, ItemSpriteSheet.RING_ALEXANDRITE);
				 showableItems.put(AlchemistsToolkit.class, ItemSpriteSheet.ARTIFACT_TOOLKIT);
				 showableItems.put(ChaliceOfBlood.class, ItemSpriteSheet.ARTIFACT_CHALICE3);
				 showableItems.put(CloakOfShadows.class, ItemSpriteSheet.ARTIFACT_CLOAK);
				 showableItems.put(DriedRose.class, ItemSpriteSheet.ARTIFACT_ROSE3);
				 showableItems.put(EtherealChains.class, ItemSpriteSheet.ARTIFACT_CHAINS);
				 showableItems.put(HornOfPlenty.class, ItemSpriteSheet.ARTIFACT_HORN4);
				 showableItems.put(MasterThievesArmband.class, ItemSpriteSheet.ARTIFACT_ARMBAND);
				 showableItems.put(SandalsOfNature.class, ItemSpriteSheet.ARTIFACT_SANDALS);
				 showableItems.put(TalismanOfForesight.class, ItemSpriteSheet.ARTIFACT_TALISMAN);
				 showableItems.put(TimekeepersHourglass.class, ItemSpriteSheet.ARTIFACT_HOURGLASS);
				 showableItems.put(UnstableSpellbook.class, ItemSpriteSheet.ARTIFACT_SPELLBOOK);
				 showableItems.put(RatSkull.class, ItemSpriteSheet.RAT_SKULL);
				 showableItems.put(ParchmentScrap.class, ItemSpriteSheet.PARCHMENT_SCRAP);
				 showableItems.put(PetrifiedSeed.class, ItemSpriteSheet.PETRIFIED_SEED);
				 showableItems.put(ExoticCrystals.class, ItemSpriteSheet.EXOTIC_CRYSTALS);
				 showableItems.put(MossyClump.class, ItemSpriteSheet.MOSSY_CLUMP);
				 showableItems.put(DimensionalSundial.class, ItemSpriteSheet.SUNDIAL);
				 showableItems.put(ThirteenLeafClover.class, ItemSpriteSheet.CLOVER);
				 showableItems.put(TrapMechanism.class, ItemSpriteSheet.TRAP_MECHANISM);
				 showableItems.put(MimicTooth.class, ItemSpriteSheet.MIMIC_TOOTH);
				 showableItems.put(WondrousResin.class, ItemSpriteSheet.WONDROUS_RESIN);
				 showableItems.put(EyeOfNewt.class, ItemSpriteSheet.EYE_OF_NEWT);

	}

}