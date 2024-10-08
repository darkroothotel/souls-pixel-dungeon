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

public class AlchemyScene extends PixelScene {

	//max of 3 inputs, and 3 potential recipe outputs
	private static final InputButton[] inputs = new InputButton[3];
	private static final CombineButton[] combines = new CombineButton[3];
	private static final OutputSlot[] outputs = new OutputSlot[3];

	private IconButton cancel;
	private IconButton repeat;
	private static ArrayList<Item> lastIngredients = new ArrayList<>();
	private static Recipe lastRecipe = null;

	private Emitter smokeEmitter;
	private Emitter bubbleEmitter;
	private Emitter sparkEmitter;
	
	private Emitter lowerBubbles;
	private SkinnedBlock water;

	private Image energyIcon;
	private RenderedTextBlock energyLeft;
	private IconButton energyAdd;
	private boolean energyAddBlinking = false;

	private static final int BTN_SIZE	= 28;

	{
		inGameScene = true;
	}
	
	@Override
	public void create() {
		super.create();
		
		water = new SkinnedBlock(
				Camera.main.width, Camera.main.height,
				Dungeon.level.waterTex() ){
			
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
		water.autoAdjust = true;
		add(water);
		
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

		bubbleEmitter = new Emitter();
		bubbleEmitter.pos(0, 0, Camera.main.width, Camera.main.height);
		bubbleEmitter.autoKill = false;
		add(bubbleEmitter);

		lowerBubbles = new Emitter();
		add(lowerBubbles);
		
		RenderedTextBlock title = PixelScene.renderTextBlock( Messages.get(this, "title"), 9 );
		title.hardlight(Window.TITLE_COLOR);
		title.setPos(
				(Camera.main.width - title.width()) / 2f,
				(20 - title.height()) / 2f
		);
		align(title);
		add(title);
		
		int w = 50 + Camera.main.width/2;
		int left = (Camera.main.width - w)/2;
		
		int pos = (Camera.main.height - 100)/2;
		
		RenderedTextBlock desc = PixelScene.renderTextBlock(6);
		desc.maxWidth(w);
		desc.text( Messages.get(AlchemyScene.class, "text") );
		desc.setPos(left + (w - desc.width())/2, pos);
		add(desc);
		
		pos += desc.height() + 6;

		NinePatch inputBG = Chrome.get(Chrome.Type.TOAST_TR);
		inputBG.x = left + 6;
		inputBG.y = pos;
		inputBG.size(BTN_SIZE+8, 3*BTN_SIZE + 4 + 8);
		add(inputBG);

		pos += 4;

		synchronized (inputs) {
			for (int i = 0; i < inputs.length; i++) {
				inputs[i] = new InputButton();
				inputs[i].setRect(left + 10, pos, BTN_SIZE, BTN_SIZE);
				add(inputs[i]);
				pos += BTN_SIZE + 2;
			}
		}

		Button invSelector = new Button(){
			@Override
			protected void onClick() {
						if (Dungeon.hero != null) {
							ArrayList<Bag> bags = Dungeon.hero.belongings.getBags();

							String[] names = new String[bags.size()];
							Image[] images = new Image[bags.size()];
							for (int i = 0; i < bags.size(); i++){
								names[i] = Messages.titleCase(bags.get(i).name());
								images[i] = new ItemSprite(bags.get(i));
							}
							String info = "";
							if (ControllerHandler.controllerActive){
								info += KeyBindings.getKeyName(KeyBindings.getFirstKeyForAction(GameAction.LEFT_CLICK, true)) + ": " + Messages.get(Toolbar.class, "container_select") + "\n";
								info += KeyBindings.getKeyName(KeyBindings.getFirstKeyForAction(GameAction.BACK, true)) + ": " + Messages.get(Toolbar.class, "container_cancel");
							} else {
								info += Messages.get(WndKeyBindings.class, SPDAction.LEFT_CLICK.name()) + ": " + Messages.get(Toolbar.class, "container_select") + "\n";
								info += KeyBindings.getKeyName(KeyBindings.getFirstKeyForAction(GameAction.BACK, false)) + ": " + Messages.get(Toolbar.class, "container_cancel");
							}

							Game.scene().addToFront(new RadialMenu(Messages.get(Toolbar.class, "container_prompt"), info, names, images){
								@Override
								public void onSelect(int idx, boolean alt) {
									super.onSelect(idx, alt);
									Bag bag = bags.get(idx);
									ArrayList<Item> items = (ArrayList<Item>) bag.items.clone();

									for(Item i : bag.items){
										if (Dungeon.hero.belongings.lostInventory() && !i.keptThroughLostInventory()) items.remove(i);
										if (!Recipe.usableInRecipe(i)) items.remove(i);
									}

									if (items.size() == 0){
										SoulsPixelDungeon.scene().addToFront(new WndMessage(Messages.get(AlchemyScene.class, "no_items")));
										return;
									}

									String[] itemNames = new String[items.size()];
									Image[] itemIcons = new Image[items.size()];
									for (int i = 0; i < items.size(); i++){
										itemNames[i] = Messages.titleCase(items.get(i).name());
										itemIcons[i] = new ItemSprite(items.get(i));
									}

									String info = "";
									if (ControllerHandler.controllerActive){
										info += KeyBindings.getKeyName(KeyBindings.getFirstKeyForAction(GameAction.LEFT_CLICK, true)) + ": " + Messages.get(Toolbar.class, "item_select") + "\n";
										info += KeyBindings.getKeyName(KeyBindings.getFirstKeyForAction(GameAction.BACK, true)) + ": " + Messages.get(Toolbar.class, "item_cancel");
									} else {
										info += Messages.get(WndKeyBindings.class, SPDAction.LEFT_CLICK.name()) + ": " + Messages.get(Toolbar.class, "item_select") + "\n";
										info += KeyBindings.getKeyName(KeyBindings.getFirstKeyForAction(GameAction.BACK, false)) + ": " + Messages.get(Toolbar.class, "item_cancel");
									}

									Game.scene().addToFront(new RadialMenu(Messages.get(Toolbar.class, "item_prompt"), info, itemNames, itemIcons){
										@Override
										public void onSelect(int idx, boolean alt) {
											super.onSelect(idx, alt);
											Item item = items.get(idx);
											synchronized (inputs) {
												if (item != null && inputs[0] != null) {
													for (int i = 0; i < inputs.length; i++) {
														if (inputs[i].item() == null) {
															if (item instanceof LiquidMetal){
																inputs[i].item(item.detachAll(Dungeon.hero.belongings.backpack));
															} else {
																inputs[i].item(item.detach(Dungeon.hero.belongings.backpack));
															}
															break;
														}
													}
													updateState();
												}
											}

										}
									});
								}
							});
						}
			}

			@Override
			public GameAction keyAction() {
				return SPDAction.INVENTORY_SELECTOR;
			}
		};
		add(invSelector);

		cancel = new IconButton(Icons.CLOSE.get()){
			@Override
			protected void onClick() {
				super.onClick();
				clearSlots();
				updateState();
			}

			@Override
			public GameAction keyAction() {
				return SPDAction.BACK;
			}

			@Override
			protected String hoverText() {
				return Messages.get(AlchemyScene.class, "cancel");
			}
		};
		cancel.setRect(left + 8, pos + 2, 16, 16);
		cancel.enable(false);
		add(cancel);

		repeat = new IconButton(Icons.REPEAT.get()){
			@Override
			protected void onClick() {
				super.onClick();
				if (lastRecipe != null){
					populate(lastIngredients, Dungeon.hero.belongings);
				}
			}

			@Override
			public GameAction keyAction() {
				return SPDAction.TAG_RESUME;
			}

			@Override
			protected String hoverText() {
				return Messages.get(AlchemyScene.class, "repeat");
			}
		};
		repeat.setRect(left + 24, pos + 2, 16, 16);
		repeat.enable(false);
		add(repeat);

		lastIngredients.clear();
		lastRecipe = null;

		for (int i = 0; i < inputs.length; i++){
			combines[i] = new CombineButton(i);
			combines[i].enable(false);

			outputs[i] = new OutputSlot();
			outputs[i].item(null);

			if (i == 0){
				//first ones are always visible
				combines[i].setRect(left + (w-30)/2f, inputs[1].top()+5, 30, inputs[1].height()-10);
				outputs[i].setRect(left + w - BTN_SIZE - 10, inputs[1].top(), BTN_SIZE, BTN_SIZE);
			} else {
				combines[i].visible = false;
				outputs[i].visible = false;
			}

			add(combines[i]);
			add(outputs[i]);
		}

		smokeEmitter = new Emitter();
		smokeEmitter.pos(outputs[0].left() + (BTN_SIZE-16)/2f, outputs[0].top() + (BTN_SIZE-16)/2f, 16, 16);
		smokeEmitter.autoKill = false;
		add(smokeEmitter);
		
		pos += 10;

		lowerBubbles.pos(0, pos, Camera.main.width, Math.max(0, Camera.main.height-pos));
		lowerBubbles.pour(Speck.factory( Speck.BUBBLE ), 0.1f );

		IconButton btnGuide = new IconButton( new ItemSprite(ItemSpriteSheet.ALCH_PAGE, null)){
			@Override
			protected void onClick() {
				super.onClick();
				clearSlots();
				updateState();
				AlchemyScene.this.addToFront(new Window(){
				
					{
						WndJournal.AlchemyTab t = new WndJournal.AlchemyTab();
						int w, h;
						if (landscape()) {
							w = WndJournal.WIDTH_L; h = WndJournal.HEIGHT_L;
						} else {
							w = WndJournal.WIDTH_P; h = WndJournal.HEIGHT_P;
						}
						resize(w, h);
						add(t);
						t.setRect(0, 0, w, h);
					}
				
				});
			}

			@Override
			public GameAction keyAction() {
				return SPDAction.JOURNAL;
			}

			@Override
			protected String hoverText() {
				return Messages.titleCase(Document.ALCHEMY_GUIDE.title());
			}
		};
		btnGuide.setRect(0, 0, 20, 20);
		add(btnGuide);

		String energyText = Messages.get(AlchemyScene.class, "energy") + " " + Dungeon.energy;
		if (toolkit != null){
			energyText += "+" + toolkit.availableEnergy();
		}

		energyLeft = PixelScene.renderTextBlock(energyText, 9);
		energyLeft.setPos(
				(Camera.main.width - energyLeft.width())/2,
				Camera.main.height - 8 - energyLeft.height()
		);
		energyLeft.hardlight(0x44CCFF);
		add(energyLeft);

		energyIcon = new ItemSprite( toolkit != null ? ItemSpriteSheet.ARTIFACT_TOOLKIT : ItemSpriteSheet.ENERGY);
		energyIcon.x = energyLeft.left() - energyIcon.width();
		energyIcon.y = energyLeft.top() - (energyIcon.height() - energyLeft.height())/2;
		align(energyIcon);
		add(energyIcon);

		energyAdd = new IconButton(Icons.get(Icons.PLUS)){

			private float time = 0;

			@Override
			public void update() {
				super.update();
				if (energyAddBlinking){
					icon.brightness( 0.5f + (float)Math.abs(Math.cos( StatusPane.FLASH_RATE * (time += Game.elapsed) )));
				} else {
					if (time > 0){
						icon.resetColor();
					}
					time = 0;
				}
			}

			@Override
			protected void onClick() {
				WndEnergizeItem.openItemSelector();
			}

			@Override
			public GameAction keyAction() {
				return SPDAction.TAG_ACTION;
			}

			@Override
			protected String hoverText() {
				return Messages.get(AlchemyScene.class, "energize");
			}
		};
		energyAdd.setRect(energyLeft.right(), energyLeft.top() - (16 - energyLeft.height())/2, 16, 16);
		align(energyAdd);
		add(energyAdd);

		sparkEmitter = new Emitter();
		sparkEmitter.pos(energyLeft.left(), energyLeft.top(), energyLeft.width(), energyLeft.height());
		sparkEmitter.autoKill = false;
		add(sparkEmitter);
		
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
		water.offset( 0, -5 * Game.elapsed );
	}
	
	@Override
	protected void onBackPressed() {
		Game.switchScene(GameScene.class);
	}
	
	protected WndBag.ItemSelector itemSelector = new WndBag.ItemSelector() {

		@Override
		public String textPrompt() {
			return Messages.get(AlchemyScene.class, "select");
		}

		@Override
		public boolean itemSelectable(Item item) {
			return Recipe.usableInRecipe(item);
		}

		@Override
		public void onSelect( Item item ) {
			synchronized (inputs) {
				if (item != null && inputs[0] != null) {
					for (int i = 0; i < inputs.length; i++) {
						if (inputs[i].item() == null) {
							if (item instanceof LiquidMetal){
								inputs[i].item(item.detachAll(Dungeon.hero.belongings.backpack));
							} else {
								inputs[i].item(item.detach(Dungeon.hero.belongings.backpack));
							}
							break;
						}
					}
					updateState();
				}
			}
		}
	};
	
	private<T extends Item> ArrayList<T> filterInput(Class<? extends T> itemClass){
		ArrayList<T> filtered = new ArrayList<>();
		for (int i = 0; i < inputs.length; i++){
			Item item = inputs[i].item();
			if (item != null && itemClass.isInstance(item)){
				filtered.add((T)item);
			}
		}
		return filtered;
	}
	
	private void updateState(){

		repeat.enable(false);

		ArrayList<Item> ingredients = filterInput(Item.class);
		ArrayList<Recipe> recipes = Recipe.findRecipes(ingredients);

		//disables / hides unneeded buttons
		for (int i = recipes.size(); i < combines.length; i++){
			combines[i].enable(false);
			outputs[i].item(null);

			if (i != 0){
				combines[i].visible = false;
				outputs[i].visible = false;
			}
		}

		cancel.enable(!ingredients.isEmpty());

		if (recipes.isEmpty()){
			combines[0].setPos(combines[0].left(), inputs[1].top()+5);
			outputs[0].setPos(outputs[0].left(), inputs[1].top());
			energyAddBlinking = false;
			return;
		}

		//positions active buttons
		float gap = recipes.size() == 2 ? 6 : 2;

		float height = inputs[2].bottom() - inputs[0].top();
		height -= recipes.size()*BTN_SIZE + (recipes.size()-1)*gap;
		float top = inputs[0].top() + height/2;

		//positions and enables active buttons
		boolean promptToAddEnergy = false;
		for (int i = 0; i < recipes.size(); i++){

			Recipe recipe = recipes.get(i);

			int cost = recipe.cost(ingredients);

			outputs[i].visible = true;
			outputs[i].setRect(outputs[0].left(), top, BTN_SIZE, BTN_SIZE);
			outputs[i].item(recipe.sampleOutput(ingredients));
			top += BTN_SIZE+gap;

			int availableEnergy = Dungeon.energy;
			if (toolkit != null){
				availableEnergy += toolkit.availableEnergy();
			}

			combines[i].visible = true;
			combines[i].setRect(combines[0].left(), outputs[i].top()+5, 30, 20);
			combines[i].enable(cost <= availableEnergy, cost);

			if (cost > availableEnergy && recipe instanceof TrinketCatalyst.Recipe){
				promptToAddEnergy = true;
			}

		}

		energyAddBlinking = promptToAddEnergy;

	}
	
	private void combine( int slot ){
		
		ArrayList<Item> ingredients = filterInput(Item.class);
		if (ingredients.isEmpty()) return;

		lastIngredients.clear();
		for (Item i : ingredients){
			lastIngredients.add(i.duplicate());
		}

		ArrayList<Recipe> recipes = Recipe.findRecipes(ingredients);
		if (recipes.size() <= slot) return;

		Recipe recipe = recipes.get(slot);
		
		Item result = null;
		
		if (recipe != null){
			int cost = recipe.cost(ingredients);
			if (toolkit != null){
				cost = toolkit.consumeEnergy(cost);
			}
			Dungeon.energy -= cost;

			String energyText = Messages.get(AlchemyScene.class, "energy") + " " + Dungeon.energy;
			if (toolkit != null){
				energyText += "+" + toolkit.availableEnergy();
			}
			energyLeft.text(energyText);
			energyLeft.setPos(
					(Camera.main.width - energyLeft.width())/2,
					Camera.main.height - 8 - energyLeft.height()
			);

			energyIcon.x = energyLeft.left() - energyIcon.width();
			align(energyIcon);

			energyAdd.setPos(energyLeft.right(), energyAdd.top());
			align(energyAdd);
			
			result = recipe.brew(ingredients);
		}
		
		if (result != null){

			craftItem(ingredients, result);

		}

		boolean foundItems = true;
		for (Item i : lastIngredients){
			Item found = Dungeon.hero.belongings.getSimilar(i);
			if (found == null){ //atm no quantity check as items are always loaded individually
				//currently found can be true if we need, say, 3x of an item but only have 2x of it
				foundItems = false;
			}
		}

		lastRecipe = recipe;
		repeat.enable(foundItems);

		cancel.enable(false);
		synchronized (inputs) {
			for (int i = 0; i < inputs.length; i++) {
				if (inputs[i] != null && inputs[i].item() != null) {
					cancel.enable(true);
					break;
				}
			}
		}
	}

	public void craftItem( ArrayList<Item> ingredients, Item result ){
		bubbleEmitter.start(Speck.factory( Speck.BUBBLE ), 0.01f, 100 );
		smokeEmitter.burst(Speck.factory( Speck.WOOL ), 10 );
		Sample.INSTANCE.play( Assets.Sounds.PUFF );

		int resultQuantity = result.quantity();
		if (!result.collect()){
			Dungeon.level.drop(result, Dungeon.hero.pos);
		}

		Statistics.itemsCrafted++;
		Badges.validateItemsCrafted();

		try {
			Dungeon.saveAll();
		} catch (IOException e) {
			SoulsPixelDungeon.reportException(e);
		}

		synchronized (inputs) {
			for (int i = 0; i < inputs.length; i++) {
				if (inputs[i] != null && inputs[i].item() != null) {
					Item item = inputs[i].item();
					if (item.quantity() <= 0) {
						inputs[i].item(null);
					} else {
						inputs[i].slot.updateText();
					}
				}
			}
		}

		updateState();
		//we reset the quantity in case the result was merged into another stack in the backpack
		result.quantity(resultQuantity);
		outputs[0].item(result);
	}
	
	public void populate(ArrayList<Item> toFind, Belongings inventory){
		clearSlots();
		
		int curslot = 0;
		for (Item finding : toFind){
			int needed = finding.quantity();
			ArrayList<Item> found = inventory.getAllSimilar(finding);
			while (!found.isEmpty() && needed > 0){
				Item detached;
				if (finding instanceof LiquidMetal) {
					detached = found.get(0).detachAll(inventory.backpack);
				} else {
					detached = found.get(0).detach(inventory.backpack);
				}
				inputs[curslot].item(detached);
				curslot++;
				needed -= detached.quantity();
				if (detached == found.get(0)) {
					found.remove(0);
				}
			}
		}
		updateState();
	}
	
	@Override
	public void destroy() {
		synchronized ( inputs ) {
			clearSlots();
			for (int i = 0; i < inputs.length; i++) {
				inputs[i] = null;
			}
		}
		
		try {
			Dungeon.saveAll();
			Badges.saveGlobal();
			Journal.saveGlobal();
		} catch (IOException e) {
			SoulsPixelDungeon.reportException(e);
		}
		super.destroy();
	}
	
	public void clearSlots(){
		synchronized ( inputs ) {
			for (int i = 0; i < inputs.length; i++) {
				if (inputs[i] != null && inputs[i].item() != null) {
					Item item = inputs[i].item();
					if (!item.collect()) {
						Dungeon.level.drop(item, Dungeon.hero.pos);
					}
					inputs[i].item(null);
				}
			}
		}
		cancel.enable(false);
		repeat.enable(lastRecipe != null);
	}

	public void createEnergy(){
		String energyText = Messages.get(AlchemyScene.class, "energy") + " " + Dungeon.energy;
		if (toolkit != null){
			energyText += "+" + toolkit.availableEnergy();
		}
		energyLeft.text(energyText);
		energyLeft.setPos(
				(Camera.main.width - energyLeft.width())/2,
				Camera.main.height - 8 - energyLeft.height()
		);

		energyIcon.x = energyLeft.left() - energyIcon.width();
		align(energyIcon);

		energyAdd.setPos(energyLeft.right(), energyAdd.top());
		align(energyAdd);

		bubbleEmitter.start(Speck.factory( Speck.BUBBLE ), 0.01f, 100 );
		sparkEmitter.burst(SparkParticle.FACTORY, 20);
		Sample.INSTANCE.play( Assets.Sounds.LIGHTNING );

		updateState();
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
					Item item = InputButton.this.item;
					if (item != null) {
						if (!item.collect()) {
							Dungeon.level.drop(item, Dungeon.hero.pos);
						}
						InputButton.this.item(null);
						updateState();
					}
					AlchemyScene.this.addToFront(WndBag.getBag( itemSelector ));
				}

				@Override
				protected boolean onLongClick() {
					Item item = InputButton.this.item;
					if (item != null){
						AlchemyScene.this.addToFront(new WndInfoItem(item));
						return true;
					}
					return false;
				}

				@Override
				//only the first empty button accepts key input
				public GameAction keyAction() {
					for (InputButton i : inputs){
						if (i.item == null || i.item instanceof WndBag.Placeholder) {
							if (i == InputButton.this) {
								return SPDAction.INVENTORY;
							} else {
								return super.keyAction();
							}
						}
					}
					return super.keyAction();
				}

				@Override
				protected String hoverText() {
					if (item == null || item instanceof WndBag.Placeholder){
						return Messages.get(AlchemyScene.class, "add");
					}
					return super.hoverText();
				}

				@Override
				public GameAction secondaryTooltipAction() {
					return SPDAction.INVENTORY_SELECTOR;
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
				slot.item(new WndBag.Placeholder(ItemSpriteSheet.SOMETHING));
			} else {
				slot.item(this.item = item);
			}
		}
	}

	private class CombineButton extends Component {

		protected int slot;

		protected RedButton button;
		protected RenderedTextBlock costText;

		private CombineButton(int slot){
			super();

			this.slot = slot;
		}

		@Override
		protected void createChildren() {
			super.createChildren();

			button = new RedButton(""){
				@Override
				protected void onClick() {
					super.onClick();
					combine(slot);
				}

				@Override
				protected String hoverText() {
					return Messages.get(AlchemyScene.class, "craft");
				}

				@Override
				public GameAction keyAction() {
					if (slot == 0 && !combines[1].active && !combines[2].active){
						return SPDAction.TAG_LOOT;
					}
					return super.keyAction();
				}
			};
			button.icon(Icons.get(Icons.ARROW));
			add(button);

			costText = PixelScene.renderTextBlock(6);
			add(costText);
		}

		@Override
		protected void layout() {
			super.layout();

			button.setRect(x, y, width(), height());

			costText.setPos(
					left() + (width() - costText.width())/2,
					top() - costText.height()
			);
		}

		public void enable( boolean enabled ){
			enable(enabled, 0);
		}

		public void enable( boolean enabled, int cost ){
			button.enable(enabled);
			if (enabled) {
				button.icon().tint(1, 1, 0, 1);
				button.alpha(1f);
				costText.hardlight(0x44CCFF);
			} else {
				button.icon().color(0, 0, 0);
				button.alpha(0.6f);
				costText.hardlight(0xFF0000);
			}

			if (cost == 0){
				costText.visible = false;
			} else {
				costText.visible = true;
				costText.text(Messages.get(AlchemyScene.class, "energy") + " " + cost);
			}

			layout();
			active = enabled;
		}

	}

	private class OutputSlot extends Component {

		protected NinePatch bg;
		protected ItemSlot slot;

		@Override
		protected void createChildren() {

			bg = Chrome.get(Chrome.Type.TOAST_TR);
			add(bg);

			slot = new ItemSlot() {
				@Override
				protected void onClick() {
					super.onClick();
					if (visible && item != null && item.trueName() != null){
						AlchemyScene.this.addToFront(new WndInfoItem(item));
					}
				}
			};
			slot.item(null);
			add( slot );
		}

		@Override
		protected void layout() {
			super.layout();

			bg.x = x;
			bg.y = y;
			bg.size(width(), height());

			slot.setRect(x+2, y+2, width()-4, height()-4);
		}

		public void item( Item item ) {
			slot.item(item);
		}
	}

	private static AlchemistsToolkit toolkit;

	public static void assignToolkit( AlchemistsToolkit toolkit ){
		AlchemyScene.toolkit = toolkit;
	}

	public static void clearToolkit(){
		AlchemyScene.toolkit = null;
	}

}
