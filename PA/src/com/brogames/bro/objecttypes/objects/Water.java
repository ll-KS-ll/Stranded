package com.brogames.bro.objecttypes.objects;

import com.brogames.bro.ImageGetter;
import com.brogames.bro.objecttypes.ObjectType;
import android.graphics.Color;

public class Water extends Object{

	public Water(){
		objectType = ObjectType.WATER;
		color.setColor(Color.BLUE);
		isObstacle = true;
		isAnimation = true;
		maxFrames = 4;
		bmpAnim = ImageGetter.getImageAnim(objectType, maxFrames);
		animSpeed = 30;
	}
}
