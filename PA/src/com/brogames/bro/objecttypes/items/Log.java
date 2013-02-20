package com.brogames.bro.objecttypes.items;

import com.brogames.bro.ImageGetter;
import com.brogames.bro.objecttypes.ObjectType;

public class Log extends Item{
	
	public Log(int objectType) {
		super(objectType);

		bmp = ImageGetter.getImage(true, objectType, 0);
		recipe.add(ObjectType.BOW_DRILL);
	}
}
