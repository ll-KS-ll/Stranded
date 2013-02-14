package com.brogames.bro.objecttypes.items;

import com.brogames.bro.ImageGetter;
import com.brogames.bro.objecttypes.ObjectType;
import android.graphics.Color;

public class SmallStone extends Item{
	
	public SmallStone() {
		objectType = ObjectType.SMALL_STONE;
		color.setColor(Color.YELLOW);
		
		bmp = ImageGetter.getImage(true, objectType, 0);
		recipe.add(ObjectType.ARROW);
	}
}
