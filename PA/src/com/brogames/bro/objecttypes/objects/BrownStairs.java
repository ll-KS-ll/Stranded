package com.brogames.bro.objecttypes.objects;

import com.brogames.bro.ImageGetter;

public class BrownStairs extends Environment{

	public BrownStairs(int objectType){
		super(objectType);
		bmp = ImageGetter.getImage(false, objectType, 0);
		isObstacle = false;
	}

}
