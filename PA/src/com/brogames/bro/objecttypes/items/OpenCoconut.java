package com.brogames.bro.objecttypes.items;

import com.brogames.bro.ImageGetter;

public class OpenCoconut extends Item {

	public OpenCoconut(int objectType) {
		super(objectType);
		bmp = ImageGetter.getImage(true, objectType, 0);
	}
}
