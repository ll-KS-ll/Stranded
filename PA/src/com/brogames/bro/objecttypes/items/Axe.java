package com.brogames.bro.objecttypes.items;

import com.brogames.bro.ImageGetter;
import com.brogames.bro.objecttypes.ObjectType;
import android.graphics.Color;

public class Axe extends Item{

	
	public Axe(int objectType){
		super(objectType);
		color.setColor(Color.BLUE);
		
		bmp = ImageGetter.getImage(true, objectType, 0);
		
		components[0] = ObjectType.STONE;
		components[1] = ObjectType.STICK;
		components[2] = ObjectType.WOODY_VINES;
		components[3] = -1;
		
		conPalm = new int[2];
		conPalm[0] = 2; // '1' removes object from map, '2' needed for removing objects with a height of two tiles.
		conPalm[1] = ObjectType.LOG;
	}
	
}
