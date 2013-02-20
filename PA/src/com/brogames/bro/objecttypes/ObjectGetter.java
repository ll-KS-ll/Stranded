package com.brogames.bro.objecttypes;

import com.brogames.bro.objecttypes.items.*;
import com.brogames.bro.objecttypes.objects.Object;
import com.brogames.bro.objecttypes.objects.*;

public class ObjectGetter {

	public static Object setObject(int objectType) {

		switch (objectType) {

		case ObjectType.GRAY_STAIRS_UP:
			return new GrayStairs(objectType);
			
		case ObjectType.GRAY_STAIRS_DOWN:
			return new GrayStairs(objectType);
			
		case ObjectType.GRAY_STAIRS_LEFT:
			return new GrayStairs(objectType);
			
		case ObjectType.GRAY_STAIRS_RIGHT:
			return new GrayStairs(objectType);
			
		case ObjectType.HIGH_GRASS:
			return new HighGrass();

		case ObjectType.BUSH:
			return new Bush();

		case ObjectType.WATER:
			return new Water();

		case ObjectType.ROCK:
			return new Rock();

		case ObjectType.SAND:
			return new Sand();
			
		case ObjectType.PALM:
			return new Palm();
			
		case ObjectType.PALM_TOP:
			return new PalmTop();
			
		case ObjectType.FOREST_PASSAGE_TOP:
			return new ForestPassageTop();
			
		case ObjectType.FOREST_PASSAGE:
			return new ForestPassage();
		
		case ObjectType.EMPTY:
			// Empty object
			return null;
		
		default:
			return new Undefined(objectType);
		}
	}

	public static Item setItem(int objectType){
		
		switch(objectType){
		case ObjectType.STONE:
			return new Stone();
		
		case ObjectType.STICK:
			return new Stick();
			
		case ObjectType.WOODY_VINES:
			return new WoodyVines();
		
		case ObjectType.AXE:
			return new Axe();
		
		case ObjectType.CURVED_STICK:
			return new CurvedStick();
			
		case ObjectType.SMALL_STONE:
			return new SmallStone();
			
		case ObjectType.BOW:
			return new Bow();
		
		case ObjectType.ARROW:
			return new Arrow();
			
		case ObjectType.PORTAL:
			return new Portal();
			
		case ObjectType.FEATHER:
			return new Feather();
			
		case ObjectType.BOW_DRILL:
			return new BowDrill();
		
		case ObjectType.SHOVEL:
			return new Shovel();
		
		case ObjectType.SHARP_BONE:
			return new SharpBone();
			
		case ObjectType.LOG:
			return new Log();
		
		case ObjectType.BANANA:
			return new Banana();
			
		case ObjectType.COCONUT:
			return new Coconut();
			
		case ObjectType.SHOVEL_BLADE:
			return new ShovelBlade();
			
		case ObjectType.OPEN_COCONUT:
			return new OpenCoconut();
			
		case ObjectType.MAP:
			return new Map();
	
		case ObjectType.TINDER:
			return new Tinder();
		}
		
		return null;
	}
	
}
