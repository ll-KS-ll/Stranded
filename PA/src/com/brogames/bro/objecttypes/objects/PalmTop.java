package com.brogames.bro.objecttypes.objects;

import android.graphics.Color;
import com.brogames.bro.ImageGetter;

public class PalmTop extends Environment{

	public PalmTop(int objectType){
		super(objectType);
		color.setColor(Color.RED);
		isAnimation = true;
		maxFrames = 2;
		bmpAnim = ImageGetter.getImageAnim(objectType, maxFrames);
		animSpeed = 20;
	}
	
}
