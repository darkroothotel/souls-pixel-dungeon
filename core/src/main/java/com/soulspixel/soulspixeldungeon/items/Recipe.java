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

import com.soulspixel.soulspixeldungeon.SoulsPixelDungeon;
import com.soulspixel.soulspixeldungeon.items.bombs.Bomb;
import com.soulspixel.soulspixeldungeon.items.food.Blandfruit;
import com.soulspixel.soulspixeldungeon.items.food.MeatPie;
import com.soulspixel.soulspixeldungeon.items.food.StewedMeat;
import com.soulspixel.soulspixeldungeon.items.potions.Potion;
import com.soulspixel.soulspixeldungeon.items.potions.brews.AquaBrew;
import com.soulspixel.soulspixeldungeon.items.potions.brews.BlizzardBrew;
import com.soulspixel.soulspixeldungeon.items.potions.brews.CausticBrew;
import com.soulspixel.soulspixeldungeon.items.potions.brews.InfernalBrew;
import com.soulspixel.soulspixeldungeon.items.potions.brews.ShockingBrew;
import com.soulspixel.soulspixeldungeon.items.potions.brews.UnstableBrew;
import com.soulspixel.soulspixeldungeon.items.potions.elixirs.ElixirOfAquaticRejuvenation;
import com.soulspixel.soulspixeldungeon.items.potions.elixirs.ElixirOfArcaneArmor;
import com.soulspixel.soulspixeldungeon.items.potions.elixirs.ElixirOfDragonsBlood;
import com.soulspixel.soulspixeldungeon.items.potions.elixirs.ElixirOfFeatherFall;
import com.soulspixel.soulspixeldungeon.items.potions.elixirs.ElixirOfHoneyedHealing;
import com.soulspixel.soulspixeldungeon.items.potions.elixirs.ElixirOfIcyTouch;
import com.soulspixel.soulspixeldungeon.items.potions.elixirs.ElixirOfMight;
import com.soulspixel.soulspixeldungeon.items.potions.elixirs.ElixirOfToxicEssence;
import com.soulspixel.soulspixeldungeon.items.potions.exotic.ExoticPotion;
import com.soulspixel.soulspixeldungeon.items.scrolls.Scroll;
import com.soulspixel.soulspixeldungeon.items.scrolls.exotic.ExoticScroll;
import com.soulspixel.soulspixeldungeon.items.spells.Alchemize;
import com.soulspixel.soulspixeldungeon.items.spells.BeaconOfReturning;
import com.soulspixel.soulspixeldungeon.items.spells.CurseInfusion;
import com.soulspixel.soulspixeldungeon.items.spells.MagicalInfusion;
import com.soulspixel.soulspixeldungeon.items.spells.PhaseShift;
import com.soulspixel.soulspixeldungeon.items.spells.ReclaimTrap;
import com.soulspixel.soulspixeldungeon.items.spells.Recycle;
import com.soulspixel.soulspixeldungeon.items.spells.SpellOfEmber;
import com.soulspixel.soulspixeldungeon.items.spells.SummonElemental;
import com.soulspixel.soulspixeldungeon.items.spells.TelekineticGrab;
import com.soulspixel.soulspixeldungeon.items.spells.UnstableSpell;
import com.soulspixel.soulspixeldungeon.items.spells.WildEnergy;
import com.soulspixel.soulspixeldungeon.items.trinkets.Trinket;
import com.soulspixel.soulspixeldungeon.items.trinkets.TrinketCatalyst;
import com.soulspixel.soulspixeldungeon.items.wands.Wand;
import com.soulspixel.soulspixeldungeon.items.weapon.missiles.MissileWeapon;
import com.watabou.utils.Reflection;

import java.util.ArrayList;

public abstract class Recipe {
	
	public abstract boolean testIngredients(ArrayList<Item> ingredients);
	
	public abstract int cost(ArrayList<Item> ingredients);
	
	public abstract Item brew(ArrayList<Item> ingredients);
	
	public abstract Item sampleOutput(ArrayList<Item> ingredients);
	
	//subclass for the common situation of a recipe with static inputs and outputs
	public static abstract class SimpleRecipe extends Recipe {
		
		//*** These elements must be filled in by subclasses
		protected Class<?extends Item>[] inputs; //each class should be unique
		protected int[] inQuantity;
		
		protected int cost;
		
		protected Class<?extends Item> output;
		protected int outQuantity;
		//***
		
		//gets a simple list of items based on inputs
		public ArrayList<Item> getIngredients() {
			ArrayList<Item> result = new ArrayList<>();
			for (int i = 0; i < inputs.length; i++) {
				Item ingredient = Reflection.newInstance(inputs[i]);
				ingredient.quantity(inQuantity[i]);
				result.add(ingredient);
			}
			return result;
		}
		
		@Override
		public final boolean testIngredients(ArrayList<Item> ingredients) {
			
			int[] needed = inQuantity.clone();
			
			for (Item ingredient : ingredients){
				if (!ingredient.isIdentified()) return false;
				for (int i = 0; i < inputs.length; i++){
					if (ingredient.getClass() == inputs[i]){
						needed[i] -= ingredient.quantity();
						break;
					}
				}
			}
			
			for (int i : needed){
				if (i > 0){
					return false;
				}
			}
			
			return true;
		}
		
		public final int cost(ArrayList<Item> ingredients){
			return cost;
		}
		
		@Override
		public final Item brew(ArrayList<Item> ingredients) {
			if (!testIngredients(ingredients)) return null;
			
			int[] needed = inQuantity.clone();
			
			for (Item ingredient : ingredients){
				for (int i = 0; i < inputs.length; i++) {
					if (ingredient.getClass() == inputs[i] && needed[i] > 0) {
						if (needed[i] <= ingredient.quantity()) {
							ingredient.quantity(ingredient.quantity() - needed[i]);
							needed[i] = 0;
						} else {
							needed[i] -= ingredient.quantity();
							ingredient.quantity(0);
						}
					}
				}
			}
			
			//sample output and real output are identical in this case.
			return sampleOutput(null);
		}
		
		//ingredients are ignored, as output doesn't vary
		public final Item sampleOutput(ArrayList<Item> ingredients){
			try {
				Item result = Reflection.newInstance(output);
				result.quantity(outQuantity);
				return result;
			} catch (Exception e) {
				SoulsPixelDungeon.reportException( e );
				return null;
			}
		}
	}
	
	
	//*******
	// Static members
	//*******

	private static Recipe[] variableRecipes = new Recipe[]{
			new LiquidMetal.Recipe()
	};
	
	private static Recipe[] oneIngredientRecipes = new Recipe[]{
		new Scroll.ScrollToStone(),
		new ExoticPotion.PotionToExotic(),
		new ExoticScroll.ScrollToExotic(),
		new ArcaneResin.Recipe(),
		new BlizzardBrew.Recipe(),
		new InfernalBrew.Recipe(),
		new AquaBrew.Recipe(),
		new ShockingBrew.Recipe(),
		new ElixirOfDragonsBlood.Recipe(),
		new ElixirOfIcyTouch.Recipe(),
		new ElixirOfToxicEssence.Recipe(),
		new ElixirOfMight.Recipe(),
		new ElixirOfFeatherFall.Recipe(),
		new MagicalInfusion.Recipe(),
		new BeaconOfReturning.Recipe(),
		new PhaseShift.Recipe(),
		new Recycle.Recipe(),
		new TelekineticGrab.Recipe(),
		new SummonElemental.Recipe(),
		new StewedMeat.oneMeat(),
		new TrinketCatalyst.Recipe(),
		new Trinket.UpgradeTrinket()
	};
	
	private static Recipe[] twoIngredientRecipes = new Recipe[]{
		new Blandfruit.CookFruit(),
		new Bomb.EnhanceBomb(),
		new UnstableBrew.Recipe(),
		new CausticBrew.Recipe(),
		new ElixirOfArcaneArmor.Recipe(),
		new ElixirOfAquaticRejuvenation.Recipe(),
		new ElixirOfHoneyedHealing.Recipe(),
		new UnstableSpell.Recipe(),
		new Alchemize.Recipe(),
		new CurseInfusion.Recipe(),
		new ReclaimTrap.Recipe(),
		new WildEnergy.Recipe(),
		new SpellOfEmber.Recipe(),
		new StewedMeat.twoMeat()
	};
	
	private static Recipe[] threeIngredientRecipes = new Recipe[]{
		new Potion.SeedToPotion(),
		new StewedMeat.threeMeat(),
		new MeatPie.Recipe()
	};
	
	public static ArrayList<Recipe> findRecipes(ArrayList<Item> ingredients){

		ArrayList<Recipe> result = new ArrayList<>();

		for (Recipe recipe : variableRecipes){
			if (recipe.testIngredients(ingredients)){
				result.add(recipe);
			}
		}

		if (ingredients.size() == 1){
			for (Recipe recipe : oneIngredientRecipes){
				if (recipe.testIngredients(ingredients)){
					result.add(recipe);
				}
			}
			
		} else if (ingredients.size() == 2){
			for (Recipe recipe : twoIngredientRecipes){
				if (recipe.testIngredients(ingredients)){
					result.add(recipe);
				}
			}
			
		} else if (ingredients.size() == 3){
			for (Recipe recipe : threeIngredientRecipes){
				if (recipe.testIngredients(ingredients)){
					result.add(recipe);
				}
			}
		}
		
		return result;
	}
	
	public static boolean usableInRecipe(Item item){
		if (item instanceof EquipableItem){
			//only thrown weapons and wands allowed among equipment items
			return item.isIdentified() && !item.cursed && item instanceof MissileWeapon;
		} else if (item instanceof Wand) {
			return item.isIdentified() && !item.cursed;
		} else {
			//other items can be unidentified, but not cursed
			return !item.cursed;
		}
	}
}


