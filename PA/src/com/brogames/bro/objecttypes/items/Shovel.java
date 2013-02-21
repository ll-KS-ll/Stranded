package com.brogames.bro.objecttypes.items;

import com.brogames.bro.ImageGetter;

public class Shovel extends Item{

	public Shovel(int objectType) {
		super(objectType);
		bmp = ImageGetter.getImage(true, objectType, 0);
	}
}
