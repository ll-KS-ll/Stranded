package com.brogames.bro.objecttypes.objects;

import com.brogames.bro.ImageGetter;
import com.brogames.bro.objecttypes.ObjectType;
import android.graphics.Color;

public class Wreck12 extends Object{

	public Wreck12(){
		objectType = ObjectType.WRECK12;
		color.setColor(Color.RED);
		bmp = ImageGetter.getImage(false, objectType, 0);
	}
	
}
