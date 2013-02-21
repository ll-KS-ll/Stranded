package com.brogames.bro.objecttypes.items;

public class Portal extends Item{

	public Portal(int objectType) {
		super(objectType);
		color.setARGB(150, 0, 0, 0);
		isPickup = false;
	}
	
}
