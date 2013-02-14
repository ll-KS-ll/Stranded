package com.brogames.bro.objecttypes.objects;

import com.brogames.bro.ImageGetter;
import com.brogames.bro.objecttypes.ObjectType;
import android.graphics.Color;

public class Lava extends Object{

	public Lava(){
		objectType = ObjectType.LAVA;
		color.setColor(Color.RED);
		isObstacle = true;
		isAnimation = true;
		maxFrames = 4;
		bmpAnim = ImageGetter.getImageAnim(objectType, maxFrames);
		animSpeed = 30;
	}
}
