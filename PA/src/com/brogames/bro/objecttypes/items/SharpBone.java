package com.brogames.bro.objecttypes.items;

import com.brogames.bro.ImageGetter;
import com.brogames.bro.objecttypes.ObjectType;

public class SharpBone extends Item{
	
	public SharpBone(int objectType){
		super(objectType);
		bmp = ImageGetter.getImage(true, objectType, 0);
		recipe.add(ObjectType.FISHING_ROD);
	}
}
