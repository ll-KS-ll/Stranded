package com.brogames.bro.objecttypes.objects;

import com.brogames.bro.ImageGetter;
import com.brogames.bro.objecttypes.ObjectType;
import android.graphics.Color;

public class Forest10 extends Object{

	public Forest10(){
		objectType = ObjectType.FOREST10;
		color.setColor(Color.RED);
		isObstacle = true;
		bmp = ImageGetter.getImage(false, objectType, 0);
	}
	
}
