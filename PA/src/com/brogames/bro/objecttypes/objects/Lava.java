package com.brogames.bro.objecttypes.objects;

import android.graphics.Color;
import com.brogames.bro.ImageGetter;

public class Lava extends Environment{

	public Lava(int objectType){
		super(objectType);
		color.setColor(Color.RED);
		isObstacle = true;
		isAnimation = true;
		maxFrames = 4;
		bmpAnim = ImageGetter.getImageAnim(objectType, maxFrames);
		animSpeed = 30;
	}
}
