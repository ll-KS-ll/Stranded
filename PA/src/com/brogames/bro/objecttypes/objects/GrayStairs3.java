package com.brogames.bro.objecttypes.objects;

import com.brogames.bro.ImageGetter;
import com.brogames.bro.objecttypes.ObjectType;
import android.graphics.Color;

public class GrayStairs3 extends Object{

	public GrayStairs3(){
		objectType = ObjectType.GRAY_STAIRS3;
		color.setColor(Color.GREEN);
		
		bmp = ImageGetter.getImage(false, objectType, 0);
	}

}
