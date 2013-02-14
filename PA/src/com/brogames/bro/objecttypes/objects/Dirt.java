package com.brogames.bro.objecttypes.objects;

import com.brogames.bro.ImageGetter;
import com.brogames.bro.objecttypes.ObjectType;
import android.graphics.Color;

public class Dirt extends Object{

	public Dirt(){
		objectType = ObjectType.DIRT;
		color.setColor(Color.MAGENTA);
		
		int rand = (int) Math.round(Math.random() * 2);
		bmp = ImageGetter.getImage(false, objectType, rand);
	}

}
