package com.brogames.bro.objecttypes.items;

import com.brogames.bro.objecttypes.ObjectType;
import com.brogames.bro.Interact;

public class FirePlace extends Item{
	
	public FirePlace(int objectType){
		super(objectType);
		
		//bmp = ImageGetter.getImage(true, objectType, 0); // No image yet
		
		components[0] = ObjectType.LOG;
		components[1] = ObjectType.TINDER;
		components[2] = -1;
		components[3] = -1;
		
		/* Consequences handling list:
		 * 
		 * 1. What to do?
		 * 2. What to return?
		 * 3. What to add?
		 * 4. Take item?
		 * 
		 */
		
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
