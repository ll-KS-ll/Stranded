package com.brogames.bro.objecttypes.objects;

import com.brogames.bro.ImageGetter;
import android.graphics.Color;

public class Bush extends Environment{

	public Bush(int objectType){
		super(objectType);
		color.setColor(Color.BLUE);
		isObstacle = true;
		isAnimation = true;
		maxFrames = 8;
		bmpAnim = ImageGetter.getImageAnim(objectType, maxFrames);
		animSpeed = 20;
	}
	
}
