package com.brogames.bro.objecttypes.objects;

import com.brogames.bro.ImageGetter;

public class BrownStairs extends Object{

	public BrownStairs(int objectType){
		this.objectType = objectType;
		bmp = ImageGetter.getImage(false, objectType, 0);
		isObstacle = false;
	}

}
