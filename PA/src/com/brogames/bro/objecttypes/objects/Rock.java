package com.brogames.bro.objecttypes.objects;

import android.graphics.Color;
import com.brogames.bro.ImageGetter;

public class Rock extends Environment{

	public Rock(int objectType){
		super(objectType);
		color.setColor(Color.DKGRAY);
		
		int rand = (int) Math.round(Math.random() * 2);
		//int rand2 = (int) Math.round(Math.random());
		bmp = ImageGetter.getImage(false, objectType, rand);
		isObstacle = true;
	}

}
