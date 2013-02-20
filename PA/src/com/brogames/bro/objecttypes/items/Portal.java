package com.brogames.bro.objecttypes.items;

import com.brogames.bro.objecttypes.ObjectType;

public class Portal extends Item{

	public Portal() {
		objectType = ObjectType.PORTAL;
		color.setARGB(150, 0, 0, 0);
		isPickup = false;
	}
	
}
