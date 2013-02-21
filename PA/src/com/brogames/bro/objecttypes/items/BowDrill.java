package com.brogames.bro.objecttypes.items;

import com.brogames.bro.ImageGetter;
import com.brogames.bro.objecttypes.ObjectType;
import com.brogames.bro.Interact;

public class BowDrill extends Item {
	
	public BowDrill(int objectType) {
		super(objectType);
		bmp = ImageGetter.getImage(true, objectType, 0);

		components[0] = ObjectType.BOW;
		components[1] = ObjectType.STICK;
	}
	
	public int[] getConsequences(int objectType){
		super.getConsequences(objectType); 
		switch(objectType){
		case ObjectType.FIRE_PLACE:
			consequences[0] = Interact.REPLACE;
			consequences[1] = -1;
			consequences[2] = ObjectType.FIRE;
			consequences[3] = -1;
			break;
		}
		return consequences;
	}
	
}
