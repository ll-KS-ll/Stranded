package com.brogames.bro.objecttypes.objects;

import android.graphics.Color;
import com.brogames.bro.ImageGetter;

public class Palm extends Environment{

	public Palm(int objectType){
		super(objectType);
		color.setColor(Color.RED);
		isObstacle = true;
		isAnimation = true;
		maxFrames = 2;
		bmpAnim = ImageGetter.getImageAnim(objectType, maxFrames);
		animSpeed = 20;
	}

}

