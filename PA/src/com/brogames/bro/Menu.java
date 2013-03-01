package com.brogames.bro;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.brogames.bro.objecttypes.ObjectHandler;
import com.brogames.bro.objecttypes.items.Item;
import com.core.ks.InputObject;
import com.core.ks.Popup;

public class Menu {

	private boolean isOpen;
	private Bitmap[] hunger = new Bitmap[8];
	private Bitmap[] thirst = new Bitmap[4];
	private Bitmap background, bag;
	private Bitmap buttonUp, buttonDown;
	private Bitmap map, notes;
	private Bitmap bmpEquip;
	private int tileWidth, tileHeight, width;
	private int hungerIndex = 0, thirstIndex = 0;
	private String message;
	private int offset;
	
	public Menu(Bitmap bmp, int equipType) {
		int tw = LaunchView.TILE_WIDTH;
		int th = LaunchView.TILE_HEIGHT;
		isOpen = false;
		Item equip = ObjectHandler.setItem(equipType);
		if(equip != null)
			bmpEquip = equip.getBmp();
		
		background = Bitmap.createBitmap(bmp, tw, th * 4, tw, th);
		bag = Bitmap.createBitmap(bmp, tw * 0, th * 0, tw * 2, th * 3);
		
		buttonUp = Bitmap.createBitmap(bmp, tw * 0, th * 3, tw, th);
		buttonDown = Bitmap.createBitmap(bmp, tw, th * 3, tw, th);

		map = Bitmap.createBitmap(bmp, tw * 2, th * 3, tw, th);
		notes = Bitmap.createBitmap(bmp, tw * 3, th * 3, tw, th);

		for (int n = 0; n < hunger.length; n++)
			hunger[n] = Bitmap.createBitmap(bmp, tw * (n + 2), th * 0, tw, th);

		for (int n = 0; n < thirst.length; n++)
			thirst[n] = Bitmap.createBitmap(bmp, tw * (n + 2), th * 2, tw, th);

		// New code
		if(LaunchView.SCREEN_WIDTH / tw > 10)
			offset = 1;
		else
			offset = 0;
		
		
		tileWidth = tw;
		tileHeight = th;
	}

	public void tick(Item equip, int hunger, int thirst) {
		if (hunger < 1200)
			hungerIndex = 0;
		else if (hunger < 2500)
			hungerIndex = 1;
		else if (hunger < 3700)
			hungerIndex = 2;
		else if (hunger < 5000)
			hungerIndex = 3;
		else if (hunger < 6200)
			hungerIndex = 4;
		else if (hunger < 7500)
			hungerIndex = 5;
		else if (hunger < 8700)
			hungerIndex = 6;
		else
			hungerIndex = 7;

		if (thirst < 2500)
			thirstIndex = 0;
		else if (thirst < 5000)
			thirstIndex = 1;
		else if (thirst < 7500)
			thirstIndex = 2;
		else
			thirstIndex = 3;
		
		if(equip != null)
			bmpEquip = equip.getBmp();
		else
			bmpEquip = null;
	}

	public void render(Canvas canvas) {
		int top = canvas.getHeight() - tileHeight;
		width = canvas.getWidth();

		if (isOpen) {
			for (int n = 0; n <= canvas.getWidth(); n += tileWidth)
				canvas.drawBitmap(background, n, top, null);

			canvas.drawBitmap(buttonDown, width - tileWidth, top, null);
			canvas.drawBitmap(hunger[hungerIndex], width - tileWidth * (2+offset), top, null);
			canvas.drawBitmap(thirst[thirstIndex], width - tileWidth * (3+offset), top, null);
			canvas.drawBitmap(map, width - tileWidth * (5+offset), top, null);
			canvas.drawBitmap(notes, width - tileWidth * (6+offset), top, null);
			canvas.drawBitmap(bag, 0, top - tileHeight * 2, null);
			
			if(bmpEquip != null)
				canvas.drawBitmap(bmpEquip, tileWidth*(2+offset), top, null);
			
		} else {
			canvas.drawBitmap(buttonUp, width - tileWidth, top, null);
		}
	}

	public void processInput(InputObject input, Popup popup) {
		
		// Right down button
		if (width - input.x < tileWidth)
			close();
		// Bag
		if (input.x < tileWidth * 2)
			popup.setPopup(6);

		// Hunger icon
		if (input.x > width - tileWidth * (2+offset) && input.x < width - tileWidth *(1+offset)) {
			switch (hungerIndex) {
			case 0:
				message = "I'm not hungry at all.";
				break;
			case 1:
				message = "I'm not hungry.";
				break;
			case 2:
				message = "I'm doing just fine.";
				break;
			case 3:
				message = "I could use a snack";
				break;
			case 4:
				message = "I should get some food";
				break;
			case 5:
				message = "I'm hungry.";
				break;
			case 6:
				message = "I really need to eat something.";
				break;
			case 7:
				message = "I need to eat something or I'll die!";
				break;

			}
			popup.setPopup(5);
		}
		
		// Thirst icon
		if (input.x > width - tileWidth*(3+offset) && input.x < width - tileWidth*(2+offset)) {
			switch (thirstIndex) {
			case 0:
				message = "I'm not thirsty all.";
				break;
			case 1:
				message = "I'm a bit thirsty.";
				break;
			case 2:
				message = "I'm really thirsty.";
				break;
			case 3:
				message = "I need water or I'll die!";
				break;
			}
			popup.setPopup(5);
		}

		
		// Map icon
		if (input.x > width - tileWidth * (5+offset) && input.x < width - tileWidth*(4+offset)) {
			message = "I haven't discovered anything yet.";
			popup.setPopup(5);
		}
		
		// Notes icon
		if (input.x > width - tileWidth * (6+offset) && input.x < width - tileWidth*(5+offset)) {
			message = "I haven't made any notes yet.";
			popup.setPopup(5);
		}
		
		// Equip icon
		if (bmpEquip != null && input.x > tileWidth * (2+offset) && input.x < tileWidth*(3+offset)) {
			message = "This is what I've equipped to use.";
			popup.setPopup(5);
		}
	}

	public String getMessage(){
		return message;
	}
	
	public void open() {
		isOpen = true;
	}

	public void close() {
		isOpen = false;
	}

	public boolean isOpen() {
		return isOpen;
	}
}
