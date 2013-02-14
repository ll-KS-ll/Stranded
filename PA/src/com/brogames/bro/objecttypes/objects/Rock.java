package com.brogames.bro.objecttypes.objects;

import com.brogames.bro.ImageGetter;
import com.brogames.bro.objecttypes.ObjectType;
import android.graphics.Color;

public class Rock extends Object{

	public Rock(){
		objectType = ObjectType.ROCK;
		color.setColor(Color.DKGRAY);
		
		int rand = (int) Math.round(Math.random() * 2);
		//int rand2 = (int) Math.round(Math.random());
		bmp = ImageGetter.getImage(false, objectType, rand);
		isObstacle = true;
	}

}
