package com.brogames.bro.popups;

import java.util.Arrays;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.Log;

import com.brogames.bro.Bag;
import com.brogames.bro.R;
import com.brogames.bro.Tile;
import com.brogames.bro.objecttypes.items.CraftSystem;
import com.core.ks.InputObject;
import com.core.ks.Popup;

public class Inventory extends Popup {

	// Core stuff
	private int objWidth, objHeight;
	private int rows, cols;
	private CraftSystem craftSys;
	// Slots
	private Slot[] slots = new Slot[25];
	private Slot[] craftSlots = new Slot[4];
	private int selected;
	// Buttons
	private BagButtons buttons;
	// Other
	private Bag bag;
	private Tile tile;
	private boolean craftIsPossible = false;
	private boolean detachIsPossible = false;
	private Bitmap background;
	// http://www.youtube.com/watch?v=Eq3CuMDXaPs

	public Inventory(Resources res, Bag bag, Tile tile) {
		super();

		objWidth = 2 * tileWidth;
		objHeight = 2 * tileHeight;

		this.bag = bag;
		this.tile = tile;
		
		craftSys = new CraftSystem();
		selected = -1;
		Bitmap tempBmp = BitmapFactory.decodeResource(res, R.drawable.bag_layout);
		
		// Check orientation 
		if (screenWidth > screenHeight) {
			rows = 19;
			cols = 13;
			background = Bitmap.createBitmap(tempBmp);
		} else {
			rows = 13;
			cols = 19;
			Matrix matrix = new Matrix();
			matrix.setRotate(90);
			background = Bitmap.createBitmap(tempBmp, 0, 0, tempBmp.getWidth(), tempBmp.getHeight(), matrix, true);
		}
		
		setSize(tileWidth * rows, tileHeight * cols);

		// Set positions for bag slots
		int q = 0;
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				int xPos = tileWidth + tileWidth / 4 * j + objWidth * j;
				int yPos = tileHeight + tileHeight / 4 * i + objHeight * i;
				slots[q] = new Slot(bag.getItem(q), xPos, yPos, objWidth, objHeight, q);
				q++;
			}
		}

		// Set positions for craft slots
		int z = 0;
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				int xPos = tileWidth * (rows - 3) - (tileWidth / 4 + objWidth)*j; //Same
				int yPos;
				if (screenWidth > screenHeight)
					yPos = tileHeight + (tileHeight / 4 + objHeight)*i;
				else
					yPos = tileHeight * (cols - 3) - (tileHeight / 4 + objHeight)*i;
				craftSlots[z] = new Slot(null, xPos, yPos, objWidth, objHeight, z);
				z++;
			}
		}
		
		// Setup Buttons
		int xText = tileWidth * (rows - 3) - (tileWidth / 4 / 2 + objWidth);
		int yText = tileHeight + tileHeight / 4 * 2 + objHeight * 2;
		Bitmap normal = BitmapFactory.decodeResource(res, R.drawable.button_normal);
		Bitmap pressed = BitmapFactory.decodeResource(res, R.drawable.button_pressed);
		buttons = new BagButtons(normal, pressed, xText, yText);
	}

	public void tick() {
		buttons.tick();
	}

	public void draw(Canvas canvas) {
		// Background
		canvas.drawBitmap(background, 0, 0, null);
		// Slots
		for (int n = 0; n < slots.length; n++)
			slots[n].render(canvas, bag.getStack(n));
		// Craft slots
		for (int n = 0; n < craftSlots.length; n++)
			craftSlots[n].render(canvas);
		// Buttons
		buttons.render(canvas);
	}

	public void onClick(InputObject input) {
		
		// Slots
		for (int n = 0; n < slots.length; n++) {
			if (slots[n].wasPressed(input)) {
				if (!slots[n].isUsed() && n < bag.getUniqueItemsCount()) {
					if (selected != -1)
						slots[selected].unselect();
					selected = n;
					slots[n].select();
					buttons.setActive(BagButtons.EQUIP_BUTTON);
					buttons.setActive(BagButtons.DROP_BUTTON);
				}
			}
		}

		// Craft slots
		for (int n = 0; n < craftSlots.length; n++) {
			if (craftSlots[n].wasPressed(input)) {
				buttons.setActive(BagButtons.CLEAR_BUTTON);
				buttons.setInactive(BagButtons.EQUIP_BUTTON);
				buttons.setInactive(BagButtons.DROP_BUTTON);
				craftSlots[n].craftPressed(slots, selected);
				selected = -1;
				craftIsPossible = craftSys.craftIsPossible(craftSlots);
				if (craftIsPossible)
					buttons.setActive(BagButtons.CRAFT_BUTTON);
				else
					buttons.setInactive(BagButtons.CRAFT_BUTTON);
				detachIsPossible = craftSys.detachIsPossible(craftSlots);
				if (detachIsPossible)
					buttons.setActive(BagButtons.DISASSEMBLE_BUTTON);
				else
					buttons.setInactive(BagButtons.DISASSEMBLE_BUTTON);
			}
		}

		// Buttons
		int pressedButton = buttons.wasPressed(input);
		if (pressedButton != -1) {
			switch (pressedButton) {
			case BagButtons.CRAFT_BUTTON:
				craft();
				break;
			case BagButtons.DISASSEMBLE_BUTTON:
				detach();
				break;
			case BagButtons.CLEAR_BUTTON:
				clear();
				break;
			case BagButtons.EQUIP_BUTTON:
				if (selected != -1)
					bag.equipItem(selected);
				updateSlots();
				break;
			case BagButtons.DROP_BUTTON:
				drop();
				break;
			case BagButtons.CLOSE_BUTTON:
				try {
					Thread.sleep(150);
				} catch (InterruptedException e) {
				}
				close();
				break;
			default:
				Log.d("Inventory", "Bag buttons out of index, check switch-statment in processMotionEvent()");
			}
			
		}
	}

	public void craft() {
		if (craftIsPossible) {
			int[] indexes = new int[craftSlots.length];
			for (int n = 0; n < craftSlots.length; n++)
				indexes[n] = craftSlots[n].getLink();
			Arrays.sort(indexes);
			for (int n = craftSlots.length-1; n >= 0 ; n--)
				bag.removeItem(indexes[n]);
			clear();
			bag.insertItem(craftSys.getCraftedItem());
			updateSlots();
		}
	}
	
	public void detach() {
		if (detachIsPossible) {
			bag.removeItem(craftSlots[craftSys.getCraftSlotIndex()].getLink());
			for (int n = 0; n < craftSlots.length; n++)
				if(craftSys.getDetachedItem(n) != null)
					bag.insertItem(craftSys.getDetachedItem(n));
			clear();
			updateSlots();
		}
	}

	public void clear() {
		for (int n = 0; n < craftSlots.length; n++)
			craftSlots[n].removeCraftItem(slots);

		buttons.setInactive(BagButtons.CRAFT_BUTTON);
		buttons.setInactive(BagButtons.DISASSEMBLE_BUTTON);
		craftIsPossible = detachIsPossible = false;
	}

	public void drop() {
		if (selected != -1) {
			tile.addItem(slots[selected].getItem().getObjectType());
			bag.removeItem(selected);
			updateSlots();
		}
	}

	public void updateSlots() {
		for (int n = 0; n < slots.length; n++) {
			slots[n].update(bag.getItem(n));
		}
		selected = -1;
		buttons.setInactive(BagButtons.EQUIP_BUTTON);
		buttons.setInactive(BagButtons.DROP_BUTTON);
	}
}