package com.brogames.bro.objecttypes.items;

import com.brogames.bro.ImageGetter;
import com.brogames.bro.objecttypes.ObjectType;

public class Stick extends Item{

	public Stick(int objectType){
		super(objectType);
		
		bmp = ImageGetter.getImage(true, objectType, 0);
		recipe.add(ObjectType.AXE);
		recipe.add(ObjectType.ARROW);
		recipe.add(ObjectType.BOW_DRILL);
	}

}
