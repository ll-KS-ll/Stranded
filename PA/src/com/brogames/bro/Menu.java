package com.brogames.bro;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.brogames.bro.objecttypes.items.Item;
import com.core.ks.InputObject;
import com.core.ks.Popup;

public class Menu {

	private boolean isOpen;
	private Bitmap[] hunger = new Bitmap[8];
	private Bitmap[] thirst = new Bitmap[4];
	private Bitmap background, bag;
	private Bitmap buttonUp, buttonDown;
	private int hungerIndex = 0, thirstIndex = 0;
	private String message;
	private int offset;
	
	private final int slots = LaunchView.SCREEN_WIDTH / LaunchView.TILE_WIDTH;
	private MenuSlot[] menu = new MenuSlot[slots];
	private final int MENU_BUTTON = slots - 1;
	private int EQUIP = 2, HUNGER = slots - 2, THIRST = slots - 3,
			MAP = slots - 5, NOTES = slots - 6;

	public Menu(Bitmap bmp, int equipType) {
		int tw = LaunchView.TILE_WIDTH;
		int th = LaunchView.TILE_HEIGHT;
		isOpen = false;
		
		// Initialize menu slots
		for (int n = 0; n < menu.length; n++)
			menu[n] = new MenuSlot(n);

		// Images
		background = Bitmap.createBitmap(bmp, tw, th * 4, tw, th);
		buttonUp = Bitmap.createBitmap(bmp, tw * 0, th * 3, tw, th);
		buttonDown = Bitmap.createBitmap(bmp, tw, th * 3, tw, th);
		for (int n = 0; n < hunger.length; n++)
			hunger[n] = Bitmap.createBitmap(bmp, tw * (n + 2), th * 0, tw, th);

		for (int n = 0; n < thirst.length; n++)
			thirst[n] = Bitmap.createBitmap(bmp, tw * (n + 2), th * 2, tw, th);
		bag = Bitmap.createBitmap(bmp, tw * 0, th * 0, tw * 2, th * 3);
		
		// Offset
		if (LaunchView.SCREEN_WIDTH / tw > 10)
			offset = 1;
		else
			offset = 0;

		setupItems();
		addItems(bmp);
	}

	private void setupItems() {
		EQUIP += offset;
		HUNGER -= offset;
		THIRST -= offset;
		MAP -= offset;
		NOTES -= offset;
	}

	private void addItems(Bitmap bmp) {
		int tw = LaunchView.TILE_WIDTH;
		int th = LaunchView.TILE_HEIGHT;
		
		// Map
		menu[MAP].add(Bitmap.createBitmap(bmp, tw * 2, th * 3, tw, th));
		// Note
		menu[NOTES].add(Bitmap.createBitmap(bmp, tw * 3, th * 3, tw, th));
		// Hunger
		menu[HUNGER].add(hunger[0]);
		// Thirst
		menu[THIRST].add(thirst[0]);
		// Button up
		menu[MENU_BUTTON].add(buttonUp);
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

		if (equip != null) {
			menu[EQUIP].add(equip.getBmp());
		} else
			menu[EQUIP].remove();
	}

	public void render(Canvas canvas) {
		if (isOpen) {
			for (int n = 0; n < slots; n++)
				menu[n].draw(canvas);
			canvas.drawBitmap(bag, 0, canvas.getHeight()
					- LaunchView.TILE_HEIGHT * 3, null);
		} else {
			menu[MENU_BUTTON].draw(canvas);
		}
	}

	public void processInput(InputObject input, Popup popup) {

		// Right down button
		if (menu[MENU_BUTTON].wasClicked(input))
			close();

		// Bag
		if (input.x < LaunchView.TILE_WIDTH * 2)
			popup.setPopup(6);

		// Hunger icon
		if (menu[HUNGER].wasClicked(input)) {
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
		if (menu[THIRST].wasClicked(input)) {
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
		if (menu[MAP].wasClicked(input)) {
			message = "I haven't discovered anything yet.";
			popup.setPopup(5);
		}

		// Notes icon
		if (menu[NOTES].wasClicked(input)) {
			message = "I haven't made any notes yet.";
			popup.setPopup(5);
		}

		// Equip icon
		if (menu[EQUIP].wasClicked(input)) {
			message = "This is what I've equipped to use.";
			popup.setPopup(5);
		}
	}

	public String getMessage() {
		return message;
	}

	public void open() {
		isOpen = true;
		menu[MENU_BUTTON].add(buttonDown);
	}

	public void close() {
		isOpen = false;
		menu[MENU_BUTTON].add(buttonUp);
	}

	public boolean isOpen() {
		return isOpen;
	}

	private class MenuSlot {

		private Bitmap bmp;
		private boolean isSomething = false;
		private int left, top, right;

		public MenuSlot(int index) {
			left = LaunchView.TILE_WIDTH * index;
			top = LaunchView.SCREEN_HEIGHT - LaunchView.TILE_HEIGHT;
			right = left + LaunchView.TILE_WIDTH;
		}

		public void add(Bitmap bitmap) {
			bmp = bitmap;
			isSomething = true;
		}

		public void remove() {
			bmp = null;
			isSomething = false;
		}

		public void draw(Canvas canvas) {
			if (isOpen())
				canvas.drawBitmap(background, left, top, null);
			if (isSomething)
				canvas.drawBitmap(bmp, left, top, null);
		}

		public boolean wasClicked(InputObject input) {
			if (input.x > left && input.x < right && isSomething)
				return true;
			return false;
		}
	}
}
