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
import com.soulspixel.soulspixeldungeon.Badges;
import com.soulspixel.soulspixeldungeon.Chrome;
import com.soulspixel.soulspixeldungeon.Dungeon;
import com.soulspixel.soulspixeldungeon.SPDAction;
import com.soulspixel.soulspixeldungeon.SoulsPixelDungeon;
import com.soulspixel.soulspixeldungeon.Statistics;
import com.soulspixel.soulspixeldungeon.actors.hero.Belongings;
import com.soulspixel.soulspixeldungeon.effects.Speck;
import com.soulspixel.soulspixeldungeon.effects.particles.SparkParticle;
import com.soulspixel.soulspixeldungeon.items.Item;
import com.soulspixel.soulspixeldungeon.items.LiquidMetal;
import com.soulspixel.soulspixeldungeon.items.Recipe;
import com.soulspixel.soulspixeldungeon.items.artifacts.AlchemistsToolkit;
import com.soulspixel.soulspixeldungeon.items.bags.Bag;
import com.soulspixel.soulspixeldungeon.items.trinkets.TrinketCatalyst;
import com.soulspixel.soulspixeldungeon.journal.Document;
import com.soulspixel.soulspixeldungeon.journal.Journal;
import com.soulspixel.soulspixeldungeon.messages.Messages;
import com.soulspixel.soulspixeldungeon.sprites.ItemSprite;
import com.soulspixel.soulspixeldungeon.sprites.ItemSpriteSheet;
import com.soulspixel.soulspixeldungeon.ui.Button;
import com.soulspixel.soulspixeldungeon.ui.ExitButton;
import com.soulspixel.soulspixeldungeon.ui.IconButton;
import com.soulspixel.soulspixeldungeon.ui.Icons;
import com.soulspixel.soulspixeldungeon.ui.ItemSlot;
import com.soulspixel.soulspixeldungeon.ui.RadialMenu;
import com.soulspixel.soulspixeldungeon.ui.RedButton;
import com.soulspixel.soulspixeldungeon.ui.RenderedTextBlock;
import com.soulspixel.soulspixeldungeon.ui.StatusPane;
import com.soulspixel.soulspixeldungeon.ui.Toolbar;
import com.soulspixel.soulspixeldungeon.ui.Window;
import com.soulspixel.soulspixeldungeon.windows.WndBag;
import com.soulspixel.soulspixeldungeon.windows.WndEnergizeItem;
import com.soulspixel.soulspixeldungeon.windows.WndInfoItem;
import com.soulspixel.soulspixeldungeon.windows.WndJournal;
import com.soulspixel.soulspixeldungeon.windows.WndKeyBindings;
import com.soulspixel.soulspixeldungeon.windows.WndMessage;
import com.watabou.gltextures.TextureCache;
import com.watabou.glwrap.Blending;
import com.watabou.input.ControllerHandler;
import com.watabou.input.GameAction;
import com.watabou.input.KeyBindings;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.Image;
import com.watabou.noosa.NinePatch;
import com.watabou.noosa.NoosaScript;
import com.watabou.noosa.NoosaScriptNoLighting;
import com.watabou.noosa.SkinnedBlock;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.particles.Emitter;
import com.watabou.noosa.ui.Component;

import java.io.IOException;
import java.util.ArrayList;

public class BonfireScene extends PixelScene {

	private SkinnedBlock bg;

	private static final int BTN_SIZE	= 28;

	{
		inGameScene = true;
	}
	
	@Override
	public void create() {
		super.create();
		
		bg = new SkinnedBlock(Camera.main.width, Camera.main.height, Assets.Environment.GOLDEN_FOG ){
			@Override
			protected NoosaScript script() {
				return NoosaScriptNoLighting.get();
			}

			@Override
			public void draw() {
				//water has no alpha component, this improves performance
				Blending.disable();
				super.draw();
				Blending.enable();
			}
		};
		bg.autoAdjust = true;
		add(bg);
		
		Image im = new Image(TextureCache.createGradient(0x66000000, 0x88000000, 0xAA000000, 0xCC000000, 0xFF000000));
		im.angle = 90;
		im.x = Camera.main.width;
		im.scale.x = Camera.main.height/5f;
		im.scale.y = Camera.main.width;
		add(im);

		ExitButton btnExit = new ExitButton(){
			@Override
			protected void onClick() {
				Game.switchScene(GameScene.class);
			}
		};
		btnExit.setPos( Camera.main.width - btnExit.width(), 0 );
		add( btnExit );
		
		RenderedTextBlock title = PixelScene.renderTextBlock( Messages.get(this, "title"), 9 );
		title.hardlight(Window.TITLE_COLOR);
		title.setPos((Camera.main.width - title.width()) / 2f, (20 - title.height()) / 2f);
		align(title);
		add(title);
		
		int w = 50 + Camera.main.width/2;
		int left = (Camera.main.width - w)/2;
		
		int pos = (Camera.main.height - 100)/2;
		
		RenderedTextBlock desc = PixelScene.renderTextBlock(6);
		desc.maxWidth(w);
		desc.text( Messages.get(BonfireScene.class, "message") );
		desc.setPos(left + (w - desc.width())/2, pos);
		add(desc);

		IconButton btnGuide = new IconButton( new ItemSprite(ItemSpriteSheet.GUIDE_PAGE, null)){
			@Override
			protected void onClick() {
				super.onClick();
				BonfireScene.this.addToFront(new WndJournal());
			}

			@Override
			public GameAction keyAction() {
				return SPDAction.JOURNAL;
			}

			@Override
			protected String hoverText() {
				return Messages.titleCase(Document.GUIDE_INTRO);
			}
		};
		btnGuide.setRect(0, 0, 20, 20);
		add(btnGuide);
		
		fadeIn();
		
		try {
			Dungeon.saveAll();
			Badges.saveGlobal();
			Journal.saveGlobal();
		} catch (IOException e) {
			SoulsPixelDungeon.reportException(e);
		}
	}
	
	@Override
	public void update() {
		super.update();
		bg.offset( 0, -5 * Game.elapsed );
	}
	
	@Override
	protected void onBackPressed() {
		Game.switchScene(GameScene.class);
	}
	
	@Override
	public void destroy() {
		try {
			Dungeon.saveAll();
			Badges.saveGlobal();
			Journal.saveGlobal();
		} catch (IOException e) {
			SoulsPixelDungeon.reportException(e);
		}
		super.destroy();
	}
}
