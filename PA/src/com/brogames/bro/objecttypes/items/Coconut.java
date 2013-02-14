package com.brogames.bro.objecttypes.items;

import android.graphics.Color;

import com.brogames.bro.ImageGetter;
import com.brogames.bro.objecttypes.ObjectType;

public class Coconut extends Item {

	public Coconut() {
		objectType = ObjectType.COCONUT;
		color.setColor(Color.CYAN);
		
		bmp = ImageGetter.getImage(true, objectType, 0);
	}
}
