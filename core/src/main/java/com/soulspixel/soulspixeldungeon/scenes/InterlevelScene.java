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

package com.soulspixel.soulspixeldungeon.scenes;

import com.soulspixel.soulspixeldungeon.Assets;
import com.soulspixel.soulspixeldungeon.Dungeon;
import com.soulspixel.soulspixeldungeon.GamesInProgress;
import com.soulspixel.soulspixeldungeon.SoulsPixelDungeon;
import com.soulspixel.soulspixeldungeon.Statistics;
import com.soulspixel.soulspixeldungeon.actors.Actor;
import com.soulspixel.soulspixeldungeon.actors.buffs.Buff;
import com.soulspixel.soulspixeldungeon.actors.mobs.Mob;
import com.soulspixel.soulspixeldungeon.items.Item;
import com.soulspixel.soulspixeldungeon.items.LostBackpack;
import com.soulspixel.soulspixeldungeon.items.ShowItems;
import com.soulspixel.soulspixeldungeon.levels.Level;
import com.soulspixel.soulspixeldungeon.levels.Terrain;
import com.soulspixel.soulspixeldungeon.levels.features.Chasm;
import com.soulspixel.soulspixeldungeon.levels.features.LevelTransition;
import com.soulspixel.soulspixeldungeon.levels.rooms.special.SpecialRoom;
import com.soulspixel.soulspixeldungeon.messages.Messages;
import com.soulspixel.soulspixeldungeon.sprites.ItemSprite;
import com.soulspixel.soulspixeldungeon.ui.GameLog;
import com.soulspixel.soulspixeldungeon.ui.RenderedTextBlock;
import com.soulspixel.soulspixeldungeon.ui.Window;
import com.soulspixel.soulspixeldungeon.windows.WndError;
import com.watabou.gltextures.TextureCache;
import com.watabou.glwrap.Blending;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.Image;
import com.watabou.noosa.NoosaScript;
import com.watabou.noosa.NoosaScriptNoLighting;
import com.watabou.noosa.SkinnedBlock;
import com.watabou.utils.BArray;
import com.watabou.utils.DeviceCompat;
import com.watabou.utils.Random;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class InterlevelScene extends PixelScene {
	
	//slow fade on entering a new region
	private static final float SLOW_FADE = 2f; //.33 in, 1.33 steady, .33 out, 2 seconds total
	//norm fade when loading, falling, returning, or descending to a new floor
	private static final float NORM_FADE = 0.67f; //.33 in, .67 steady, .33 out, 1.33 seconds total
	//fast fade when ascending, or descending to a floor you've been on
	private static final float FAST_FADE = 0.50f; //.33 in, .33 steady, .33 out, 1 second total
	
	private static float fadeTime;
	
	public enum Mode {
		DESCEND, ASCEND, CONTINUE, RESURRECT, RETURN, FALL, RESET, NONE,
		SECRET_EXIT, SECRET_ENTRANCE, ASCEND_FALL_BRANCH, DESCEND_FALL_BRANCH
	}
	public static Mode mode;

	public static LevelTransition curTransition = null;
	public static int returnDepth;
	public static int returnBranch;
	public static int returnPos;

	public static boolean fallIntoPit;
	
	private enum Phase {
		FADE_IN, STATIC, FADE_OUT
	}
	private Phase phase;
	private float timeLeft;
	
	private RenderedTextBlock message;
	private RenderedTextBlock title;
	private Image sprite;
	
	private static Thread thread;
	private static Exception error = null;
	private float waitingTime;

	public static int lastRegion = -1;

	{
		inGameScene = true;
	}

	//TODO: work around -> ALL items should be split into desc and info in Messages
	public String removeLinesWithPercentAndUnderscore(String input) {
		StringBuilder result = new StringBuilder();
		String[] lines = input.split("\n");

		for (String line : lines) {
			if (!line.contains("%_") || !line.contains("_%") || !line.contains("_")) {
				result.append(line).append("\n");
			}
		}

		// Remove the last newline character if it exists
		if (result.length() > 0) {
			result.setLength(result.length() - 1);
		}

		return result.toString();
	}
	
	@Override
	public void create() {
		super.create();
		
		String loadingAsset;
		int loadingDepth;
		final float scrollSpeed;
		fadeTime = SLOW_FADE;
		switch (mode){
			default:
				loadingDepth = Dungeon.depth;
				scrollSpeed = 0;
				break;
			case CONTINUE:
				loadingDepth = GamesInProgress.check(GamesInProgress.curSlot).depth;
				scrollSpeed = 5;
				break;
			case DESCEND:
				if (Dungeon.hero == null){
					loadingDepth = 1;
					fadeTime = SLOW_FADE;
				} else {
					if (curTransition != null)  loadingDepth = curTransition.destDepth;
					else                        loadingDepth = Dungeon.depth+1;
					if (Statistics.deepestFloor >= loadingDepth) {
						fadeTime = SLOW_FADE;
					}
				}
				scrollSpeed = 5;
				break;
			case FALL:
				loadingDepth = Dungeon.depth+1;
				scrollSpeed = 50;
				break;
			case ASCEND:
				fadeTime = SLOW_FADE;
				if (curTransition != null)  loadingDepth = curTransition.destDepth;
				else                        loadingDepth = Dungeon.depth-1;
				scrollSpeed = -5;
				break;
			case RETURN:
				loadingDepth = returnDepth;
				scrollSpeed = returnDepth > Dungeon.depth ? 15 : -15;
				break;
		}

		//flush the texture cache whenever moving between floors, helps reduce memory load
		int depth = loadingDepth;
		if (depth != lastRegion){
			TextureCache.clear();
			lastRegion = depth;
		}

		//TODO: Update
		switch (lastRegion){
			default:
				loadingAsset = Assets.Interfaces.SHADOW;
				break;
			case 1:
            case 3:
                loadingAsset = Assets.Interfaces.LOADING_SEWERS;
				break;
			case 2:
				loadingAsset = Assets.Interfaces.LOADING_SEWERS_FROG;
				break;
        }

		if(mode == Mode.SECRET_ENTRANCE || mode == Mode.SECRET_EXIT || mode == Mode.ASCEND_FALL_BRANCH || mode == Mode.DESCEND_FALL_BRANCH){
			fadeTime = SLOW_FADE;
			loadingAsset = Assets.Interfaces.SHADOW;
		}
		
		SkinnedBlock bg = new SkinnedBlock(Camera.main.width, Camera.main.height, loadingAsset ){
			@Override
			protected NoosaScript script() {
				return NoosaScriptNoLighting.get();
			}
			
			@Override
			public void draw() {
				Blending.disable();
				super.draw();
				Blending.enable();
			}
			
			@Override
			public void update() {
				super.update();
				offset(0, Game.elapsed * scrollSpeed);
			}
		};
		bg.scale(4, 4);
		bg.autoAdjust = true;
		add(bg);
		
		Image im = new Image(TextureCache.createGradient(0xAA000000, 0xBB000000, 0xCC000000, 0xDD000000, 0xFF000000)){
			@Override
			public void update() {
				super.update();
				if (phase == Phase.FADE_IN)         aa = Math.max( 0, (timeLeft - (fadeTime - 0.333f)));
				else if (phase == Phase.FADE_OUT)   aa = Math.max( 0, (0.333f - timeLeft));
				else                                aa = 0;
			}
		};
		im.angle = 90;
		im.x = Camera.main.width;
		im.scale.x = Camera.main.height/5f;
		im.scale.y = Camera.main.width;
		add(im);

		Class<?> item = Random.element(ShowItems.showableItems.keySet());
		String text = Messages.get(item, "desc");
		String name = Messages.get(item, "name");

		text = removeLinesWithPercentAndUnderscore(text);
		name = Messages.titleCase(name);

		message = PixelScene.renderTextBlock( text, 9 );
		message.maxWidth(120);
		message.setPos((Camera.main.width - message.width()) / 2,
				(Camera.main.height - message.height()) / 2);
		align(message);
		add( message );

		title = PixelScene.renderTextBlock( name, 12 );
		title.hardlight(Window.TITLE_COLOR);
		title.maxWidth(120);
		title.setPos(message.left(), message.top()-title.height()-3);
		align(title);
		add( title );

		sprite = new ItemSprite(ShowItems.showableItems.get(item));
		sprite.scale.scale(2f);
		sprite.x = title.left()-(sprite.width()+3);
		sprite.y = title.top();
		align(sprite);
		add(sprite);

		phase = Phase.FADE_IN;
		timeLeft = fadeTime;
		
		if (thread == null) {
			thread = new Thread() {
				@Override
				public void run() {
					
					try {

						Actor.fixTime();

						switch (mode) {
							case DESCEND:
								descend();
								break;
							case ASCEND:
								ascend();
								break;
							case CONTINUE:
								restore();
								break;
							case RESURRECT:
								resurrect();
								break;
							case RETURN:
								returnTo();
								break;
							case FALL:
								fall();
								break;
							case RESET:
								reset();
								break;
							case SECRET_ENTRANCE:
								secretEntrance();
								break;
							case SECRET_EXIT:
								secretExit();
								break;
							case ASCEND_FALL_BRANCH:
								ascendFallBranch();
								break;
							case DESCEND_FALL_BRANCH:
								descendFallBranch();
								break;
						}
						
					} catch (Exception e) {
						
						error = e;
						
					}

					synchronized (thread) {
						if (phase == Phase.STATIC && error == null) {
							phase = Phase.FADE_OUT;
							timeLeft = fadeTime;
						}
					}
				}
			};
			thread.start();
		}
		waitingTime = 0f;
	}
	
	@Override
	public void update() {
		super.update();

		waitingTime += Game.elapsed;
		
		float p = timeLeft / fadeTime;
		
		switch (phase) {
		
		case FADE_IN:
			title.alpha(1-p);
			message.alpha( 1 - p );
			sprite.alpha(1 - p );
			if ((timeLeft -= Game.elapsed) <= 0) {
				synchronized (thread) {
					if (!thread.isAlive() && error == null) {
						phase = Phase.FADE_OUT;
						timeLeft = fadeTime;
					} else {
						phase = Phase.STATIC;
					}
				}
			}
			break;
			
		case FADE_OUT:
			title.alpha( p );
			message.alpha( p );
			sprite.alpha( p );
			if ((timeLeft -= Game.elapsed) <= 0) {
				Game.switchScene( GameScene.class );
				thread = null;
				error = null;
			}
			break;
			
		case STATIC:
			if (error != null) {
				String errorMsg;
				if (error instanceof FileNotFoundException)     errorMsg = Messages.get(this, "file_not_found");
				else if (error instanceof IOException)          errorMsg = Messages.get(this, "io_error");
				else if (error.getMessage() != null &&
						error.getMessage().equals("old save")) errorMsg = Messages.get(this, "io_error");

				else throw new RuntimeException("fatal error occurred while moving between floors. " +
							"Seed:" + Dungeon.seed + " depth:" + Dungeon.depth, error);

				add( new WndError( errorMsg ) {
					public void onBackPressed() {
						super.onBackPressed();
						Game.switchScene( StartScene.class );
					}
				} );
				thread = null;
				error = null;
			} else if (thread != null && (int)waitingTime == 10){
				waitingTime = 11f;
				String s = "";
				for (StackTraceElement t : thread.getStackTrace()){
					s += "\n";
					s += t.toString();
				}
				//we care about reporting game logic exceptions, not slow IO
				if (!s.contains("FileUtils.bundleToFile")){
					SoulsPixelDungeon.reportException(
							new RuntimeException("waited more than 10 seconds on levelgen. " +
									"Seed:" + Dungeon.seed + " depth:" + Dungeon.depth + " trace:" +
									s));
				}
			}
			break;
		}
	}

	private void descend() throws IOException {

		if (Dungeon.hero == null) {
			Mob.clearHeldAllies();
			Dungeon.init();
			GameLog.wipe();

			//When debugging, we may start a game at a later depth to quickly test something
			// if this happens, the games quickly generates all prior levels on branch 0 first,
			// which ensures levelgen consistency with a regular game that was played to that depth.
			if (DeviceCompat.isDebug()){
				int trueDepth = Dungeon.depth;
				int trueBranch = Dungeon.branch;
				for (int i = 1; i < trueDepth + (trueBranch == 0 ? 0 : 1); i++){
					if (!Dungeon.levelHasBeenGenerated(i, 0)){
						Dungeon.depth = i;
						Dungeon.branch = 0;
						Dungeon.level = Dungeon.newLevel();
						Dungeon.saveLevel(GamesInProgress.curSlot);
					}
				}
				Dungeon.depth = trueDepth;
				Dungeon.branch = trueBranch;
			}

			Level level = Dungeon.newLevel();
			Dungeon.switchLevel( level, -1 );
		} else {
			Mob.holdAllies( Dungeon.level );
			Dungeon.saveAll();

			Level level;
			Dungeon.depth = curTransition.destDepth;
			Dungeon.branch = curTransition.destBranch;

			if (Dungeon.levelHasBeenGenerated(Dungeon.depth, Dungeon.branch)) {
				level = Dungeon.loadLevel( GamesInProgress.curSlot );
			} else {
				level = Dungeon.newLevel();
			}

			LevelTransition destTransition = level.getTransition(curTransition.destType);
			curTransition = null;
			Dungeon.switchLevel( level, destTransition.cell() );
		}

	}

	public void ascendFallBranch() throws IOException {
		ascendFallBranchSwitch();
	}

	public void descendFallBranch() throws IOException {
		descendFallBranchSwitch();
	}

	public void secretExit() throws IOException {
		switch (Dungeon.depth){
			case 1:
				exitSwitchTo(3);
				break;
			case 2:
				exitSwitchTo(4);
				break;
			case 3:
				exitSwitchTo(5);
				break;
		}
	}

	public void secretEntrance() throws IOException {
		switch (Dungeon.depth){
			case 3:
				entranceSwitchTo(1);
				break;
			case 4:
				entranceSwitchTo(2);
				break;
			case 5:
				entranceSwitchTo(3);
				break;
		}
	}

	private void exitSwitchTo(int depth) throws IOException {
		Mob.holdAllies( Dungeon.level );

		Dungeon.saveAll();

		Level level;
		Dungeon.depth = depth;
		if (Dungeon.levelHasBeenGenerated(Dungeon.depth, Dungeon.branch)) {
			level = Dungeon.loadLevel( GamesInProgress.curSlot );
		} else {
			level = Dungeon.newLevel();
		}
		Dungeon.switchLevel( level, level.secretEntrance());
	}

	private void entranceSwitchTo(int depth) throws IOException {
		Mob.holdAllies( Dungeon.level );

		Dungeon.saveAll();

		Level level;
		Dungeon.depth = depth;
		if (Dungeon.levelHasBeenGenerated(Dungeon.depth, Dungeon.branch)) {
			level = Dungeon.loadLevel( GamesInProgress.curSlot );
		} else {
			level = Dungeon.newLevel();
		}
		Dungeon.switchLevel( level, level.secretExit());
	}

	private void ascendFallBranchSwitch() throws IOException {
		Mob.holdAllies( Dungeon.level );

		Dungeon.saveAll();

		Level level;
		Dungeon.branch = 0;
		if (Dungeon.levelHasBeenGenerated(Dungeon.depth, Dungeon.branch)) {
			level = Dungeon.loadLevel( GamesInProgress.curSlot );
		} else {
			level = Dungeon.newLevel();
		}
		Dungeon.switchLevel( level, level.descendFallBranch());
	}

	private void descendFallBranchSwitch() throws IOException {
		Mob.holdAllies( Dungeon.level );

		Dungeon.saveAll();

		Level level;
		Dungeon.branch = 1;
		if (Dungeon.levelHasBeenGenerated(Dungeon.depth, Dungeon.branch)) {
			level = Dungeon.loadLevel( GamesInProgress.curSlot );
		} else {
			level = Dungeon.newLevel();
		}
		Dungeon.switchLevel( level, level.ascendFallBranch());
	}

	//TODO atm falling always just increments depth by 1, do we eventually want to roll it into the transition system?
	@SuppressWarnings("SuspiciousIndentation")
    private void fall() throws IOException {

		Mob.holdAllies( Dungeon.level );

		Buff.affect( Dungeon.hero, Chasm.Falling.class );
		Dungeon.saveAll();

		if(fallIntoPit){
			Level level;
			Dungeon.depth++;
			if (Dungeon.levelHasBeenGenerated(Dungeon.depth, Dungeon.branch)) {
				level = Dungeon.loadLevel( GamesInProgress.curSlot );
			} else {
				level = Dungeon.newLevel();
			}
			Dungeon.switchLevel( level, level.fallCell( fallIntoPit ));
		} else {
			if(Dungeon.branch == 0){
				Level level;
				Dungeon.branch = 1;
				if (Dungeon.levelHasBeenGenerated(Dungeon.depth, Dungeon.branch)) {
					level = Dungeon.loadLevel( GamesInProgress.curSlot );
				} else {
					level = Dungeon.newLevel();
				}
				Dungeon.switchLevel( level, level.randomDestination(null));
			} else if(Dungeon.branch > 0) {
				Level level;
				Dungeon.branch = 0;
                Dungeon.depth++;
				if (Dungeon.levelHasBeenGenerated(Dungeon.branch, Dungeon.depth)) {
					level = Dungeon.loadLevel( GamesInProgress.curSlot );
				} else {
					level = Dungeon.newLevel();
				}
				Dungeon.switchLevel( level, level.randomDestination(null));
			} else {
				Level level;
				Dungeon.branch = 0;
				if (Dungeon.levelHasBeenGenerated(Dungeon.branch, Dungeon.depth)) {
					level = Dungeon.loadLevel( GamesInProgress.curSlot );
				} else {
					level = Dungeon.newLevel();
				}
				Dungeon.switchLevel( level, level.randomDestination(null));
			}
		}
	}

	private void ascend() throws IOException {
		Mob.holdAllies( Dungeon.level );
		Dungeon.saveAll();

		Level level;
		Dungeon.depth = curTransition.destDepth;
		Dungeon.branch = curTransition.destBranch;

		if (Dungeon.levelHasBeenGenerated(Dungeon.depth, Dungeon.branch)) {
			level = Dungeon.loadLevel( GamesInProgress.curSlot );
		} else {
			level = Dungeon.newLevel();
		}

		LevelTransition destTransition = level.getTransition(curTransition.destType);
		curTransition = null;
		Dungeon.switchLevel( level, destTransition.cell() );
	}
	
	private void returnTo() throws IOException {
		Mob.holdAllies( Dungeon.level );
		Dungeon.saveAll();

		Level level;
		Dungeon.depth = returnDepth;
		Dungeon.branch = returnBranch;
		if (Dungeon.levelHasBeenGenerated(Dungeon.depth, Dungeon.branch)) {
			level = Dungeon.loadLevel( GamesInProgress.curSlot );
		} else {
			level = Dungeon.newLevel();
		}

		Dungeon.switchLevel( level, returnPos );
	}
	
	private void restore() throws IOException {
		
		Mob.clearHeldAllies();

		GameLog.wipe();

		Dungeon.loadGame( GamesInProgress.curSlot );
		if (Dungeon.depth == -1) {
			Dungeon.depth = Statistics.deepestFloor;
			Dungeon.switchLevel( Dungeon.loadLevel( GamesInProgress.curSlot ), -1 );
		} else {
			Level level = Dungeon.loadLevel( GamesInProgress.curSlot );
			Dungeon.switchLevel( level, Dungeon.hero.pos );
		}
	}
	
	private void resurrect() {
		
		Mob.holdAllies( Dungeon.level );

		Level level;
		if (Dungeon.level.locked) {
			ArrayList<Item> preservedItems = Dungeon.level.getItemsToPreserveFromSealedResurrect();

			Dungeon.hero.resurrect();
			level = Dungeon.newLevel();
			Dungeon.hero.pos = level.randomRespawnCell(Dungeon.hero);
			if (Dungeon.hero.pos == -1) Dungeon.hero.pos = level.entrance();

			for (Item i : preservedItems){
				int pos = level.randomRespawnCell(null);
				if (pos == -1) pos = level.entrance();
				level.drop(i, pos);
			}
			int pos = level.randomRespawnCell(null);
			if (pos == -1) pos = level.entrance();
			level.drop(new LostBackpack(), pos);

		} else {
			level = Dungeon.level;
			BArray.setFalse(level.heroFOV);
			BArray.setFalse(level.visited);
			BArray.setFalse(level.mapped);
			int invPos = Dungeon.hero.pos;
			int tries = 0;
			do {
				Dungeon.hero.pos = level.randomRespawnCell(Dungeon.hero);
				tries++;

			//prevents spawning on traps or plants, prefers farther locations first
			} while (level.traps.get(Dungeon.hero.pos) != null
					|| (level.plants.get(Dungeon.hero.pos) != null && tries < 500)
					|| level.trueDistance(invPos, Dungeon.hero.pos) <= 30 - (tries/10));

			//directly trample grass
			if (level.map[Dungeon.hero.pos] == Terrain.HIGH_GRASS || level.map[Dungeon.hero.pos] == Terrain.FURROWED_GRASS){
				level.map[Dungeon.hero.pos] = Terrain.GRASS;
			}
			Dungeon.hero.resurrect();
			level.drop(new LostBackpack(), invPos);
		}

		Dungeon.switchLevel( level, Dungeon.hero.pos );
	}

	private void reset() throws IOException {
		
		Mob.holdAllies( Dungeon.level );

		SpecialRoom.resetPitRoom(Dungeon.depth+1);

		Level level = Dungeon.newLevel();
		Dungeon.switchLevel( level, level.entrance() );
	}
	
	@Override
	protected void onBackPressed() {
		//Do nothing
	}
}
