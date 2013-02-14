package com.brogames.bro.objecttypes.objects;

import com.brogames.bro.ImageGetter;
import com.brogames.bro.objecttypes.ObjectType;
import android.graphics.Color;

public class BrownStairs6 extends Object{

	public BrownStairs6(){
		objectType = ObjectType.BROWN_STAIRS6;
		color.setColor(Color.GREEN);
		
		bmp = ImageGetter.getImage(false, objectType, 0);
	}

}
