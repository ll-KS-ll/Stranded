package com.brogames.bro.objecttypes.objects;

import com.brogames.bro.ImageGetter;

public class Undefined extends Object{

	public Undefined(int objectType){
		this.objectType = objectType;
		bmp = ImageGetter.getImage(false, objectType, 0);
		isObstacle = true;
	}

}
