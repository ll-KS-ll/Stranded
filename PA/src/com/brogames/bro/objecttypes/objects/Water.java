package com.brogames.bro.objecttypes.objects;

import android.graphics.Color;
import com.brogames.bro.ImageGetter;

public class Water extends Environment{

	public Water(int objectType){
		super(objectType);
		color.setColor(Color.BLUE);
		isObstacle = true;
		isAnimation = true;
		maxFrames = 4;
		bmpAnim = ImageGetter.getImageAnim(objectType, maxFrames);
		animSpeed = 30;
	}
}
