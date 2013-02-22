package com.brogames.bro.popups;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import com.brogames.bro.objecttypes.items.Item;
import com.core.ks.InputObject;

public class Slot {

	// Core
	private int x, y, right, bottom, index;
	private int link;
	private boolean isUsed;
	private boolean isSelected;
	// Paints
	private Paint slotColor = new Paint();
	private Paint selectedIndicator = new Paint();
	private Paint usedIndicator = new Paint();
	private Paint stackText = new Paint();
	private Paint imageObserver = new Paint();
	private Paint slotFrame = new Paint();
	// Images
	private Matrix matrix = new Matrix();
	private Bitmap bitmap;
	private Item item;

	public Slot(Item item, int x, int y, int width, int height, int index) {
		this.x = x;
		this.y = y;
		right = x + width;
		bottom = y + height;
		this.index = index;
		
		addItem(item);

		isUsed = isSelected = false;
		link = -1;

		slotColor.setAntiAlias(true);
		slotColor.setARGB(255, 112, 61, 20);
		slotFrame.setAntiAlias(true);
		slotFrame.setColor(Color.BLACK);
		slotFrame.setStyle(Style.STROKE);
		selectedIndicator.setAntiAlias(true);
		selectedIndicator.setColor(Color.CYAN);
		selectedIndicator.setStyle(Style.STROKE);
		usedIndicator.setAntiAlias(true);
		usedIndicator.setARGB(240, 50, 50, 50);
		stackText.setColor(Color.WHITE);
		stackText.setTextSize(height / 5);
		stackText.setTextAlign(Align.RIGHT);
		stackText.setAntiAlias(true);
		imageObserver.setAntiAlias(true);
		imageObserver.setFilterBitmap(true);
	}

	public void render(Canvas canvas, int stack) {
		canvas.drawRect(x, y, right, bottom, slotColor);

		if (item != null) {
			if (bitmap != null)
				canvas.drawBitmap(bitmap, x, y, imageObserver);
			else
				canvas.drawRect(x, y, right, bottom, item.getColor());
			canvas.drawText(String.valueOf(stack), right - 8, bottom - 8, stackText);
		}

		canvas.drawRect(x, y, right, bottom, slotFrame);
		
		if (isSelected)
			canvas.drawRect(x, y, right, bottom, selectedIndicator);
		if (isUsed)
			canvas.drawRect(x, y, right+1, bottom+1, usedIndicator);
	}

	public void render(Canvas canvas) {
		canvas.drawRect(x, y, right, bottom, slotColor);

		if (item != null)
			if (bitmap != null)
				canvas.drawBitmap(bitmap, x, y, imageObserver);
			else
				canvas.drawRect(x, y, right, bottom, item.getColor());

		canvas.drawRect(x, y, right, bottom, slotFrame);
		
		if (isSelected)
			canvas.drawRect(x, y, right, bottom, selectedIndicator);
	}

	public void update(Item item){
		addItem(item);
		unselect();
		setAsUnused();
	}
	
	public void link(int link){
		this.link = link;
	}
	
	public int getLink(){
		return link;
	}
	
	public int removeLink(){
		int temp = link;
		link = -1;
		return temp;
	}
	
	public Item getItem() {
		isUsed = true;
		isSelected = false;
		return item;
	}

	public void addItem(Item item) {
		this.item = item;
		matrix.setScale(2, 2);
		if (item != null)
			if (item.getBmp() != null)
				bitmap = Bitmap.createBitmap(item.getBmp(), 0, 0, item.getBmp().getWidth(), item.getBmp().getHeight(), matrix, true);
	}

	public void removeItem() {
		item = null;
		unselect();
		setAsUnused();
	}

	public boolean isEmpty() {
		if (item != null)
			return false;
		return true;
	}

	public void select() {
		isSelected = true;
	}

	public void unselect() {
		isSelected = false;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setAsUsed() {
		isUsed = true;
		unselect();
	}

	public void setAsUnused() {
		isUsed = false;
	}

	public boolean isUsed() {
		return isUsed;
	}

	public int getIndex(){
		return index;
	}
	
	public void addCraftItem(Slot slot){
		addItem(slot.getItem());
		link(slot.getIndex());
		slot.setAsUsed();
	}
	
	public void removeCraftItem(Slot[] slots){
		item = null;
		if(getLink() != -1)
			slots[removeLink()].setAsUnused();
	}
	
	public boolean wasPressed(InputObject input) {
		if (input.x >= x && input.x <= right && input.y >= y && input.y <= bottom)
			return true;
		return false;
	}
	
	public void craftPressed(Slot[] slots, int index){
		if (isEmpty()) {
			if (index != -1 && !slots[index].isUsed)
				addCraftItem(slots[index]);
			index = -1;
		}else{
			removeCraftItem(slots);
		}
	}
}
