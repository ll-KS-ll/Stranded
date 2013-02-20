package com.brogames.bro.objecttypes.items;

import com.brogames.bro.ImageGetter;
import com.brogames.bro.objecttypes.ObjectType;

public class Feather extends Item {

	public Feather(int objectType) {
		super(objectType);
		bmp = ImageGetter.getImage(true, objectType, 0);
		recipe.add(ObjectType.ARROW);
	}
	
}
