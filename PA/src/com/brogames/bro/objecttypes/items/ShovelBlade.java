package com.brogames.bro.objecttypes.items;

import com.brogames.bro.ImageGetter;

public class ShovelBlade extends Item{

	public ShovelBlade(int objectType) {
		super(objectType);
		bmp = ImageGetter.getImage(true, objectType, 0);
	}
}
