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

import com.soulspixel.soulspixeldungeon.Assets;
import com.soulspixel.soulspixeldungeon.Badges;
import com.soulspixel.soulspixeldungeon.Dungeon;
import com.soulspixel.soulspixeldungeon.actors.Actor;
import com.soulspixel.soulspixeldungeon.actors.Char;
import com.soulspixel.soulspixeldungeon.actors.buffs.Buff;
import com.soulspixel.soulspixeldungeon.actors.hero.Hero;
import com.soulspixel.soulspixeldungeon.actors.hero.HeroSubClass;
import com.soulspixel.soulspixeldungeon.actors.hero.Talent;
import com.soulspixel.soulspixeldungeon.items.weapon.Weapon;
import com.soulspixel.soulspixeldungeon.messages.Messages;
import com.soulspixel.soulspixeldungeon.scenes.GameScene;
import com.soulspixel.soulspixeldungeon.sprites.ItemSprite;
import com.soulspixel.soulspixeldungeon.utils.GLog;
import com.soulspixel.soulspixeldungeon.windows.WndOptions;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.BArray;
import com.watabou.utils.PathFinder;

import java.util.ArrayList;

abstract public class KindOfWeapon extends EquipableItem {

	{
		weightClass = WeightClass.LIGHT;
	}

	protected String hitSound = Assets.Sounds.HIT;
	protected float hitSoundPitch = 1f;

	public Char.DamageType damageTypeDealt = Char.DamageType.STANDARD;
	public ArrayList<Char.DamageType> damageTypeImmune = new ArrayList<>();
	public ArrayList<Char.DamageType> damageTypeResisted = new ArrayList<>();
	public ArrayList<Char.DamageType> damageTypeWeak = new ArrayList<>();

	public int poiseResist = 0;
	
	@Override
	public void execute(Hero hero, String action) {
		if (hero.subClass == HeroSubClass.CHAMPION && action.equals(AC_EQUIP)){
			usesTargeting = false;
			String primaryName = Messages.titleCase(hero.belongings.weapon != null ? hero.belongings.weapon.trueName() : Messages.get(KindOfWeapon.class, "empty"));
			String secondaryName = Messages.titleCase(hero.belongings.secondWep != null ? hero.belongings.secondWep.trueName() : Messages.get(KindOfWeapon.class, "empty"));
			if (primaryName.length() > 18) primaryName = primaryName.substring(0, 15) + "...";
			if (secondaryName.length() > 18) secondaryName = secondaryName.substring(0, 15) + "...";
			GameScene.show(new WndOptions(
					new ItemSprite(this),
					Messages.titleCase(name()),
					Messages.get(KindOfWeapon.class, "which_equip_msg"),
					Messages.get(KindOfWeapon.class, "which_equip_primary", primaryName),
					Messages.get(KindOfWeapon.class, "which_equip_secondary", secondaryName)
			){
				@Override
				protected void onSelect(int index) {
					super.onSelect(index);
					if (index == 0 || index == 1){
						//In addition to equipping itself, item reassigns itself to the quickslot
						//This is a special case as the item is being removed from inventory, but is staying with the hero.
						int slot = Dungeon.quickslot.getSlot( KindOfWeapon.this );
						slotOfUnequipped = -1;
						if (index == 0) {
							doEquip(hero);
						} else {
							equipSecondary(hero);
						}
						if (slot != -1) {
							Dungeon.quickslot.setSlot( slot, KindOfWeapon.this );
							updateQuickslot();
						//if this item wasn't quickslotted, but the item it is replacing as equipped was
						//then also have the item occupy the unequipped item's quickslot
						} else if (slotOfUnequipped != -1 && defaultAction() != null) {
							Dungeon.quickslot.setSlot( slotOfUnequipped, KindOfWeapon.this );
							updateQuickslot();
						}
					}
				}
			});
		} else {
			super.execute(hero, action);
		}
	}

	@Override
	public String info() {
		String desc = super.info();
		desc += "\n\n"+Messages.get(this, "dt_deals_info", damageTypeDealt.getName(damageTypeDealt));
		if(!damageTypeImmune.isEmpty()){
			desc += "\n"+Messages.get(this, "immune_info");
			for (Char.DamageType dt : damageTypeImmune){
				desc += "\n_"+dt.getName(dt)+"_";
			}
		}
		if(!damageTypeResisted.isEmpty()){
			desc += "\n"+Messages.get(this, "resisted_info");
			for (Char.DamageType dt : damageTypeResisted){
				desc += "\n_"+dt.getName(dt)+"_";
			}
		}
		if(!damageTypeWeak.isEmpty()){
			desc += "\n"+Messages.get(this, "weak_info");
			for (Char.DamageType dt : damageTypeWeak){
				desc += "\n_"+dt.getName(dt)+"_";
			}
		}

		desc += "\n\n"+Messages.get(this, "weight_info", getWeight());

		return desc;
	}

	@Override
	public boolean isEquipped( Hero hero ) {
		return hero.belongings.weapon() == this || hero.belongings.secondWep() == this;
	}

	private static boolean isSwiftEquipping = false;

	protected float timeToEquip( Hero hero ) {
		return isSwiftEquipping ? 0f : super.timeToEquip(hero);
	}
	
	@Override
	public boolean doEquip( Hero hero ) {

		isSwiftEquipping = false;
		if (hero.belongings.contains(this) && hero.hasTalent(Talent.SWIFT_EQUIP)){
			if (hero.buff(Talent.SwiftEquipCooldown.class) == null
					|| hero.buff(Talent.SwiftEquipCooldown.class).hasSecondUse()){
				isSwiftEquipping = true;
			}
		}

		detachAll( hero.belongings.backpack );
		
		if (hero.belongings.weapon == null || hero.belongings.weapon.doUnequip( hero, true )) {
			
			hero.belongings.weapon = this;
			activate( hero );
			Talent.onItemEquipped(hero, this);
			Badges.validateDuelistUnlock();
			updateQuickslot();

			cursedKnown = true;
			if (cursed) {
				equipCursed( hero );
				GLog.n( Messages.get(KindOfWeapon.class, "equip_cursed") );
			}

			hero.spendAndNext( timeToEquip(hero) );
			if (isSwiftEquipping) {
				GLog.i(Messages.get(this, "swift_equip"));
				if (hero.buff(Talent.SwiftEquipCooldown.class) == null){
					Buff.affect(hero, Talent.SwiftEquipCooldown.class, 19f)
							.secondUse = hero.pointsInTalent(Talent.SWIFT_EQUIP) == 2;
				} else if (hero.buff(Talent.SwiftEquipCooldown.class).hasSecondUse()) {
					hero.buff(Talent.SwiftEquipCooldown.class).secondUse = false;
				}
				isSwiftEquipping = false;
			}
			return true;
			
		} else {
			isSwiftEquipping = false;
			collect( hero.belongings.backpack );
			return false;
		}
	}

	public boolean equipSecondary( Hero hero ){

		isSwiftEquipping = false;
		if (hero.belongings.contains(this) && hero.hasTalent(Talent.SWIFT_EQUIP)){
			if (hero.buff(Talent.SwiftEquipCooldown.class) == null
					|| hero.buff(Talent.SwiftEquipCooldown.class).hasSecondUse()){
				isSwiftEquipping = true;
			}
		}

		boolean wasInInv = hero.belongings.contains(this);
		detachAll( hero.belongings.backpack );

		if (hero.belongings.secondWep == null || hero.belongings.secondWep.doUnequip( hero, true )) {

			hero.belongings.secondWep = this;
			activate( hero );
			Talent.onItemEquipped(hero, this);
			Badges.validateDuelistUnlock();
			updateQuickslot();

			cursedKnown = true;
			if (cursed) {
				equipCursed( hero );
				GLog.n( Messages.get(KindOfWeapon.class, "equip_cursed") );
			}

			hero.spendAndNext( timeToEquip(hero) );
			if (isSwiftEquipping) {
				GLog.i(Messages.get(this, "swift_equip"));
				if (hero.buff(Talent.SwiftEquipCooldown.class) == null){
					Buff.affect(hero, Talent.SwiftEquipCooldown.class, 19f)
							.secondUse = hero.pointsInTalent(Talent.SWIFT_EQUIP) == 2;
				} else if (hero.buff(Talent.SwiftEquipCooldown.class).hasSecondUse()) {
					hero.buff(Talent.SwiftEquipCooldown.class).secondUse = false;
				}
				isSwiftEquipping = false;
			}
			return true;

		} else {
			isSwiftEquipping = false;
			collect( hero.belongings.backpack );
			return false;
		}
	}

	@Override
	public boolean doUnequip( Hero hero, boolean collect, boolean single ) {
		boolean second = hero.belongings.secondWep == this;

		if (second){
			//do this first so that the item can go to a full inventory
			hero.belongings.secondWep = null;
		}

		if (super.doUnequip( hero, collect, single )) {

			if (!second){
				hero.belongings.weapon = null;
			}
			return true;

		} else {

			if (second){
				hero.belongings.secondWep = this;
			}
			return false;

		}
	}

	public int min(){
		return min(buffedLvl());
	}

	public int max(){
		return max(buffedLvl());
	}

	abstract public int min(int lvl);
	abstract public int max(int lvl);

	public int damageRoll( Char owner ) {
		return Char.combatRoll( min(), max() );
	}
	
	public float accuracyFactor( Char owner, Char target ) {
		return 1f;
	}
	
	public float delayFactor( Char owner ) {
		return 1f;
	}

	public int reachFactor( Char owner ){
		return 1;
	}
	
	public boolean canReach( Char owner, int target){
		int reach = reachFactor(owner);
		if (Dungeon.level.distance( owner.pos, target ) > reach){
			return false;
		} else {
			boolean[] passable = BArray.not(Dungeon.level.solid, null);
			for (Char ch : Actor.chars()) {
				if (ch != owner) passable[ch.pos] = false;
			}
			
			PathFinder.buildDistanceMap(target, passable, reach);
			
			return PathFinder.distance[owner.pos] <= reach;
		}
	}

	public int defenseFactor( Char owner ) {
		return 0;
	}
	
	public int proc( Char attacker, Char defender, int damage ) {
		return damage;
	}

	public void hitSound( float pitch ){
		Sample.INSTANCE.play(hitSound, 1, pitch * hitSoundPitch);
	}

	public void stanceBreakingEffect(Char target) {
		if(this instanceof Weapon){
			if(((Weapon) this).enchantment != null){
				((Weapon) this).enchantment.stanceBreakingEffect(target);
			}
		}
	}

	public int bonusPoiseDamage() {
		return 0;
	}

	public int getPoiseResist() {
		if(poiseResist > weightClass.getWeight(weightClass) || poiseResist < weightClass.getWeight(weightClass)){
			return poiseResist;
		}
		return weightClass.getWeight(weightClass);
	}
}
