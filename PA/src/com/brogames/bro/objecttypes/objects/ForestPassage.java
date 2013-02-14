package com.brogames.bro.objecttypes.objects;

import com.brogames.bro.ImageGetter;
import com.brogames.bro.objecttypes.ObjectType;
import android.graphics.Color;

public class ForestPassage extends Object{

	public ForestPassage(){
		objectType = ObjectType.FOREST_PASSAGE;
		color.setColor(Color.DKGRAY);
		
		bmp = ImageGetter.getImage(false, objectType, 0);
	}

}