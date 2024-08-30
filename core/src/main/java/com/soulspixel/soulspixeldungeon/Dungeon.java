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

package com.soulspixel.soulspixeldungeon;

import com.soulspixel.soulspixeldungeon.actors.Actor;
import com.soulspixel.soulspixeldungeon.actors.Char;
import com.soulspixel.soulspixeldungeon.actors.buffs.Amok;
import com.soulspixel.soulspixeldungeon.actors.buffs.AscensionChallenge;
import com.soulspixel.soulspixeldungeon.actors.buffs.Awareness;
import com.soulspixel.soulspixeldungeon.actors.buffs.Dread;
import com.soulspixel.soulspixeldungeon.actors.buffs.Light;
import com.soulspixel.soulspixeldungeon.actors.buffs.MagicalSight;
import com.soulspixel.soulspixeldungeon.actors.buffs.MindVision;
import com.soulspixel.soulspixeldungeon.actors.buffs.RevealedArea;
import com.soulspixel.soulspixeldungeon.actors.buffs.Terror;
import com.soulspixel.soulspixeldungeon.actors.hero.Hero;
import com.soulspixel.soulspixeldungeon.actors.hero.Talent;
import com.soulspixel.soulspixeldungeon.actors.hero.abilities.huntress.SpiritHawk;
import com.soulspixel.soulspixeldungeon.actors.mobs.Mimic;
import com.soulspixel.soulspixeldungeon.actors.mobs.Mob;
import com.soulspixel.soulspixeldungeon.actors.mobs.npcs.Blacksmith;
import com.soulspixel.soulspixeldungeon.actors.mobs.npcs.Bonfire;
import com.soulspixel.soulspixeldungeon.actors.mobs.npcs.Ghost;
import com.soulspixel.soulspixeldungeon.actors.mobs.npcs.Imp;
import com.soulspixel.soulspixeldungeon.actors.mobs.npcs.Wandmaker;
import com.soulspixel.soulspixeldungeon.items.Amulet;
import com.soulspixel.soulspixeldungeon.items.Generator;
import com.soulspixel.soulspixeldungeon.items.Heap;
import com.soulspixel.soulspixeldungeon.items.Item;
import com.soulspixel.soulspixeldungeon.items.artifacts.TalismanOfForesight;
import com.soulspixel.soulspixeldungeon.items.potions.Potion;
import com.soulspixel.soulspixeldungeon.items.rings.Ring;
import com.soulspixel.soulspixeldungeon.items.scrolls.Scroll;
import com.soulspixel.soulspixeldungeon.items.trinkets.MimicTooth;
import com.soulspixel.soulspixeldungeon.items.wands.WandOfRegrowth;
import com.soulspixel.soulspixeldungeon.items.wands.WandOfWarding;
import com.soulspixel.soulspixeldungeon.journal.Notes;
import com.soulspixel.soulspixeldungeon.levels.CavesBossLevel;
import com.soulspixel.soulspixeldungeon.levels.CavesLevel;
import com.soulspixel.soulspixeldungeon.levels.CityBossLevel;
import com.soulspixel.soulspixeldungeon.levels.CityLevel;
import com.soulspixel.soulspixeldungeon.levels.DeadEndLevel;
import com.soulspixel.soulspixeldungeon.levels.HallsBossLevel;
import com.soulspixel.soulspixeldungeon.levels.HallsLevel;
import com.soulspixel.soulspixeldungeon.levels.LastLevel;
import com.soulspixel.soulspixeldungeon.levels.Level;
import com.soulspixel.soulspixeldungeon.levels.MiningLevel;
import com.soulspixel.soulspixeldungeon.levels.PrisonBossLevel;
import com.soulspixel.soulspixeldungeon.levels.PrisonLevel;
import com.soulspixel.soulspixeldungeon.levels.RegularLevel;
import com.soulspixel.soulspixeldungeon.levels.SewerBossLevel;
import com.soulspixel.soulspixeldungeon.levels.SewerFrogLevel;
import com.soulspixel.soulspixeldungeon.levels.SewerLevel;
import com.soulspixel.soulspixeldungeon.levels.features.LevelTransition;
import com.soulspixel.soulspixeldungeon.levels.rooms.secret.SecretRoom;
import com.soulspixel.soulspixeldungeon.levels.rooms.special.SpecialRoom;
import com.soulspixel.soulspixeldungeon.messages.Messages;
import com.soulspixel.soulspixeldungeon.scenes.GameScene;
import com.soulspixel.soulspixeldungeon.ui.QuickSlotButton;
import com.soulspixel.soulspixeldungeon.ui.Toolbar;
import com.soulspixel.soulspixeldungeon.utils.DungeonSeed;
import com.soulspixel.soulspixeldungeon.windows.WndResurrect;
import com.watabou.noosa.Game;
import com.watabou.utils.BArray;
import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;
import com.watabou.utils.FileUtils;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;
import com.watabou.utils.SparseArray;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.TimeZone;

public class Dungeon {

	//enum of items which have limited spawns, records how many have spawned
	//could all be their own separate numbers, but this allows iterating, much nicer for bundling/initializing.
	public static enum LimitedDrops {
		//limited world drops
		STRENGTH_POTIONS,
		UPGRADE_SCROLLS,
		ARCANE_STYLI,
		ENCH_STONE,
		INT_STONE,
		TRINKET_CATA,
		LAB_ROOM, //actually a room, but logic is the same

		//Health potion sources
		//enemies
		SWARM_HP,
		NECRO_HP,
		BAT_HP,
		WARLOCK_HP,
		//Demon spawners are already limited in their spawnrate, no need to limit their health drops
		//alchemy
		COOKING_HP,
		BLANDFRUIT_SEED,

		//Other limited enemy drops
		SLIME_WEP,
		SKELE_WEP,
		THEIF_MISC,
		GUARD_ARM,
		SHAMAN_WAND,
		DM200_EQUIP,
		GOLEM_EQUIP,

		//containers
		VELVET_POUCH,
		SCROLL_HOLDER,
		POTION_BANDOLIER,
		MAGICAL_HOLSTER,

		//lore documents
		LORE_SEWERS,
		LORE_PRISON,
		LORE_CAVES,
		LORE_CITY,
		LORE_HALLS;

		public int count = 0;

		//for items which can only be dropped once, should directly access count otherwise.
		public boolean dropped(){
			return count != 0;
		}
		public void drop(){
			count = 1;
		}

		public static void reset(){
			for (LimitedDrops lim : values()){
				lim.count = 0;
			}
		}

		public static void store( Bundle bundle ){
			for (LimitedDrops lim : values()){
				bundle.put(lim.name(), lim.count);
			}
		}

		public static void restore( Bundle bundle ){
			for (LimitedDrops lim : values()){
				if (bundle.contains(lim.name())){
					lim.count = bundle.getInt(lim.name());
				} else {
					lim.count = 0;
				}
				
			}

			//pre-v2.2.0 saves
			if (Dungeon.version < 750
					&& Dungeon.isChallenged(Challenges.NO_SCROLLS)
					&& UPGRADE_SCROLLS.count > 0){
				//we now count SOU fully, and just don't drop every 2nd one
				UPGRADE_SCROLLS.count += UPGRADE_SCROLLS.count-1;
			}
		}

	}

	public static int challenges;
	public static int mobsToChampion;
	public static Hero hero;
	public static Level level;

	public static QuickSlot quickslot = new QuickSlot();
	
	public static int depth;
	//determines path the hero is on. Current uses:
	// 0 is the default path
	// 1 is for quest sub-floors
	public static int branch;

	//keeps track of what levels the game should try to load instead of creating fresh
	public static ArrayList<Integer> generatedLevels = new ArrayList<>();

	public static int gold;
	public static int energy;
	
	public static HashSet<Integer> chapters;

	public static SparseArray<ArrayList<Item>> droppedItems;
	public static SparseArray<ArrayList<Item>> droppedItemsBranch1;

	//first variable is only assigned when game is started, second is updated every time game is saved
	public static int initialVersion;
	public static int version;

	public static boolean daily;
	public static boolean dailyReplay;
	public static String customSeedText = "";
	public static long seed;
	
	public static void init() {

		initialVersion = version = Game.versionCode;
		challenges = SPDSettings.challenges();
		mobsToChampion = -1;

		if (daily) {
			//Ensures that daily seeds are not in the range of user-enterable seeds
			seed = SPDSettings.lastDaily() + DungeonSeed.TOTAL_SEEDS;
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ROOT);
			format.setTimeZone(TimeZone.getTimeZone("UTC"));
			customSeedText = format.format(new Date(SPDSettings.lastDaily()));
		} else if (!SPDSettings.customSeed().isEmpty()){
			customSeedText = SPDSettings.customSeed();
			seed = DungeonSeed.convertFromText(customSeedText);
		} else {
			customSeedText = "";
			seed = DungeonSeed.randomSeed();
		}

		Actor.clear();
		Actor.resetNextID();

		//offset seed slightly to avoid output patterns
		Random.pushGenerator( seed+1 );

			Scroll.initLabels();
			Potion.initColors();
			Ring.initGems();

			SpecialRoom.initForRun();
			SecretRoom.initForRun();

			Generator.fullReset();

		Random.resetGenerators();
		
		Statistics.reset();
		Notes.reset();

		quickslot.reset();
		QuickSlotButton.reset();
		Toolbar.swappedQuickslots = false;
		
		depth = 1;
		branch = 0;
		generatedLevels.clear();

		gold = 0;
		energy = 0;

		droppedItems = new SparseArray<>();
		droppedItemsBranch1 = new SparseArray<>();

		LimitedDrops.reset();
		
		chapters = new HashSet<>();
		
		Ghost.Quest.reset();
		Wandmaker.Quest.reset();
		Blacksmith.Quest.reset();
		Imp.Quest.reset();

		hero = new Hero();
		hero.live();
		
		Badges.reset();
		
		GamesInProgress.selectedClass.initHero( hero );
	}

	public static boolean isChallenged( int mask ) {
		return (challenges & mask) != 0;
	}

	public static boolean levelHasBeenGenerated(int depth, int branch){
		return generatedLevels.contains(depth + 1000*branch);
	}

	public static String getFloorName(){
		if(branch == 0){
			return Messages.get(Bonfire.class, "floor_"+depth+"_name");
		} else if(branch != 1) {
			return Messages.get(Bonfire.class, "floor_"+depth+"_branch_"+branch+"_branch_name");
		} else {
			return Messages.get(Bonfire.class, "floor_dead_end_name");
		}
	}

	public static String getFloorDesc(){
		if(branch == 0){
			return Messages.get(Bonfire.class, "floor_"+depth+"_desc");
		} else if(branch != 1) {
			return Messages.get(Bonfire.class, "floor_"+depth+"_branch_"+branch+"_branch_desc");
		} else {
			return Messages.get(Bonfire.class, "floor_dead_end_desc");
		}
	}
	
	public static Level newLevel() {
		
		Dungeon.level = null;
		Actor.clear();
		
		Level level;
		if (branch == 0) {
			switch (depth) {
				case 1: //bonfire secret exit to 3 fall to 2
					level = new SewerLevel();
					break;
				case 2: //-- secret exit to 4 pit from 1
					level = new SewerFrogLevel();
					break;
				case 3: //-- secret entrance to 1 secret exit to 5
				case 4: //bonfire secrete entrance to 2
				case 5: //-- secret entrance to 3
				case 6: //--
				case 7:
				case 8: //--
				case 9: //--
				case 10:
				case 11:  //--
				case 12:  //--
					level = new SewerLevel();
					break;
				case 13:
					level = new SewerBossLevel();
					break;
				case 14:  //--
					level = new SewerLevel();
					break;
				case 15: //shop  //--
				case 16:
				case 17: //--
				case 18: //--
				case 19:
				case 20:  //--
				case 21: //--
				case 22:
				case 23: //--
				case 24: //--
				case 25:
				case 26: //--
				case 27: //--
					level = new PrisonLevel();
					break;
				case 28:
					level = new PrisonBossLevel();
					break;
				case 29:  //--
					level = new PrisonLevel();
					break;
				case 30: //shop  //--
				case 31:
				case 32:  //--
				case 33:  //--
				case 34:
				case 35:  //--
				case 36:  //--
				case 37:  //
				case 38:  //  //--
				case 39:  //  //--
				case 40:  //Minig Level
				case 41:   //--
				case 42: //--
					level = new CavesLevel();
					break;
				case 43:
					level = new CavesBossLevel();
					break;
				case 44:  //--
					level = new CavesLevel();
					break;
				case 45: //shop  //--
				case 46:
				case 47:  //--
				case 48: //--
				case 49:
				case 50:  //--
				case 51:   //--
				case 52:
				case 53:  //--
				case 54:  //--
				case 55:
				case 56: //--
				case 57:  //--
					level = new CityLevel();
					break;
				case 58:
					level = new CityBossLevel();
					break;
				case 59:  //--
					level = new CityLevel();
					break;
				case 60:  //--
				case 61:
				case 62:  //--
				case 63:  //--
				case 64:
				case 65: //--
				case 66: //--
				case 67:
				case 68: //--
				case 69: //--
				case 70:
				case 71: //--
				case 72: //--
					level = new HallsLevel();
					break;
				case 73:
					level = new HallsBossLevel();
					break;
				case 74: //--
				case 75: //--
					level = new HallsLevel();
					break;
				case 76:
					level = new LastLevel();
					break;
				default:
					level = new DeadEndLevel();
			}
		} else if (branch == 1) {
			switch (depth) {
				case 1:
				case 2:
				case 3:
				case 4:
				case 5:
				case 6:
				case 7:
				case 8:
				case 9:
				case 10:
				case 11:
				case 12:
					level = new SewerLevel();
					break;
				case 37:
				case 38:
				case 39:
				case 40:
					level = new MiningLevel();
					break;
				default:
					level = new DeadEndLevel();
			}
		} else if (branch == 2){
			switch (depth){
				case 37:
				case 38:
				case 39:
				case 40:
					level = new MiningLevel();
					break;
				default:
					level = new DeadEndLevel();
			}
		} else {
			level = new DeadEndLevel();
		}

		//dead end levels get cleared, don't count as generated
		if (!(level instanceof DeadEndLevel)){
			//this assumes that we will never have a depth value outside the range 0 to 999
			// or -500 to 499, etc.
			if (!generatedLevels.contains(depth + 1000*branch)) {
				generatedLevels.add(depth + 1000 * branch);
			}

			if (depth > Statistics.deepestFloor && branch == 0) {
				Statistics.deepestFloor = depth;

				if (Statistics.qualifiedForNoKilling) {
					Statistics.completedWithNoKilling = true;
				} else {
					Statistics.completedWithNoKilling = false;
				}
			}
		}

		Statistics.qualifiedForBossRemainsBadge = false;
		
		level.create();
		
		if (branch == 0) Statistics.qualifiedForNoKilling = !bossLevel();
		Statistics.qualifiedForBossChallengeBadge = false;
		
		return level;
	}
	
	public static void resetLevel() {
		
		Actor.clear();
		
		level.reset();
		switchLevel( level, level.entrance() );
	}

	public static Level.Feeling feelingOnLevel(){
		switch (depth){
			default:
				return Level.Feeling.NONE;
			case 2:
				return Level.Feeling.WATER;
		}
	}

	public static boolean hasNoEntrance(){
		boolean v = !((depth-1)%3==0);
		if(v){
			level.entrance=-1;
		}
		return v;
	}

	public static boolean hasNoExit(){
		boolean v = !((depth-1)%3==0);
		if(v){
			level.exit=-1;
		}
		return v;
	}

	public static long seedCurDepth(){
		return seedForDepth(depth, branch);
	}

	public static long seedForDepth(int depth, int branch){
		int lookAhead = depth;
		lookAhead += 100*branch; //Assumes depth is always 1-30, and branch is always 0 or higher

		Random.pushGenerator( seed );

			for (int i = 0; i < lookAhead; i ++) {
				Random.Long(); //we don't care about these values, just need to go through them
			}
			long result = Random.Long();

		Random.popGenerator();
		return result;
	}
	
	public static boolean shopOnLevel() {
		return depth == 15 || depth == 30 || depth == 45;
	}

	public static boolean bonfireOnLevel() {
		return depth == 1;
	}

	public static boolean secretExitOnLevel() {
		return depth == 1 || depth == 2 || depth == 3;
	}

	public static boolean secretEntranceOnLevel() {
		return depth == 3 || depth == 5 || depth == 4;
	}

	public static boolean weakFloorOnLevel() {
		return depth == 1;
	}

	public static boolean pitRoomOnLevel() {
		return depth == 2;
	}

	
	public static boolean bossLevel() {
		return bossLevel( depth );
	}
	
	public static boolean bossLevel( int depth ) {
		return depth == 13 || depth == 28 || depth == 43 || depth == 58 || depth == 73;
	}

	//value used for scaling of damage values and other effects.
	//is usually the dungeon depth, but can be set to 26 when ascending
	public static int scalingDepth(){
		if (Dungeon.hero != null && Dungeon.hero.buff(AscensionChallenge.class) != null){
			return 76;
		} else {
			return depth;
		}
	}

	public static boolean interfloorTeleportAllowed(){
		if (Dungeon.level.locked
				|| Dungeon.level instanceof MiningLevel
				|| (Dungeon.hero != null && Dungeon.hero.belongings.getItem(Amulet.class) != null)){
			return false;
		}
		return true;
	}
	
	public static void switchLevel( final Level level, int pos ) {

		//Position of -2 specifically means trying to place the hero the exit
		if (pos == -2){
			LevelTransition t = level.getTransition(LevelTransition.Type.REGULAR_EXIT);
			if (t != null) pos = t.cell();
		}

		//Place hero at the entrance if they are out of the map (often used for pox = -1)
		// or if they are in solid terrain (except in the mining level, where that happens normally)
		if (pos < 0 || pos >= level.length()
				|| (!(level instanceof MiningLevel) && !level.passable[pos] && !level.avoid[pos])){
			pos = level.getTransition(null).cell();
		}
		
		PathFinder.setMapSize(level.width(), level.height());
		
		Dungeon.level = level;
		hero.pos = pos;

		if (hero.buff(AscensionChallenge.class) != null){
			hero.buff(AscensionChallenge.class).onLevelSwitch();
		}

		Mob.restoreAllies( level, pos );

		Actor.init();

		level.addRespawner();
		
		for(Mob m : level.mobs){
			if (m.pos == hero.pos && !Char.hasProp(m, Char.Property.IMMOVABLE)){
				//displace mob
				for(int i : PathFinder.NEIGHBOURS8){
					if (Actor.findChar(m.pos+i) == null && level.passable[m.pos + i]){
						m.pos += i;
						break;
					}
				}
			}
		}
		
		Light light = hero.buff( Light.class );
		hero.viewDistance = light == null ? level.viewDistance : Math.max( Light.DISTANCE, level.viewDistance );
		
		hero.curAction = hero.lastAction = null;

		observe();
		try {
			saveAll();
		} catch (IOException e) {
			SoulsPixelDungeon.reportException(e);
			/*This only catches IO errors. Yes, this means things can go wrong, and they can go wrong catastrophically.
			But when they do the user will get a nice 'report this issue' dialogue, and I can fix the bug.*/
		}
	}

	public static void dropToChasm( Item item ) {
		if(Dungeon.branch != 0){
			int depth = Dungeon.depth;
			ArrayList<Item> dropped = Dungeon.droppedItemsBranch1.get( depth );
			if (dropped == null) {
				Dungeon.droppedItemsBranch1.put( depth, dropped = new ArrayList<>() );
			}
			dropped.add( item );
		} else {
			int depth = Dungeon.depth+1;
			ArrayList<Item> dropped = Dungeon.droppedItems.get( depth );
			if (dropped == null) {
				Dungeon.droppedItems.put( depth, dropped = new ArrayList<>() );
			}
			dropped.add( item );
		}
	}

	public static boolean posNeeded() {
		//2 POS each floor set
		int posLeftThisSet = 6 - (LimitedDrops.STRENGTH_POTIONS.count - (depth / 13) * 6);
		if (posLeftThisSet <= 0) return false;

		int floorThisSet = (depth % 13);

		//pos drops every two floors, (numbers 1-2, and 3-4) with a 50% chance for the earlier one each time.
		int targetPOSLeft = 2 - floorThisSet/2;
		if (floorThisSet % 2 == 1 && Random.Int(2) == 0) targetPOSLeft --;

		if (targetPOSLeft < posLeftThisSet) return true;
		else return false;

	}
	
	public static boolean souNeeded() {
		int souLeftThisSet;
		//3 SOU each floor set
		souLeftThisSet = 9 - (LimitedDrops.UPGRADE_SCROLLS.count - (depth /13) * 9);
		if (souLeftThisSet <= 0) return false;

		int floorThisSet = (depth % 13);
		//chance is floors left / scrolls left
		return Random.Int(5 - floorThisSet) < souLeftThisSet;
	}
	
	public static boolean asNeeded() {
		//1 AS each floor set
		int asLeftThisSet = 3 - (LimitedDrops.ARCANE_STYLI.count - (depth / 13));
		if (asLeftThisSet <= 0) return false;

		int floorThisSet = (depth % 13);
		//chance is floors left / scrolls left
		return Random.Int(9 - floorThisSet) < asLeftThisSet;
	}

	public static boolean enchStoneNeeded(){
		//1 enchantment stone, spawns on chapter 2 or 3
		if (!LimitedDrops.ENCH_STONE.dropped()){
			int region = 1+depth/5;
			if (region > 1){
				int floorsVisited = depth - 6;
				if (floorsVisited > 12) floorsVisited--; //skip floor 10
				return Random.Int(6-floorsVisited) == 0; //1/8 chance each floor
			}
		}
		return false;
	}

	public static boolean intStoneNeeded(){
		//one stone on floors 1-3
		return depth < 10 && !LimitedDrops.INT_STONE.dropped() && Random.Int(4-depth) == 0;
	}

	public static boolean trinketCataNeeded(){
		//one trinket catalyst on floors 1-3
		return depth < 10 && !LimitedDrops.TRINKET_CATA.dropped() && Random.Int(4-depth) == 0;
	}

	public static boolean labRoomNeeded(){
		//one laboratory each floor set, in floor 3 or 4, 1/2 chance each floor
		int region = 1+depth/6;
		if (region > LimitedDrops.LAB_ROOM.count){
			int floorThisRegion = depth%5;
			if (floorThisRegion >= 3 || (floorThisRegion == 2 && Random.Int(2) == 0)){
				return true;
			}
		}
		return false;
	}

	// 1/4
	// 3/4 * 1/3 = 3/12 = 1/4
	// 3/4 * 2/3 * 1/2 = 6/24 = 1/4
	// 1/4

	private static final String INIT_VER	= "init_ver";
	private static final String VERSION		= "version";
	private static final String SEED		= "seed";
	private static final String CUSTOM_SEED	= "custom_seed";
	private static final String DAILY	    = "daily";
	private static final String DAILY_REPLAY= "daily_replay";
	private static final String CHALLENGES	= "challenges";
	private static final String MOBS_TO_CHAMPION	= "mobs_to_champion";
	private static final String HERO		= "hero";
	private static final String DEPTH		= "depth";
	private static final String BRANCH		= "branch";
	private static final String GENERATED_LEVELS    = "generated_levels";
	private static final String GOLD		= "gold";
	private static final String ENERGY		= "energy";
	private static final String DROPPED     = "dropped%d";
	private static final String DROPPEDB1     = "droppedb1%d";
	private static final String PORTED      = "ported%d";
	private static final String LEVEL		= "level";
	private static final String LIMDROPS    = "limited_drops";
	private static final String CHAPTERS	= "chapters";
	private static final String QUESTS		= "quests";
	private static final String BADGES		= "badges";
	
	public static void saveGame( int save ) {
		try {
			Bundle bundle = new Bundle();

			bundle.put( INIT_VER, initialVersion );
			bundle.put( VERSION, version = Game.versionCode );
			bundle.put( SEED, seed );
			bundle.put( CUSTOM_SEED, customSeedText );
			bundle.put( DAILY, daily );
			bundle.put( DAILY_REPLAY, dailyReplay );
			bundle.put( CHALLENGES, challenges );
			bundle.put( MOBS_TO_CHAMPION, mobsToChampion );
			bundle.put( HERO, hero );
			bundle.put( DEPTH, depth );
			bundle.put( BRANCH, branch );

			bundle.put( GOLD, gold );
			bundle.put( ENERGY, energy );

			for (int d : droppedItems.keyArray()) {
				bundle.put(Messages.format(DROPPED, d), droppedItems.get(d));
			}
			for (int d : droppedItemsBranch1.keyArray()) {
				bundle.put(Messages.format(DROPPEDB1, d), droppedItemsBranch1.get(d));
			}

			quickslot.storePlaceholders( bundle );

			Bundle limDrops = new Bundle();
			LimitedDrops.store( limDrops );
			bundle.put ( LIMDROPS, limDrops );
			
			int count = 0;
			int ids[] = new int[chapters.size()];
			for (Integer id : chapters) {
				ids[count++] = id;
			}
			bundle.put( CHAPTERS, ids );
			
			Bundle quests = new Bundle();
			Ghost		.Quest.storeInBundle( quests );
			Wandmaker	.Quest.storeInBundle( quests );
			Blacksmith	.Quest.storeInBundle( quests );
			Imp			.Quest.storeInBundle( quests );
			bundle.put( QUESTS, quests );
			
			SpecialRoom.storeRoomsInBundle( bundle );
			SecretRoom.storeRoomsInBundle( bundle );
			
			Statistics.storeInBundle( bundle );
			Notes.storeInBundle( bundle );
			Generator.storeInBundle( bundle );

			int[] bundleArr = new int[generatedLevels.size()];
			for (int i = 0; i < generatedLevels.size(); i++){
				bundleArr[i] = generatedLevels.get(i);
			}
			bundle.put( GENERATED_LEVELS, bundleArr);
			
			Scroll.save( bundle );
			Potion.save( bundle );
			Ring.save( bundle );

			Actor.storeNextID( bundle );
			
			Bundle badges = new Bundle();
			Badges.saveLocal( badges );
			bundle.put( BADGES, badges );
			
			FileUtils.bundleToFile( GamesInProgress.gameFile(save), bundle);
			
		} catch (IOException e) {
			GamesInProgress.setUnknown( save );
			SoulsPixelDungeon.reportException(e);
		}
	}
	
	public static void saveLevel( int save ) throws IOException {
		Bundle bundle = new Bundle();
		bundle.put( LEVEL, level );
		
		FileUtils.bundleToFile(GamesInProgress.depthFile( save, depth, branch ), bundle);
	}
	
	public static void saveAll() throws IOException {
		if (hero != null && (hero.isAlive() || WndResurrect.instance != null)) {
			
			Actor.fixTime();
			updateLevelExplored();
			saveGame( GamesInProgress.curSlot );
			saveLevel( GamesInProgress.curSlot );

			GamesInProgress.set( GamesInProgress.curSlot );

		}
	}
	
	public static void loadGame( int save ) throws IOException {
		loadGame( save, true );
	}
	
	public static void loadGame( int save, boolean fullLoad ) throws IOException {
		
		Bundle bundle = FileUtils.bundleFromFile( GamesInProgress.gameFile( save ) );

		//pre-1.3.0 saves
		if (bundle.contains(INIT_VER)){
			initialVersion = bundle.getInt( INIT_VER );
		} else {
			initialVersion = bundle.getInt( VERSION );
		}

		version = bundle.getInt( VERSION );

		seed = bundle.contains( SEED ) ? bundle.getLong( SEED ) : DungeonSeed.randomSeed();
		customSeedText = bundle.getString( CUSTOM_SEED );
		daily = bundle.getBoolean( DAILY );
		dailyReplay = bundle.getBoolean( DAILY_REPLAY );

		Actor.clear();
		Actor.restoreNextID( bundle );

		quickslot.reset();
		QuickSlotButton.reset();
		Toolbar.swappedQuickslots = false;

		Dungeon.challenges = bundle.getInt( CHALLENGES );
		Dungeon.mobsToChampion = bundle.getInt( MOBS_TO_CHAMPION );
		
		Dungeon.level = null;
		Dungeon.depth = -1;
		
		Scroll.restore( bundle );
		Potion.restore( bundle );
		Ring.restore( bundle );

		quickslot.restorePlaceholders( bundle );
		
		if (fullLoad) {
			
			LimitedDrops.restore( bundle.getBundle(LIMDROPS) );

			chapters = new HashSet<>();
			int ids[] = bundle.getIntArray( CHAPTERS );
			if (ids != null) {
				for (int id : ids) {
					chapters.add( id );
				}
			}
			
			Bundle quests = bundle.getBundle( QUESTS );
			if (!quests.isNull()) {
				Ghost.Quest.restoreFromBundle( quests );
				Wandmaker.Quest.restoreFromBundle( quests );
				Blacksmith.Quest.restoreFromBundle( quests );
				Imp.Quest.restoreFromBundle( quests );
			} else {
				Ghost.Quest.reset();
				Wandmaker.Quest.reset();
				Blacksmith.Quest.reset();
				Imp.Quest.reset();
			}
			
			SpecialRoom.restoreRoomsFromBundle(bundle);
			SecretRoom.restoreRoomsFromBundle(bundle);
		}
		
		Bundle badges = bundle.getBundle(BADGES);
		if (!badges.isNull()) {
			Badges.loadLocal( badges );
		} else {
			Badges.reset();
		}
		
		Notes.restoreFromBundle( bundle );
		
		hero = null;
		hero = (Hero)bundle.get( HERO );
		
		depth = bundle.getInt( DEPTH );
		branch = bundle.getInt( BRANCH );

		gold = bundle.getInt( GOLD );
		energy = bundle.getInt( ENERGY );

		Statistics.restoreFromBundle( bundle );
		Generator.restoreFromBundle( bundle );

		generatedLevels.clear();
		if (bundle.contains(GENERATED_LEVELS)){
			for (int i : bundle.getIntArray(GENERATED_LEVELS)){
				generatedLevels.add(i);
			}
		//pre-v2.1.1 saves
		} else  {
			for (int i = 1; i <= Statistics.deepestFloor; i++){
				generatedLevels.add(i);
			}
		}

		droppedItems = new SparseArray<>();
		for (int i=1; i <= 76; i++) {
			
			//dropped items
			ArrayList<Item> items = new ArrayList<>();
			if (bundle.contains(Messages.format( DROPPED, i )))
				for (Bundlable b : bundle.getCollection( Messages.format( DROPPED, i ) ) ) {
					items.add( (Item)b );
				}
			if (!items.isEmpty()) {
				droppedItems.put( i, items );
			}

		}
		droppedItemsBranch1 = new SparseArray<>();
		for (int i=1; i <= 76; i++) {

			//dropped items
			ArrayList<Item> items = new ArrayList<>();
			if (bundle.contains(Messages.format( DROPPEDB1, i )))
				for (Bundlable b : bundle.getCollection( Messages.format( DROPPEDB1, i ) ) ) {
					items.add( (Item)b );
				}
			if (!items.isEmpty()) {
				droppedItemsBranch1.put( i, items );
			}

		}
	}
	
	public static Level loadLevel( int save ) throws IOException {
		
		Dungeon.level = null;
		Actor.clear();

		Bundle bundle = FileUtils.bundleFromFile( GamesInProgress.depthFile( save, depth, branch ));

		Level level = (Level)bundle.get( LEVEL );

		if (level == null){
			throw new IOException();
		} else {
			return level;
		}
	}
	
	public static void deleteGame( int save, boolean deleteLevels ) {

		if (deleteLevels) {
			String folder = GamesInProgress.gameFolder(save);
			for (String file : FileUtils.filesInDir(folder)){
				if (file.contains("depth")){
					FileUtils.deleteFile(folder + "/" + file);
				}
			}
		}

		FileUtils.overwriteFile(GamesInProgress.gameFile(save), 1);
		
		GamesInProgress.delete( save );
	}
	
	public static void preview( GamesInProgress.Info info, Bundle bundle ) {
		info.depth = bundle.getInt( DEPTH );
		info.version = bundle.getInt( VERSION );
		info.challenges = bundle.getInt( CHALLENGES );
		info.seed = bundle.getLong( SEED );
		info.customSeed = bundle.getString( CUSTOM_SEED );
		info.daily = bundle.getBoolean( DAILY );
		info.dailyReplay = bundle.getBoolean( DAILY_REPLAY );

		Hero.preview( info, bundle.getBundle( HERO ) );
		Statistics.preview( info, bundle );
	}
	
	public static void fail( Object cause ) {
		if (WndResurrect.instance == null) {
			updateLevelExplored();
			Statistics.gameWon = false;
			Rankings.INSTANCE.submit( false, cause );
		}
	}
	
	public static void win( Object cause ) {

		updateLevelExplored();
		Statistics.gameWon = true;

		hero.belongings.identify();

		Rankings.INSTANCE.submit( true, cause );
	}

	public static void updateLevelExplored(){
		if (branch == 0 && level instanceof RegularLevel && !Dungeon.bossLevel()){
			Statistics.floorsExplored.put( depth, level.isLevelExplored(depth));
		}
	}

	//default to recomputing based on max hero vision, in case vision just shrank/grew
	public static void observe(){
		int dist = Math.max(Dungeon.hero.viewDistance, 8);
		dist *= 1f + 0.25f*Dungeon.hero.pointsInTalent(Talent.FARSIGHT);

		if (Dungeon.hero.buff(MagicalSight.class) != null){
			dist = Math.max( dist, MagicalSight.DISTANCE );
		}

		observe( dist+1 );
	}
	
	public static void observe( int dist ) {

		if (level == null) {
			return;
		}
		
		level.updateFieldOfView(hero, level.heroFOV);

		int x = hero.pos % level.width();
		int y = hero.pos / level.width();
	
		//left, right, top, bottom
		int l = Math.max( 0, x - dist );
		int r = Math.min( x + dist, level.width() - 1 );
		int t = Math.max( 0, y - dist );
		int b = Math.min( y + dist, level.height() - 1 );
	
		int width = r - l + 1;
		int height = b - t + 1;
		
		int pos = l + t * level.width();
	
		for (int i = t; i <= b; i++) {
			BArray.or( level.visited, level.heroFOV, pos, width, level.visited );
			pos+=level.width();
		}

		//always visit adjacent tiles, even if they aren't seen
		for (int i : PathFinder.NEIGHBOURS9){
			level.visited[hero.pos+i] = true;
		}
	
		GameScene.updateFog(l, t, width, height);

		boolean stealthyMimics = MimicTooth.stealthyMimics();
		if (hero.buff(MindVision.class) != null){
			for (Mob m : level.mobs.toArray(new Mob[0])){
				if (stealthyMimics && m instanceof Mimic && m.alignment == Char.Alignment.NEUTRAL){
					continue;
				}

				BArray.or( level.visited, level.heroFOV, m.pos - 1 - level.width(), 3, level.visited );
				BArray.or( level.visited, level.heroFOV, m.pos - 1, 3, level.visited );
				BArray.or( level.visited, level.heroFOV, m.pos - 1 + level.width(), 3, level.visited );
				//updates adjacent cells too
				GameScene.updateFog(m.pos, 2);
			}
		}

		if (hero.buff(Awareness.class) != null){
			for (Heap h : level.heaps.valueList()){
				BArray.or( level.visited, level.heroFOV, h.pos - 1 - level.width(), 3, level.visited );
				BArray.or( level.visited, level.heroFOV, h.pos - 1, 3, level.visited );
				BArray.or( level.visited, level.heroFOV, h.pos - 1 + level.width(), 3, level.visited );
				GameScene.updateFog(h.pos, 2);
			}
		}

		for (TalismanOfForesight.CharAwareness c : hero.buffs(TalismanOfForesight.CharAwareness.class)){
			Char ch = (Char) Actor.findById(c.charID);
			if (ch == null || !ch.isAlive()) continue;
			BArray.or( level.visited, level.heroFOV, ch.pos - 1 - level.width(), 3, level.visited );
			BArray.or( level.visited, level.heroFOV, ch.pos - 1, 3, level.visited );
			BArray.or( level.visited, level.heroFOV, ch.pos - 1 + level.width(), 3, level.visited );
			GameScene.updateFog(ch.pos, 2);
		}

		for (TalismanOfForesight.HeapAwareness h : hero.buffs(TalismanOfForesight.HeapAwareness.class)){
			if (Dungeon.depth != h.depth || Dungeon.branch != h.branch) continue;
			BArray.or( level.visited, level.heroFOV, h.pos - 1 - level.width(), 3, level.visited );
			BArray.or( level.visited, level.heroFOV, h.pos - 1, 3, level.visited );
			BArray.or( level.visited, level.heroFOV, h.pos - 1 + level.width(), 3, level.visited );
			GameScene.updateFog(h.pos, 2);
		}

		for (RevealedArea a : hero.buffs(RevealedArea.class)){
			if (Dungeon.depth != a.depth || Dungeon.branch != a.branch) continue;
			BArray.or( level.visited, level.heroFOV, a.pos - 1 - level.width(), 3, level.visited );
			BArray.or( level.visited, level.heroFOV, a.pos - 1, 3, level.visited );
			BArray.or( level.visited, level.heroFOV, a.pos - 1 + level.width(), 3, level.visited );
			GameScene.updateFog(a.pos, 2);
		}

		for (Char ch : Actor.chars()){
			if (ch instanceof WandOfWarding.Ward
					|| ch instanceof WandOfRegrowth.Lotus
					|| ch instanceof SpiritHawk.HawkAlly){
				x = ch.pos % level.width();
				y = ch.pos / level.width();

				//left, right, top, bottom
				dist = ch.viewDistance+1;
				l = Math.max( 0, x - dist );
				r = Math.min( x + dist, level.width() - 1 );
				t = Math.max( 0, y - dist );
				b = Math.min( y + dist, level.height() - 1 );

				width = r - l + 1;
				height = b - t + 1;

				pos = l + t * level.width();

				for (int i = t; i <= b; i++) {
					BArray.or( level.visited, level.heroFOV, pos, width, level.visited );
					pos+=level.width();
				}
				GameScene.updateFog(ch.pos, dist);
			}
		}

		GameScene.afterObserve();
	}

	//we store this to avoid having to re-allocate the array with each pathfind
	private static boolean[] passable;

	private static void setupPassable(){
		if (passable == null || passable.length != Dungeon.level.length())
			passable = new boolean[Dungeon.level.length()];
		else
			BArray.setFalse(passable);
	}

	public static boolean[] findPassable(Char ch, boolean[] pass, boolean[] vis, boolean chars){
		return findPassable(ch, pass, vis, chars, chars);
	}

	public static boolean[] findPassable(Char ch, boolean[] pass, boolean[] vis, boolean chars, boolean considerLarge){
		setupPassable();
		if (ch.flying || ch.buff( Amok.class ) != null) {
			BArray.or( pass, Dungeon.level.avoid, passable );
		} else {
			System.arraycopy( pass, 0, passable, 0, Dungeon.level.length() );
		}

		if (considerLarge && Char.hasProp(ch, Char.Property.LARGE)){
			BArray.and( passable, Dungeon.level.openSpace, passable );
		}

		ch.modifyPassable(passable);

		if (chars) {
			for (Char c : Actor.chars()) {
				if (vis[c.pos]) {
					passable[c.pos] = false;
				}
			}
		}

		return passable;
	}

	public static PathFinder.Path findPath(Char ch, int to, boolean[] pass, boolean[] vis, boolean chars) {

		return PathFinder.find( ch.pos, to, findPassable(ch, pass, vis, chars) );

	}
	
	public static int findStep(Char ch, int to, boolean[] pass, boolean[] visible, boolean chars ) {

		if (Dungeon.level.adjacent( ch.pos, to )) {
			return Actor.findChar( to ) == null && pass[to] ? to : -1;
		}

		return PathFinder.getStep( ch.pos, to, findPassable(ch, pass, visible, chars) );

	}
	
	public static int flee( Char ch, int from, boolean[] pass, boolean[] visible, boolean chars ) {
		boolean[] passable = findPassable(ch, pass, visible, false, true);
		passable[ch.pos] = true;

		//only consider other chars impassable if our retreat step may collide with them
		if (chars) {
			for (Char c : Actor.chars()) {
				if (c.pos == from || Dungeon.level.adjacent(c.pos, ch.pos)) {
					passable[c.pos] = false;
				}
			}
		}

		//chars affected by terror have a shorter lookahead and can't approach the fear source
		boolean canApproachFromPos = ch.buff(Terror.class) == null && ch.buff(Dread.class) == null;
		return PathFinder.getStepBack( ch.pos, from, canApproachFromPos ? 8 : 4, passable, canApproachFromPos );
		
	}

}
