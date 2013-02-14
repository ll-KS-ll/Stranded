package com.brogames.bro.objecttypes.items;

import com.brogames.bro.ImageGetter;
import com.brogames.bro.objecttypes.ObjectType;
import android.graphics.Color;

public class Stick extends Item{

	public Stick(){
		objectType = ObjectType.STICK;
		color.setColor(Color.YELLOW);
		
		bmp = ImageGetter.getImage(true, objectType, 0);
		recipe.add(ObjectType.AXE);
		recipe.add(ObjectType.ARROW);
	}

}
