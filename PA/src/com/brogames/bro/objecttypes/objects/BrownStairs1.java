package com.brogames.bro.objecttypes.objects;

import com.brogames.bro.ImageGetter;
import com.brogames.bro.objecttypes.ObjectType;
import android.graphics.Color;

public class BrownStairs1 extends Object{

	public BrownStairs1(){
		objectType = ObjectType.BROWN_STAIRS1;
		color.setColor(Color.GREEN);
		
		bmp = ImageGetter.getImage(false, objectType, 0);
	}

}
