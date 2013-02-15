package com.brogames.bro;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;

import com.brogames.bro.objecttypes.ObjectGetter;
import com.brogames.bro.objecttypes.items.Item;
import com.brogames.bro.objecttypes.objects.Object;

public class Tile {

	private Object object;
	private Item item;
	private boolean wayPoint = false, last = false;
	private int tileWidth, tileHeight;
	private Paint color = new Paint();
	private int objectType;

	public Tile(int objectType, int x, int y, Bundle sizes) {
		this.objectType = objectType;

		if (objectType < 331)
			object = ObjectGetter.setObject(objectType);
		else
			item = ObjectGetter.setItem(objectType);

		tileWidth = sizes.getInt("boardWidth");
		tileHeight = sizes.getInt("boardHeight");
	}

	public void addItem(int objectType) {
		item = ObjectGetter.setItem(objectType);
	}

	public void removeItem() {
		item = null;
		objectType = 0;
	}

	public Item getItem() {
		return item;
	}

	public int getObjectType() {
		return objectType;
	}

	public void add(int objectType) {
		this.objectType = objectType;
		object = ObjectGetter.setObject(objectType);
	}

	public void removeObject() {
		object = null;
	}

	public Object getObject() {
		return object;
	}

	public boolean isObstacle() {
		if (object != null)
			return object.isObstacle();
		else
			return false;
	}

	public void addWaypoint(boolean last) {
		wayPoint = true;
		this.last = last;
	}

	public void removeWaypoint() {
		wayPoint = false;
		last = false;
	}

	public void draw(Canvas canvas, float xPos, float yPos) {
		if (object != null) {
			if (object.getBmp() != null)
				canvas.drawBitmap(object.getBmp(), xPos, yPos, null);
		}
		if (item != null) {
			if (item.getBmp() != null)
				canvas.drawBitmap(item.getBmp(), xPos, yPos, null);
		}

		if (wayPoint) {
			color.setColor(Color.BLACK);
			if (last) {
				color.setStrokeWidth(tileWidth / 12);
				canvas.drawLine(xPos + tileWidth / 4,
						yPos + 3 * tileHeight / 5, xPos + 3 * tileWidth / 4,
						yPos + 9 * tileHeight / 10, color);
				canvas.drawLine(xPos + tileWidth / 4, yPos + 9 * tileHeight
						/ 10, xPos + 3 * tileWidth / 4, yPos + 3 * tileHeight
						/ 5, color);
			} else
				canvas.drawCircle(xPos + tileWidth / 2, yPos + 3 * tileHeight
						/ 4, tileWidth / 10, color);
		}
	}
}
