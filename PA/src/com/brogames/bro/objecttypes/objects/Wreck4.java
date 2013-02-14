package com.brogames.bro.objecttypes.objects;

import com.brogames.bro.ImageGetter;
import com.brogames.bro.objecttypes.ObjectType;
import android.graphics.Color;

public class Wreck4 extends Object{

	public Wreck4(){
		objectType = ObjectType.WRECK4;
		color.setColor(Color.RED);
		bmp = ImageGetter.getImage(false, objectType, 0);
	}
	
}
