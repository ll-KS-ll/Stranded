package com.brogames.bro.objecttypes.objects;

import com.brogames.bro.ImageGetter;
import com.brogames.bro.objecttypes.ObjectType;
import android.graphics.Color;

public class Forest1 extends Object{

	public Forest1(){
		objectType = ObjectType.FOREST1;
		color.setColor(Color.RED);
		bmp = ImageGetter.getImage(false, objectType, 0);
	}
	
}
