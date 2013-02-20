package com.brogames.bro.objecttypes.items;

import com.brogames.bro.ImageGetter;
import com.brogames.bro.objecttypes.ObjectType;

public class BowDrill extends Item {
	
	public BowDrill(int objectType) {
		super(objectType);
		
		bmp = ImageGetter.getImage(true, objectType, 0);
		
		components[0] = ObjectType.BOW;
		components[1] = ObjectType.STICK;
		components[2] = ObjectType.STONE;
		components[3] = ObjectType.LOG;
	}
}
