package com.brogames.bro.objecttypes.items;

import com.brogames.bro.ImageGetter;
import com.brogames.bro.objecttypes.ObjectType;

public class WoodyVines extends Item {

	public WoodyVines(int objectType) {
		super(objectType);
		
		bmp = ImageGetter.getImage(true, objectType, 0);
		recipe.add(ObjectType.AXE);
		recipe.add(ObjectType.BOW);
	}

}
