package com.brogames.bro.popups;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;

import com.brogames.bro.objecttypes.items.Item;
import com.core.ks.InputObject;
import com.core.ks.Popup;

public class PickupNotification extends Popup {
	
	private Paint paint = new Paint();
	private Paint paintToast = new Paint();
	private Paint paintText = new Paint();
	private Paint paintFrame = new Paint();
	private Paint imageObserver = new Paint();
	private int x, y;
	private long lastTimer;
	private Matrix matrix;
	private Bitmap bmp;
	private float scale;
	private RectF toast, itemBackground;
	private int timeVisible = 1500;
	
	public PickupNotification(Item item) {
		super();
		
		// Full transparency 
		color.setARGB(0, 0, 0, 0);
		
		// Text
		paintText.setAntiAlias(true);
		paintText.setTextSize(tileHeight/2.1f);
		// Background
		paintToast.setAntiAlias(true);
		paintToast.setARGB(255, 112, 61, 20);
		// Frames
		paintFrame.setAntiAlias(true);
		paintFrame.setColor(Color.BLACK);
		paintFrame.setStyle(Style.STROKE);
		// Item background
		paint.setAntiAlias(true);
		paint.setARGB(255, 250, 250, 250);
		// Bitmap observer
		imageObserver.setAntiAlias(true);
		imageObserver.setFilterBitmap(true);
		
		// Item image
		bmp = item.getBmp();
		
		// Image properties
		x = screenWidth/2 - tileWidth*3  + tileWidth/10;
		y = (int) (3*screenHeight/4 + (tileHeight*(scale+0.2F) - tileHeight*scale)/2);
		scale = 1.3F;
		
		// Scaling and translate
		matrix = new Matrix();
		matrix.postScale(scale, scale);
		matrix.postTranslate(x, y);
		
		// Boundaries
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
		// Background
		canvas.drawRoundRect(toast, tileWidth/10, tileHeight/10, paintToast);
		canvas.drawRoundRect(toast, tileWidth/10, tileHeight/10, paintFrame);
		// Image background
		canvas.drawRoundRect(itemBackground, tileWidth/10, tileHeight/10, paint);
		canvas.drawRoundRect(itemBackground, tileWidth/10, tileHeight/10, paintFrame);
		// Text
		canvas.drawText("You got a new item!", x + tileWidth*(scale+0.2F), y + tileHeight/1.2f, paintText);
		// Image
		canvas.drawBitmap(bmp, matrix, imageObserver);
	}
	
	public void processMotionEvent(InputObject input) {
		close();
	}

}
	

