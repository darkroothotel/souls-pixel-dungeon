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
import com.soulspixel.soulspixeldungeon.actors.Actor;
import com.soulspixel.soulspixeldungeon.actors.buffs.LightShadows;
import com.soulspixel.soulspixeldungeon.actors.mobs.npcs.Bonfire;
import com.soulspixel.soulspixeldungeon.effects.Fireball;
import com.soulspixel.soulspixeldungeon.effects.particles.BonfireLightShaftParticle;
import com.soulspixel.soulspixeldungeon.items.Item;
import com.soulspixel.soulspixeldungeon.items.spells.SpellOfEmber;
import com.soulspixel.soulspixeldungeon.journal.Journal;
import com.soulspixel.soulspixeldungeon.messages.Messages;
import com.soulspixel.soulspixeldungeon.sprites.BonfireSprite;
import com.soulspixel.soulspixeldungeon.sprites.ItemSprite;
import com.soulspixel.soulspixeldungeon.sprites.ItemSpriteSheet;
import com.soulspixel.soulspixeldungeon.ui.BonfireHeroButton;
import com.soulspixel.soulspixeldungeon.ui.ExitButton;
import com.soulspixel.soulspixeldungeon.ui.IconButton;
import com.soulspixel.soulspixeldungeon.ui.ItemSlot;
import com.soulspixel.soulspixeldungeon.ui.RedButton;
import com.soulspixel.soulspixeldungeon.ui.RenderedTextBlock;
import com.soulspixel.soulspixeldungeon.ui.Window;
import com.soulspixel.soulspixeldungeon.windows.WndBag;
import com.soulspixel.soulspixeldungeon.windows.WndHero;
import com.soulspixel.soulspixeldungeon.windows.WndInfoItem;
import com.soulspixel.soulspixeldungeon.windows.WndJournal;
import com.soulspixel.soulspixeldungeon.windows.WndLinkedBonfireList;
import com.watabou.gltextures.TextureCache;
import com.watabou.glwrap.Blending;
import com.watabou.input.GameAction;
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
import com.watabou.utils.PointF;

import java.io.IOException;

public class BonfireScene extends PixelScene {

	private final Bonfire bonfire;
	Fireball fb = new Fireball();
	private Emitter lightEmitter;
	private BonfireSprite bonfireSprite;
	private RenderedTextBlock title;
	private TravelBtn travelbtn;

	private SkinnedBlock bg;

	private InputButton upgradeBtn = new InputButton();
	private UpgradeBtn upgradeConfirm = new UpgradeBtn();

	private static final int BTN_SIZE	= 28;

	{
		bonfire = (Bonfire) Actor.findChar(Dungeon.hero.getBonfirePos());
		inGameScene = true;
	}

	private void placeFireball( float x, float y ) {
		fb.setPos( x, y );
		add( fb );
	}

	private void removeFireball(){
		remove(fb);
	}

	private String upgradeTitle(){
		return bonfire.getLevel() > 0 ? Messages.get(this, "imbued_title", bonfire.getLevel()) : "";
	}

	private String upgradeMsg(){
        if (bonfire.getLevel() == 0) {
            return "";
        }
        return Messages.get(this, "msg_level_" + bonfire.getLevel());
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
		
		title = PixelScene.renderTextBlock( Messages.get(this, "title", Dungeon.getFloorName()), 9 );
		title.hardlight(Window.TITLE_COLOR);
		title.setPos((Camera.main.width - title.width()) / 2f, (20 - title.height()) / 2f);
		align(title);
		add(title);

		RenderedTextBlock subtitle = PixelScene.renderTextBlock( Messages.get(Bonfire.class, "depth", Dungeon.depth)+"\n"+upgradeTitle(), 9 );
		subtitle.hardlight(Window.WHITE);
		subtitle.setPos(title.centerX()-(subtitle.width()/2f), title.bottom()+5);
		align(subtitle);
		add(subtitle);
		
		int w = 50 + Camera.main.width/2;
		int left = (Camera.main.width - w)/2;
		
		int pos = (Camera.main.height - 100)/2;

		bonfireSprite = new BonfireSprite();
		bonfireSprite.scale = new PointF(2, 2);
		bonfireSprite.x = left + (w - bonfireSprite.width)/2;
		bonfireSprite.y = pos;
		add(bonfireSprite);

		pos += (int) (bonfireSprite.height+BTN_SIZE);
		
		RenderedTextBlock desc = PixelScene.renderTextBlock(6);
		desc.maxWidth(w);
		desc.text( Messages.get(BonfireScene.class, "message")+"\n\n"+upgradeMsg() );
		desc.setPos(left + (w - desc.width())/2, pos);
		add(desc);

		pos = (int) (desc.bottom() + ((float) BTN_SIZE /4));

		travelbtn = new BonfireScene.TravelBtn();
		travelbtn.setRect(desc.centerX()+(travelbtn.centerX()/2), pos, travelbtn.width(), travelbtn.height());
		if(Dungeon.hero.getLinkedbonfires().size > 2 && bonfire.isLinked()){
			add(travelbtn);
		}

		upgradeBtn = new BonfireScene.InputButton();
		upgradeBtn.setRect(desc.left(), pos, BTN_SIZE, BTN_SIZE);
		add(upgradeBtn);

		upgradeConfirm = new BonfireScene.UpgradeBtn();
		upgradeConfirm.enable(false);
		upgradeConfirm.setRect(upgradeBtn.right()+2, upgradeBtn.top()+((float) BTN_SIZE /4), (float) BTN_SIZE /2, (float) BTN_SIZE /2);
		add(upgradeConfirm);

		lightEmitter = new Emitter();
		lightEmitter.pour(BonfireLightShaftParticle.FACTORY_SCENE, 0.5f);
		lightEmitter.pos(bonfireSprite.center());
		lightEmitter.autoKill = false;
		add(lightEmitter);


		BonfireHeroButton btnHero = new BonfireHeroButton(){
			@Override
			protected void onClick() {
				add(new WndHero(true));
			}
			@Override
			public void update() {
				super.update();
				if(Dungeon.hero.talentPointsAvailable() > 0){
					icon.tint(0xFFFFFF, (Game.elapsed*100f)-1f);
				} else {
					icon.resetColor();
				}
			}
		};
		btnHero.setPos( desc.right()-btnHero.width(), pos );
		add( btnHero );

		IconButton btnGuide = new IconButton( new ItemSprite(ItemSpriteSheet.GUIDE_PAGE, null)){
			@Override
			protected void onClick() {
				super.onClick();
				SoulsPixelDungeon.scene().addToFront(new WndJournal());
			}

			@Override
			public GameAction keyAction() {
				return SPDAction.JOURNAL;
			}

			@Override
			protected String hoverText() {
				return Messages.get(BonfireScene.class, "jou_hover");
			}
		};
		btnGuide.setRect(0, 0, 20, 20);
		add(btnGuide);
		IconButton btnInventory = new IconButton( new ItemSprite(ItemSpriteSheet.BACKPACK, null)){
			@Override
			protected void onClick() {
				super.onClick();
				WndBag.ItemSelector i = new WndBag.ItemSelector() {
					@Override
					public String textPrompt() {
						return Messages.get(BonfireScene.class, "inventory_browse");
					}

					@Override
					public boolean itemSelectable(Item item) {
						return true;
					}

					@Override
					public void onSelect(Item item) {
						if(item != null) SoulsPixelDungeon.scene().addToFront(new WndInfoItem(item){
							@Override
							public void onBackPressed() {
								onClick();
								super.onBackPressed();
							}
						});
					}
				};

				SoulsPixelDungeon.scene().addToFront(new WndBag(Dungeon.hero.belongings.backpack, i));
			}

			public GameAction keyAction() {
				return SPDAction.INVENTORY;
			}

			@Override
			protected String hoverText() {
				return Messages.get(BonfireScene.class, "inv_hover");
			}
		};
		btnInventory.setRect(0, btnGuide.bottom()+5, 20, 20);
		add(btnInventory);
		
		fadeIn();
		
		try {
			Dungeon.saveAll();
			Badges.saveGlobal();
			Journal.saveGlobal();
		} catch (IOException e) {
			SoulsPixelDungeon.reportException(e);
		}
	}

	protected WndBag.ItemSelector itemSelector = new WndBag.ItemSelector() {

		@Override
		public String textPrompt() {
			return Messages.get(BonfireScene.class, "select");
		}

		@Override
		public boolean itemSelectable(Item item) {
			return item instanceof SpellOfEmber;
		}

		@Override
		public void onSelect( Item item ) {
			if(item instanceof SpellOfEmber){
				upgradeBtn.item(item.detach(Dungeon.hero.belongings.backpack));
			}
		}
	};
	
	@Override
	public void update() {
		super.update();
		bg.offset( 0, 5 * Game.elapsed );
        upgradeConfirm.enable(upgradeBtn.item() != null);
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

	private class InputButton extends Component {

		protected NinePatch bg;
		protected ItemSlot slot;

		private Item item = null;

		@Override
		protected void createChildren() {
			super.createChildren();

			bg = Chrome.get( Chrome.Type.RED_BUTTON);
			add( bg );

			slot = new ItemSlot() {
				@Override
				protected void onPointerDown() {
					bg.brightness( 1.2f );
					Sample.INSTANCE.play( Assets.Sounds.CLICK );
				}
				@Override
				protected void onPointerUp() {
					bg.resetColor();
				}
				@Override
				protected void onClick() {
					super.onClick();
					Item item = BonfireScene.InputButton.this.item;
					if (item != null) {
						if (!item.collect()) {
							Dungeon.level.drop(item, Dungeon.hero.pos);
						}
						BonfireScene.InputButton.this.item(null);
					}
					SoulsPixelDungeon.scene().addToFront(WndBag.getBag( itemSelector ));
				}

				@Override
				protected boolean onLongClick() {
					Item item = BonfireScene.InputButton.this.item;
					if (item != null){
						SoulsPixelDungeon.scene().addToFront(new WndInfoItem(item));
						return true;
					}
					return false;
				}

				@Override
				public GameAction keyAction() {
					BonfireScene.InputButton i = upgradeBtn;
					if (i.item == null || i.item instanceof WndBag.Placeholder) {
						if (i == BonfireScene.InputButton.this) {
							return SPDAction.E;
						} else {
							return super.keyAction();
						}
					}
					return super.keyAction();
				}

				@Override
				protected String hoverText() {
					if (item == null || item instanceof WndBag.Placeholder){
						return Messages.get(BonfireScene.class, "input");
					}
					return super.hoverText();
				}

				@Override
				public GameAction secondaryTooltipAction() {
					return SPDAction.E;
				}
			};
			slot.enable(true);
			add( slot );
		}

		@Override
		protected void layout() {
			super.layout();

			bg.x = x;
			bg.y = y;
			bg.size( width, height );

			slot.setRect( x + 2, y + 2, width - 4, height - 4 );
		}

		public Item item(){
			return item;
		}

		public void item( Item item ) {
			if (item == null){
				this.item = null;
				slot.item(new WndBag.Placeholder(ItemSpriteSheet.POTION_HOLDER));
				removeFireball();
			} else {
				slot.item(this.item = item);
				if(bonfire.getLevel() < 5){
					placeFireball(upgradeConfirm.centerX(), upgradeConfirm.centerY());
				}
			}
		}
	}

	private class UpgradeBtn extends Component {

		protected RedButton button;

		boolean noMoreUpgrade = false;

		@Override
		protected void createChildren() {
			super.createChildren();

			button = new RedButton(""){
				@Override
				protected void onClick() {
					super.onClick();
					if(bonfire.getLevel() < 5){
						upgradeBtn.item(null);
						bonfire.setLevel(bonfire.getLevel()+1);
						Sample.INSTANCE.play( Assets.Sounds.PUFF );
						Statistics.bonfiresLit++;
						create();
						switch (bonfire.getLevel()){
							case 1:
								bonfireSprite.tint(0x550000, 0.4f);
								break;
							case 2:
								bonfireSprite.tint(0x660000, 0.5f);
								break;
							case 3:
								bonfireSprite.tint(0x770000, 0.6f);
								break;
							case 4:
								bonfireSprite.tint(0x880000, 0.7f);
								break;
							case 5:
								bonfireSprite.tint(0x990000, 0.8f);
								break;
						}
					} else {
						noMoreUpgrade = true;
					}
				}

				@Override
				protected String hoverText() {
					if(bonfire.getLevel() >= 5){
						noMoreUpgrade = true;
					}
					return noMoreUpgrade ? Messages.get(BonfireScene.class, "no_more_upgrade") : Messages.get(BonfireScene.class, "upgrade");
				}

				@Override
				public GameAction keyAction() {
					if(bonfire.getLevel() >= 5){
						noMoreUpgrade = true;
					}
					if (upgradeBtn.active && !noMoreUpgrade){
						return SPDAction.TAG_LOOT;
					}
					return super.keyAction();
				}
			};
			button.icon(new LightShadows().getIcon(false));
			add(button);
		}

		@Override
		protected void layout() {
			super.layout();

			button.setRect(x, y, width(), height());
		}

		public void enable( boolean enabled ){
			if(bonfire.getLevel() >= 5){
				noMoreUpgrade = true;
			}
			enable(enabled, 0);
		}

		public void enable( boolean enabled, int cost ){
			if(bonfire.getLevel() >= 5){
				noMoreUpgrade = true;
			}
			button.enable(enabled);
			if (enabled) {
				button.alpha(1f);
			} else {
				button.alpha(0.6f);
			}

			layout();
			active = enabled;
		}

	}

	private class TravelBtn extends Component {

		protected RedButton button;

		@Override
		protected void createChildren() {
			super.createChildren();

			button = new RedButton(Messages.get(BonfireScene.class, "travel")){
				@Override
				protected void onClick() {
					super.onClick();
					SoulsPixelDungeon.scene().addToFront(new WndLinkedBonfireList(null));
				}

				@Override
				protected String hoverText() {
					return Messages.get(BonfireScene.class, "hover_travel");
				}

				@Override
				public GameAction keyAction() {
					return SPDAction.EXAMINE;
				}
			};
			button.textColor(Window.TITLE_COLOR);
			add(button);
		}

		@Override
		protected void layout() {
			super.layout();

			button.setRect(x, y, BTN_SIZE, (float) BTN_SIZE /2);
		}

	}
}
