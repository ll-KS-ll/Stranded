package com.brogames.bro.objecttypes.items;

import java.util.Vector;
import com.brogames.bro.objecttypes.ObjectType;
import com.brogames.bro.objecttypes.TileObject;

public class Item extends TileObject implements Comparable<Item> {

	protected int[] components = { -1, -1, -1, -1 };
	protected Vector<Integer> recipe = new Vector<Integer>();
	protected int[] consequens, conRock, conBush, conPalm, conWater; 
	protected boolean isPickup = true;

	 /* hej ni behöver en båt, en vild hund, ett svärd, tårtor, ett tält, en
	 * kompis, //Jaci */

	public Item(int objectType) {
		super(objectType);
		isItem = true;
		isObstacle = false;
	}

	public int[] getComponents() {
		return components;
	}

	public Vector<Integer> getRecipe() {
		return recipe;
	}

	public int[] getConsequences(int objectType) { 
		switch (objectType) {
		case ObjectType.BUSH:
			consequens = conBush;
			break;
		case ObjectType.ROCK:
			consequens = conRock;
			break;
		case ObjectType.WATER:
			consequens = conWater;
			break;
		case ObjectType.PALM:
			consequens = conPalm;
			break;
		default:
			consequens = null;
		}
		return consequens;
	}

	public boolean isPickup() {
		return isPickup;
	}

	@Override
	public int compareTo(Item another) {
		if (getObjectType() > another.getObjectType()) {
			return 1;
		} else if (getObjectType() < another.getObjectType()) {
			return -1;
		} else {
			return 0;
		}
	}

	@Override
	public boolean equals(Object obj) {
		Item item = (Item) obj;
		if (item != null)
			if (item.getObjectType() == this.getObjectType())
				return true;
		return false;
	}

	@Override
	public int hashCode() {
		return -1;
	}
}
