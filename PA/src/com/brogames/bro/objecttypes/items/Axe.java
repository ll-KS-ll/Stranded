package com.brogames.bro.objecttypes.items;

import com.brogames.bro.ImageGetter;
import com.brogames.bro.objecttypes.ObjectType;
import com.brogames.bro.Interact;

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
	}
	
	public int[] getConsequences(int objectType){
		super.getConsequences(objectType); 
		switch(objectType){
		case ObjectType.PALM:
			consequences[0] = Interact.REMOVE_PALM;
			consequences[1] = ObjectType.LOG;
			consequences[2] = -1;
			consequences[3] = -1;
			break;
		}
		return consequences;
	}
	
}
