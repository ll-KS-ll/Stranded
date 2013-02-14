package com.brogames.bro.objecttypes.objects;

import com.brogames.bro.ImageGetter;
import com.brogames.bro.objecttypes.ObjectType;
import android.graphics.Color;

public class Grass extends Object{

	public Grass(){
		objectType = ObjectType.GRASS;
		color.setColor(Color.GREEN);
		
		int rand = (int) Math.round(Math.random());
		bmp = ImageGetter.getImage(false, objectType, rand);
	}

}
