package com.brogames.bro.objecttypes.items;

import com.brogames.bro.ImageGetter;
import com.brogames.bro.objecttypes.ObjectType;

public class FishingRod extends Item{

	
	public FishingRod(int objectType){
		super(objectType);
		
		bmp = ImageGetter.getImage(true, objectType, 0);
		
		components[0] = ObjectType.WOODY_VINES;
		components[1] = ObjectType.STICK;
		components[2] = ObjectType.SHARP_BONE;
		components[3] = -1;
		
	}
	
}

