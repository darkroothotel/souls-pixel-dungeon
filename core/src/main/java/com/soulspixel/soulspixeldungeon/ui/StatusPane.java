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

package com.soulspixel.soulspixeldungeon.ui;

import com.soulspixel.soulspixeldungeon.Assets;
import com.soulspixel.soulspixeldungeon.Dungeon;
import com.soulspixel.soulspixeldungeon.SPDAction;
import com.soulspixel.soulspixeldungeon.Statistics;
import com.soulspixel.soulspixeldungeon.actors.Actor;
import com.soulspixel.soulspixeldungeon.effects.CircleArc;
import com.soulspixel.soulspixeldungeon.effects.Speck;
import com.soulspixel.soulspixeldungeon.messages.Messages;
import com.soulspixel.soulspixeldungeon.scenes.GameScene;
import com.soulspixel.soulspixeldungeon.scenes.PixelScene;
import com.soulspixel.soulspixeldungeon.sprites.HeroSprite;
import com.soulspixel.soulspixeldungeon.windows.WndHero;
import com.soulspixel.soulspixeldungeon.windows.WndKeyBindings;
import com.watabou.input.GameAction;
import com.watabou.noosa.BitmapText;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.Image;
import com.watabou.noosa.NinePatch;
import com.watabou.noosa.particles.Emitter;
import com.watabou.noosa.ui.Component;
import com.watabou.utils.ColorMath;
import com.watabou.utils.GameMath;

public class StatusPane extends Component {

	private NinePatch bg;
	private Image avatar;
	private Button heroInfo;
	public static float talentBlink;
	private float warning;

	public static final float FLASH_RATE = (float)(Math.PI*1.5f); //1.5 blinks per second

	private int lastTier = 0;

	private Image rawShielding;
	private Image shieldedHP;
	private Image hp;
	private BitmapText hpText;
	private Button heroInfoOnBar;

	private Image stamina;
	private BitmapText staminaText;

	private int lastLvl = -1;

	private BitmapText level;

	private BuffIndicator buffs;
	private Compass compass;

	private BusyIndicator busy;
	private CircleArc counter;

	private static String asset = Assets.Interfaces.STATUS;

	private boolean large;

	public StatusPane( boolean large ){
		super();

		this.large = large;

		if (large)  bg = new NinePatch( asset, 0, 64, 41, 39, 33, 0, 4, 0 );
		else        bg = new NinePatch( asset, 0, 0, 128, 41, 85, 0, 45, 0 );
		add( bg );

		heroInfo = new Button(){
			@Override
			protected void onClick () {
				Camera.main.panTo( Dungeon.hero.sprite.center(), 5f );
				GameScene.show( new WndHero(false) );
			}
			
			@Override
			public GameAction keyAction() {
				return SPDAction.HERO_INFO;
			}

			@Override
			protected String hoverText() {
				return Messages.titleCase(Messages.get(WndKeyBindings.class, "hero_info"));
			}
		};
		add(heroInfo);

		avatar = HeroSprite.avatar( Dungeon.hero.heroClass, lastTier );
		add( avatar );

		talentBlink = 0;

		compass = new Compass( Statistics.amuletObtained ? Dungeon.level.entrance() : Dungeon.level.exit() );
		add( compass );

		if (large)  rawShielding = new Image(asset, 0, 112, 128, 9);
		else        rawShielding = new Image(asset, 0, 40+6, 50, 4);
		rawShielding.alpha(0.5f);
		add(rawShielding);

		if (large)  shieldedHP = new Image(asset, 0, 112, 128, 9);
		else        shieldedHP = new Image(asset, 0, 40+6, 50, 4);
		add(shieldedHP);

		if (large)  hp = new Image(asset, 0, 103, 128, 9);
		else        hp = new Image(asset, 0, 36+6, 50, 4);
		add( hp );

		hpText = new BitmapText(PixelScene.pixelFont);
		hpText.alpha(0.6f);
		add(hpText);

		heroInfoOnBar = new Button(){
			@Override
			protected void onClick () {
				Camera.main.panTo( Dungeon.hero.sprite.center(), 5f );
				GameScene.show( new WndHero(false) );
			}
		};
		add(heroInfoOnBar);

		if (large)  stamina = new Image(asset, 0, 121, 128, 7);
		else        stamina = new Image(asset, 0, 44+6, 50, 4);
		add( stamina );

		if (large){
			staminaText = new BitmapText(PixelScene.pixelFont);
			staminaText.hardlight( 0xFFFFAA );
			staminaText.alpha(0.6f);
			add(staminaText);
		} else {
			staminaText = new BitmapText(PixelScene.pixelFont);
			staminaText.alpha(0.6f);
			add(staminaText);
		}

		level = new BitmapText( PixelScene.pixelFont);
		level.hardlight( 0xFFFFAA );
		add( level );

		buffs = new BuffIndicator( Dungeon.hero, large );
		add( buffs );

		busy = new BusyIndicator();
		add( busy );

		counter = new CircleArc(18, 4.25f);
		counter.color( 0x808080, true );
		counter.show(this, busy.center(), 0f);
	}

	@Override
	protected void layout() {

		height = large ? 39 : 32;

		bg.x = x;
		bg.y = y;
		if (large)  bg.size( 160, bg.height ); //HP bars must be 128px wide atm
		else        bg.size( width, bg.height );

		avatar.x = bg.x - avatar.width / 2f + 15;
		avatar.y = bg.y - avatar.height / 2f + 15;
		PixelScene.align(avatar);

		heroInfo.setRect( x, y, 30, large ? 40 : 30 );

		compass.x = avatar.x + avatar.width / 2f - compass.origin.x;
		compass.y = avatar.y + avatar.height / 2f - compass.origin.y;
		PixelScene.align(compass);

		if (large) {
			stamina.x = x + 30;
			stamina.y = y + 30;

			hp.x = shieldedHP.x = rawShielding.x = x + 30;
			hp.y = shieldedHP.y = rawShielding.y = y + 19;

			hpText.x = hp.x + (128 - hpText.width())/2f;
			hpText.y = hp.y + 1;
			PixelScene.align(hpText);

			staminaText.x = stamina.x + (128 - staminaText.width())/2f;
			staminaText.y = stamina.y;
			PixelScene.align(staminaText);

			heroInfoOnBar.setRect(heroInfo.right(), y + 19, 130, 20);

			buffs.setRect(x + 31, y, 128, 16);

			busy.x = x + bg.width + 1;
			busy.y = y + bg.height - 9;
		} else {
			hp.x = shieldedHP.x = rawShielding.x = x + 30;
			hp.y = shieldedHP.y = rawShielding.y = y + 2;

			hpText.scale.set(PixelScene.align(0.5f));
			hpText.x = hp.x + 1;
			hpText.y = hp.y + (hp.height - (hpText.baseLine()+hpText.scale.y))/2f;
			hpText.y -= 0.001f; //prefer to be slightly higher
			PixelScene.align(hpText);

			stamina.x = 30;
			stamina.y = 8;

			staminaText.scale.set(PixelScene.align(0.5f));
			staminaText.x = stamina.x + 1;
			staminaText.y = stamina.y + (stamina.height - (staminaText.baseLine()+staminaText.scale.y))/2f;
			staminaText.y -= 0.001f; //prefer to be slightly higher
			PixelScene.align(staminaText);

			heroInfoOnBar.setRect(heroInfo.right(), y, 50, 9);

			buffs.setRect( x + 31, y + 15, 50, 8 );

			busy.x = x + 1;
			busy.y = y + 32;
		}

		counter.point(busy.center());
	}
	
	private static final int[] warningColors = new int[]{0x660000, 0xCC0000, 0x660000};

	private int oldHP = 0;
	private int oldShield = 0;
	private int oldMax = 0;
	private int oldSta = 0;
	private int oldMaxSta = 0;

	@Override
	public void update() {
		super.update();
		
		int health = Dungeon.hero.HP;
		int shield = Dungeon.hero.shielding();
		int max = Dungeon.hero.HT;
		int sta = Dungeon.hero.STAMINA;
		int maxSta = Dungeon.hero.MAX_STAMINA;

		if(Dungeon.hero.isUndead()){
			avatar.tint(HeroSprite.UNDEAD_COLOR, 0.5f);
		} else {
			if (!Dungeon.hero.isAlive()) {
				avatar.tint(0x000000, 0.5f);
			} else if ((health/(float)max) <= 0.3f) {
				warning += Game.elapsed * 5f *(0.4f - (health/(float)max));
				warning %= 1f;
				avatar.tint(ColorMath.interpolate(warning, warningColors), 0.5f );
			} else if (talentBlink > 0.33f){ //stops early so it doesn't end in the middle of a blink
				talentBlink -= Game.elapsed;
				avatar.tint(1, 1, 0, (float)Math.abs(Math.cos(talentBlink*FLASH_RATE))/2f);
			} else {
				avatar.resetColor();
			}
		}

		hp.scale.x = Math.max( 0, (health-shield)/(float)max);
		shieldedHP.scale.x = health/(float)max;

		if (shield > health) {
			rawShielding.scale.x = Math.min(1, shield / (float) max);
		} else {
			rawShielding.scale.x = 0;
		}

		if (oldHP != health || oldShield != shield || oldMax != max){
			if (shield <= 0) {
				hpText.text(health + "/" + max);
			} else {
				hpText.text(health + "+" + shield + "/" + max);
			}
			oldHP = health;
			oldShield = shield;
			oldMax = max;
		}

		if(oldSta != sta || oldMaxSta != maxSta){
			staminaText.text(sta + "/" + maxSta);
			oldSta = sta;
			oldMaxSta = maxSta;
		}

		if (large) {
			stamina.scale.x = (128 / stamina.width) * sta / maxSta;

			hpText.measure();
			hpText.x = hp.x + (128 - hpText.width())/2f;

			staminaText.measure();
			staminaText.x = hp.x + (128 - staminaText.width())/2f;

		} else {
			stamina.scale.x = (float) Dungeon.hero.STAMINA / Dungeon.hero.MAX_STAMINA;
		}

		if (Dungeon.hero.lvl != lastLvl) {

			if (lastLvl != -1) {
				showStarParticles();
			}

			lastLvl = Dungeon.hero.lvl;

			if (large){
				level.text( "lv. " + lastLvl );
				level.measure();
				level.x = x + (30f - level.width()) / 2f;
				level.y = y + 33f - level.baseLine() / 2f;
			} else {
				level.text( Integer.toString( lastLvl ) );
				level.measure();
				level.x = x + 27.5f - level.width() / 2f;
				level.y = y + 33.0f - level.baseLine() / 2f;
			}
			PixelScene.align(level);
		}

		int tier = Dungeon.hero.tier();
		if (tier != lastTier) {
			lastTier = tier;
			avatar.copy( HeroSprite.avatar( Dungeon.hero.heroClass, tier ) );
		}

		counter.setSweep((1f - Actor.now()%1f)%1f);
	}

	public void alpha( float value ){
		value = GameMath.gate(0, value, 1f);
		bg.alpha(value);
		avatar.alpha(value);
		rawShielding.alpha(0.5f*value);
		shieldedHP.alpha(value);
		hp.alpha(value);
		hpText.alpha(0.6f*value);
		stamina.alpha(value);
		if (staminaText != null) staminaText.alpha(0.6f*value);
		level.alpha(value);
		compass.alpha(value);
		busy.alpha(value);
		counter.alpha(value);
	}

	public void showStarParticles(){
		Emitter emitter = (Emitter)recycle( Emitter.class );
		emitter.revive();
		emitter.pos( avatar.center() );
		emitter.burst( Speck.factory( Speck.STAR ), 12 );
	}

}
