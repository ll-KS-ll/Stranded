package com.brogames.bro.objecttypes.objects;

import com.brogames.bro.ImageGetter;
import com.brogames.bro.objecttypes.ObjectType;
import android.graphics.Color;

public class BrownPlateau11 extends Object{

	public BrownPlateau11(){
		objectType = ObjectType.BROWN_PLATEAU11;
		color.setColor(Color.RED);
		isObstacle = true;
		bmp = ImageGetter.getImage(false, objectType, 0);
	}
	
}