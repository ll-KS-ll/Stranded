package com.brogames.bro.objecttypes.objects;

import com.brogames.bro.ImageGetter;
import com.brogames.bro.objecttypes.ObjectType;
import android.graphics.Color;

public class Rubble extends Object{

	public Rubble(){
		objectType = ObjectType.RUBBLE;
		color.setColor(Color.GRAY);
		
		int rand = (int) Math.round(Math.random() * 2);	
		bmp = ImageGetter.getImage(false, objectType, rand);
	}

}
