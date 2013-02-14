package com.brogames.bro.objecttypes.items;

import android.graphics.Color;

import com.brogames.bro.ImageGetter;
import com.brogames.bro.objecttypes.ObjectType;

public class Pot extends Item{
	
	public Pot() {
		objectType = ObjectType.POT;
		color.setColor(Color.CYAN);
		
		bmp = ImageGetter.getImage(true, objectType, 0);
		
		components[0] = ObjectType.CLAY;
		components[1] = ObjectType.BOW_DRILL;
		components[2] = -1;
		components[3] = -1;
		
	}
}
