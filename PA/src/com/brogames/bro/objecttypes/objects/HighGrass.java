package com.brogames.bro.objecttypes.objects;

import android.graphics.Color;
import com.brogames.bro.ImageGetter;

public class HighGrass extends Environment{

	public HighGrass(int objectType){
		super(objectType);
		color.setColor(Color.GREEN);
	
		bmp = ImageGetter.getImage(false, objectType, 0);
	}

}
