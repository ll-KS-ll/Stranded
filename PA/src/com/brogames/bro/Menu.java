package com.brogames.bro;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;

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
	private int boardWidth, boardHeight, width;
	private int hungerIndex = 0, thirstIndex = 0;
	private String message;

	public Menu(Bitmap bmp, Bitmap bmpEquip, Bundle boardSize) {
		int bw = boardSize.getInt("boardWidth");
		int bh = boardSize.getInt("boardHeight");
		isOpen = false;
		this.bmpEquip = bmpEquip;
		
		background = Bitmap.createBitmap(bmp, bw, bh * 4, bw, bh);
		bag = Bitmap.createBitmap(bmp, bw * 0, bh * 0, bw * 2, bh * 3);
		
		buttonUp = Bitmap.createBitmap(bmp, bw * 0, bh * 3, bw, bh);
		buttonDown = Bitmap.createBitmap(bmp, bw, bh * 3, bw, bh);

		map = Bitmap.createBitmap(bmp, bw * 2, bh * 3, bw, bh);
		notes = Bitmap.createBitmap(bmp, bw * 3, bh * 3, bw, bh);

		for (int n = 0; n < hunger.length; n++)
			hunger[n] = Bitmap.createBitmap(bmp, bw * (n + 2), bh * 0, bw, bh);

		for (int n = 0; n < thirst.length; n++)
			thirst[n] = Bitmap.createBitmap(bmp, bw * (n + 2), bh * 2, bw, bh);

		boardWidth = bw;
		boardHeight = bh;
	}

	public void tick(Bitmap bmpEquip, int hunger, int thirst) {
		if (hunger < 12)
			hungerIndex = 0;
		else if (hunger < 25)
			hungerIndex = 1;
		else if (hunger < 37)
			hungerIndex = 2;
		else if (hunger < 50)
			hungerIndex = 3;
		else if (hunger < 62)
			hungerIndex = 4;
		else if (hunger < 75)
			hungerIndex = 5;
		else if (hunger < 87)
			hungerIndex = 6;
		else
			hungerIndex = 7;

		if (thirst < 25)
			thirstIndex = 0;
		else if (thirst < 50)
			thirstIndex = 1;
		else if (thirst < 75)
			thirstIndex = 2;
		else
			thirstIndex = 3;
		
		this.bmpEquip = bmpEquip;
	}

	public void render(Canvas canvas) {
		int top = canvas.getHeight() - boardHeight;
		width = canvas.getWidth();

		if (isOpen) {
			for (int n = 0; n <= canvas.getWidth(); n += boardWidth)
				canvas.drawBitmap(background, n, top, null);

			canvas.drawBitmap(buttonDown, width - boardWidth, top, null);
			canvas.drawBitmap(hunger[hungerIndex], width - boardWidth * 2, top, null);
			canvas.drawBitmap(thirst[thirstIndex], width - boardWidth * 3, top, null);
			canvas.drawBitmap(map, width - boardWidth * 5, top, null);
			canvas.drawBitmap(notes, width - boardWidth * 6, top, null);
			canvas.drawBitmap(bag, 0, top - boardHeight * 2, null);
			
			if(bmpEquip != null)
				canvas.drawBitmap(bmpEquip, boardWidth*3, top, null);
			
		} else {
			canvas.drawBitmap(buttonUp, width - boardWidth, top, null);
		}
	}

	public void processInput(InputObject input, Popup popup) {
		
		// Right down button
		if (width - input.x < boardWidth)
			close();
		// Bag
		if (input.x < boardWidth * 2)
			popup.setPopup(6);

		// Hunger icon
		if (input.x > width - boardWidth * 2 && input.x < width - boardWidth) {
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
		if (input.x > width - boardWidth*3 && input.x < width - boardWidth*2) {
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
		if (input.x > width - boardWidth * 5 && input.x < width - boardWidth*4) {
			message = "I haven't discovered anything yet.";
			popup.setPopup(5);
		}
		
		// Notes icon
		if (input.x > width - boardWidth * 6 && input.x < width - boardWidth*5) {
			message = "I haven't made any notes yet.";
			popup.setPopup(5);
		}
		
		// Notes icon
		if (input.x > boardWidth * 3 && input.x < boardWidth*4) {
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
