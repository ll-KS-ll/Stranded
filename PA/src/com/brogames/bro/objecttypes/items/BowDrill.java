package com.brogames.bro.objecttypes.items;

import android.graphics.Color;

import com.brogames.bro.ImageGetter;
import com.brogames.bro.objecttypes.ObjectType;
import com.brogames.bro.Interact;

public class BowDrill extends Item {

	public BowDrill() {
		objectType = ObjectType.BOW_DRILL;
		color.setColor(Color.CYAN);

		bmp = ImageGetter.getImage(true, objectType, 0);

		components[0] = ObjectType.BOW;
		components[1] = ObjectType.STICK;

		/* Consequences handling list:
		 * 
		 * 1. What to do?
		 * 2. What to return?
		 * 4. What to add?
		 * 
		 */
		interectableObjects.add(ObjectType.LOG_PILE);
		consequens[0] = Interact.REPLACE_OBJECT;
		consequens[1] = -1;
		consequens[2] = ObjectType.FIRE_PLACE;
		consequens[3] = -1;
		
	}
}
