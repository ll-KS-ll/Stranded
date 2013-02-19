package com.brogames.bro.objecttypes.objects;

import com.brogames.bro.ImageGetter;
import com.brogames.bro.objecttypes.ObjectType;
import android.graphics.Color;

public class FirePlace extends Object{

	public FirePlace(){
		objectType = ObjectType.FIRE_PLACE;
		color.setColor(Color.RED);
		isObstacle = true;
		isAnimation = true;
		maxFrames = 8;
		bmpAnim = ImageGetter.getImageAnim(objectType, maxFrames);
		animSpeed = 30;
	}

}

