package com.brogames.bro.objecttypes.objects;

import com.brogames.bro.ImageGetter;
import android.graphics.Color;

public class Grass extends Environment{

	public Grass(int objectType){
		super(objectType);
		color.setColor(Color.GREEN);
		
		int rand = (int) Math.round(Math.random());
		bmp = ImageGetter.getImage(false, objectType, rand);
	}

}
