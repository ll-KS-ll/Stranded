package com.brogames.bro.popups;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;

import com.brogames.bro.objecttypes.items.Item;
import com.core.ks.InputObject;
import com.core.ks.Popup;

public class PickupAnimation extends Popup {
	
	private Paint paint = new Paint();
	private int x, y;
	private int boardWidth, boardHeight, screenWidth, screenHeight;
	private long lastTimer;
	private Matrix matrix;
	private Bitmap bmp;
	
	public PickupAnimation(Item item, Bundle sizes) {
		super(); // Call super or die!
		
		boardWidth = sizes.getInt("boardWidth");
		boardHeight = sizes.getInt("boardHeight");
		screenWidth = sizes.getInt("screenWidth");
		screenHeight = sizes.getInt("screenHeight");
		
		matrix = new Matrix();
		float scale = 1.3F;
		matrix.setScale(scale, scale);
		
		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		
		color.setARGB(0, 0, 0, 0);
		
		if(item.getBmp() != null)
			bmp = Bitmap.createBitmap(item.getBmp(), 0, 0, boardWidth, boardHeight, matrix, true);
		
		x = screenWidth/2 - boardWidth/4;
		y = screenHeight/2 - boardHeight/4;
		matrix.setTranslate(x, y);
		
		lastTimer = System.currentTimeMillis() + 300;
	}

	public void tick() {
	
		if (System.currentTimeMillis() - lastTimer > 10) {
			lastTimer += 10;
			if (x < screenWidth-boardWidth && y < screenHeight-boardWidth ){
				x+= (screenWidth/2-boardWidth)/4;
				y += (screenHeight/2-boardHeight)/4;
				matrix.setTranslate(x, y);
			}else
				close();
		}

	}

	public void render(Canvas canvas) {
		super.render(canvas); // It's proper manor to always call super.render
		if(bmp != null)
			canvas.drawBitmap(bmp, matrix, null);
	}
	
	public void processMotionEvent(InputObject input) {
		
	}

}
	

