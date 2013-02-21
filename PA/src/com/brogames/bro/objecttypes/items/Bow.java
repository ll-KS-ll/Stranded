package com.brogames.bro.objecttypes.items;

import com.brogames.bro.ImageGetter;
import com.brogames.bro.objecttypes.ObjectType;
import android.graphics.Color;

public class Bow extends Item{

	
	public Bow(int objectType){
		super(objectType);
		color.setColor(Color.BLUE);
		bmp = ImageGetter.getImage(true, objectType, 0);
		
		components[0] = ObjectType.WOODY_VINES;
		components[1] = ObjectType.CURVED_STICK;
		components[2] = -1;
		components[3] = -1;
		
		recipe.add(ObjectType.BOW_DRILL);
	}
	
}
