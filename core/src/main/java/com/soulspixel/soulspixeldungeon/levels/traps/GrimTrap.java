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

package com.soulspixel.soulspixeldungeon.levels.traps;

import com.soulspixel.soulspixeldungeon.Assets;
import com.soulspixel.soulspixeldungeon.Badges;
import com.soulspixel.soulspixeldungeon.Dungeon;
import com.soulspixel.soulspixeldungeon.actors.Actor;
import com.soulspixel.soulspixeldungeon.actors.Char;
import com.soulspixel.soulspixeldungeon.effects.CellEmitter;
import com.soulspixel.soulspixeldungeon.effects.MagicMissile;
import com.soulspixel.soulspixeldungeon.effects.particles.ShadowParticle;
import com.soulspixel.soulspixeldungeon.mechanics.Ballistica;
import com.soulspixel.soulspixeldungeon.messages.Messages;
import com.soulspixel.soulspixeldungeon.tiles.DungeonTilemap;
import com.soulspixel.soulspixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;

public class GrimTrap extends Trap {

	{
		color = GREY;
		shape = LARGE_DOT;
		
		canBeHidden = false;
		avoidsHallways = true;
	}

	@Override
	public void activate() {

		//we handle this inside of a separate actor as the trap may produce a visual effect we need to pause for
		Actor.add(new Actor() {

			{
				actPriority = VFX_PRIO;
			}

			@Override
			protected boolean act() {
				Actor.remove(this);
				Char target = Actor.findChar(pos);

				//find the closest char that can be aimed at
				if (target == null){
					float closestDist = Float.MAX_VALUE;
					for (Char ch : Actor.chars()){
						if (!ch.isAlive()) continue;
						float curDist = Dungeon.level.trueDistance(pos, ch.pos);
						if (ch.invisible > 0) curDist += 1000;
						Ballistica bolt = new Ballistica(pos, ch.pos, Ballistica.PROJECTILE);
						if (bolt.collisionPos == ch.pos && curDist < closestDist){
							target = ch;
							closestDist = curDist;
						}
					}
				}

				if (target != null) {
					final Char finalTarget = target;
					//instant kill, use a mix of current HP and max HP, just like psi blast (for resistances)
					int damage = Math.round(finalTarget.HT/2f + finalTarget.HP/2f);

					//can't do more than 90% HT for the hero specifically
					if (finalTarget == Dungeon.hero){
						damage = (int)Math.min(damage, finalTarget.HT*0.9f);
					}

					final int finalDmg = damage;
					if (Dungeon.level.heroFOV[pos] || Dungeon.level.heroFOV[target.pos]) {
						((MagicMissile)finalTarget.sprite.parent.recycle(MagicMissile.class)).reset(
								MagicMissile.SHADOW,
								DungeonTilemap.tileCenterToWorld(pos),
								finalTarget.sprite.center(),
								new Callback() {
									@Override
									public void call() {
										finalTarget.damage(finalDmg, GrimTrap.this, Char.DamageType.DARK_MAGIC);
										if (finalTarget == Dungeon.hero) {
											Sample.INSTANCE.play(Assets.Sounds.CURSED);
											if (!finalTarget.isAlive()) {
												Badges.validateDeathFromGrimOrDisintTrap();
												Dungeon.fail( GrimTrap.this );
												GLog.n( Messages.get(GrimTrap.class, "ondeath") );
											}
										} else {
											Sample.INSTANCE.play(Assets.Sounds.BURNING);
										}
										finalTarget.sprite.emitter().burst(ShadowParticle.UP, 10);
										next();
									}
								});
						return false;
					} else {
						finalTarget.damage(finalDmg, GrimTrap.this, Char.DamageType.DARK_MAGIC);
						return true;
					}
				} else {
					CellEmitter.get(pos).burst(ShadowParticle.UP, 10);
					Sample.INSTANCE.play(Assets.Sounds.BURNING);
					return true;
				}
			}

		});
	}
}
