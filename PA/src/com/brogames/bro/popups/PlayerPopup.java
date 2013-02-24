package com.brogames.bro.popups;

import java.util.Vector;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;

import com.brogames.bro.Player;
import com.brogames.bro.R;
import com.brogames.bro.objecttypes.items.Item;
import com.core.ks.InputObject;
import com.core.ks.Popup;

public class PlayerPopup extends Popup {

	private int objWidth, objHeight;
	private int x, y, rows, cols;
	private float scale;

	private Slot[] slots = new Slot[9];
	private int[] indexes = new int[9];
	private Player player;
	private Rect foodMeter, drinkMeter, foodValue, drinkValue;
	private float xHunger, yHunger, xThirst, yThirst;
	
	private Bitmap bagBitmap, background;
	private Canvas bagCanvas;
	private Paint imageObserver = new Paint();
	private Paint playerBackground = new Paint();
	private Paint playerBackgroundFrame = new Paint();
	private Paint meterFoodValue = new Paint();
	private Paint meterDrinkValue = new Paint();
	private Paint textStyle = new Paint();
	private Matrix playerMatrix = new Matrix();
	
	public PlayerPopup(Resources res, Player player) {
		super();

		objWidth = 2 * tileWidth;
		objHeight = 2 * tileHeight;

		this.player = player;

		imageObserver.setFilterBitmap(true);
		imageObserver.setAntiAlias(true);
		
		playerBackground.setAntiAlias(true);
		playerBackground.setARGB(255, 112, 61, 20);
		playerBackgroundFrame.setAntiAlias(true);
		playerBackgroundFrame.setColor(Color.BLACK);
		playerBackgroundFrame.setStyle(Style.STROKE);
		meterFoodValue.setAntiAlias(true);
		meterFoodValue.setARGB(180, 153, 204, 0);
		meterDrinkValue.setAntiAlias(true);
		meterDrinkValue.setARGB(180, 51, 181, 229);
		textStyle.setAntiAlias(true);
		textStyle.setColor(Color.BLACK);
		textStyle.setTextSize(tileHeight/1.3f);
		
		Bitmap tempBmp = BitmapFactory.decodeResource(res, R.drawable.player_layout);
		
		// Set orientation
		if (screenWidth > screenHeight) {
			rows = 16;
			cols = 10;
			background = Bitmap.createBitmap(tempBmp);
		} else {
			rows = 10;
			cols = 16;
			Matrix matrix = new Matrix();
			matrix.setRotate(90);
			background = Bitmap.createBitmap(tempBmp, 0, 0, tempBmp.getWidth(), tempBmp.getHeight(), matrix, true);
		}

		// Set scale
		if (tileWidth * rows > screenWidth || tileHeight * cols > screenHeight) {
			float temp = (float) screenHeight / (float) (tileHeight * cols);
			scale = (float) screenWidth / (float) (tileWidth * rows);
			if (temp < scale)
				scale = temp;
		} else {
			scale = 1;
		}

		// Set slots
		setSlots();

		// Canvas and bitmap to render graphics on
		bagBitmap = Bitmap.createBitmap(tileWidth * rows, tileHeight * cols, Bitmap.Config.ARGB_8888);
		bagCanvas = new Canvas(bagBitmap);
		bagCanvas.scale(scale, scale);
		playerMatrix.postScale(3.5f, 3.5f);
		playerMatrix.postTranslate(tileWidth, -tileHeight/1.5f);
		
		x = (int) (screenWidth - bagCanvas.getWidth() * scale) / 2;
		y = (int) (screenHeight - bagCanvas.getHeight() * scale) / 2;
		
		if (screenWidth > screenHeight) {
			// Landscape
			foodMeter = new Rect(tileWidth, (int)(tileHeight/1.5f)+tileHeight*6, tileWidth*6, (int)(tileHeight/1.5f)+tileHeight*7);
			drinkMeter  = new Rect(tileWidth, (int)(tileHeight/4)+tileHeight*8, tileWidth*6, (int)(tileHeight/4)+tileHeight*9);
			foodValue = new Rect(tileWidth, (int)(tileHeight/1.5f)+tileHeight*6, tileWidth*6, (int)(tileHeight/1.5f)+tileHeight*7);
			drinkValue  = new Rect(tileWidth, (int)(tileHeight/4)+tileHeight*8, tileWidth*6, (int)(tileHeight/4)+tileHeight*9);
			xHunger = tileWidth*2.3f;
			yHunger = (int)(tileHeight/0.69f)+tileHeight*6;
			xThirst = tileWidth*2.5f;
			yThirst = tileHeight/0.98f+tileHeight*8;
		} else {
			// Portrait
			foodMeter = new Rect(tileWidth*5, (int)(tileHeight*2.5f), (int)(tileWidth*9.3f), (int)(tileHeight*3.5f));
			drinkMeter  = new Rect(tileWidth*5, tileHeight*5, (int)(tileWidth*9.3f), tileHeight*6);
			foodValue = new Rect(tileWidth*5, (int)(tileHeight*2.5f), (int)(tileWidth*9.3f), (int)(tileHeight*3.5f));
			drinkValue  = new Rect(tileWidth*5, tileHeight*5, (int)(tileWidth*9.3f), tileHeight*6);
			xHunger = tileWidth*5;
			yHunger = tileHeight*2.2f;
			xThirst = tileWidth*5;
			yThirst = tileHeight*4.7f;
		}
		
	}
	
	public void tick(){
		float hunger = player.getHunger();
		float thirst = player.getThirst();
		if (screenWidth > screenHeight) {
			float foodPercentage = tileWidth*5*(hunger/10000);
			float drinkPercentage = tileWidth*5*(thirst/10000);
			if(0 > foodPercentage)
				foodPercentage = 0;
			if(0 > drinkPercentage)
				drinkPercentage = 0;
			foodValue.set(tileWidth, (int)(tileHeight/1.5f)+tileHeight*6, tileWidth*6-(int)foodPercentage, (int)(tileHeight/1.5f)+tileHeight*7);
			drinkValue.set(tileWidth, (int)(tileHeight/4)+tileHeight*8, tileWidth*6-(int)drinkPercentage, (int)(tileHeight/4)+tileHeight*9);
		} else {
			float foodPercentage = tileWidth*4.3f*(hunger/10000);
			float drinkPercentage = tileWidth*4.3f*(thirst/10000);
			if(0 > foodPercentage)
				foodPercentage = 0;
			if(0 > drinkPercentage)
				drinkPercentage = 0;
			foodValue.set(tileWidth*5, (int)(tileHeight*2.5f), (int)(tileWidth*9.3f-foodPercentage), (int)(tileHeight*3.5f));
			drinkValue.set(tileWidth*5, tileHeight*5, (int)(tileWidth*9.3f-drinkPercentage), tileHeight*6);
		}
	}

	public void render(Canvas canvas) {
		super.render(canvas);

		// Background
		bagCanvas.drawBitmap(background, 0, 0, imageObserver);
		
		// Slots
		for (int n = 0; n < slots.length; n++)
			slots[n].render(bagCanvas, player.getBag().getStack(indexes[n]));

		// Player
		bagCanvas.drawRect(tileWidth, tileHeight, tileWidth*4.5f, tileHeight*6, playerBackground);
		bagCanvas.drawBitmap(player.getBitmap(), playerMatrix, imageObserver);
		bagCanvas.drawRect(tileWidth, tileHeight, tileWidth*4.5f, tileHeight*6, playerBackgroundFrame);
		
		//Meters 
		bagCanvas.drawRect(foodMeter, playerBackground);
		bagCanvas.drawRect(foodValue, meterFoodValue);
		bagCanvas.drawRect(foodMeter, playerBackgroundFrame);
		bagCanvas.drawText("Hunger", xHunger, yHunger, textStyle); // Text
		bagCanvas.drawRect(drinkMeter, playerBackground);
		bagCanvas.drawRect(drinkValue, meterDrinkValue);
		bagCanvas.drawRect(drinkMeter, playerBackgroundFrame);
		bagCanvas.drawText("Thirst", xThirst, yThirst, textStyle); // Text
		
		
		canvas.drawBitmap(bagBitmap, x, y, imageObserver);
	}

	public void processMotionEvent(InputObject input) {

		// Transform input
		input.x /= scale;
		input.y /= scale;
		input.x -= x;
		input.y -= y;

		// Slots
		for (int n = 0; n < slots.length; n++) {
			if (slots[n].wasPressed(input)) {
				if (!slots[n].isUsed() && n < player.getBag().getUniqueItemsCount()) {
					Item consumable = slots[n].getItem();
					slots[n].setAsUnused();
					if(consumable == null)
						return;
					if(consumable.getCategory() == Item.FOOD_AND_FLUID){
						player.eat(consumable.getConsumeValue());
						player.drink(consumable.getConsumeValue());
					}else if(consumable.getCategory() == Item.FOOD){
						player.eat(consumable.getConsumeValue());
					}else if(consumable.getCategory() == Item.FLUID){
						player.drink(consumable.getConsumeValue());
					}
					if(player.getBag().getStack(indexes[n]) == 1)
						slots[n].removeItem();
					player.getBag().removeItem(indexes[n]);
					setSlots();
				}
			}
		}

		if(input.x < 0 || input.x > tileWidth*rows)
			close();
		else if(input.y < 0 || input.y > tileHeight*cols)
			close();
	}
	
	public void setSlots(){
		Vector<Item> consumables = new Vector<Item>();
		int m = 0;
		for(int i=0; i<player.getBag().getUniqueItemsCount(); i++){
			Item consumable = player.getBag().getItem(i);
			if (consumable != null)
				if (consumable.getCategory() == Item.FOOD || consumable.getCategory() == Item.FLUID
				|| consumable.getCategory() == Item.FOOD_AND_FLUID){
					consumables.addElement(consumable);
					indexes[m] = i;
					m++;
				}
		}
		int q = 0;
		for (int i = 2; i >=0; i--) {
			for (int j = 2; j >=0; j--) {
				int xPos = (rows-3)*tileWidth - (tileWidth /*/ 4*/ * j + objWidth * j);
				int yPos = (cols-3)*tileHeight - (tileHeight /*/ 4*/ * i + objHeight * i);
				if (!consumables.isEmpty())
					slots[q] = new Slot(consumables.remove(0), xPos, yPos, objWidth, objHeight, q);
				else
					slots[q] = new Slot(null, xPos, yPos, objWidth, objHeight, q);
				q++;
			}
		}
	}
}
