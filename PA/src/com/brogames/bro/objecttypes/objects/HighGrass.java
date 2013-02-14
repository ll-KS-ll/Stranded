package com.brogames.bro.objecttypes.objects;

import com.brogames.bro.ImageGetter;
import com.brogames.bro.objecttypes.ObjectType;
import android.graphics.Color;

public class HighGrass extends Object{

	public HighGrass(){
		objectType = ObjectType.HIGH_GRASS;
		color.setColor(Color.GREEN);
	
		bmp = ImageGetter.getImage(false, objectType, 0);
	}

}
