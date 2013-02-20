package com.brogames.bro.objecttypes.objects;

import android.graphics.Color;
import com.brogames.bro.ImageGetter;

public class Sand extends Environment{

	public Sand(int objectType){
		super(objectType);
		color.setColor(Color.YELLOW);
		
		bmp = ImageGetter.getImage(false, objectType, 0);
	}

}
