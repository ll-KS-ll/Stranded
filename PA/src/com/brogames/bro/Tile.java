package com.brogames.bro;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;

import com.brogames.bro.objecttypes.Empty;
import com.brogames.bro.objecttypes.ObjectGetter;
import com.brogames.bro.objecttypes.ObjectType;
import com.brogames.bro.objecttypes.items.Item;
import com.brogames.bro.objecttypes.objects.Environment;
import com.brogames.bro.objecttypes.TileObject;

public class Tile {

	private TileObject object;
	private boolean wayPoint = false, last = false;
	private int tileWidth, tileHeight;
	private Paint color = new Paint();

	public Tile(int objectType, int x, int y, Bundle sizes) {
		object = ObjectGetter.setTileObject(objectType);

		tileWidth = sizes.getInt("boardWidth");
		tileHeight = sizes.getInt("boardHeight");
	}

	// ITEM
	/** Try to add an item to this tile.
	 *  Will set the tile object to null if objectType doesn't is an item.
	 * @param objectType - the object type for the item
	 */
	public void addItem(int objectType) {object = ObjectGetter.setItem(objectType);}
	/** Remove the item from this tile */
	public void removeItem() {object = new Empty(ObjectType.EMPTY);}
	/** Try get the tile's item.
	 *  If the tile isn't an item it will return null;
	 *  @return item - current item for this tile
	 */
	public Item getItem() {
		if(object.isItem())
			return (Item)object;
		return null;
	}

	// ENVIRONMENT
	/** Try to add an environment object to this tile.
	 *  Will set this tile object to null if objectType doesn't is an environment.
	 * @param objectType - the object type for the environment
	 */
	public void addEnvironment(int objectType) {
		object = ObjectGetter.setEnvironment(objectType);
	}
	/** Remove the environment object from this tile */
	public void removeEnvironment() {object = new Empty(ObjectType.EMPTY);}
	/** Try get the tile's environment object.
	 *  If the tile isn't an environment object it will return null;
	 *  @return environment - current environment for this tile
	 */
	public Environment getEnvironment() {
		if(object.isItem())
			return (Environment)object;
		return null;
	}
	
	// TILE OBJECT
	/**Add an tile object to this tile.
	 * It might be an environment object or an item.
	 * @param objectType - the object type for this tile object
	 */
	public void addObject(int objectType) {object = ObjectGetter.setTileObject(objectType);}
	/**Remove tile object for this tile*/
	public void removeObject() {object = new Empty(ObjectType.EMPTY);}
	/**Get the object for this tile.
	 * Can return both environment objects or items.
	 * @return object - tile object for this tile
	 */
	public TileObject getObject() {return object;}
	
	public int getObjectType() {
		if(object != null)
			return object.getObjectType();
		return -1;
	}
	
	public boolean isItem() {
		if(object != null)
			return object.isItem();
		return false;
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
		if (object != null && getObjectType() != ObjectType.EMPTY ) {
			if (object.getBmp() != null)
				canvas.drawBitmap(object.getBmp(), xPos, yPos, null);
			else
				canvas.drawRect(xPos, yPos, xPos+tileWidth, yPos+tileHeight, object.getColor());
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
