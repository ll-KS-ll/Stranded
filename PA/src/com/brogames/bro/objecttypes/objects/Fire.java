package com.brogames.bro.objecttypes.objects;

import com.brogames.bro.ImageGetter;

public class Fire extends Environment{

	public Fire(int objectType){
		super(objectType);

		isAnimation = true;
		maxFrames = 8;
		bmpAnim = ImageGetter.getImageAnim(objectType, maxFrames);
		animSpeed = 30;
	}

}

