package com.brogames.bro.objecttypes.objects;

import com.brogames.bro.ImageGetter;

public class Undefined extends Environment{

	public Undefined(int objectType){
		super(objectType);
		bmp = ImageGetter.getImage(false, objectType, 0);
		isObstacle = true;
	}

}
