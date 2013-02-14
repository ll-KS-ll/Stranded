package com.brogames.bro.objecttypes.items;

import com.brogames.bro.ImageGetter;
import com.brogames.bro.objecttypes.ObjectType;
import android.graphics.Color;

public class SharpBone extends Item{
	
	public SharpBone(){
		objectType = ObjectType.SHARP_BONE;
		color.setColor(Color.CYAN);
		
		bmp = ImageGetter.getImage(true, objectType, 0);
		recipe.add(ObjectType.FISHING_ROD);
	}
}
