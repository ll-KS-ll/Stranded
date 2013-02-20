package com.brogames.bro.objecttypes.items;

import com.brogames.bro.ImageGetter;

public class Map extends Item{

	
	public Map(int objectType){
		super(objectType);
		bmp = ImageGetter.getImage(true, objectType, 0);
	}
	
}

