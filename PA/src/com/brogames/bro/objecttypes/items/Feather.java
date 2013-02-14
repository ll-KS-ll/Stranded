package com.brogames.bro.objecttypes.items;

import android.graphics.Color;

import com.brogames.bro.ImageGetter;
import com.brogames.bro.objecttypes.ObjectType;

public class Feather extends Item {

	public Feather() {
		objectType = ObjectType.FEATHER;
		color.setColor(Color.CYAN);
		
		bmp = ImageGetter.getImage(true, objectType, 0);
		recipe.add(ObjectType.ARROW);
	}
	
}
