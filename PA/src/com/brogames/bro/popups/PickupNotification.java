package com.brogames.bro.popups;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;

import com.brogames.bro.objecttypes.items.Item;
import com.core.ks.InputObject;
import com.core.ks.Popup;

public class PickupNotification extends Popup {
	
	private Paint paint = new Paint();
	private Paint paintToast = new Paint();
	private Paint paintText = new Paint();
	private int x, y;
	private int tileWidth, tileHeight, screenWidth, screenHeight;
	private long lastTimer;
	private Matrix matrix;
	private Bitmap bmp;
	private float scale;
	private RectF toast, itemBackground;
	private int timeVisible = 1500;
	
	public PickupNotification(Item item, Bundle sizes) {
		super(); // Call super or die!
		
		tileWidth = sizes.getInt("boardWidth");
		tileHeight = sizes.getInt("boardHeight");
		screenWidth = sizes.getInt("screenWidth");
		screenHeight = sizes.getInt("screenHeight");
		
		paintText.setTextSize(30);
		
		paint.setColor(Color.WHITE);
		
		paintToast.setARGB(255, 80, 41, 9);
		
		matrix = new Matrix();
		scale = 1.3F;
		matrix.setScale(scale, scale);
		
		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		
		color.setARGB(0, 0, 0, 0);
		
		Log.d("Item", "item" + item);
		bmp = Bitmap.createBitmap(item.getBmp(), 0, 0, tileWidth, tileHeight, matrix, true);
		
		x = screenWidth/2 - tileWidth*3  + tileWidth/10;
		y = (int) (3*screenHeight/4 + (tileHeight*(scale+0.2F) - tileHeight*scale)/2);
		matrix.setTranslate(x, y);
		
		toast = new RectF(screenWidth/2 - tileWidth*3, 3*screenHeight/4, screenWidth/2 + tileWidth*3, 3*screenHeight/4 + tileHeight*(scale+0.2F));
		itemBackground = new RectF(x, y, x + tileWidth*scale, y + tileHeight*scale);
		
		lastTimer = System.currentTimeMillis();
	}

	public void tick() {
		
		if (System.currentTimeMillis() - lastTimer > timeVisible)
				close();
	}

	public void render(Canvas canvas) {
		super.render(canvas);
		canvas.drawRoundRect(toast, tileWidth/10, tileHeight/10, paintToast);
		canvas.drawRoundRect(itemBackground, tileWidth/10, tileHeight/10, paint);
		canvas.drawText("You got a new item!", x + tileWidth*(scale+0.2F), y + tileHeight, paintText);
		canvas.drawBitmap(bmp, matrix, null);
	}
	
	public void processMotionEvent(InputObject input) {
		close();
	}

}
	

