package com.brogames.bro.objecttypes.items;

import android.graphics.Color;

import com.brogames.bro.ImageGetter;
import com.brogames.bro.objecttypes.ObjectType;

public class Banana extends Item{

	public Banana() {
		objectType = ObjectType.BANANA;
		color.setColor(Color.CYAN);

		bmp = ImageGetter.getImage(true, objectType, 0);
	}
}
