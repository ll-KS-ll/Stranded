package com.brogames.bro.objecttypes.objects;

import com.brogames.bro.ImageGetter;

public class GrayStairs extends Object{

	public GrayStairs(int objectType){
		this.objectType = objectType;
		bmp = ImageGetter.getImage(false, objectType, 0);
		isObstacle = false;
	}

}
