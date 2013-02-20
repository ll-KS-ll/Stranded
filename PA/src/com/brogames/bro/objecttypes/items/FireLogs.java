package com.brogames.bro.objecttypes.items;

import android.graphics.Color;
import com.brogames.bro.ImageGetter;
import com.brogames.bro.objecttypes.ObjectType;
import com.brogames.bro.Interact;

public class FireLogs extends Item{
	
	public FireLogs(){
		objectType = ObjectType.FIRE_LOGS;
		color.setColor(Color.BLUE);
		
		//bmp = ImageGetter.getImage(true, objectType, 0); // No image yet
		
		components[0] = ObjectType.LOG;
		components[1] = ObjectType.TINDER;
		components[2] = -1;
		components[3] = -1;
		
		/* Consequences handling list:
		 * 
		 * 1. What to do?
		 * 2. What to return?
		 * 3. What to add?
		 * 4. Take item?
		 * 
		 */
		
		interectableObjects.add(ObjectType.GRASS);
		
		consequens[0] = Interact.ADD_OBJECT;
		consequens[1] = -1;
		consequens[2] = ObjectType.LOG_PILE;
		consequens[3] = 1;
	}
	
}
