package com.brogames.bro.objecttypes.items;

import android.graphics.Color;

import com.brogames.bro.ImageGetter;
import com.brogames.bro.objecttypes.ObjectType;

public class TreeLog extends Item{
	
	public TreeLog() {
		objectType = ObjectType.LOG;
		color.setColor(Color.CYAN);

		bmp = ImageGetter.getImage(true, objectType, 0);
		recipe.add(ObjectType.FIRE_LOGS);
	}
}
