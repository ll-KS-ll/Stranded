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
	
	private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private Paint paintToast = new Paint(Paint.ANTI_ALIAS_FLAG);
	private Paint paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
	private Paint paintFrame = new Paint(Paint.ANTI_ALIAS_FLAG);
	private Paint imageObserver = new Paint(Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG);
	private int imgX, imgY;
	private long lastTimer;
	private Matrix matrix;
	private Bitmap bmp;
	private static final float scale = 1.3f;
	private RectF toast, itemBackground;
	private int timeVisible = 1500;
	
	public PickupNotification(Item item) {
		super();
		
		// Full transparency 
		setFullTransparency(true);
		
		// Text
		paintText.setTextSize(tileHeight/2.1f);
		// Background
		paintToast.setARGB(255, 112, 61, 20);
		// Frames
		paintFrame.setColor(Color.BLACK);
		paintFrame.setStyle(Style.STROKE);
		// Item background
		paint.setARGB(255, 250, 250, 250);
		
		// Item image
		bmp = item.getBmp();

		setSize(tileWidth*6, (int)(tileHeight*scale)+2*tileHeight/8);
		setLocation(x, screenHeight-tileHeight*4+(int)(tileHeight*scale));
		
		// Image properties
		imgX = tileWidth/8;
		imgY = tileHeight/8;
		
		// Scaling and translate
		matrix = new Matrix();
		matrix.postScale(scale, scale);
		matrix.postTranslate(imgX, imgY);
		
		// Boundaries
		toast = new RectF(0, 0, width, height);
		itemBackground = new RectF(imgX, imgY, imgX + tileWidth*scale, imgY + tileHeight*scale);
		
		lastTimer = System.currentTimeMillis();
	}

	public void tick() {
		if (System.currentTimeMillis() - lastTimer > timeVisible)
			close();
	}

	public void draw(Canvas canvas) {
		// Background
		canvas.drawRoundRect(toast, tileWidth/10, tileHeight/10, paintToast);
		canvas.drawRoundRect(toast, tileWidth/10, tileHeight/10, paintFrame);
		// Image background
		canvas.drawRoundRect(itemBackground, tileWidth/10, tileHeight/10, paint);
		canvas.drawRoundRect(itemBackground, tileWidth/10, tileHeight/10, paintFrame);
		// Text
		canvas.drawText("You got a new item!", imgX + tileWidth*(scale+0.2F), imgY + tileHeight/1.2f, paintText);
		// Image
		canvas.drawBitmap(bmp, matrix, imageObserver);
	}
	
	public void onClick(InputObject input) {
		close();
		setPopup(6);
	}

}
	

