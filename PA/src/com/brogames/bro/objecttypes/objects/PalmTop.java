package com.brogames.bro.objecttypes.objects;

import com.brogames.bro.ImageGetter;
import com.brogames.bro.objecttypes.ObjectType;
import android.graphics.Color;

public class PalmTop extends Object{

	public PalmTop(){
		objectType = ObjectType.PALM_TOP;
		color.setColor(Color.RED);
		isAnimation = true;
		maxFrames = 2;
		bmpAnim = ImageGetter.getImageAnim(objectType, maxFrames);
		animSpeed = 20;
	}
	
}
