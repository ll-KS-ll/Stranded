package com.brogames.bro.objecttypes.items;

import com.brogames.bro.ImageGetter;
import com.brogames.bro.objecttypes.ObjectType;
import android.graphics.Color;

public class Tinder extends Item{

	
	public Tinder(){
		objectType = ObjectType.TINDER;
		color.setColor(Color.BLUE);
		
		bmp = ImageGetter.getImage(true, objectType, 0);
		
		recipe.add(ObjectType.FIRE_LOGS);
	}
	
}