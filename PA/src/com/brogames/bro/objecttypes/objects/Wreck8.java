package com.brogames.bro.objecttypes.objects;

import com.brogames.bro.ImageGetter;
import com.brogames.bro.objecttypes.ObjectType;
import android.graphics.Color;

public class Wreck8 extends Object{

	public Wreck8(){
		objectType = ObjectType.WRECK8;
		color.setColor(Color.RED);
		bmp = ImageGetter.getImage(false, objectType, 0);
	}
	
}
