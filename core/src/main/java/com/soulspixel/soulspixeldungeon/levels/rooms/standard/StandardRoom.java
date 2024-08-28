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

package com.soulspixel.soulspixeldungeon.levels.rooms.standard;

import com.soulspixel.soulspixeldungeon.Dungeon;
import com.soulspixel.soulspixeldungeon.levels.Level;
import com.soulspixel.soulspixeldungeon.levels.Terrain;
import com.soulspixel.soulspixeldungeon.levels.rooms.Room;
import com.watabou.utils.Point;
import com.watabou.utils.Random;
import com.watabou.utils.Reflection;

import java.util.ArrayList;

public abstract class StandardRoom extends Room {
	
	public enum SizeCategory {
		
		NORMAL(4, 10, 1),
		LARGE(10, 14, 2),
		GIANT(14, 18, 3);
		
		public final int minDim, maxDim;
		public final int roomValue;
		
		SizeCategory(int min, int max, int val){
			minDim = min;
			maxDim = max;
			roomValue = val;
		}
		
	}
	
	public SizeCategory sizeCat;
	{ setSizeCat(); }
	
	//Note that if a room wishes to allow itself to be forced to a certain size category,
	//but would (effectively) never roll that size category, consider using Float.MIN_VALUE
	public float[] sizeCatProbs(){
		//always normal by default
		return new float[]{1, 0, 0};
	}
	
	public boolean setSizeCat(){
		return setSizeCat(0, SizeCategory.values().length-1);
	}
	
	//assumes room value is always ordinal+1
	public boolean setSizeCat( int maxRoomValue ){
		return setSizeCat(0, maxRoomValue-1);
	}
	
	//returns false if size cannot be set
	public boolean setSizeCat( int minOrdinal, int maxOrdinal ) {
		float[] probs = sizeCatProbs();
		SizeCategory[] categories = SizeCategory.values();
		
		if (probs.length != categories.length) return false;
		
		for (int i = 0; i < minOrdinal; i++)                    probs[i] = 0;
		for (int i = maxOrdinal+1; i < categories.length; i++)  probs[i] = 0;
		
		int ordinal = Random.chances(probs);
		
		if (ordinal != -1){
			sizeCat = categories[ordinal];
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public int minWidth() { return sizeCat.minDim; }
	public int maxWidth() { return sizeCat.maxDim; }
	
	@Override
	public int minHeight() { return sizeCat.minDim; }
	public int maxHeight() { return sizeCat.maxDim; }

	//larger standard rooms generally count as multiple rooms for various counting/weighting purposes
	//but there can be exceptions
	public int sizeFactor(){
		return sizeCat.roomValue;
	}

	public int mobSpawnWeight(){
		return sizeFactor();
	}

	public int connectionWeight(){
		return sizeFactor() * sizeFactor();
	}

	@Override
	public boolean canMerge(Level l, Room other, Point p, int mergeTerrain) {
		int cell = l.pointToCell(pointInside(p, 1));
		return (Terrain.flags[l.map[cell]] & Terrain.SOLID) == 0;
	}

	//FIXME this is a very messy way of handing variable standard rooms
	private static ArrayList<Class<?extends StandardRoom>> rooms = new ArrayList<>();
	static {
		rooms.add(EmptyRoom.class);


		rooms.add(SewerPipeRoom.class);
		rooms.add(RingRoom.class);
		rooms.add(WaterBridgeRoom.class);
		rooms.add(CircleBasinRoom.class);

		rooms.add(SegmentedRoom.class);
		rooms.add(PillarsRoom.class);
		rooms.add(ChasmBridgeRoom.class);
		rooms.add(CellBlockRoom.class);

		rooms.add(CaveRoom.class);
		rooms.add(CavesFissureRoom.class);
		rooms.add(CirclePitRoom.class);
		rooms.add(CircleWallRoom.class);

		rooms.add(HallwayRoom.class);
		rooms.add(StatuesRoom.class);
		rooms.add(LibraryRingRoom.class);
		rooms.add(SegmentedLibraryRoom.class);

		rooms.add(RuinsRoom.class);
		rooms.add(ChasmRoom.class);
		rooms.add(SkullsRoom.class);
		rooms.add(RitualRoom.class);


		rooms.add(PlantsRoom.class);
		rooms.add(AquariumRoom.class);
		rooms.add(PlatformRoom.class);
		rooms.add(BurnedRoom.class);
		rooms.add(FissureRoom.class);
		rooms.add(GrassyGraveRoom.class);
		rooms.add(StripedRoom.class);
		rooms.add(StudyRoom.class);
		rooms.add(SuspiciousChestRoom.class);
		rooms.add(MinefieldRoom.class);
	}

	private static float[][] chances = new float[76][];
	static {
		//dungeon 1
		chances[1] =  new float[]{5,  10,10,10,5, 0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0,  1,0,1,0,1,0,1,1,0,0};
		chances[2] =  new float[]{5,  10,10,10,5, 0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0,  1,1,1,1,1,1,1,1,1,1};
		chances[3] =  new float[]{5,  10,10,10,5, 0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0,  1,1,1,1,1,1,1,1,1,1};
		chances[4] =  new float[]{5,  10,10,10,5, 0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0,  1,1,1,1,1,1,1,1,1,1};
		chances[5] =  new float[]{5,  10,10,10,5, 0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0,  1,1,1,1,1,1,1,1,1,1};
		chances[6] =  new float[]{5,  10,10,10,5, 0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0,  1,1,1,1,1,1,1,1,1,1};
		chances[7] =  new float[]{5,  10,10,10,5, 0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0,  1,1,1,1,1,1,1,1,1,1};
		chances[8] =  new float[]{5,  10,10,10,5, 0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0,  1,1,1,1,1,1,1,1,1,1};
		chances[9] =  new float[]{5,  10,10,10,5, 0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0,  1,1,1,1,1,1,1,1,1,1};
		chances[10] =  new float[]{5,  10,10,10,5, 0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0,  1,1,1,1,1,1,1,1,1,1};
		chances[11] =  new float[]{5,  10,10,10,5, 0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0,  1,1,1,1,1,1,1,1,1,1};
		chances[12] =  new float[]{5,  10,10,10,5, 0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0,  1,1,1,1,1,1,1,1,1,1};
		//boss
		chances[13] =  new float[]{5,  10,10,10,0, 0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0,  0,0,0,0,0,0,0,0,0,0};
		//extra floor
		chances[14] =  new float[]{5,  10,10,10,5, 0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0,  1,1,1,1,1,1,1,1,1,1};
		//dungeon 2
		chances[15] =  new float[]{5,  0,0,0,0, 10,10,10,5, 0,0,0,0, 0,0,0,0, 0,0,0,0,  1,1,1,1,1,1,1,1,1,1};
		chances[16] =  new float[]{5,  0,0,0,0, 10,10,10,5, 0,0,0,0, 0,0,0,0, 0,0,0,0,  1,1,1,1,1,1,1,1,1,1};
		chances[17] =  new float[]{5,  0,0,0,0, 10,10,10,5, 0,0,0,0, 0,0,0,0, 0,0,0,0,  1,1,1,1,1,1,1,1,1,1};
		chances[18] =  new float[]{5,  0,0,0,0, 10,10,10,5, 0,0,0,0, 0,0,0,0, 0,0,0,0,  1,1,1,1,1,1,1,1,1,1};
		chances[19] =  new float[]{5,  0,0,0,0, 10,10,10,5, 0,0,0,0, 0,0,0,0, 0,0,0,0,  1,1,1,1,1,1,1,1,1,1};
		chances[20] =  new float[]{5,  0,0,0,0, 10,10,10,5, 0,0,0,0, 0,0,0,0, 0,0,0,0,  1,1,1,1,1,1,1,1,1,1};
		chances[21] =  new float[]{5,  0,0,0,0, 10,10,10,5, 0,0,0,0, 0,0,0,0, 0,0,0,0,  1,1,1,1,1,1,1,1,1,1};
		chances[22] =  new float[]{5,  0,0,0,0, 10,10,10,5, 0,0,0,0, 0,0,0,0, 0,0,0,0,  1,1,1,1,1,1,1,1,1,1};
		chances[23] =  new float[]{5,  0,0,0,0, 10,10,10,5, 0,0,0,0, 0,0,0,0, 0,0,0,0,  1,1,1,1,1,1,1,1,1,1};
		chances[24] =  new float[]{5,  0,0,0,0, 10,10,10,5, 0,0,0,0, 0,0,0,0, 0,0,0,0,  1,1,1,1,1,1,1,1,1,1};
		chances[25] =  new float[]{5,  0,0,0,0, 10,10,10,5, 0,0,0,0, 0,0,0,0, 0,0,0,0,  1,1,1,1,1,1,1,1,1,1};
		chances[26] =  new float[]{5,  0,0,0,0, 10,10,10,5, 0,0,0,0, 0,0,0,0, 0,0,0,0,  1,1,1,1,1,1,1,1,1,1};
		chances[27] =  new float[]{5,  0,0,0,0, 10,10,10,5, 0,0,0,0, 0,0,0,0, 0,0,0,0,  1,1,1,1,1,1,1,1,1,1};
		//boss room
		chances[28] =  new float[]{5,  0,0,0,0, 10,10,10,5, 0,0,0,0, 0,0,0,0, 0,0,0,0,  1,1,1,1,1,1,1,1,1,1};
		//extra floor
		chances[29] =  new float[]{5,  0,0,0,0, 10,10,10,5, 0,0,0,0, 0,0,0,0, 0,0,0,0,  1,1,1,1,1,1,1,1,1,1};
		//dungeon 3
		chances[30] = new float[]{5,  0,0,0,0, 0,0,0,0, 15,10,5,5,  0,0,0,0, 0,0,0,0,  1,1,1,1,1,1,1,1,1,1};
		chances[31] = new float[]{5,  0,0,0,0, 0,0,0,0, 15,10,5,5,  0,0,0,0, 0,0,0,0,  1,1,1,1,1,1,1,1,1,1};
		chances[32] = new float[]{5,  0,0,0,0, 0,0,0,0, 15,10,5,5,  0,0,0,0, 0,0,0,0,  1,1,1,1,1,1,1,1,1,1};
		chances[33] = new float[]{5,  0,0,0,0, 0,0,0,0, 15,10,5,5,  0,0,0,0, 0,0,0,0,  1,1,1,1,1,1,1,1,1,1};
		chances[34] = new float[]{5,  0,0,0,0, 0,0,0,0, 15,10,5,5,  0,0,0,0, 0,0,0,0,  1,1,1,1,1,1,1,1,1,1};
		chances[35] = new float[]{5,  0,0,0,0, 0,0,0,0, 15,10,5,5,  0,0,0,0, 0,0,0,0,  1,1,1,1,1,1,1,1,1,1};
		chances[36] = new float[]{5,  0,0,0,0, 0,0,0,0, 15,10,5,5,  0,0,0,0, 0,0,0,0,  1,1,1,1,1,1,1,1,1,1};
		chances[37] = new float[]{5,  0,0,0,0, 0,0,0,0, 15,10,5,5,  0,0,0,0, 0,0,0,0,  1,1,1,1,1,1,1,1,1,1};
		chances[38] = new float[]{5,  0,0,0,0, 0,0,0,0, 15,10,5,5,  0,0,0,0, 0,0,0,0,  1,1,1,1,1,1,1,1,1,1};
		chances[39] = new float[]{5,  0,0,0,0, 0,0,0,0, 15,10,5,5,  0,0,0,0, 0,0,0,0,  1,1,1,1,1,1,1,1,1,1};
		chances[40] = new float[]{5,  0,0,0,0, 0,0,0,0, 15,10,5,5,  0,0,0,0, 0,0,0,0,  1,1,1,1,1,1,1,1,1,1};
		chances[41] = new float[]{5,  0,0,0,0, 0,0,0,0, 15,10,5,5,  0,0,0,0, 0,0,0,0,  1,1,1,1,1,1,1,1,1,1};
		chances[42] = new float[]{5,  0,0,0,0, 0,0,0,0, 15,10,5,5,  0,0,0,0, 0,0,0,0,  1,1,1,1,1,1,1,1,1,1};
		//boss level
		chances[43] = new float[]{5,  0,0,0,0, 0,0,0,0, 15,10,5,5,  0,0,0,0, 0,0,0,0,  1,1,1,1,1,1,1,1,1,1};
		//extra level
		chances[44] = new float[]{5,  0,0,0,0, 0,0,0,0, 15,10,5,5,  0,0,0,0, 0,0,0,0,  1,1,1,1,1,1,1,1,1,1};
		//dungeon 4
		chances[45] = new float[]{5,  0,0,0,0, 0,0,0,0, 0,0,0,0, 10,10,10,5, 0,0,0,0,  1,1,1,1,1,1,1,1,1,1};
		chances[46] = new float[]{5,  0,0,0,0, 0,0,0,0, 0,0,0,0, 10,10,10,5, 0,0,0,0,  1,1,1,1,1,1,1,1,1,1};
		chances[47] = new float[]{5,  0,0,0,0, 0,0,0,0, 0,0,0,0, 10,10,10,5, 0,0,0,0,  1,1,1,1,1,1,1,1,1,1};
		chances[48] = new float[]{5,  0,0,0,0, 0,0,0,0, 0,0,0,0, 10,10,10,5, 0,0,0,0,  1,1,1,1,1,1,1,1,1,1};
		chances[49] = new float[]{5,  0,0,0,0, 0,0,0,0, 0,0,0,0, 10,10,10,5, 0,0,0,0,  1,1,1,1,1,1,1,1,1,1};
		chances[50] = new float[]{5,  0,0,0,0, 0,0,0,0, 0,0,0,0, 10,10,10,5, 0,0,0,0,  1,1,1,1,1,1,1,1,1,1};
		chances[51] = new float[]{5,  0,0,0,0, 0,0,0,0, 0,0,0,0, 10,10,10,5, 0,0,0,0,  1,1,1,1,1,1,1,1,1,1};
		chances[52] = new float[]{5,  0,0,0,0, 0,0,0,0, 0,0,0,0, 10,10,10,5, 0,0,0,0,  1,1,1,1,1,1,1,1,1,1};
		chances[53] = new float[]{5,  0,0,0,0, 0,0,0,0, 0,0,0,0, 10,10,10,5, 0,0,0,0,  1,1,1,1,1,1,1,1,1,1};
		chances[54] = new float[]{5,  0,0,0,0, 0,0,0,0, 0,0,0,0, 10,10,10,5, 0,0,0,0,  1,1,1,1,1,1,1,1,1,1};
		chances[55] = new float[]{5,  0,0,0,0, 0,0,0,0, 0,0,0,0, 10,10,10,5, 0,0,0,0,  1,1,1,1,1,1,1,1,1,1};
		chances[56] = new float[]{5,  0,0,0,0, 0,0,0,0, 0,0,0,0, 10,10,10,5, 0,0,0,0,  1,1,1,1,1,1,1,1,1,1};
		chances[57] = new float[]{5,  0,0,0,0, 0,0,0,0, 0,0,0,0, 10,10,10,5, 0,0,0,0,  1,1,1,1,1,1,1,1,1,1};
		//boss level
		chances[58] = new float[]{5,  0,0,0,0, 0,0,0,0, 0,0,0,0, 10,10,10,5, 0,0,0,0,  1,1,1,1,1,1,1,1,1,1};
		//extra level
		chances[59] = new float[]{5,  0,0,0,0, 0,0,0,0, 0,0,0,0, 10,10,10,5, 0,0,0,0,  1,1,1,1,1,1,1,1,1,1};
		//dungeon 5
		chances[60] = new float[]{5,  0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0, 15,10,5,5,   1,1,1,1,1,1,1,1,1,1};
		chances[61] = new float[]{5,  0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0, 15,10,5,5,   1,1,1,1,1,1,1,1,1,1};
		chances[62] = new float[]{5,  0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0, 15,10,5,5,   1,1,1,1,1,1,1,1,1,1};
		chances[63] = new float[]{5,  0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0, 15,10,5,5,   1,1,1,1,1,1,1,1,1,1};
		chances[64] = new float[]{5,  0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0, 15,10,5,5,   1,1,1,1,1,1,1,1,1,1};
		chances[65] = new float[]{5,  0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0, 15,10,5,5,   1,1,1,1,1,1,1,1,1,1};
		chances[66] = new float[]{5,  0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0, 15,10,5,5,   1,1,1,1,1,1,1,1,1,1};
		chances[67] = new float[]{5,  0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0, 15,10,5,5,   1,1,1,1,1,1,1,1,1,1};
		chances[68] = new float[]{5,  0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0, 15,10,5,5,   1,1,1,1,1,1,1,1,1,1};
		chances[69] = new float[]{5,  0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0, 15,10,5,5,   1,1,1,1,1,1,1,1,1,1};
		chances[70] = new float[]{5,  0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0, 15,10,5,5,   1,1,1,1,1,1,1,1,1,1};
		chances[71] = new float[]{5,  0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0, 15,10,5,5,   1,1,1,1,1,1,1,1,1,1};
		//boss level
		chances[72] = new float[]{5,  0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0, 15,10,5,5,   1,1,1,1,1,1,1,1,1,1};
		//extra levels
		chances[73] = new float[]{5,  0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0, 15,10,5,5,   1,1,1,1,1,1,1,1,1,1};
		chances[74] = new float[]{5,  0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0, 15,10,5,5,   1,1,1,1,1,1,1,1,1,1};
		//last level
		chances[75] = new float[]{5,  0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0, 15,10,5,5,   1,1,1,1,1,1,1,1,1,1};
	}

	//plantArmor, light, confuse, teleport, trip, detect, shader, normal, blindness, crabArmor, vomit, silence, weak, invisibility, sticky,
	private static float[][] chancesRoomMood = new float[76][];
	static {
		//dungeon 1
		chancesRoomMood[1] =  new float[]{0, 5, 0, 0, 0, 0, 0,  10, 5, 0, 0, 0, 0, 0, 0};
		chancesRoomMood[2] =  new float[]{5, 5, 0, 0, 0, 5, 0,  30, 5, 5, 0, 5, 0, 0, 0};
		chancesRoomMood[3] =  new float[]{5, 5, 5, 0, 5, 5, 0,  50, 5, 5, 5, 5, 5, 0, 0};
		chancesRoomMood[4] =  new float[]{5, 5, 5, 5, 5, 5, 5,  70, 5, 5, 5, 5, 5, 5, 5};
		chancesRoomMood[5] =  new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		chancesRoomMood[6] =  new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		chancesRoomMood[7] =  new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		chancesRoomMood[8] =  new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		chancesRoomMood[9] =  new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		chancesRoomMood[10] =  new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		chancesRoomMood[11] =  new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		chancesRoomMood[12] =  new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		//boss
		chancesRoomMood[13] =  new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		//extra floor
		chancesRoomMood[14] =  new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		//dungeon 2
		chancesRoomMood[15] =  new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		chancesRoomMood[16] =  new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		chancesRoomMood[17] =  new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		chancesRoomMood[18] =  new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		chancesRoomMood[19] =  new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		chancesRoomMood[20] =  new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		chancesRoomMood[21] =  new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		chancesRoomMood[22] =  new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		chancesRoomMood[23] =  new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		chancesRoomMood[24] =  new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		chancesRoomMood[25] =  new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		chancesRoomMood[26] =  new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		chancesRoomMood[27] =  new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		//boss room
		chancesRoomMood[28] =  new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		//extra floor
		chancesRoomMood[29] =  new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		//dungeon 3
		chancesRoomMood[30] = new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		chancesRoomMood[31] = new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		chancesRoomMood[32] = new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		chancesRoomMood[33] = new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		chancesRoomMood[34] = new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		chancesRoomMood[35] = new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		chancesRoomMood[36] = new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		chancesRoomMood[37] = new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		chancesRoomMood[38] = new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		chancesRoomMood[39] = new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		chancesRoomMood[40] = new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		chancesRoomMood[41] = new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		chancesRoomMood[42] = new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		//boss level
		chancesRoomMood[43] = new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		//extra level
		chancesRoomMood[44] = new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		//dungeon 4
		chancesRoomMood[45] = new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		chancesRoomMood[46] = new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		chancesRoomMood[47] = new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		chancesRoomMood[48] = new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		chancesRoomMood[49] = new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		chancesRoomMood[50] = new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		chancesRoomMood[51] = new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		chancesRoomMood[52] = new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		chancesRoomMood[53] = new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		chancesRoomMood[54] = new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		chancesRoomMood[55] = new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		chancesRoomMood[56] = new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		chancesRoomMood[57] = new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		//boss level
		chancesRoomMood[58] = new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		//extra level
		chancesRoomMood[59] = new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		//dungeon 5
		chancesRoomMood[60] = new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		chancesRoomMood[61] = new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		chancesRoomMood[62] = new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		chancesRoomMood[63] = new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		chancesRoomMood[64] = new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		chancesRoomMood[65] = new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		chancesRoomMood[66] = new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		chancesRoomMood[67] = new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		chancesRoomMood[68] = new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		chancesRoomMood[69] = new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		chancesRoomMood[70] = new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		chancesRoomMood[71] = new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		//boss level
		chancesRoomMood[72] = new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		//extra levels
		chancesRoomMood[73] = new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		chancesRoomMood[74] = new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
		//last level
		chancesRoomMood[75] = new float[]{0, 0, 0, 0, 0, 0, 0,  10, 0, 0, 0, 0, 0, 0, 0};
	}

	private static final int NUMBER_OF_MOODS = 15;
	
	
	public static StandardRoom createRoom(){
		StandardRoom standardRoom = Reflection.newInstance(rooms.get(Random.chances(chances[Dungeon.depth])));
		//standardRoom.type = Random.chances(chancesRoomMood[Dungeon.depth]);
        standardRoom.type = Random.chances(chancesRoomMood[Dungeon.depth]) - ((NUMBER_OF_MOODS - 1) / 2);
		return standardRoom;
	}
	
}
