package com.brogames.bro.objecttypes;

import com.brogames.bro.objecttypes.items.*;
import com.brogames.bro.objecttypes.TileObject;
import com.brogames.bro.objecttypes.objects.*;

public class ObjectGetter {

	public static TileObject setTileObject(int objectType) {

		if(objectType < 0)
			return null;
		
		switch (objectType) {

		//ENVIRONMENT
		
		case ObjectType.GRAY_STAIRS_UP:
			return new GrayStairs(objectType);
			
		case ObjectType.GRAY_STAIRS_DOWN:
			return new GrayStairs(objectType);
			
		case ObjectType.GRAY_STAIRS_LEFT:
			return new GrayStairs(objectType);
			
		case ObjectType.GRAY_STAIRS_RIGHT:
			return new GrayStairs(objectType);
			
		case ObjectType.HIGH_GRASS:
			return new HighGrass(objectType);

		case ObjectType.BUSH:
			return new Bush(objectType);

		case ObjectType.WATER:
			return new Water(objectType);

		case ObjectType.ROCK:
			return new Rock(objectType);

		case ObjectType.SAND:
			return new Sand(objectType);
			
		case ObjectType.PALM:
			return new Palm(objectType);
			
		case ObjectType.PALM_TOP:
			return new PalmTop(objectType);
			
		case ObjectType.FOREST_PASSAGE_TOP:
			return new ForestPassageTop(objectType);
			
		case ObjectType.FOREST_PASSAGE:
			return new ForestPassage(objectType);
		
		// MISC
			
		case ObjectType.EMPTY:
			return new Empty(objectType);
		
		default:
			return new Undefined(objectType);
		
		// ITEMS	
			
		case ObjectType.STONE:
			return new Stone(objectType);
		
		case ObjectType.STICK:
			return new Stick(objectType);
			
		case ObjectType.WOODY_VINES:
			return new WoodyVines(objectType);
		
		case ObjectType.AXE:
			return new Axe(objectType);
		
		case ObjectType.CURVED_STICK:
			return new CurvedStick(objectType);
			
		case ObjectType.SMALL_STONE:
			return new SmallStone(objectType);
			
		case ObjectType.BOW:
			return new Bow(objectType);
		
		case ObjectType.ARROW:
			return new Arrow(objectType);
			
		case ObjectType.PORTAL:
			return new Portal(objectType);
			
		case ObjectType.FEATHER:
			return new Feather(objectType);
			
		case ObjectType.BOW_DRILL:
			return new BowDrill(objectType);
		
		case ObjectType.SHOVEL:
			return new Shovel(objectType);
		
		case ObjectType.SHARP_BONE:
			return new SharpBone(objectType);
			
		case ObjectType.LOG:
			return new Log(objectType);
		
		case ObjectType.BANANA:
			return new Banana(objectType);
			
		case ObjectType.COCONUT:
			return new Coconut(objectType);
			
		case ObjectType.SHOVEL_BLADE:
			return new ShovelBlade(objectType);
			
		case ObjectType.OPEN_COCONUT:
			return new OpenCoconut(objectType);
			
		case ObjectType.MAP:
			return new Map(objectType);
	
		case ObjectType.TINDER:
			return new Tinder(objectType);
		
		}
	}

	public static Item setItem(int objectType){
		if(objectType > ObjectType.FIRST_GRID)
			return (Item)setTileObject(objectType);
		return null;
	}
	
	public static Environment setEnvironment(int objectType){
		if(objectType <= ObjectType.FIRST_GRID)
			return (Environment)setTileObject(objectType);
		return null;
	}
	
}
