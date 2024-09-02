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

package com.soulspixel.soulspixeldungeon.items.wands;

import com.soulspixel.soulspixeldungeon.Assets;
import com.soulspixel.soulspixeldungeon.Badges;
import com.soulspixel.soulspixeldungeon.Challenges;
import com.soulspixel.soulspixeldungeon.Dungeon;
import com.soulspixel.soulspixeldungeon.SoulsPixelDungeon;
import com.soulspixel.soulspixeldungeon.actors.Actor;
import com.soulspixel.soulspixeldungeon.actors.Char;
import com.soulspixel.soulspixeldungeon.actors.blobs.Blob;
import com.soulspixel.soulspixeldungeon.actors.blobs.ConfusionGas;
import com.soulspixel.soulspixeldungeon.actors.blobs.Fire;
import com.soulspixel.soulspixeldungeon.actors.blobs.ParalyticGas;
import com.soulspixel.soulspixeldungeon.actors.blobs.Regrowth;
import com.soulspixel.soulspixeldungeon.actors.blobs.ToxicGas;
import com.soulspixel.soulspixeldungeon.actors.buffs.Buff;
import com.soulspixel.soulspixeldungeon.actors.buffs.Burning;
import com.soulspixel.soulspixeldungeon.actors.buffs.Frost;
import com.soulspixel.soulspixeldungeon.actors.buffs.Hex;
import com.soulspixel.soulspixeldungeon.actors.buffs.Recharging;
import com.soulspixel.soulspixeldungeon.actors.hero.Hero;
import com.soulspixel.soulspixeldungeon.actors.mobs.GoldenMimic;
import com.soulspixel.soulspixeldungeon.actors.mobs.Mimic;
import com.soulspixel.soulspixeldungeon.actors.mobs.npcs.NPC;
import com.soulspixel.soulspixeldungeon.actors.mobs.npcs.Sheep;
import com.soulspixel.soulspixeldungeon.effects.CellEmitter;
import com.soulspixel.soulspixeldungeon.effects.Flare;
import com.soulspixel.soulspixeldungeon.effects.FloatingText;
import com.soulspixel.soulspixeldungeon.effects.MagicMissile;
import com.soulspixel.soulspixeldungeon.effects.Speck;
import com.soulspixel.soulspixeldungeon.effects.SpellSprite;
import com.soulspixel.soulspixeldungeon.effects.particles.ShadowParticle;
import com.soulspixel.soulspixeldungeon.items.Generator;
import com.soulspixel.soulspixeldungeon.items.Item;
import com.soulspixel.soulspixeldungeon.items.bombs.Bomb;
import com.soulspixel.soulspixeldungeon.items.scrolls.ScrollOfMirrorImage;
import com.soulspixel.soulspixeldungeon.items.scrolls.ScrollOfRecharging;
import com.soulspixel.soulspixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.soulspixel.soulspixeldungeon.items.scrolls.exotic.ScrollOfMetamorphosis;
import com.soulspixel.soulspixeldungeon.items.scrolls.exotic.ScrollOfSirensSong;
import com.soulspixel.soulspixeldungeon.items.trinkets.WondrousResin;
import com.soulspixel.soulspixeldungeon.levels.Level;
import com.soulspixel.soulspixeldungeon.levels.Terrain;
import com.soulspixel.soulspixeldungeon.levels.traps.CursingTrap;
import com.soulspixel.soulspixeldungeon.levels.traps.ShockingTrap;
import com.soulspixel.soulspixeldungeon.levels.traps.SummoningTrap;
import com.soulspixel.soulspixeldungeon.mechanics.Ballistica;
import com.soulspixel.soulspixeldungeon.messages.Languages;
import com.soulspixel.soulspixeldungeon.messages.Messages;
import com.soulspixel.soulspixeldungeon.plants.Plant;
import com.soulspixel.soulspixeldungeon.scenes.GameScene;
import com.soulspixel.soulspixeldungeon.scenes.InterlevelScene;
import com.soulspixel.soulspixeldungeon.sprites.CharSprite;
import com.soulspixel.soulspixeldungeon.ui.Icons;
import com.soulspixel.soulspixeldungeon.ui.TargetHealthIndicator;
import com.soulspixel.soulspixeldungeon.utils.GLog;
import com.soulspixel.soulspixeldungeon.windows.WndOptions;
import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.io.IOException;
import java.util.ArrayList;

//helper class to contain all the cursed wand zapping logic, so the main wand class doesn't get huge.
public class CursedWand {

	private static float COMMON_CHANCE = 0.6f;
	private static float UNCOMMON_CHANCE = 0.3f;
	private static float RARE_CHANCE = 0.09f;
	private static float VERY_RARE_CHANCE = 0.01f;

	public static void cursedZap(final Item origin, final Char user, final Ballistica bolt, final Callback afterZap){

		cursedFX(user, bolt, new Callback() {
			@Override
			public void call() {
				if (cursedEffect(origin, user, bolt.collisionPos)){
					if (afterZap != null) afterZap.call();
				}
			}
		});
	}

	public static void tryForWandProc( Char target, Item origin ){
		if (target != null && origin instanceof Wand){
			Wand.wandProc(target, origin.buffedLvl(), 1);
		}
	}

	public static boolean cursedEffect(final Item origin, final Char user, final Char target){
		return cursedEffect(origin, user, target.pos);
	}

	public static boolean cursedEffect(final Item origin, final Char user, final int targetPos){
		switch (Random.chances(new float[]{COMMON_CHANCE, UNCOMMON_CHANCE, RARE_CHANCE, VERY_RARE_CHANCE})){
			case 0: default:
				return commonEffect(origin, user, targetPos);
			case 1:
				return uncommonEffect(origin, user, targetPos);
			case 2:
				return rareEffect(origin, user, targetPos);
			case 3:
				return veryRareEffect(origin, user, targetPos);
		}
	}

	private static boolean commonEffect(final Item origin, final Char user, final int targetPos){
		boolean positiveOnly = Random.Float() < WondrousResin.positiveCurseEffectChance();
		switch(Random.Int(4)){

			//anti-entropy
			//doesn't affect caster if positive only
			case 0: default:
				Char target = Actor.findChar(targetPos);
				if (Random.Int(2) == 0) {
					if (target != null) Buff.affect(target, Burning.class).reignite(target);
					if (!positiveOnly) Buff.affect(user, Frost.class, Frost.DURATION);
				} else {
					if (!positiveOnly)Buff.affect(user, Burning.class).reignite(user);
					if (target != null) Buff.affect(target, Frost.class, Frost.DURATION);
				}
				tryForWandProc(target, origin);
				return true;

			//spawns some regrowth
			case 1:
				GameScene.add( Blob.seed(targetPos, 30, Regrowth.class));
				tryForWandProc(Actor.findChar(targetPos), origin);
				return true;

			//random teleportation
			//can only teleport enemy if positive only
			case 2:
				if(!positiveOnly && Random.Int(2) == 0) {
					if (user != null && !user.properties().contains(Char.Property.IMMOVABLE)) {
						ScrollOfTeleportation.teleportChar(user);
					} else {
						return cursedEffect(origin, user, targetPos);
					}
				} else {
					Char ch = Actor.findChar( targetPos );
					if (ch != null && !ch.properties().contains(Char.Property.IMMOVABLE)) {
						ScrollOfTeleportation.teleportChar(ch);
						tryForWandProc(ch, origin);
					} else {
						return cursedEffect(origin, user, targetPos);
					}
				}
				return true;

			//random gas at location
			case 3:
				Sample.INSTANCE.play( Assets.Sounds.GAS );
				tryForWandProc(Actor.findChar(targetPos), origin);
				switch (Random.Int(3)) {
					case 0: default:
						GameScene.add( Blob.seed( targetPos, 800, ConfusionGas.class ) );
						return true;
					case 1:
						GameScene.add( Blob.seed( targetPos, 500, ToxicGas.class ) );
						return true;
					case 2:
						GameScene.add( Blob.seed( targetPos, 200, ParalyticGas.class ) );
						return true;
				}
		}

	}

	private static boolean uncommonEffect(final Item origin, final Char user, final int targetPos){
		boolean positiveOnly = Random.Float() < WondrousResin.positiveCurseEffectChance();
		switch(Random.Int(4)){

			//Random plant
			case 0: default:
				int pos = targetPos;

				if (Dungeon.level.map[pos] != Terrain.ALCHEMY
						&& !Dungeon.level.pit[pos]
						&& Dungeon.level.traps.get(pos) == null
						&& !Dungeon.isChallenged(Challenges.NO_HERBALISM)) {
					Dungeon.level.plant((Plant.Seed) Generator.randomUsingDefaults(Generator.Category.SEED), pos);
					tryForWandProc(Actor.findChar(pos), origin);
				} else {
					return cursedEffect(origin, user, targetPos);
				}

				return true;

			//Health transfer
			//can only harm enemy if positive only
			case 1:
				final Char target = Actor.findChar( targetPos );
				if (target != null) {
					int damage = Dungeon.scalingDepth() * 2;
					Char toHeal, toDamage;

					if (positiveOnly || Random.Int(2) == 0){
						toHeal = user;
						toDamage = target;
					} else {
						toHeal = target;
						toDamage = user;
					}
					toHeal.HP = Math.min(toHeal.HT, toHeal.HP + damage);
					toHeal.sprite.emitter().burst(Speck.factory(Speck.HEALING), 3);
					toHeal.sprite.showStatusWithIcon( CharSprite.POSITIVE, Integer.toString(damage), FloatingText.HEALING );

					toDamage.damage(damage, new CursedWand(), Char.DamageType.DARK_MAGIC);
					toDamage.sprite.emitter().start(ShadowParticle.UP, 0.05f, 10);

					if (toDamage == Dungeon.hero){
						Sample.INSTANCE.play(Assets.Sounds.CURSED);
						if (!toDamage.isAlive()) {
							if (user == Dungeon.hero && origin != null) {
								Badges.validateDeathFromFriendlyMagic();
								Dungeon.fail( origin );
								GLog.n( Messages.get( CursedWand.class, "ondeath", origin.name() ) );
							} else {
								Badges.validateDeathFromEnemyMagic();
								Dungeon.fail( toHeal );
							}
						}
					} else {
						Sample.INSTANCE.play(Assets.Sounds.BURNING);
					}
					tryForWandProc(target, origin);
				} else {
					return cursedEffect(origin, user, targetPos);
				}
				return true;

			//Bomb explosion
			case 2:
				new Bomb.ConjuredBomb().explode(targetPos);
				tryForWandProc(Actor.findChar(targetPos), origin);
				return true;

			//shock and recharge
			//no shock if positive only
			case 3:
				if (!positiveOnly) new ShockingTrap().set( user.pos ).activate();
				Buff.prolong(user, Recharging.class, Recharging.DURATION);
				ScrollOfRecharging.charge(user);
				SpellSprite.show(user, SpellSprite.CHARGE);
				return true;
		}

	}

	private static boolean rareEffect(final Item origin, final Char user, final int targetPos){
		boolean positiveOnly = Random.Float() < WondrousResin.positiveCurseEffectChance();
		switch(Random.Int(4)){

			//sheep transformation
			case 0: default:

				Char ch = Actor.findChar( targetPos );
				if (ch != null && !(ch instanceof Hero)
						//ignores bosses, questgivers, rat king, etc.
						&& !ch.properties().contains(Char.Property.BOSS)
						&& !ch.properties().contains(Char.Property.MINIBOSS)
						&& !(ch instanceof NPC && ch.alignment == Char.Alignment.NEUTRAL)){
					Sheep sheep = new Sheep();
					sheep.lifespan = 10;
					sheep.pos = ch.pos;
					ch.destroy();
					ch.sprite.killAndErase();
					Dungeon.level.mobs.remove(ch);
					TargetHealthIndicator.instance.target(null);
					GameScene.add(sheep);
					CellEmitter.get(sheep.pos).burst(Speck.factory(Speck.WOOL), 4);
					Sample.INSTANCE.play(Assets.Sounds.PUFF);
					Sample.INSTANCE.play(Assets.Sounds.SHEEP);
					Dungeon.level.occupyCell(sheep);
				} else {
					return cursedEffect(origin, user, targetPos);
				}
				return true;

			//curses!
			//or hexes target if positive only
			case 1:
				if (positiveOnly){
					ch = Actor.findChar( targetPos );
					if (ch != null){
						Buff.affect(ch, Hex.class, Hex.DURATION);
					}
					return true;
				}
				if (user instanceof Hero) {
					CursingTrap.curse( (Hero) user );
				} else {
					return cursedEffect(origin, user, targetPos);
				}
				return true;

			//inter-level teleportation
			//of scroll of teleportation if positive only, or inter-floor teleport disallowed
			case 2:
				if (!positiveOnly && Dungeon.depth > 1 && Dungeon.interfloorTeleportAllowed() && user == Dungeon.hero) {

					//each depth has 1 more weight than the previous depth.
					float[] depths = new float[Dungeon.depth-1];
					for (int i = 1; i < Dungeon.depth; i++) depths[i-1] = i;
					int depth = 1+Random.chances(depths);

					Level.beforeTransition();
					InterlevelScene.mode = InterlevelScene.Mode.RETURN;
					InterlevelScene.returnDepth = depth;
					InterlevelScene.returnBranch = 0;
					InterlevelScene.returnPos = -1;
					Game.switchScene(InterlevelScene.class);

				} else {
					ScrollOfTeleportation.teleportChar(user);

				}
				return true;

			//summon monsters
			//or mirror images if positive only
			case 3:
				if (positiveOnly && user == Dungeon.hero){
					ScrollOfMirrorImage.spawnImages(Dungeon.hero, 2);
				} else {
					new SummoningTrap().set(targetPos).activate();
				}
				return true;
		}
	}

	private static boolean veryRareEffect(final Item origin, final Char user, final int targetPos){
		boolean positiveOnly = Random.Float() < WondrousResin.positiveCurseEffectChance();
		switch( Random.Int(4) ){

			//great forest fire!
			//only grass, no fire, if positive only
			case 0: default:
				for (int i = 0; i < Dungeon.level.length(); i++){
					GameScene.add( Blob.seed(i, 15, Regrowth.class));
				}

				new Flare(8, 32).color(0xFFFF66, true).show(user.sprite, 2f);
				Sample.INSTANCE.play(Assets.Sounds.TELEPORT);
				GLog.p(Messages.get(CursedWand.class, "grass"));
				if (!positiveOnly) {
					GLog.w(Messages.get(CursedWand.class, "fire"));
					do {
						GameScene.add(Blob.seed(Dungeon.level.randomDestination(null), 10, Fire.class));
					} while (Random.Int(5) != 0);
				}
				return true;

			//golden mimic
			//mimic is enthralled if positive only
			case 1:

				Char ch = Actor.findChar(targetPos);
				int spawnCell = targetPos;
				if (ch != null){
					ArrayList<Integer> candidates = new ArrayList<Integer>();
					for (int n : PathFinder.NEIGHBOURS8) {
						int cell = targetPos + n;
						if (Dungeon.level.passable[cell] && Actor.findChar( cell ) == null) {
							candidates.add( cell );
						}
					}
					if (!candidates.isEmpty()){
						spawnCell = Random.element(candidates);
					} else {
						return cursedEffect(origin, user, targetPos);
					}
				}

				Mimic mimic = Mimic.spawnAt(spawnCell, GoldenMimic.class, false);
				mimic.stopHiding();
				mimic.alignment = Char.Alignment.ENEMY;
				//play vfx/sfx manually as mimic isn't in the scene yet
				Sample.INSTANCE.play(Assets.Sounds.MIMIC, 1, 0.85f);
				CellEmitter.get(mimic.pos).burst(Speck.factory(Speck.STAR), 10);
				mimic.items.clear();
				GameScene.add(mimic);

				if (positiveOnly){
					Buff.affect(mimic, ScrollOfSirensSong.Enthralled.class);
				} else {
					Item reward;
					do {
						reward = Generator.randomUsingDefaults(Random.oneOf(Generator.Category.WEAPON, Generator.Category.ARMOR,
								Generator.Category.RING, Generator.Category.WAND));
					} while (reward.level() < 1);
					mimic.items.add(reward);
				}

				return true;

			//appears to crash the game (actually just closes it)
			case 2:
				
				try {
					Dungeon.saveAll();
					if(Messages.lang() != Languages.ENGLISH){
						//Don't bother doing this joke to none-english speakers, I doubt it would translate.
						return cursedEffect(origin, user, targetPos);
					} else {
						SoulsPixelDungeon.runOnRenderThread(
								new Callback() {
									@Override
									public void call() {
										GameScene.show(
												new WndOptions(Icons.get(Icons.WARNING),
														"CURSED WAND ERROR",
														"this application will now self-destruct",
														"abort",
														"retry",
														"fail") {

													@Override
													protected void onSelect(int index) {
														Game.instance.finish();
													}

													@Override
													public void onBackPressed() {
														//do nothing
													}
												}
										);
									}
								}
						);
						return false;
					}
				} catch(IOException e){
					SoulsPixelDungeon.reportException(e);
					//maybe don't kill the game if the save failed.
					return cursedEffect(origin, user, targetPos);
				}

			//random transmogrification
			//or triggers metamorph effect if positive only
			case 3:
				if (positiveOnly){
					GameScene.show(new ScrollOfMetamorphosis.WndMetamorphChoose());
					return true;
				}

				//skips this effect if there is no item to transmogrify
				if (origin == null || user != Dungeon.hero || !Dungeon.hero.belongings.contains(origin)){
					return cursedEffect(origin, user, targetPos);
				}
				origin.detach(Dungeon.hero.belongings.backpack);
				Item result;
				do {
					result = Generator.randomUsingDefaults(Random.oneOf(Generator.Category.WEAPON, Generator.Category.ARMOR,
							Generator.Category.RING, Generator.Category.ARTIFACT));
				} while (result.cursed);
				if (result.isUpgradable()) result.upgrade();
				result.cursed = result.cursedKnown = true;
				if (origin instanceof Wand){
					GLog.w( Messages.get(CursedWand.class, "transmogrify_wand") );
				} else {
					GLog.w( Messages.get(CursedWand.class, "transmogrify_other") );
				}
				Dungeon.level.drop(result, user.pos).sprite.drop();
				return true;
		}
	}

	private static void cursedFX(final Char user, final Ballistica bolt, final Callback callback){
		MagicMissile.boltFromChar( user.sprite.parent,
				MagicMissile.RAINBOW,
				user.sprite,
				bolt.collisionPos,
				callback);
		Sample.INSTANCE.play( Assets.Sounds.ZAP );
	}

}
