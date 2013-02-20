package com.brogames.bro.objecttypes.items;

import com.brogames.bro.ImageGetter;
import com.brogames.bro.objecttypes.ObjectType;
import com.brogames.bro.Interact;

import android.graphics.Color;

public class Axe extends Item{
	
	public Axe(){
		objectType = ObjectType.AXE;
		color.setColor(Color.BLUE);
		
		bmp = ImageGetter.getImage(true, objectType, 0);
		
		components[0] = ObjectType.STONE;
		components[1] = ObjectType.STICK;
		components[2] = ObjectType.WOODY_VINES;
		components[3] = -1;
		
		/* Consequences handling list:
		 * 
		 * 1. What to do?
		 * 2. What to return?
		 * 3. What to add?
		 * 4. Take item?
		 * 
		 */
		interectableObjects.add(ObjectType.PALM);
		consequens[0] = Interact.REMOVE_MULTIPLE;
		consequens[1] = ObjectType.LOG;
		consequens[2] = -1;
		consequens[3] = -1;
	}
	
}
