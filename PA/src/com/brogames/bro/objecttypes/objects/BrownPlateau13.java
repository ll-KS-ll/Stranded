package com.brogames.bro.objecttypes.objects;

import com.brogames.bro.ImageGetter;
import com.brogames.bro.objecttypes.ObjectType;
import android.graphics.Color;

public class BrownPlateau13 extends Object{

	public BrownPlateau13(){
		objectType = ObjectType.BROWN_PLATEAU13;
		color.setColor(Color.RED);
		isObstacle = true;
		bmp = ImageGetter.getImage(false, objectType, 0);
	}
	
}
