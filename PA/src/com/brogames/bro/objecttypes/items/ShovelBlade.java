package com.brogames.bro.objecttypes.items;

import android.graphics.Color;

import com.brogames.bro.ImageGetter;
import com.brogames.bro.objecttypes.ObjectType;

public class ShovelBlade extends Item{

	public ShovelBlade() {
		objectType = ObjectType.SHOVEL_BLADE;
		color.setColor(Color.CYAN);
		
		bmp = ImageGetter.getImage(true, objectType, 0);
	}
}
