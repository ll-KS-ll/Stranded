package com.brogames.bro.objecttypes;

import com.brogames.bro.objecttypes.items.*;
import com.brogames.bro.objecttypes.objects.Object;
import com.brogames.bro.objecttypes.objects.*;
import android.util.Log;

public class ObjectGetter {

	public static Object setObject(int objectType) {

		switch (objectType){
		
		case ObjectType.GRASS:
			return new Grass();
			
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
			
		case ObjectType.DIRT:
			return new Dirt();
		
		case ObjectType.RUBBLE:
			return new Rubble();
			
		case ObjectType.FOREST1:
			return new Forest1();
			
		case ObjectType.FOREST2:
			return new Forest2();
			
		case ObjectType.FOREST3:
			return new Forest3();
			
		case ObjectType.FOREST4:
			return new Forest4();
			
		case ObjectType.FOREST5:
			return new Forest5();
			
		case ObjectType.FOREST6:
			return new Forest6();
			
		case ObjectType.FOREST7:
			return new Forest7();
			
		case ObjectType.FOREST8:
			return new Forest8();
			
		case ObjectType.FOREST9:
			return new Forest9();
			
		case ObjectType.FOREST10:
			return new Forest10();
			
		case ObjectType.FOREST11:
			return new Forest11();
			
		case ObjectType.FOREST12:
			return new Forest12();
			
		case ObjectType.FOREST13:
			return new Forest13();
			
		case ObjectType.FOREST14:
			return new Forest14();
			
		case ObjectType.FOREST15:
			return new Forest15();
			
		case ObjectType.FOREST16:
			return new Forest16();
			
		case ObjectType.FOREST_PASSAGE_TOP:
			return new ForestPassageTop();
			
		case ObjectType.FOREST_PASSAGE:
			return new ForestPassage();
			
		case ObjectType.WRECK1:
			return new Wreck1();
			
		case ObjectType.WRECK2:
			return new Wreck2();
			
		case ObjectType.WRECK3:
			return new Wreck3();
			
		case ObjectType.WRECK4:
			return new Wreck4();
			
		case ObjectType.WRECK5:
			return new Wreck5();
			
		case ObjectType.WRECK6:
			return new Wreck6();
			
		case ObjectType.WRECK7:
			return new Wreck7();
			
		case ObjectType.WRECK8:
			return new Wreck8();
			
		case ObjectType.WRECK9:
			return new Wreck9();
			
		case ObjectType.WRECK10:
			return new Wreck10();
			
		case ObjectType.WRECK11:
			return new Wreck11();
			
		case ObjectType.WRECK12:
			return new Wreck12();
			
		case ObjectType.WRECK13:
			return new Wreck13();
			
		case ObjectType.WRECK14:
			return new Wreck14();
			
		case ObjectType.WRECK15:
			return new Wreck15();
			
		case ObjectType.WRECK16:
			return new Wreck16();
			
		case ObjectType.LOG_PILE:
			return new LogPile();
			
		case ObjectType.FIRE_PLACE:
			return new FirePlace();
			
		default: 
			if(objectType != 0)
				Log.e("ObjectHandler", "No object found to set for objecttype " + objectType);
		}
		return null;
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
			
		case ObjectType.POT:
			return new Pot();
			
		case ObjectType.LOG:
			return new TreeLog();
		
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
			
		case ObjectType.FIRE_LOGS:
			return new FireLogs();
			
		default:
			if(objectType != 0)
				Log.e("ObjectHandler", "No item found to set for objecttype " + objectType);
		}
		
		return null;
	}
	
}
