package com.brogames.bro.objecttypes.items;

import com.brogames.bro.ImageGetter;
import com.brogames.bro.objecttypes.ObjectType;

public class Arrow extends Item{

	
	public Arrow(int objectType){
		super(objectType);
		
		bmp = ImageGetter.getImage(true, objectType, 0);
		
		components[0] = ObjectType.SMALL_STONE;
		components[1] = ObjectType.STICK;
		components[2] = ObjectType.FEATHER;
		components[3] = -1;
		
	}
	
}
