package com.brogames.bro.objecttypes.objects;

import com.brogames.bro.objecttypes.ObjectType;
import android.graphics.Color;

public class LogPile extends Object{

	public LogPile(){
		objectType = ObjectType.LOG_PILE;
		color.setColor(Color.GRAY);
		isObstacle = true;
		//bmpAnim = ImageGetter.getImageAnim(objectType, maxFrames); // No image yet :(
	}

}