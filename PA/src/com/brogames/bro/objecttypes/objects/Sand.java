package com.brogames.bro.objecttypes.objects;

import android.graphics.Color;
import com.brogames.bro.objecttypes.ObjectType;
import com.brogames.bro.ImageGetter;

public class Sand extends Object{

	
	public Sand(){
		objectType = ObjectType.SAND;
		color.setColor(Color.YELLOW);
		
		bmp = ImageGetter.getImage(false, objectType, 0);
	}

}
