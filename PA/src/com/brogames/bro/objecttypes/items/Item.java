package com.brogames.bro.objecttypes.items;

import java.util.Vector;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;

public class Item implements Comparable<Item> {

	protected Paint color = new Paint();
	protected Bitmap bmp = null;
	protected int objectType = -1;
	protected int[] components = { -1, -1, -1, -1 };
	protected Vector<Integer> recipe = new Vector<Integer>();
	protected int[] consequens = new int[4]; 
	protected Vector<Integer> interectableObjects = new Vector<Integer>();
	protected boolean isPickup = true;

	/*
	 * hej ni behöver en båt, en vild hund, ett svärd, tårtor, ett tält, en
	 * kompis, //Jaci
	 */

	public Item() {
		color.setColor(Color.CYAN);
	}

	public Paint getColor() {
		return color;
	}

	public Bitmap getBmp() {
		return bmp;
	}

	public int[] getComponents() {
		return components;
	}

	public Vector<Integer> getRecipe() {
		return recipe;
	}

	public int getObjectType() {
		return objectType;
	}

	public int[] getConsequences() { 
		return consequens;
	}

	public Vector<Integer> getInterectableObjects(){
		return interectableObjects;
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
