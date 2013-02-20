package com.brogames.bro.objecttypes.items;

import com.brogames.bro.ImageGetter;

public class Tinder extends Item{

	
	public Tinder(int objectType){
		super(objectType);
		
		bmp = ImageGetter.getImage(true, objectType, 0);
	}
	
}