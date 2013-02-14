package com.brogames.bro.objecttypes.items;

import com.brogames.bro.ImageGetter;
import com.brogames.bro.objecttypes.ObjectType;

import android.graphics.Color;

public class Stone extends Item {

	public Stone() {
		objectType = ObjectType.STONE;
		color.setColor(Color.GRAY);
		
		bmp = ImageGetter.getImage(true, objectType, 0);
		recipe.add(ObjectType.AXE);
	}

}
