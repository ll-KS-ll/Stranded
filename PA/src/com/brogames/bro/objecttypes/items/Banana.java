package com.brogames.bro.objecttypes.items;

import android.graphics.Color;
import com.brogames.bro.ImageGetter;

public class Banana extends Item{

	public Banana(int objectType) {
		super(objectType);
		color.setColor(Color.CYAN);

		bmp = ImageGetter.getImage(true, objectType, 0);
	}
}
