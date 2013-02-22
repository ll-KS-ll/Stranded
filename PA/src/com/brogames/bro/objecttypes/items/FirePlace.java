package com.brogames.bro.objecttypes.items;

import com.brogames.bro.ImageGetter;
import com.brogames.bro.Interact;
import com.brogames.bro.objecttypes.ObjectType;

public class FirePlace extends Item{
	
	public FirePlace(int objectType){
		super(objectType);
		
		bmp = ImageGetter.getImage(false, objectType-ObjectType.FIRST_GRID, 0); 
		
		components[0] = ObjectType.LOG;
		components[1] = ObjectType.TINDER;
		components[2] = -1;
		components[3] = -1;
		
	}
	
	public int[] getConsequences(int objectType){
		super.getConsequences(objectType);
		switch(objectType){
		case ObjectType.EMPTY:
			consequences[0] = Interact.ADD;
			consequences[1] = -1;
			consequences[2] = ObjectType.FIRE_PLACE;
			consequences[3] = 1;
			break;
		}
		return consequences;
	}
	
}
