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

package com.soulspixel.soulspixeldungeon.items.bombs;

import com.soulspixel.soulspixeldungeon.Assets;
import com.soulspixel.soulspixeldungeon.Dungeon;
import com.soulspixel.soulspixeldungeon.actors.Actor;
import com.soulspixel.soulspixeldungeon.actors.Char;
import com.soulspixel.soulspixeldungeon.effects.Flare;
import com.soulspixel.soulspixeldungeon.effects.particles.ShadowParticle;
import com.soulspixel.soulspixeldungeon.sprites.ItemSpriteSheet;
import com.soulspixel.soulspixeldungeon.tiles.DungeonTilemap;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.BArray;
import com.watabou.utils.PathFinder;

import java.util.ArrayList;

public class HolyBomb extends Bomb {
	
	{
		image = ItemSpriteSheet.HOLY_BOMB;
	}
	
	@Override
	public void explode(int cell) {
		super.explode(cell);
		
		if (Dungeon.level.heroFOV[cell]) {
			new Flare(10, 64).show(Dungeon.hero.sprite.parent, DungeonTilemap.tileCenterToWorld(cell), 2f);
		}
		
		ArrayList<Char> affected = new ArrayList<>();
		
		PathFinder.buildDistanceMap( cell, BArray.not( Dungeon.level.solid, null ), 2 );
		for (int i = 0; i < PathFinder.distance.length; i++) {
			if (PathFinder.distance[i] < Integer.MAX_VALUE) {
				Char ch = Actor.findChar(i);
				if (ch != null) {
					affected.add(ch);
					
				}
			}
		}
		
		for (Char ch : affected){
			if (ch.properties().contains(Char.Property.UNDEAD) || ch.properties().contains(Char.Property.DEMONIC)){
				ch.sprite.emitter().start( ShadowParticle.UP, 0.05f, 10 );
				
				//bomb deals an additional 50% damage to unholy enemies in a 5x5 range
				int damage = Math.round(Char.combatRoll( Dungeon.scalingDepth()+5, 10 + Dungeon.scalingDepth() * 2 ) * 0.5f);
				ch.damage(damage, new HolyDamage(), Char.DamageType.HOLY_MAGIC);
			}
		}
		
		Sample.INSTANCE.play( Assets.Sounds.READ );
	}

	public static class HolyDamage{}
	
	@Override
	public int value() {
		//prices of ingredients
		return quantity * (20 + 30);
	}
}
