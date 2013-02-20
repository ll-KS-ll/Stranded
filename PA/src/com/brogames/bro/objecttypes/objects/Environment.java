package com.brogames.bro.objecttypes.objects;

import com.brogames.bro.objecttypes.TileObject;

public class Environment extends TileObject{

	public Environment(int objectType) {
		super(objectType);
		isItem = false;
		isObstacle = true;
	}

	
}
