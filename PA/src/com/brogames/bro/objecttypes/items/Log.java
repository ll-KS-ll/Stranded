package com.brogames.bro.objecttypes.items;

import android.graphics.Color;

import com.brogames.bro.ImageGetter;
import com.brogames.bro.objecttypes.ObjectType;

public class Log extends Item{
	
	public Log() {
		objectType = ObjectType.LOG;
		color.setColor(Color.CYAN);

		bmp = ImageGetter.getImage(true, objectType, 0);
		recipe.add(ObjectType.BOW_DRILL);
	}
}
