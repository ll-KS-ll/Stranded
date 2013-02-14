package com.brogames.bro.objecttypes.items;

import com.brogames.bro.ImageGetter;
import com.brogames.bro.objecttypes.ObjectType;
import android.graphics.Color;

public class Map extends Item{

	
	public Map(){
		objectType = ObjectType.MAP;
		color.setColor(Color.BLUE);
		
		bmp = ImageGetter.getImage(true, objectType, 0);
	}
	
}

