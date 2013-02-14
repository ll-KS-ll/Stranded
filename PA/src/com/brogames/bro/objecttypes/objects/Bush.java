package com.brogames.bro.objecttypes.objects;

import com.brogames.bro.ImageGetter;
import com.brogames.bro.objecttypes.ObjectType;
import android.graphics.Color;

public class Bush extends Object{

	public Bush(){
		objectType = ObjectType.BUSH;
		color.setColor(Color.BLUE);
		isObstacle = true;
		isAnimation = true;
		maxFrames = 8;
		bmpAnim = ImageGetter.getImageAnim(objectType, maxFrames);
		animSpeed = 20;
	}
	
}
