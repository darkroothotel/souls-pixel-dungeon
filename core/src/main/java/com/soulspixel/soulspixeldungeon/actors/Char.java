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

package com.soulspixel.soulspixeldungeon.actors;

import com.soulspixel.soulspixeldungeon.Assets;
import com.soulspixel.soulspixeldungeon.Badges;
import com.soulspixel.soulspixeldungeon.Dungeon;
import com.soulspixel.soulspixeldungeon.actors.blobs.Electricity;
import com.soulspixel.soulspixeldungeon.actors.blobs.StormCloud;
import com.soulspixel.soulspixeldungeon.actors.blobs.ToxicGas;
import com.soulspixel.soulspixeldungeon.actors.buffs.Adrenaline;
import com.soulspixel.soulspixeldungeon.actors.buffs.AllyBuff;
import com.soulspixel.soulspixeldungeon.actors.buffs.Amok;
import com.soulspixel.soulspixeldungeon.actors.buffs.ArcaneArmor;
import com.soulspixel.soulspixeldungeon.actors.buffs.AscensionChallenge;
import com.soulspixel.soulspixeldungeon.actors.buffs.AtEase;
import com.soulspixel.soulspixeldungeon.actors.buffs.Barkskin;
import com.soulspixel.soulspixeldungeon.actors.buffs.Berserk;
import com.soulspixel.soulspixeldungeon.actors.buffs.Bleeding;
import com.soulspixel.soulspixeldungeon.actors.buffs.Bless;
import com.soulspixel.soulspixeldungeon.actors.buffs.Buff;
import com.soulspixel.soulspixeldungeon.actors.buffs.Burning;
import com.soulspixel.soulspixeldungeon.actors.buffs.Carcinisation;
import com.soulspixel.soulspixeldungeon.actors.buffs.ChampionEnemy;
import com.soulspixel.soulspixeldungeon.actors.buffs.Charm;
import com.soulspixel.soulspixeldungeon.actors.buffs.Chill;
import com.soulspixel.soulspixeldungeon.actors.buffs.Corrosion;
import com.soulspixel.soulspixeldungeon.actors.buffs.Corruption;
import com.soulspixel.soulspixeldungeon.actors.buffs.Cripple;
import com.soulspixel.soulspixeldungeon.actors.buffs.Daze;
import com.soulspixel.soulspixeldungeon.actors.buffs.Doom;
import com.soulspixel.soulspixeldungeon.actors.buffs.Dread;
import com.soulspixel.soulspixeldungeon.actors.buffs.FireImbue;
import com.soulspixel.soulspixeldungeon.actors.buffs.Frost;
import com.soulspixel.soulspixeldungeon.actors.buffs.FrostImbue;
import com.soulspixel.soulspixeldungeon.actors.buffs.Fury;
import com.soulspixel.soulspixeldungeon.actors.buffs.Haste;
import com.soulspixel.soulspixeldungeon.actors.buffs.Hex;
import com.soulspixel.soulspixeldungeon.actors.buffs.Hunger;
import com.soulspixel.soulspixeldungeon.actors.buffs.LifeLink;
import com.soulspixel.soulspixeldungeon.actors.buffs.LostInventory;
import com.soulspixel.soulspixeldungeon.actors.buffs.MagicalSleep;
import com.soulspixel.soulspixeldungeon.actors.buffs.Momentum;
import com.soulspixel.soulspixeldungeon.actors.buffs.MonkEnergy;
import com.soulspixel.soulspixeldungeon.actors.buffs.Ooze;
import com.soulspixel.soulspixeldungeon.actors.buffs.Paralysis;
import com.soulspixel.soulspixeldungeon.actors.buffs.Poison;
import com.soulspixel.soulspixeldungeon.actors.buffs.Preparation;
import com.soulspixel.soulspixeldungeon.actors.buffs.ShieldBuff;
import com.soulspixel.soulspixeldungeon.actors.buffs.Sleep;
import com.soulspixel.soulspixeldungeon.actors.buffs.Slow;
import com.soulspixel.soulspixeldungeon.actors.buffs.SnipersMark;
import com.soulspixel.soulspixeldungeon.actors.buffs.Speed;
import com.soulspixel.soulspixeldungeon.actors.buffs.Stamina;
import com.soulspixel.soulspixeldungeon.actors.buffs.StanceBroken;
import com.soulspixel.soulspixeldungeon.actors.buffs.Sticky;
import com.soulspixel.soulspixeldungeon.actors.buffs.Stickyfloor;
import com.soulspixel.soulspixeldungeon.actors.buffs.Terror;
import com.soulspixel.soulspixeldungeon.actors.buffs.Uneasy;
import com.soulspixel.soulspixeldungeon.actors.buffs.Vertigo;
import com.soulspixel.soulspixeldungeon.actors.buffs.Vulnerable;
import com.soulspixel.soulspixeldungeon.actors.buffs.Weakness;
import com.soulspixel.soulspixeldungeon.actors.hero.Hero;
import com.soulspixel.soulspixeldungeon.actors.hero.HeroSubClass;
import com.soulspixel.soulspixeldungeon.actors.hero.Talent;
import com.soulspixel.soulspixeldungeon.actors.hero.abilities.duelist.Challenge;
import com.soulspixel.soulspixeldungeon.actors.hero.abilities.rogue.DeathMark;
import com.soulspixel.soulspixeldungeon.actors.hero.abilities.warrior.Endure;
import com.soulspixel.soulspixeldungeon.actors.mobs.ArmoredStatue;
import com.soulspixel.soulspixeldungeon.actors.mobs.CrystalSpire;
import com.soulspixel.soulspixeldungeon.actors.mobs.DwarfKing;
import com.soulspixel.soulspixeldungeon.actors.mobs.Elemental;
import com.soulspixel.soulspixeldungeon.actors.mobs.GnollGeomancer;
import com.soulspixel.soulspixeldungeon.actors.mobs.Mob;
import com.soulspixel.soulspixeldungeon.actors.mobs.Necromancer;
import com.soulspixel.soulspixeldungeon.actors.mobs.Statue;
import com.soulspixel.soulspixeldungeon.actors.mobs.Tengu;
import com.soulspixel.soulspixeldungeon.actors.mobs.npcs.MirrorImage;
import com.soulspixel.soulspixeldungeon.actors.mobs.npcs.PrismaticImage;
import com.soulspixel.soulspixeldungeon.effects.CellEmitter;
import com.soulspixel.soulspixeldungeon.effects.FloatingText;
import com.soulspixel.soulspixeldungeon.effects.Speck;
import com.soulspixel.soulspixeldungeon.effects.particles.ShadowParticle;
import com.soulspixel.soulspixeldungeon.items.Heap;
import com.soulspixel.soulspixeldungeon.items.armor.glyphs.AntiMagic;
import com.soulspixel.soulspixeldungeon.items.armor.glyphs.Potential;
import com.soulspixel.soulspixeldungeon.items.armor.glyphs.Viscosity;
import com.soulspixel.soulspixeldungeon.items.artifacts.DriedRose;
import com.soulspixel.soulspixeldungeon.items.artifacts.TimekeepersHourglass;
import com.soulspixel.soulspixeldungeon.items.potions.exotic.PotionOfCleansing;
import com.soulspixel.soulspixeldungeon.items.quest.Pickaxe;
import com.soulspixel.soulspixeldungeon.items.rings.RingOfElements;
import com.soulspixel.soulspixeldungeon.items.scrolls.ScrollOfRetribution;
import com.soulspixel.soulspixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.soulspixel.soulspixeldungeon.items.scrolls.exotic.ScrollOfChallenge;
import com.soulspixel.soulspixeldungeon.items.scrolls.exotic.ScrollOfPsionicBlast;
import com.soulspixel.soulspixeldungeon.items.trinkets.ThirteenLeafClover;
import com.soulspixel.soulspixeldungeon.items.wands.WandOfBlastWave;
import com.soulspixel.soulspixeldungeon.items.wands.WandOfFireblast;
import com.soulspixel.soulspixeldungeon.items.wands.WandOfFrost;
import com.soulspixel.soulspixeldungeon.items.wands.WandOfLightning;
import com.soulspixel.soulspixeldungeon.items.wands.WandOfLivingEarth;
import com.soulspixel.soulspixeldungeon.items.weapon.Weapon;
import com.soulspixel.soulspixeldungeon.items.weapon.enchantments.Blazing;
import com.soulspixel.soulspixeldungeon.items.weapon.enchantments.Grim;
import com.soulspixel.soulspixeldungeon.items.weapon.enchantments.Kinetic;
import com.soulspixel.soulspixeldungeon.items.weapon.enchantments.Shocking;
import com.soulspixel.soulspixeldungeon.items.weapon.enchantments.Vortex;
import com.soulspixel.soulspixeldungeon.items.weapon.melee.Sickle;
import com.soulspixel.soulspixeldungeon.items.weapon.missiles.MissileWeapon;
import com.soulspixel.soulspixeldungeon.items.weapon.missiles.darts.ShockingDart;
import com.soulspixel.soulspixeldungeon.journal.Document;
import com.soulspixel.soulspixeldungeon.levels.RegularLevel;
import com.soulspixel.soulspixeldungeon.levels.Terrain;
import com.soulspixel.soulspixeldungeon.levels.features.Chasm;
import com.soulspixel.soulspixeldungeon.levels.features.Door;
import com.soulspixel.soulspixeldungeon.levels.rooms.Room;
import com.soulspixel.soulspixeldungeon.levels.rooms.moods.EntranceEffect;
import com.soulspixel.soulspixeldungeon.levels.rooms.moods.LingeringMood;
import com.soulspixel.soulspixeldungeon.levels.traps.GeyserTrap;
import com.soulspixel.soulspixeldungeon.levels.traps.GnollRockfallTrap;
import com.soulspixel.soulspixeldungeon.levels.traps.GrimTrap;
import com.soulspixel.soulspixeldungeon.messages.Messages;
import com.soulspixel.soulspixeldungeon.plants.Earthroot;
import com.soulspixel.soulspixeldungeon.plants.Swiftthistle;
import com.soulspixel.soulspixeldungeon.scenes.GameScene;
import com.soulspixel.soulspixeldungeon.sprites.CharSprite;
import com.soulspixel.soulspixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.BArray;
import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Point;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;

public abstract class Char extends Actor {
	
	public int pos = 0;
	
	public CharSprite sprite;
	
	public int HT;
	public int HP;

	public int MAX_POISE = 10;
	public int POISE = 10;
	public int MAX_STAMINA = 10;
	public int STAMINA = 10;
	public float poiseRecovery = 1f;
	public float staminaRecovery = 1f;
	public float poiseRecoveryMult = 1f;
	public float staminaRecoveryMult = 1f;
	
	protected float baseSpeed	= 1;
	protected PathFinder.Path path;

	public int paralysed	    = 0;
	public boolean rooted		= false;
	public boolean flying		= false;
	public int invisible		= 0;

	//these are relative to the hero
	public enum Alignment{
		ENEMY,
		NEUTRAL,
		ALLY
	}
	public Alignment alignment;
	
	public int viewDistance	= 8;
	
	public boolean[] fieldOfView = null;
	
	private LinkedHashSet<Buff> buffs = new LinkedHashSet<>();

	public enum DamageType{
		STANDARD,
		STRIKE,
		SLASH,
		PIERCE,
		BASH,
		THRUST,
		STAB,
		WATER,
		EARTH,
		FIRE,
		LIGHTNING,
		FROST,
		ACIDIC,
		POISON,
		BLEED,
		MAGIC,
		HOLY_MAGIC,
		DARK_MAGIC,
		HUNGER,
		FALL,
		BOMB;

		public String getName(DamageType dt){
			String s = null;
			switch (dt){
				case STANDARD:
					s = Messages.get(Char.class, "dt_standard_name");
					break;
                case STRIKE:
					s = Messages.get(Char.class, "dt_strike_name");
                    break;
                case SLASH:
					s = Messages.get(Char.class, "dt_slash_name");
                    break;
                case PIERCE:
					s = Messages.get(Char.class, "dt_pierce_name");
                    break;
                case BASH:
					s = Messages.get(Char.class, "dt_bash_name");
                    break;
                case THRUST:
					s = Messages.get(Char.class, "dt_thrust_name");
                    break;
                case STAB:
					s = Messages.get(Char.class, "dt_stab_name");
                    break;
                case WATER:
					s = Messages.get(Char.class, "dt_water_name");
                    break;
                case EARTH:
					s = Messages.get(Char.class, "dt_earth_name");
                    break;
                case FIRE:
					s = Messages.get(Char.class, "dt_fire_name");
                    break;
                case LIGHTNING:
					s = Messages.get(Char.class, "dt_lightning_name");
                    break;
                case FROST:
					s = Messages.get(Char.class, "dt_frost_name");
                    break;
                case ACIDIC:
					s = Messages.get(Char.class, "dt_acidic_name");
                    break;
                case POISON:
					s = Messages.get(Char.class, "dt_poison_name");
                    break;
                case BLEED:
					s = Messages.get(Char.class, "dt_bleed_name");
                    break;
                case MAGIC:
					s = Messages.get(Char.class, "dt_magic_name");
                    break;
                case HOLY_MAGIC:
					s = Messages.get(Char.class, "dt_holy_magic_name");
                    break;
                case DARK_MAGIC:
					s = Messages.get(Char.class, "dt_dark_magic_name");
                    break;
                case HUNGER:
					s = Messages.get(Char.class, "dt_hunger_name");
                    break;
                case FALL:
					s = Messages.get(Char.class, "dt_fall_name");
                    break;
                case BOMB:
					s = Messages.get(Char.class, "dt_bomb_name");
                    break;
            }
			return s;
		}

		public float whenWeak(DamageType dt) {
			float dmg = 0f;
			switch (dt) {
                case HOLY_MAGIC:
				case DARK_MAGIC:
                case BASH:
                case THRUST:
                case STAB:
                case WATER:
                case EARTH:
                case FIRE:
                case LIGHTNING:
                case FROST:
                case ACIDIC:
                case POISON:
                case BLEED:
                case STANDARD:
				case STRIKE:
				case SLASH:
				case PIERCE:
				case MAGIC:
                case HUNGER:
                case FALL:
				case BOMB:
					dmg = dmg * 0.2f;
					break;
            }
			return dmg;
		}

		public float whenResist(DamageType dt) {
			float dmg = 0f;
			switch (dt) {
                case HOLY_MAGIC:
                case DARK_MAGIC:
                case BASH:
                case THRUST:
                case STAB:
                case WATER:
                case EARTH:
                case FIRE:
                case LIGHTNING:
                case FROST:
                case ACIDIC:
                case POISON:
                case BLEED:
                case STANDARD:
				case STRIKE:
				case SLASH:
				case PIERCE:
				case MAGIC:
                case HUNGER:
                case FALL:
				case BOMB:
					dmg = dmg * -0.2f;
					break;
            }
			return dmg;
		}

		public float getPoiseMult(DamageType dt) {
			switch (dt) {
				case HOLY_MAGIC:
                case MAGIC:
                case HUNGER:
                case FALL:
                case BLEED:
                case DARK_MAGIC:
                case WATER:
                case EARTH:
                case FIRE:
                case LIGHTNING:
                case FROST:
                case ACIDIC:
                case POISON:
                    return 1f;
                case BASH:
					return 1.5f;
				case THRUST:
					return 1.4f;
				case STAB:
                case STANDARD:
                    return 1.1f;
                case STRIKE:
					return 1.3f;
				case SLASH:
                case PIERCE:
                    return 1.2f;
                case BOMB:
					return 2f;
			}
			return 1f;
		}
	}

	public DamageType damageTypeDealt = DamageType.STANDARD;
	public ArrayList<DamageType> damageResisted = new ArrayList<>();
	public ArrayList<DamageType> damageWeak = new ArrayList<>();
	public ArrayList<DamageType> damageImmune = new ArrayList<>();

	public DamageType getDamageTypeDealt(){
		return damageTypeDealt;
	}
	public ArrayList<DamageType> getDamageResisted(){
		return damageResisted;
	}
	public ArrayList<DamageType> getDamageWeak(){
		return damageWeak;
	}
	public ArrayList<DamageType> getDamageImmune(){
		return damageImmune;
	}

	public void stanceBroken(){
		increasePoise(MAX_POISE);
		Buff.affect(this, StanceBroken.class, 1f);
		if(this instanceof Mob){
			if(Dungeon.hero.getVisibleEnemies().contains(this)){
				if(!Document.ADVENTURERS_GUIDE.isPageRead(Document.GUIDE_POISE)){
					GameScene.flashForDocument(Document.ADVENTURERS_GUIDE, Document.GUIDE_POISE);
				}
			}
		}
	}

	public void reduceStamina(int i){
		STAMINA -= i;
		if(STAMINA < 0){
			STAMINA = 0;
		}
	}

	public boolean staminaCheck(int staminaCost){
		if(STAMINA <= 0){
			return false;
		}
		if(hasStaminaToDo(staminaCost)){
			reduceStamina(staminaCost);
			return true;
		} else {
			reduceStamina(staminaCost);
			if(this instanceof Hero){
				if(!Document.ADVENTURERS_GUIDE.isPageRead(Document.GUIDE_STAMINA)){
					GameScene.flashForDocument(Document.ADVENTURERS_GUIDE, Document.GUIDE_STAMINA);
				}
			}
			return false;
		}
	}

	public boolean hasStaminaToDo(int i){
		int s = STAMINA;
		return s - i >= 0;
	}

	public void increaseStamina(int i){
		STAMINA += i;
		if(STAMINA > MAX_STAMINA) STAMINA = MAX_STAMINA;
	}

	public void increaseStamina(){
		int i = (int) ((float) STAMINA + (staminaRecovery*staminaRecoveryMult));
		STAMINA = i;
		if(STAMINA > MAX_STAMINA) STAMINA = MAX_STAMINA;
	}

	public void reducePoise(int i){
		POISE -= i;
		if(POISE <= 0){
			POISE = 0;
			stanceBroken();
		}
	}

	public void increasePoise(int i){
		POISE += i;
		if(POISE > MAX_POISE) POISE = MAX_POISE;
	}

	public void increasePoise(){
		int i = (int) ((float) POISE + (poiseRecovery*poiseRecoveryMult));
		POISE = i;
		if(POISE > MAX_POISE) POISE = MAX_POISE;
	}

	public static boolean isPointInsideRoom(Room rect, Point point) {
		// Shrink the rectangle by 1 unit on each side
		return point.x > rect.left && point.x < rect.right &&
				point.y > rect.top && point.y < rect.bottom ;
	}

	public Room getRoom(){
		for(Room r : ((RegularLevel) Dungeon.level).rooms()){
			if(isPointInsideRoom(r, Dungeon.level.cellToPoint(pos))){
				return r;
			}
		}
		return null;
	}

	public ArrayList<Room> getRooms(){
		return ((RegularLevel) Dungeon.level).rooms();
	}

	public void getCurrentRoomEffect(){
		if(Dungeon.level instanceof RegularLevel){
			for(Room r : ((RegularLevel) Dungeon.level).rooms()){
				if(isPointInsideRoom(r, Dungeon.level.cellToPoint(pos))){
					if(!r.discovered){
						if(this instanceof Hero){
							r.discovered = true;
						}
						if(r.type < 0){
							if(this instanceof Hero){
								GLog.w(Messages.get(Room.class, "type_ann_"+r.type));
								CellEmitter.get( pos ).start( Speck.factory( Speck.DISCOVER ), 0.1f, 4 );
								Sample.INSTANCE.play( Assets.Sounds.SECRET );
							}
							EntranceEffect.getEffect(r.type, this, (RegularLevel) Dungeon.level);
						} else if(r.type != 0) {
							if(this instanceof Hero){
								GLog.w(Messages.get(Room.class, "type_ann_"+r.type));
								CellEmitter.get( pos ).start( Speck.factory( Speck.DISCOVER ), 0.1f, 4 );
								Sample.INSTANCE.play( Assets.Sounds.SECRET );
							}
						}
					}
					if(r.type > 0){
						LingeringMood.getEffect(r.type, this, (RegularLevel) Dungeon.level);
					}
					break;
				}
			}
		}
	}
	
	@Override
	protected boolean act() {
		if (fieldOfView == null || fieldOfView.length != Dungeon.level.length()){
			fieldOfView = new boolean[Dungeon.level.length()];
		}
		Dungeon.level.updateFieldOfView( this, fieldOfView );

		//throw any items that are on top of an immovable char
		if (properties().contains(Property.IMMOVABLE)){
			throwItems();
		}
		//discover rooms or trigger room effects
		getCurrentRoomEffect();

		//buffs
		if(buff(AtEase.class) != null && buff(Uneasy.class) != null){
			buff(AtEase.class).detach();
		}
		if(buff(StanceBroken.class) != null){
			if(!(this instanceof Hero)){
				buff(StanceBroken.class).detach();
			}
		}

		//increase normal
		increaseStamina();
		increasePoise();
		return false;
	}

	protected void throwItems(){
		Heap heap = Dungeon.level.heaps.get( pos );
		if (heap != null && heap.type == Heap.Type.HEAP
				&& !(heap.peek() instanceof Tengu.BombAbility.BombItem)
				&& !(heap.peek() instanceof Tengu.ShockerAbility.ShockerItem)) {
			ArrayList<Integer> candidates = new ArrayList<>();
			for (int n : PathFinder.NEIGHBOURS8){
				if (Dungeon.level.passable[pos+n]){
					candidates.add(pos+n);
				}
			}
			if (!candidates.isEmpty()){
				Dungeon.level.drop( heap.pickUp(), Random.element(candidates) ).sprite.drop( pos );
			}
		}
	}

	public String name(){
		return Messages.get(this, "name");
	}

	public boolean canInteract(Char c){
		if (Dungeon.level.adjacent( pos, c.pos )){
			return true;
		} else if (c instanceof Hero
				&& alignment == Alignment.ALLY
				&& !hasProp(this, Property.IMMOVABLE)
				&& Dungeon.level.distance(pos, c.pos) <= 2*Dungeon.hero.pointsInTalent(Talent.ALLY_WARP)){
			return true;
		} else {
			return false;
		}
	}
	
	//swaps places by default
	public boolean interact(Char c){

		//don't allow char to swap onto hazard unless they're flying
		//you can swap onto a hazard though, as you're not the one instigating the swap
		if (!Dungeon.level.passable[pos] && !c.flying){
			return true;
		}

		//can't swap into a space without room
		if (properties().contains(Property.LARGE) && !Dungeon.level.openSpace[c.pos]
			|| c.properties().contains(Property.LARGE) && !Dungeon.level.openSpace[pos]){
			return true;
		}

		//we do a little raw position shuffling here so that the characters are never
		// on the same cell when logic such as occupyCell() is triggered
		int oldPos = pos;
		int newPos = c.pos;

		//can't swap or ally warp if either char is immovable
		if (hasProp(this, Property.IMMOVABLE) || hasProp(c, Property.IMMOVABLE)){
			return true;
		}

		//warp instantly with allies in this case
		if (c == Dungeon.hero && Dungeon.hero.hasTalent(Talent.ALLY_WARP)){
			PathFinder.buildDistanceMap(c.pos, BArray.or(Dungeon.level.passable, Dungeon.level.avoid, null));
			if (PathFinder.distance[pos] == Integer.MAX_VALUE){
				return true;
			}
			pos = newPos;
			c.pos = oldPos;
			ScrollOfTeleportation.appear(this, newPos);
			ScrollOfTeleportation.appear(c, oldPos);
			Dungeon.observe();
			GameScene.updateFog();
			return true;
		}

		//can't swap places if one char has restricted movement
		if (rooted || c.rooted || buff(Vertigo.class) != null || c.buff(Vertigo.class) != null){
			return true;
		}

		c.pos = oldPos;
		moveSprite( oldPos, newPos );
		move( newPos );

		c.pos = newPos;
		c.sprite.move( newPos, oldPos );
		c.move( oldPos );
		
		c.spend( 1 / c.speed() );

		if (c == Dungeon.hero){
			if (Dungeon.hero.subClass == HeroSubClass.FREERUNNER){
				Buff.affect(Dungeon.hero, Momentum.class).gainStack();
			}

			Dungeon.hero.busy();
		}
		
		return true;
	}
	
	protected boolean moveSprite( int from, int to ) {
		
		if (sprite.isVisible() && sprite.parent != null && (Dungeon.level.heroFOV[from] || Dungeon.level.heroFOV[to])) {
			sprite.move( from, to );
			return true;
		} else {
			sprite.turnTo(from, to);
			sprite.place( to );
			return true;
		}
	}

	public void hitSound( float pitch ){
		Sample.INSTANCE.play(Assets.Sounds.HIT, 1, pitch);
	}

	public boolean blockSound( float pitch ) {
		return false;
	}
	
	protected static final String POS       		= "pos";
	protected static final String TAG_HP    		= "HP";
	protected static final String TAG_HT    		= "HT";
	protected static final String TAG_POISE    		= "POISE";
	protected static final String TAG_MAX_POISE    	= "MAX_POISE";
	protected static final String POISE_REC    		= "poise_recovery";
	protected static final String POISE_REC_RATE	= "poise_recovery_rate";
	protected static final String TAG_STAMINA		= "STAMINA";
	protected static final String TAG_MAX_STAMINA	= "MAX_STAMINA";
	protected static final String STAMINA_REC   	= "stamina_recovery";
	protected static final String STAMINA_REC_RATE	= "stamina_recovery_rate";
	protected static final String TAG_SHLD  		= "SHLD";
	protected static final String BUFFS	    		= "buffs";
	
	@Override
	public void storeInBundle( Bundle bundle ) {
		
		super.storeInBundle( bundle );
		
		bundle.put( POS, pos );
		bundle.put( TAG_HP, HP );
		bundle.put( TAG_HT, HT );
		bundle.put( TAG_POISE, POISE );
		bundle.put( TAG_MAX_POISE, MAX_POISE );
		bundle.put( POISE_REC, poiseRecovery);
		bundle.put( POISE_REC_RATE, poiseRecoveryMult);
		bundle.put( TAG_STAMINA, STAMINA );
		bundle.put( TAG_MAX_STAMINA, MAX_STAMINA );
		bundle.put( STAMINA_REC, staminaRecovery);
		bundle.put( STAMINA_REC_RATE, staminaRecoveryMult);
		bundle.put( BUFFS, buffs );
	}
	
	@Override
	public void restoreFromBundle( Bundle bundle ) {
		
		super.restoreFromBundle( bundle );
		
		pos = bundle.getInt( POS );
		HP = bundle.getInt( TAG_HP );
		HT = bundle.getInt( TAG_HT );

		POISE = bundle.getInt( TAG_POISE );
		MAX_POISE = bundle.getInt( TAG_MAX_POISE );
		STAMINA = bundle.getInt( TAG_STAMINA );
		MAX_STAMINA = bundle.getInt( TAG_MAX_STAMINA );
		poiseRecovery = bundle.getFloat( POISE_REC );
		poiseRecoveryMult = bundle.getFloat( POISE_REC_RATE );
		staminaRecovery = bundle.getFloat( STAMINA_REC );
		staminaRecoveryMult = bundle.getFloat( STAMINA_REC_RATE );
		
		for (Bundlable b : bundle.getCollection( BUFFS )) {
			if (b != null) {
				((Buff)b).attachTo( this );
			}
		}
	}

	final public boolean attack( Char enemy ){
		return attack(enemy, 1f, 0f, 1f);
	}
	
	public boolean attack( Char enemy, float dmgMulti, float dmgBonus, float accMulti ) {

		if (enemy == null) return false;

		if(enemy.properties.contains(Property.NOT_A_MOB)){
			return false;
		}

		if(!(this instanceof Hero)){
			if(!staminaCheck(1)){
				return false;
			}
		}
		
		boolean visibleFight = Dungeon.level.heroFOV[pos] || Dungeon.level.heroFOV[enemy.pos];

		if (enemy.isInvulnerable(getClass())) {

			if (visibleFight) {
				enemy.sprite.showStatus( CharSprite.POSITIVE, Messages.get(this, "invulnerable") );

				Sample.INSTANCE.play(Assets.Sounds.HIT_PARRY, 1f, Random.Float(0.96f, 1.05f));
			}

			return false;

		} else if (hit( this, enemy, accMulti, false )) {
			
			int dr = Math.round(enemy.drRoll() * AscensionChallenge.statModifier(enemy));
			
			if (this instanceof Hero){
				Hero h = (Hero)this;
				if (h.belongings.attackingWeapon() instanceof MissileWeapon
						&& h.subClass == HeroSubClass.SNIPER
						&& !Dungeon.level.adjacent(h.pos, enemy.pos)){
					dr = 0;
				}

				if (h.buff(MonkEnergy.MonkAbility.UnarmedAbilityTracker.class) != null){
					dr = 0;
				} else if (h.subClass == HeroSubClass.MONK) {
					//3 turns with standard attack delay
					Buff.prolong(h, MonkEnergy.MonkAbility.JustHitTracker.class, 4f);
				}
			}

			//we use a float here briefly so that we don't have to constantly round while
			// potentially applying various multiplier effects
			float dmg;
			Preparation prep = buff(Preparation.class);
			if (prep != null){
				dmg = prep.damageRoll(this);
				if (this == Dungeon.hero && Dungeon.hero.hasTalent(Talent.BOUNTY_HUNTER)) {
					Buff.affect(Dungeon.hero, Talent.BountyHunterTracker.class, 0.0f);
				}
			} else {
				dmg = damageRoll();
			}

			dmg = dmg*dmgMulti;

			//flat damage bonus is affected by multipliers
			dmg += dmgBonus;

			Berserk berserk = buff(Berserk.class);
			if (berserk != null) dmg = berserk.damageFactor(dmg);

			if (buff( Fury.class ) != null) {
				dmg *= 1.5f;
			}

			for (ChampionEnemy buff : buffs(ChampionEnemy.class)){
				dmg *= buff.meleeDamageFactor();
			}

			dmg *= AscensionChallenge.statModifier(this);

			//friendly endure
			Endure.EndureTracker endure = buff(Endure.EndureTracker.class);
			if (endure != null) dmg = endure.damageFactor(dmg);

			//enemy endure
			endure = enemy.buff(Endure.EndureTracker.class);
			if (endure != null){
				dmg = endure.adjustDamageTaken(dmg);
			}

			if (enemy.buff(ScrollOfChallenge.ChallengeArena.class) != null){
				dmg *= 0.67f;
			}

			if (enemy.buff(MonkEnergy.MonkAbility.Meditate.MeditateResistance.class) != null){
				dmg *= 0.2f;
			}

			if ( buff(Weakness.class) != null ){
				dmg *= 0.67f;
			}
			
			int effectiveDamage = enemy.defenseProc( this, Math.round(dmg) );
			//do not trigger on-hit logic if defenseProc returned a negative value
			if (effectiveDamage >= 0) {
				effectiveDamage = Math.max(effectiveDamage - dr, 0);

				if (enemy.buff(Viscosity.ViscosityTracker.class) != null) {
					effectiveDamage = enemy.buff(Viscosity.ViscosityTracker.class).deferDamage(effectiveDamage);
					enemy.buff(Viscosity.ViscosityTracker.class).detach();
				}

				//vulnerable specifically applies after armor reductions
				if (enemy.buff(Vulnerable.class) != null) {
					effectiveDamage *= 1.33f;
				}

				effectiveDamage = attackProc(enemy, effectiveDamage);
			}
			if (visibleFight) {
				if (effectiveDamage > 0 || !enemy.blockSound(Random.Float(0.96f, 1.05f))) {
					hitSound(Random.Float(0.87f, 1.15f));
				}
			}

			// If the enemy is already dead, interrupt the attack.
			// This matters as defence procs can sometimes inflict self-damage, such as armor glyphs.
			if (!enemy.isAlive()){
				return true;
			}

			enemy.damage( effectiveDamage, this, null);

			if (buff(FireImbue.class) != null)  buff(FireImbue.class).proc(enemy);
			if (buff(FrostImbue.class) != null) buff(FrostImbue.class).proc(enemy);

			if (enemy.isAlive() && enemy.alignment != alignment && prep != null && prep.canKO(enemy)){
				enemy.HP = 0;
				if (!enemy.isAlive()) {
					enemy.die(this);
				} else {
					//helps with triggering any on-damage effects that need to activate
					enemy.damage(-1, this, null);
					DeathMark.processFearTheReaper(enemy);
				}
				if (enemy.sprite != null) {
					enemy.sprite.showStatus(CharSprite.NEGATIVE, Messages.get(Preparation.class, "assassinated"));
				}
			}

			Talent.CombinedLethalityTriggerTracker combinedLethality = buff(Talent.CombinedLethalityTriggerTracker.class);
			if (combinedLethality != null){
				if ( enemy.isAlive() && enemy.alignment != alignment && !Char.hasProp(enemy, Property.BOSS)
						&& !Char.hasProp(enemy, Property.MINIBOSS) && this instanceof Hero &&
						(enemy.HP/(float)enemy.HT) <= 0.4f*((Hero)this).pointsInTalent(Talent.COMBINED_LETHALITY)/3f) {
					enemy.HP = 0;
					if (!enemy.isAlive()) {
						enemy.die(this);
					} else {
						//helps with triggering any on-damage effects that need to activate
						enemy.damage(-1, this, null);
						DeathMark.processFearTheReaper(enemy);
					}
					if (enemy.sprite != null) {
						enemy.sprite.showStatus(CharSprite.NEGATIVE, Messages.get(Talent.CombinedLethalityTriggerTracker.class, "executed"));
					}
				}
				combinedLethality.detach();
			}

			if (enemy.sprite != null) {
				enemy.sprite.bloodBurstA(sprite.center(), effectiveDamage);
				enemy.sprite.flash();
			}

			if (!enemy.isAlive() && visibleFight) {
				if (enemy == Dungeon.hero) {
					
					if (this == Dungeon.hero) {
						return true;
					}

					if (this instanceof WandOfLivingEarth.EarthGuardian
							|| this instanceof MirrorImage || this instanceof PrismaticImage){
						Badges.validateDeathFromFriendlyMagic();
					}
					Dungeon.fail( this );
					GLog.n( Messages.capitalize(Messages.get(Char.class, "kill", name())) );
					
				} else if (this == Dungeon.hero) {
					GLog.i( Messages.capitalize(Messages.get(Char.class, "defeat", enemy.name())) );
				}
			}
			
			return true;
			
		} else {

			enemy.sprite.showStatus( CharSprite.NEUTRAL, enemy.defenseVerb() );
			if (visibleFight) {
				//TODO enemy.defenseSound? currently miss plays for monks/crab even when they parry
				Sample.INSTANCE.play(Assets.Sounds.MISS);
			}
			
			return false;
			
		}
	}

	public static int INFINITE_ACCURACY = 1_000_000;
	public static int INFINITE_EVASION = 1_000_000;

	final public static boolean hit( Char attacker, Char defender, boolean magic ) {
		return hit(attacker, defender, magic ? 2f : 1f, magic);
	}

	public static boolean hit( Char attacker, Char defender, float accMulti, boolean magic ) {
		float acuStat = attacker.attackSkill( defender );
		float defStat = defender.defenseSkill( attacker );

		if (defender instanceof Hero ){
			if(((Hero) defender).damageInterrupt){
				((Hero) defender).interrupt();
			}
			if(defender.buff(StanceBroken.class) != null){
				return true;
			}
		}

		if(defender instanceof Mob && ((Mob)defender).state == ((Mob) defender).STANCE_BROKEN){
			return true;
		}

		//invisible chars always hit (for the hero this is surprise attacking)
		if (attacker.invisible > 0 && attacker.canSurpriseAttack()){
			acuStat = INFINITE_ACCURACY;
		}

		if (defender.buff(MonkEnergy.MonkAbility.Focus.FocusBuff.class) != null && !magic){
			defStat = INFINITE_EVASION;
			defender.buff(MonkEnergy.MonkAbility.Focus.FocusBuff.class).detach();
			Buff.affect(defender, MonkEnergy.MonkAbility.Focus.FocusActivation.class, 0);
		}

		//if accuracy or evasion are large enough, treat them as infinite.
		//note that infinite evasion beats infinite accuracy
		if (defStat >= INFINITE_EVASION){
			return false;
		} else if (acuStat >= INFINITE_ACCURACY){
			return true;
		}

		float acuRoll = Random.Float( acuStat );
		if (attacker.buff(Bless.class) != null) acuRoll *= 1.25f;
		if (attacker.buff(AtEase.class) != null) acuRoll *= 1.5f;
		if (attacker.buff(  Hex.class) != null) acuRoll *= 0.8f;
		if (attacker.buff( Daze.class) != null) acuRoll *= 0.5f;
		if (attacker.buff( Uneasy.class) != null) acuRoll *= 0.9f;
		for (ChampionEnemy buff : attacker.buffs(ChampionEnemy.class)){
			acuRoll *= buff.evasionAndAccuracyFactor();
		}
		acuRoll *= AscensionChallenge.statModifier(attacker);
		
		float defRoll = Random.Float( defStat );
		if (defender.buff(Bless.class) != null) defRoll *= 1.25f;
		if (defender.buff(AtEase.class) != null) defRoll *= 1.5f;
		if (defender.buff(  Hex.class) != null) defRoll *= 0.8f;
		if (defender.buff( Daze.class) != null) defRoll *= 0.5f;
		if (defender.buff( Uneasy.class) != null) defRoll *= 0.9f;
		for (ChampionEnemy buff : defender.buffs(ChampionEnemy.class)){
			defRoll *= buff.evasionAndAccuracyFactor();
		}
		defRoll *= AscensionChallenge.statModifier(defender);
		
		return (acuRoll * accMulti) >= defRoll;
	}

	//used for damage and blocking calculations, normally just calls NormalIntRange
	// but may be affected by things that specifically impact combat number ranges
	public static int combatRoll(int min, int max ){
		if (Random.Float() < ThirteenLeafClover.combatDistributionInverseChance()){
			return ThirteenLeafClover.invCombatRoll(min, max);
		} else {
			return Random.NormalIntRange(min, max);
		}
	}
	
	public int attackSkill( Char target ) {
		return 0;
	}
	
	public int defenseSkill( Char enemy ) {
		return 0;
	}
	
	public String defenseVerb() {
		return Messages.get(this, "def_verb");
	}
	
	public int drRoll() {
		int dr = 0;

		dr += combatRoll( 0 , Barkskin.currentLevel(this) );

		return dr;
	}
	
	public int damageRoll() {
		return 1;
	}
	
	//TODO it would be nice to have a pre-armor and post-armor proc.
	// atm attack is always post-armor and defence is already pre-armor
	
	public int attackProc( Char enemy, int damage ) {
		for (ChampionEnemy buff : buffs(ChampionEnemy.class)){
			buff.onAttackProc( enemy );
		}
		return damage;
	}
	
	public int defenseProc( Char enemy, int damage ) {

		Earthroot.Armor armor = buff( Earthroot.Armor.class );
		if (armor != null) {
			damage = armor.absorb( damage );
		}

		if ( buff( Carcinisation.class ) != null) damage /= 2;

		//poise damage is done here
		DamageType dt = damageTypeDealt;
		int pbd = 0;
		if(enemy instanceof Hero){
			dt = ((Hero) enemy).belongings.attackingWeapon().damageTypeDealt;
			pbd = ((Hero) enemy).belongings.attackingWeapon().bonusPoiseDamage();
		} else if(enemy instanceof Statue){
			dt = ((Statue) enemy).weapon.damageTypeDealt;
			pbd = ((Statue) enemy).weapon.bonusPoiseDamage();
		}
		int pd = ((int) (((float) damage / 2 ) * dt.getPoiseMult( dt ))) + pbd;
		int pr;
		int wpr = 0;
		int apr = 0;
		if(this instanceof Hero){
			wpr = ((Hero) this).belongings.getAllWeaponsPoiseResist();
			apr = ((Hero) this).belongings.getAllArmorPoiseResist();
		} else if(this instanceof Statue){
			wpr = ((Statue) this).weapon.getPoiseResist();
			if(this instanceof ArmoredStatue){
				apr += ((ArmoredStatue) this).armor.getPoiseResist();
			}
		}

		pr = wpr+apr;
		int poisedmg = pd-pr;

		if(buff(Carcinisation.class) != null){
			poisedmg /= 2;
		}

		reducePoise(poisedmg);

		return damage;
	}
	
	public float speed() {
		float speed = baseSpeed;
		if ( buff( Cripple.class ) != null ) speed /= 2f;
		if ( buff( Stamina.class ) != null) speed *= 1.5f;
		if ( buff( Adrenaline.class ) != null) speed *= 2f;
		if ( buff( Haste.class ) != null) speed *= 3f;
		if ( buff( Dread.class ) != null) speed *= 2f;
		if ( buff( Carcinisation.class ) != null) speed /= 3f;
		if ( buff( Stickyfloor.class ) != null) speed /= 2f;
		if ( buff( Sticky.class ) != null) speed /= 1.5f;

		return speed;
	}

	//currently only used by invisible chars, or by the hero
	public boolean canSurpriseAttack(){
		return true;
	}
	
	//used so that buffs(Shieldbuff.class) isn't called every time unnecessarily
	private int cachedShield = 0;
	public boolean needsShieldUpdate = true;
	
	public int shielding(){
		if (!needsShieldUpdate){
			return cachedShield;
		}
		
		cachedShield = 0;
		for (ShieldBuff s : buffs(ShieldBuff.class)){
			cachedShield += s.shielding();
		}
		needsShieldUpdate = false;
		return cachedShield;
	}

	private int weighDamageTypes(int dmg, Char rec, ArrayList<DamageType> resists, ArrayList<DamageType> weaknesses, Char inf, DamageType subject){
		int olddmg = dmg;
		dmg = (int) (dmg + Collections.frequency(resists, subject)*subject.whenResist(subject) + Collections.frequency(weaknesses, subject)*subject.whenWeak(subject));
		if(olddmg != dmg){
			if(rec != inf){
				if(olddmg > dmg){
					rec.blockedDamageTypeEffect(olddmg, dmg, rec, resists, weaknesses, inf, subject);
					inf.wasBlockedDamageTypeEffect(olddmg, dmg, rec, resists, weaknesses, inf, subject);
					inf.staminaCheck(dmg);
				} else {
					rec.wasAttackedDamageType(olddmg, dmg, rec, resists, weaknesses, inf, subject);
					inf.attackedDamageType(olddmg, dmg, rec, resists, weaknesses, inf, subject);
					rec.staminaCheck(olddmg);
				}
				if(rec instanceof Hero || inf instanceof Hero){
					if(!Document.ADVENTURERS_GUIDE.isPageRead(Document.GUIDE_DMG_TYPES)){
						GameScene.flashForDocument(Document.ADVENTURERS_GUIDE, Document.GUIDE_DMG_TYPES);
					}
				}
			}
		}
		return dmg;
	}

	private void attackedDamageType(int olddmg, int dmg, Char rec, ArrayList<DamageType> resists, ArrayList<DamageType> weaknesses, Char inf, DamageType subject) {

	}

	private void wasAttackedDamageType(int olddmg, int dmg, Char rec, ArrayList<DamageType> resists, ArrayList<DamageType> weaknesses, Char inf, DamageType subject) {

	}

	private void wasBlockedDamageTypeEffect(int olddmg, int dmg, Char rec, ArrayList<DamageType> resists, ArrayList<DamageType> weaknesses, Char inf, DamageType subject) {

	}

	private void blockedDamageTypeEffect(int olddmg, int dmg, Char rec, ArrayList<DamageType> resists, ArrayList<DamageType> weaknesses, Char inf, DamageType subject) {

	}

	private DamageType getDealtOf(Char ch){
		if(ch instanceof Hero){
			return ((Hero) ch).belongings.attackingWeapon().damageTypeDealt;
		}
		if(ch instanceof Statue){
			return ((Statue) ch).weapon.damageTypeDealt;
		}
		return ch.getDamageTypeDealt();
	}

	private ArrayList<DamageType> getImmuneOf(Char ch){
		ArrayList<DamageType> i;
		i = ch.getDamageImmune();
		i.addAll(getAllDmgTypesImmunities(ch));
		if(ch instanceof Hero){
			i.addAll(((Hero) ch).belongings.attackingWeapon().damageTypeImmune);
			i.addAll(((Hero) ch).belongings.armor().damageTypeImmune);
			return i;
		}
		if(ch instanceof Statue){
			i = ch.getDamageImmune();
			i.addAll(((Statue) ch).weapon.damageTypeImmune);
			if(ch instanceof ArmoredStatue){
				i.addAll(((ArmoredStatue) ch).armor().damageTypeImmune);
			}
			return i;
		}
		return i;
	}

	private ArrayList<DamageType> getResistOf(Char ch){
		ArrayList<DamageType> i;
		i = ch.getDamageResisted();
		i.addAll(getAllDmgTypesResistance(ch));
		if(ch instanceof Hero){
			i.addAll(((Hero) ch).belongings.attackingWeapon().damageTypeResisted);
			i.addAll(((Hero) ch).belongings.armor().damageTypeResisted);
			return i;
		}
		if(ch instanceof Statue){
			i.addAll(((Statue) ch).weapon.damageTypeResisted);
			if(ch instanceof ArmoredStatue){
				i.addAll(((ArmoredStatue) ch).armor().damageTypeResisted);
			}
			return i;
		}
		return i;
	}

	private ArrayList<DamageType> getWeakOf(Char ch){
		ArrayList<DamageType> i;
		i = ch.getDamageWeak();
		i.addAll(getAllDmgTypesWeakness(ch));
		if(ch instanceof Hero){
			i.addAll(((Hero) ch).belongings.attackingWeapon().damageTypeWeak);
			i.addAll(((Hero) ch).belongings.armor().damageTypeWeak);
			return i;
		}
		if(ch instanceof Statue){
			i.addAll(((Statue) ch).weapon.damageTypeWeak);
			if(ch instanceof ArmoredStatue){
				i.addAll(((ArmoredStatue) ch).armor().damageTypeWeak);
			}
			return i;
		}
		return i;
	}

	private int chAttackedByCh(int dmg, Char rec, Char inf, int id){
		switch (id){
			case 1:
				if(getImmuneOf(rec).contains(getDealtOf(inf))){
					dmg = 0;
					rec.sprite.showStatus(CharSprite.POSITIVE, Messages.get(this, "unfazed"));
				}
				break;
			case 2:
				if (getResistOf(rec).contains(getDealtOf(inf)) || getWeakOf(rec).contains(getDealtOf(inf))){
					return weighDamageTypes(dmg, rec, getResistOf(rec), getWeakOf(rec), inf, getDealtOf(inf));
				}
				break;
		}
		return dmg;
	}

	private int damageWithType(int dmg, Char rec, DamageType damageType, int id){
		switch (id){
			case 1:
				if(getImmuneOf(rec).contains(damageType)){
					dmg = 0;
					rec.sprite.showStatus(CharSprite.POSITIVE, Messages.get(this, "unfazed"));
				}
				break;
			case 2:
				if (getResistOf(rec).contains(damageType) || getWeakOf(rec).contains(damageType)){
					return weighDamageTypes(dmg, rec, getResistOf(rec), getWeakOf(rec), rec, damageType);
				}
				break;
		}
		return dmg;
	}

	private int damageTypeCheck(int dmg, Object src, int id){
		if(src instanceof Char){
			dmg = chAttackedByCh(dmg, this, (Char) src, id);
		}
		return dmg;
	}

	private int damageTypeCheck(int dmg, DamageType damageType, int id){
		dmg = damageWithType(dmg, this, damageType, id);
		return dmg;
	}

	public void stanceBreakEffect(Char target){

	}
	
	public void damage(int dmg, Object src, DamageType damageType) {
		
		if (!isAlive() || dmg < 0) {
			return;
		}

		if(isInvulnerable(src.getClass())){
			sprite.showStatus(CharSprite.POSITIVE, Messages.get(this, "invulnerable"));
			return;
		}

		if(this instanceof Hero){
			if(buff(StanceBroken.class) != null){
				dmg *= 5;
				GLog.n(Messages.get(this, "hero_damage_while_stance_broken"));
				if(src instanceof Char){
					((Char) src).stanceBreakEffect(this);
				}
				//effect recovery buff invulnerable for 1f turn
			}
		}

		if(damageType == null){
			dmg = damageTypeCheck(dmg, src,1); //immune
			dmg = damageTypeCheck(dmg, src,2); //resist/weakness
		} else {
			dmg = damageTypeCheck(dmg, damageType, 1); //immune
			dmg = damageTypeCheck(dmg, damageType, 2); //resist/weakness
		}

		if (!(src instanceof LifeLink) && buff(LifeLink.class) != null){
			HashSet<LifeLink> links = buffs(LifeLink.class);
			for (LifeLink link : links.toArray(new LifeLink[0])){
				if (Actor.findById(link.object) == null){
					links.remove(link);
					link.detach();
				}
			}
			dmg = (int)Math.ceil(dmg / (float)(links.size()+1));
			for (LifeLink link : links){
				Char ch = (Char)Actor.findById(link.object);
				if (ch != null) {
					ch.damage(dmg, link, DamageType.MAGIC);
					if (!ch.isAlive()) {
						link.detach();
					}
				}
			}
		}

		Terror t = buff(Terror.class);
		if (t != null){
			t.recover();
		}
		Dread d = buff(Dread.class);
		if (d != null){
			d.recover();
		}
		Charm c = buff(Charm.class);
		if (c != null){
			c.recover(src);
		}
		if (this.buff(Frost.class) != null){
			Buff.detach( this, Frost.class );
		}
		if (this.buff(MagicalSleep.class) != null){
			Buff.detach(this, MagicalSleep.class);
		}
		if (this.buff(Doom.class) != null && !isImmune(Doom.class)){
			dmg *= 1.67f;
		}
		if (alignment != Alignment.ALLY && this.buff(DeathMark.DeathMarkTracker.class) != null){
			dmg *= 1.25f;
		}

		if (buff(Sickle.HarvestBleedTracker.class) != null){
			buff(Sickle.HarvestBleedTracker.class).detach();

			if (!isImmune(Bleeding.class)){
				Bleeding b = buff(Bleeding.class);
				if (b == null){
					b = new Bleeding();
				}
				b.announced = false;
				b.set(dmg, Sickle.HarvestBleedTracker.class);
				b.attachTo(this);
				sprite.showStatus(CharSprite.WARNING, Messages.titleCase(b.name()) + " " + (int)b.level());
				return;
			}
		}

		for (ChampionEnemy buff : buffs(ChampionEnemy.class)){
			dmg = (int) Math.ceil(dmg * buff.damageTakenFactor());
		}

		Class<?> srcClass = src.getClass();
		if (isImmune( srcClass )) {
			dmg = 0;
		} else {
			dmg = Math.round( dmg * resist( srcClass ));
		}
		
		//TODO improve this when I have proper damage source logic
		if (AntiMagic.RESISTS.contains(src.getClass()) && buff(ArcaneArmor.class) != null){
			dmg -= combatRoll(0, buff(ArcaneArmor.class).level());
			if (dmg < 0) dmg = 0;
		}
		
		if (buff( Paralysis.class ) != null) {
			buff( Paralysis.class ).processDamage(dmg);
		}

		int shielded = dmg;
		//FIXME: when I add proper damage properties, should add an IGNORES_SHIELDS property to use here.
		if (!(src instanceof Hunger)){
			for (ShieldBuff s : buffs(ShieldBuff.class)){
				dmg = s.absorbDamage(dmg);
				if (dmg == 0) break;
			}
		}

		if(this instanceof Mob){
			if(((Mob) this).state == ((Mob) this).STANCE_BROKEN){
				dmg *= 5;
				if(src instanceof Hero){
					((Hero) src).belongings.attackingWeapon().stanceBreakingEffect(this);
					GLog.h(Messages.get(this, "enemy_stance_broken"));
				}
				sprite.setGlow(null);
				sprite.hideStanceBroken();
				((Mob) this).state = ((Mob) this).WANDERING;
			}
		}

		shielded -= dmg;
		HP -= dmg;

		if (HP > 0 && shielded > 0 && shielding() == 0){
			if (this instanceof Hero && ((Hero) this).hasTalent(Talent.PROVOKED_ANGER)){
				Buff.affect(this, Talent.ProvokedAngerTracker.class, 5f);
			}
		}

		if (HP > 0 && buff(Grim.GrimTracker.class) != null){

			float finalChance = buff(Grim.GrimTracker.class).maxChance;
			finalChance *= (float)Math.pow( ((HT - HP) / (float)HT), 2);

			if (Random.Float() < finalChance) {
				int extraDmg = Math.round(HP*resist(Grim.class));
				dmg += extraDmg;
				HP -= extraDmg;

				sprite.emitter().burst( ShadowParticle.UP, 5 );
				if (!isAlive() && buff(Grim.GrimTracker.class).qualifiesForBadge){
					Badges.validateGrimWeapon();
				}
			}
		}

		if (HP < 0 && src instanceof Char && alignment == Alignment.ENEMY){
			if (((Char) src).buff(Kinetic.KineticTracker.class) != null){
				int dmgToAdd = -HP;
				dmgToAdd -= ((Char) src).buff(Kinetic.KineticTracker.class).conservedDamage;
				dmgToAdd = Math.round(dmgToAdd * Weapon.Enchantment.genericProcChanceMultiplier((Char) src));
				if (dmgToAdd > 0) {
					Buff.affect((Char) src, Kinetic.ConservedDamage.class).setBonus(dmgToAdd);
				}
				((Char) src).buff(Kinetic.KineticTracker.class).detach();
			}
		}
		
		if (sprite != null) {
			//defaults to normal damage icon if no other ones apply
			int                                                         icon = FloatingText.PHYS_DMG;
			if (NO_ARMOR_PHYSICAL_SOURCES.contains(src.getClass()))     icon = FloatingText.PHYS_DMG_NO_BLOCK;
			if (AntiMagic.RESISTS.contains(src.getClass()))             icon = FloatingText.MAGIC_DMG;
			if (src instanceof Pickaxe)                                 icon = FloatingText.PICK_DMG;

			//special case for sniper when using ranged attacks
			if (src == Dungeon.hero
					&& Dungeon.hero.subClass == HeroSubClass.SNIPER
					&& !Dungeon.level.adjacent(Dungeon.hero.pos, pos)
					&& Dungeon.hero.belongings.attackingWeapon() instanceof MissileWeapon){
				icon = FloatingText.PHYS_DMG_NO_BLOCK;
			}

			if (src instanceof Hunger)                                  icon = FloatingText.HUNGER;
			if (src instanceof Burning)                                 icon = FloatingText.BURNING;
			if (src instanceof Chill || src instanceof Frost)           icon = FloatingText.FROST;
			if (src instanceof GeyserTrap || src instanceof StormCloud) icon = FloatingText.WATER;
			if (src instanceof Burning)                                 icon = FloatingText.BURNING;
			if (src instanceof Electricity)                             icon = FloatingText.SHOCKING;
			if (src instanceof Bleeding)                                icon = FloatingText.BLEEDING;
			if (src instanceof ToxicGas)                                icon = FloatingText.TOXIC;
			if (src instanceof Corrosion)                               icon = FloatingText.CORROSION;
			if (src instanceof Poison)                                  icon = FloatingText.POISON;
			if (src instanceof Ooze)                                    icon = FloatingText.OOZE;
			if (src instanceof Viscosity.DeferedDamage)                 icon = FloatingText.DEFERRED;
			if (src instanceof Corruption)                              icon = FloatingText.CORRUPTION;
			if (src instanceof AscensionChallenge)                      icon = FloatingText.AMULET;

			sprite.showStatusWithIcon(CharSprite.NEGATIVE, Integer.toString(dmg + shielded), icon);
		}

		if (HP < 0) HP = 0;

		if (!isAlive()) {
			die( src );
		} else if (HP == 0 && buff(DeathMark.DeathMarkTracker.class) != null){
			DeathMark.processFearTheReaper(this);
		}
	}

	//these are misc. sources of physical damage which do not apply armor, they get a different icon
	private static HashSet<Class> NO_ARMOR_PHYSICAL_SOURCES = new HashSet<>();
	{
		NO_ARMOR_PHYSICAL_SOURCES.add(CrystalSpire.SpireSpike.class);
		NO_ARMOR_PHYSICAL_SOURCES.add(GnollGeomancer.Boulder.class);
		NO_ARMOR_PHYSICAL_SOURCES.add(GnollGeomancer.GnollRockFall.class);
		NO_ARMOR_PHYSICAL_SOURCES.add(GnollRockfallTrap.class);
		NO_ARMOR_PHYSICAL_SOURCES.add(DwarfKing.KingDamager.class);
		NO_ARMOR_PHYSICAL_SOURCES.add(DwarfKing.Summoning.class);
		NO_ARMOR_PHYSICAL_SOURCES.add(LifeLink.class);
		NO_ARMOR_PHYSICAL_SOURCES.add(Chasm.class);
		NO_ARMOR_PHYSICAL_SOURCES.add(WandOfBlastWave.Knockback.class);
		NO_ARMOR_PHYSICAL_SOURCES.add(Heap.class); //damage from wraiths attempting to spawn from heaps
		NO_ARMOR_PHYSICAL_SOURCES.add(Necromancer.SummoningBlockDamage.class);
		NO_ARMOR_PHYSICAL_SOURCES.add(DriedRose.GhostHero.NoRoseDamage.class);
	}
	
	public void destroy() {
		HP = 0;
		Actor.remove( this );

		for (Char ch : Actor.chars().toArray(new Char[0])){
			if (ch.buff(Charm.class) != null && ch.buff(Charm.class).object == id()){
				ch.buff(Charm.class).detach();
			}
			if (ch.buff(Dread.class) != null && ch.buff(Dread.class).object == id()){
				ch.buff(Dread.class).detach();
			}
			if (ch.buff(Terror.class) != null && ch.buff(Terror.class).object == id()){
				ch.buff(Terror.class).detach();
			}
			if (ch.buff(SnipersMark.class) != null && ch.buff(SnipersMark.class).object == id()){
				ch.buff(SnipersMark.class).detach();
			}
			if (ch.buff(Talent.FollowupStrikeTracker.class) != null
					&& ch.buff(Talent.FollowupStrikeTracker.class).object == id()){
				ch.buff(Talent.FollowupStrikeTracker.class).detach();
			}
			if (ch.buff(Talent.DeadlyFollowupTracker.class) != null
					&& ch.buff(Talent.DeadlyFollowupTracker.class).object == id()){
				ch.buff(Talent.DeadlyFollowupTracker.class).detach();
			}
		}
	}
	
	public void die( Object src ) {
		destroy();
		if (src != Chasm.class) sprite.die();
	}

	//we cache this info to prevent having to call buff(...) in isAlive.
	//This is relevant because we call isAlive during drawing, which has both performance
	//and thread coordination implications
	public boolean deathMarked = false;
	
	public boolean isAlive() {
		return HP > 0 || deathMarked;
	}

	public boolean isActive() {
		return isAlive();
	}

	@Override
	protected void spendConstant(float time) {
		TimekeepersHourglass.timeFreeze freeze = buff(TimekeepersHourglass.timeFreeze.class);
		if (freeze != null) {
			freeze.processTime(time);
			return;
		}

		Swiftthistle.TimeBubble bubble = buff(Swiftthistle.TimeBubble.class);
		if (bubble != null){
			bubble.processTime(time);
			return;
		}

		super.spendConstant(time);
	}

	@Override
	protected void spend( float time ) {

		float timeScale = 1f;
		if (buff( Slow.class ) != null) {
			timeScale *= 0.5f;
			//slowed and chilled do not stack
		} else if (buff( Chill.class ) != null) {
			timeScale *= buff( Chill.class ).speedFactor();
		}
		if (buff( Speed.class ) != null) {
			timeScale *= 2.0f;
		}
		
		super.spend( time / timeScale );
	}
	
	public synchronized LinkedHashSet<Buff> buffs() {
		return new LinkedHashSet<>(buffs);
	}
	
	@SuppressWarnings("unchecked")
	//returns all buffs assignable from the given buff class
	public synchronized <T extends Buff> HashSet<T> buffs( Class<T> c ) {
		HashSet<T> filtered = new HashSet<>();
		for (Buff b : buffs) {
			if (c.isInstance( b )) {
				filtered.add( (T)b );
			}
		}
		return filtered;
	}

	@SuppressWarnings("unchecked")
	//returns an instance of the specific buff class, if it exists. Not just assignable
	public synchronized  <T extends Buff> T buff( Class<T> c ) {
		for (Buff b : buffs) {
			if (b.getClass() == c) {
				return (T)b;
			}
		}
		return null;
	}

	public synchronized boolean isCharmedBy( Char ch ) {
		int chID = ch.id();
		for (Buff b : buffs) {
			if (b instanceof Charm && ((Charm)b).object == chID) {
				return true;
			}
		}
		return false;
	}

	public synchronized boolean add( Buff buff ) {

		if (buff(PotionOfCleansing.Cleanse.class) != null) { //cleansing buff
			if (buff.type == Buff.buffType.NEGATIVE
					&& !(buff instanceof AllyBuff)
					&& !(buff instanceof LostInventory)){
				return false;
			}
		}

		if (sprite != null && buff(Challenge.SpectatorFreeze.class) != null){
			return false; //can't add buffs while frozen and game is loaded
		}

		buffs.add( buff );
		if (Actor.chars().contains(this)) Actor.add( buff );

		if (sprite != null && buff.announced) {
			switch (buff.type) {
				case POSITIVE:
					sprite.showStatus(CharSprite.POSITIVE, Messages.titleCase(buff.name()));
					break;
				case NEGATIVE:
					sprite.showStatus(CharSprite.WARNING, Messages.titleCase(buff.name()));
					break;
				case NEUTRAL:
				default:
					sprite.showStatus(CharSprite.NEUTRAL, Messages.titleCase(buff.name()));
					break;
			}
		}

		return true;

	}
	
	public synchronized boolean remove( Buff buff ) {
		
		buffs.remove( buff );
		Actor.remove( buff );

		return true;
	}
	
	public synchronized void remove( Class<? extends Buff> buffClass ) {
		for (Buff buff : buffs( buffClass )) {
			remove( buff );
		}
	}
	
	@Override
	protected synchronized void onRemove() {
		for (Buff buff : buffs.toArray(new Buff[buffs.size()])) {
			buff.detach();
		}
	}
	
	public synchronized void updateSpriteState() {
		for (Buff buff:buffs) {
			buff.fx( true );
		}
	}
	
	public float stealth() {
		return 0;
	}

	public final void move( int step ) {
		move( step, true );
	}

	//travelling may be false when a character is moving instantaneously, such as via teleportation
	public void move( int step, boolean travelling ) {
		if(travelling){
			//TODO: 1 could be the weight class of the char
			if(!staminaCheck(1) && !(this instanceof Hero)){ //hero gets his own calculation
				step = pos;
			}
		}
		if (travelling && Dungeon.level.adjacent( step, pos ) && buff( Vertigo.class ) != null) {
			sprite.interruptMotion();
			int newPos = pos + PathFinder.NEIGHBOURS8[Random.Int( 8 )];
			if (!(Dungeon.level.passable[newPos] || Dungeon.level.avoid[newPos])
					|| (properties().contains(Property.LARGE) && !Dungeon.level.openSpace[newPos])
					|| Actor.findChar( newPos ) != null)
				return;
			else {
				sprite.move(pos, newPos);
				step = newPos;
			}
		}

		if (Dungeon.level.map[pos] == Terrain.OPEN_DOOR) {
			Door.leave( pos );
		}

		pos = step;

		if (this != Dungeon.hero) {
			sprite.visible = Dungeon.level.heroFOV[pos];
		}

		Dungeon.level.occupyCell(this );
	}
	
	public int distance( Char other ) {
		return Dungeon.level.distance( pos, other.pos );
	}

	public boolean[] modifyPassable( boolean[] passable){
		//do nothing by default, but some chars can pass over terrain that others can't
		return passable;
	}
	
	public void onMotionComplete() {
		//Does nothing by default
		//The main actor thread already accounts for motion,
		// so calling next() here isn't necessary (see Actor.process)
	}
	
	public void onAttackComplete() {
		next();
	}
	
	public void onOperateComplete() {
		next();
	}
	
	//returns percent effectiveness after resistances
	//TODO currently resistances reduce effectiveness by a static 50%, and do not stack.
	public float resist( Class effect ){
		HashSet<Class> resists = new HashSet<>(resistances);
		for (Property p : properties()){
			resists.addAll(p.resistances());
		}
		for (Buff b : buffs()){
			resists.addAll(b.resistances());
		}
		
		float result = 1f;
		for (Class c : resists){
			if (c.isAssignableFrom(effect)){
				result *= 0.5f;
			}
		}
		return result * RingOfElements.resist(this, effect);
	}
	
	protected final HashSet<Class> immunities = new HashSet<>();
	
	public boolean isImmune(Class effect ){
		HashSet<Class> immunes = new HashSet<>(immunities);
		for (Property p : properties()){
			immunes.addAll(p.immunities());
		}
		for (Buff b : buffs()){
			immunes.addAll(b.immunities());
		}
		
		for (Class c : immunes){
			if (c.isAssignableFrom(effect)){
				return true;
			}
		}
		return false;
	}

	protected final HashSet<Class> resistances = new HashSet<>();

	//similar to isImmune, but only factors in damage.
	//Is used in AI decision-making
	public boolean isInvulnerable( Class effect ){
		return buff(Challenge.SpectatorFreeze.class) != null;
	}

	protected HashSet<Property> properties = new HashSet<>();

	public ArrayList<DamageType> getAllDmgTypesResistance(Char ch){
		ArrayList<DamageType> i = new ArrayList<>();
		for(Property p : ch.properties()){
			i.addAll(p.dmgTypeResistances);
		}
		return i;
	}

	public ArrayList<DamageType> getAllDmgTypesWeakness(Char ch){
		ArrayList<DamageType> i = new ArrayList<>();
		for(Property p : ch.properties()){
			i.addAll(p.dmgTypeWeaknesses);
		}
		return i;
	}

	public ArrayList<DamageType> getAllDmgTypesImmunities(Char ch){
		ArrayList<DamageType> i = new ArrayList<>();
		for(Property p : ch.properties()){
			i.addAll(p.dmgTypeImmunities);
		}
		return i;
	}

	public HashSet<Property> properties() {
		HashSet<Property> props = new HashSet<>(properties);
		//TODO any more of these and we should make it a property of the buff, like with resistances/immunities
		if (buff(ChampionEnemy.Giant.class) != null) {
			props.add(Property.LARGE);
		}
		return props;
	}

	public enum Property {
		BOSS(new HashSet<Class>(Arrays.asList(Grim.class, GrimTrap.class, ScrollOfRetribution.class, ScrollOfPsionicBlast.class)),
				new HashSet<Class>(Arrays.asList(AllyBuff.class, Dread.class)),
				new ArrayList<DamageType>(),
				new ArrayList<DamageType>(),
				new ArrayList<DamageType>()),

		MINIBOSS(new HashSet<Class>(),
				new HashSet<Class>(Arrays.asList(AllyBuff.class, Dread.class)),
				new ArrayList<DamageType>(),
				new ArrayList<DamageType>(),
				new ArrayList<DamageType>()),

		BOSS_MINION,
		UNDEAD,
		DEMONIC,
		INORGANIC(new HashSet<Class>(),
				new HashSet<Class>(Arrays.asList(Bleeding.class, ToxicGas.class, Poison.class)),
				new ArrayList<DamageType>(),
				new ArrayList<DamageType>(),
				new ArrayList<DamageType>()),

		FIERY(new HashSet<Class>(Arrays.asList(WandOfFireblast.class, Elemental.FireElemental.class)),
				new HashSet<Class>(Arrays.asList(Burning.class, Blazing.class)),
				new ArrayList<DamageType>(),
				new ArrayList<DamageType>(Arrays.asList(DamageType.WATER, DamageType.FROST)),
				new ArrayList<DamageType>(Collections.singletonList(DamageType.FIRE))),

		MAGIC(new HashSet<Class>(),
				new HashSet<Class>(),
				new ArrayList<DamageType>(Collections.singletonList(DamageType.MAGIC)),
				new ArrayList<DamageType>(),
				new ArrayList<DamageType>()),

		ICY(new HashSet<Class>(Arrays.asList(WandOfFrost.class, Elemental.FrostElemental.class)),
				new HashSet<Class>(Arrays.asList(Frost.class, Chill.class)),
				new ArrayList<DamageType>(),
				new ArrayList<DamageType>(Arrays.asList(DamageType.FIRE, DamageType.WATER)),
				new ArrayList<DamageType>(Collections.singletonList(DamageType.FROST))),

		ACIDIC(new HashSet<Class>(Arrays.asList(Corrosion.class)),
				new HashSet<Class>(Arrays.asList(Ooze.class)),
				new ArrayList<DamageType>(),
				new ArrayList<DamageType>(),
				new ArrayList<DamageType>(Collections.singletonList(DamageType.ACIDIC))),

		ELECTRIC(new HashSet<Class>(Arrays.asList(WandOfLightning.class, Shocking.class, Potential.class,
				Electricity.class, ShockingDart.class, Elemental.ShockElemental.class)),
				new HashSet<Class>(),
				new ArrayList<DamageType>(Collections.singletonList(DamageType.FIRE)),
				new ArrayList<DamageType>(Collections.singletonList(DamageType.EARTH)),
				new ArrayList<DamageType>(Collections.singletonList(DamageType.LIGHTNING))),

		LARGE,
		//used to keep bonfires out of mindvision
		NOT_A_MOB,
		IMMOVABLE(new HashSet<Class>(),
				new HashSet<Class>(Arrays.asList(Vertigo.class, Vortex.class)),
				new ArrayList<DamageType>(),
				new ArrayList<DamageType>(),
				new ArrayList<DamageType>()),

		//A character that acts in an unchanging manner. immune to AI state debuffs or stuns/slows
		STATIC(new HashSet<Class>(),
				new HashSet<Class>(Arrays.asList(AllyBuff.class, Dread.class, Terror.class, Amok.class, Charm.class, Sleep.class,
						Paralysis.class, Frost.class, Chill.class, Slow.class, Speed.class)),
				new ArrayList<DamageType>(),
				new ArrayList<DamageType>(),
				new ArrayList<DamageType>());

		private HashSet<Class> resistances;
		private HashSet<Class> immunities;
		private ArrayList<DamageType> dmgTypeResistances;
		private ArrayList<DamageType> dmgTypeWeaknesses;
		private ArrayList<DamageType> dmgTypeImmunities;
		
		Property(){
			this(new HashSet<Class>(), new HashSet<Class>(), new ArrayList<DamageType>(), new ArrayList<DamageType>(), new ArrayList<DamageType>());
		}
		
		Property( HashSet<Class> resistances, HashSet<Class> immunities, ArrayList<DamageType> dmgTypeResistances, ArrayList<DamageType> dmgTypeWeaknesses, ArrayList<DamageType> dmgTypeImmunities){
			this.resistances = resistances;
			this.immunities = immunities;
			this.dmgTypeResistances = dmgTypeResistances;
			this.dmgTypeWeaknesses = dmgTypeWeaknesses;
			this.dmgTypeImmunities = dmgTypeImmunities;
		}
		
		public HashSet<Class> resistances(){
			return new HashSet<>(resistances);
		}
		
		public HashSet<Class> immunities(){
			return new HashSet<>(immunities);
		}

		public ArrayList<DamageType> getDmgTypeResistances(){
			return new ArrayList<>(dmgTypeResistances);
		}

		public ArrayList<DamageType> getDmgTypeWeaknesses(){
			return new ArrayList<>(dmgTypeWeaknesses);
		}

		public ArrayList<DamageType> getDmgTypeImmunities(){
			return new ArrayList<>(dmgTypeImmunities);
		}

	}

	public static boolean hasProp( Char ch, Property p){
		return (ch != null && ch.properties().contains(p));
	}
}
