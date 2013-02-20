package com.brogames.bro.objecttypes.items;

import com.brogames.bro.ImageGetter;

public class Coconut extends Item {

	public Coconut(int objectType) {
		super(objectType);
		
		bmp = ImageGetter.getImage(true, objectType, 0);
	}
}
