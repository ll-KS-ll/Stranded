package com.brogames.bro.objecttypes.objects;

import com.brogames.bro.ImageGetter;
import com.brogames.bro.objecttypes.ObjectType;
import android.graphics.Color;

public class Wreck7 extends Object{

	public Wreck7(){
		objectType = ObjectType.WRECK7;
		color.setColor(Color.RED);
		bmp = ImageGetter.getImage(false, objectType, 0);
	}
	
}
