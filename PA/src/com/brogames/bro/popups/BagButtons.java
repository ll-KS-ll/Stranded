package com.brogames.bro.popups;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Align;

import com.brogames.bro.LaunchView;
import com.core.ks.InputObject;
import com.core.ks.Timer;

public class BagButtons {

	private String[] text = { "Craft", "Detach", "Clear", "Equip", "Drop", "Close" };
	private boolean[] active = new boolean[text.length];
	private Paint[] textStyle = new Paint[text.length];
	private int xText[] = new int[text.length], yText[] = new int[text.length];
	private int tileWidth, tileHeight, textSize;
	private Paint bg = new Paint();
	private Bitmap[] bmp = new Bitmap[text.length];
	private Bitmap btnPressed, btnNormal;
	private Timer timer;
	private int pressedIndex = -1;
	
	public static final int CRAFT_BUTTON = 0;
	public static final int DISASSEMBLE_BUTTON = 1;
	public static final int CLEAR_BUTTON = 2;
	public static final int EQUIP_BUTTON = 3;
	public static final int DROP_BUTTON = 4;
	public static final int CLOSE_BUTTON = 5;
	
	public BagButtons(Bitmap btnNormal, Bitmap btnPressed, int xPos, int yPos) {
		tileWidth = LaunchView.TILE_WIDTH;
		tileHeight = LaunchView.TILE_HEIGHT;
		int screenWidth = LaunchView.SCREEN_WIDTH;
		int screenHeight = LaunchView.SCREEN_HEIGHT;

		bg.setColor(Color.RED);
		
		if(screenWidth > screenHeight){
			this.btnNormal = btnNormal;
			this.btnPressed = btnPressed;
			for (int n = 0; n < xText.length; n++)
				xText[n] = xPos;
			for (int n = 0; n < yText.length; n++)
				yText[n] = yPos + tileHeight * n + tileHeight / 6 * n;
		}else{
			Matrix m = new Matrix();
			m.setScale(0.75f, 1);
			this.btnNormal = Bitmap.createBitmap(btnNormal, 0, 0, btnNormal.getWidth(), btnNormal.getHeight(), m, true);
			this.btnPressed = Bitmap.createBitmap(btnPressed, 0, 0, btnPressed.getWidth(), btnPressed.getHeight(), m, true);;
			for (int n = 0; n < xText.length; n+=2)
				xText[n] = tileWidth;
			for (int n = 1; n < xText.length; n+=2)
				xText[n] = tileWidth + this.btnNormal.getWidth() + tileWidth / 5;
			yText[0] = tileHeight * 15 - 2*(tileHeight / 3);
			yText[1] = tileHeight * 15 - 2*(tileHeight / 3);
			yText[2] = tileHeight * 17;
			yText[3] = tileHeight * 16 - tileHeight / 3;
			yText[4] =  tileHeight * 16 - tileHeight / 3;
			yText[5] =  tileHeight * 17;
		}
		
		textSize = (int) (tileHeight / 1.6f);
		for (int n = 0; n < textStyle.length; n++) {
			textStyle[n] = new Paint();
			textStyle[n].setTextSize(textSize);
			textStyle[n].setTextAlign(Align.CENTER);
			textStyle[n].setAntiAlias(true);
			setInactive(n);
			bmp[n] = this.btnNormal;
		}
		setActive(CLOSE_BUTTON);
		
		timer = new Timer(0);
	}

	public void tick() {
		if(timer.passedLimit() && pressedIndex != -1){
			bmp[pressedIndex] = btnNormal;
			setInactive(pressedIndex);
			pressedIndex = -1;
		}
	}

	public void render(Canvas canvas) {
		for (int n = 0; n < yText.length; n++) {
			canvas.drawBitmap(bmp[n], xText[n], yText[n], null);
			canvas.drawText(text[n], xText[n] + bmp[n].getWidth()/ 2, yText[n] + bmp[n].getHeight() / 2 + textSize/3, textStyle[n]);
		}
	}

	public void pressed(int index){
		bmp[index] = btnPressed;
		pressedIndex = index;
		timer = new Timer(200);
	}
	
	public int wasPressed(InputObject input) {
		for (int n = 0; n < yText.length; n++)
			if(input.x > xText[n] && input.y > yText[n] && input.x < xText[n] + tileWidth*4 && input.y < yText[n] + tileHeight && active[n]){
				pressed(n);
				return n;
			}
		return -1;
	}
	
	public void setActive(int index){
		active[index] = true;
		textStyle[index].setColor(Color.BLACK);
		textStyle[index].setStrikeThruText(false);
	}
	
	public void setInactive(int index){
		active[index] = false;
		textStyle[index].setColor(Color.DKGRAY);
		textStyle[index].setStrikeThruText(true);
	}
}
