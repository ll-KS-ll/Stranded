package com.brogames.bro.objecttypes.objects;

import android.graphics.Color;
import com.brogames.bro.ImageGetter;

public class ForestPassageTop extends Environment{

	public ForestPassageTop(int objectType){
		super(objectType);
		color.setColor(Color.DKGRAY);
		
		bmp = ImageGetter.getImage(false, objectType, 0);
		isObstacle = true;
	}

}
