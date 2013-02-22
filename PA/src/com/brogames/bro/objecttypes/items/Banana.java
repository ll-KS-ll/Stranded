package com.brogames.bro.objecttypes.items;

import com.brogames.bro.ImageGetter;

public class Banana extends Item{

	public Banana(int objectType) {
		super(objectType);

		bmp = ImageGetter.getImage(true, objectType, 0);
		category = Item.FOOD;
		consumeValue = 1000;
	}
}
