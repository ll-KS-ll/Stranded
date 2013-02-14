package com.brogames.bro.objecttypes.objects;

import com.brogames.bro.ImageGetter;
import com.brogames.bro.objecttypes.ObjectType;
import android.graphics.Color;

public class BrownStairs2 extends Object{

	public BrownStairs2(){
		objectType = ObjectType.BROWN_STAIRS2;
		color.setColor(Color.GREEN);
		
		bmp = ImageGetter.getImage(false, objectType, 0);
	}

}
