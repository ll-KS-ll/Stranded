package com.brogames.bro.objecttypes.items;

import com.brogames.bro.ImageGetter;
import com.brogames.bro.objecttypes.ObjectType;

public class SmallStone extends Item{
	
	public SmallStone(int objectType) {
		super(objectType);
		
		bmp = ImageGetter.getImage(true, objectType, 0);
		recipe.add(ObjectType.ARROW);
	}
}
