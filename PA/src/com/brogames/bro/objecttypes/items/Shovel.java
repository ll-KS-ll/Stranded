package com.brogames.bro.objecttypes.items;

import android.graphics.Color;

import com.brogames.bro.ImageGetter;
import com.brogames.bro.objecttypes.ObjectType;

public class Shovel extends Item{

	public Shovel() {
		objectType = ObjectType.SHOVEL;
		color.setColor(Color.CYAN);
		
		bmp = ImageGetter.getImage(true, objectType, 0);
	}
}
