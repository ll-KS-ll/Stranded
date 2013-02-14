package com.brogames.bro.objecttypes.items;

import com.brogames.bro.ImageGetter;
import com.brogames.bro.objecttypes.ObjectType;
import android.graphics.Color;

public class FishingRod extends Item{

	
	public FishingRod(){
		objectType = ObjectType.FISHING_ROD;
		color.setColor(Color.CYAN);
		
		bmp = ImageGetter.getImage(true, objectType, 0);
		
		components[0] = ObjectType.WOODY_VINES;
		components[1] = ObjectType.STICK;
		components[2] = ObjectType.SHARP_BONE;
		components[3] = -1;
		
		//conPalm = new int[2];
		//conPalm[0] = 2; // '1' removes object from map, '2' needed for removing objects with a height of two tiles.
		//conPalm[1] = 2;// '0' or smaller returns no item, other numbers returns the item corresponding to the number
	}
	
}

