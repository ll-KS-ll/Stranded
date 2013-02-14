package com.brogames.bro.objecttypes.items;

import com.brogames.bro.ImageGetter;
import com.brogames.bro.objecttypes.ObjectType;

import android.graphics.Color;

public class WoodyVines extends Item {

	public WoodyVines() {
		objectType = ObjectType.WOODY_VINES;
		color.setColor(Color.RED);
		
		bmp = ImageGetter.getImage(true, objectType, 0);
		recipe.add(ObjectType.AXE);
		recipe.add(ObjectType.BOW);
	}

}
