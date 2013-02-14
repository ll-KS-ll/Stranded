package com.brogames.bro.objecttypes.objects;

import com.brogames.bro.ImageGetter;
import com.brogames.bro.objecttypes.ObjectType;
import android.graphics.Color;

public class ForestPassageTop extends Object{

	public ForestPassageTop(){
		objectType = ObjectType.FOREST_PASSAGE_TOP;
		color.setColor(Color.DKGRAY);
		
		bmp = ImageGetter.getImage(false, objectType, 0);
		isObstacle = true;
	}

}
