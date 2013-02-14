package com.brogames.bro.objecttypes.items;

import android.graphics.Color;
import android.graphics.Paint.Style;

import com.brogames.bro.objecttypes.ObjectType;

public class Portal extends Item{

	public Portal() {
		objectType = ObjectType.PORTAL;
		color.setColor(Color.CYAN);
		color.setStyle(Style.STROKE);
		isPickup = false;
	}
	
}
