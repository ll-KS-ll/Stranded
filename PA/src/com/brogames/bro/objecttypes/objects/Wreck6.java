package com.brogames.bro.objecttypes.objects;

import com.brogames.bro.ImageGetter;
import com.brogames.bro.objecttypes.ObjectType;
import android.graphics.Color;

public class Wreck6 extends Object{

	public Wreck6(){
		objectType = ObjectType.WRECK6;
		color.setColor(Color.RED);
		isObstacle = true;
		bmp = ImageGetter.getImage(false, objectType, 0);
	}
	
}
