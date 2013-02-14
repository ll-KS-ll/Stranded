package com.brogames.bro.objecttypes.objects;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;

public class Object {

	protected Paint color = new Paint();
	protected Bitmap bmp = null;
	protected Bitmap[] bmpAnim = null;
	protected boolean isAnimation = false;
	protected boolean isObstacle = false;
	protected int objectType = -1;
	protected int animFrame = 0, frames = 0, animSpeed = 10, maxFrames;
	
	/* 	COLOR - always set a color for the object
	 * 		"color.setColor(Color.'wanted color');"
	 *
	
	 *	GRAPHICS - if object has an image then set: 
	 * 		"bmp = 'bitmap image of object';"
	 *
	
	 *	ANIMATION - if object is an animation then set:
	 * 		"isAnimation = true;"
	 *  	"animSpeed = 'wanted speed';" only needed if other animation speed is needed 
	 *  	"maxFrames = 'number of frames in animation';"
	 *  	"bmpAnim = 'array of bitmaps with animation frames';"
	 *
	
	 *	OBSTACLE - if object is an obstacle then set:
	 * 		"isObstacle = true;"
	 * 
	 * 
	 
	 *	ITEM - if object is an item then set:
	 *		"isItem = true;"
	 
	 */
	
	public Object(){
		color.setColor(Color.MAGENTA);
		maxFrames = 3;
	}
	
	public Paint getColor(){
		return color;
	}
	
	public Bitmap getBmp(){
		if(isAnimation){
			frames++;
			if(frames >= animSpeed){
				animFrame++;
				frames = 0;
			}
			if(animFrame >= maxFrames)
				animFrame = 0;
			return bmpAnim[animFrame];
		}else
			return bmp;
	}
	
	public boolean isObstacle(){
		return isObstacle;
	}
	
	public int getObjectType(){
		return objectType;
	}
}
