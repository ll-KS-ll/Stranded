package com.brogames.bro.objecttypes.objects;

import com.brogames.bro.ImageGetter;
import android.graphics.Color;

public class ForestPassage extends Environment{

	public ForestPassage(int objectType){
		super(objectType);
		color.setColor(Color.DKGRAY);
		
		bmp = ImageGetter.getImage(false, objectType, 0);
	}

}