package com.brogames.bro.objecttypes.objects;

import com.brogames.bro.ImageGetter;
import com.brogames.bro.objecttypes.ObjectType;
import android.graphics.Color;

public class Palm extends Object{

	public Palm(){
		objectType = ObjectType.PALM;
		color.setColor(Color.RED);
		isObstacle = true;
		isAnimation = true;
		maxFrames = 2;
		bmpAnim = ImageGetter.getImageAnim(objectType, maxFrames);
		animSpeed = 20;
	}

}

