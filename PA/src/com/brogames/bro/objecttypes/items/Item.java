package com.brogames.bro.objecttypes.items;

import java.util.Vector;
import com.brogames.bro.objecttypes.TileObject;

public class Item extends TileObject implements Comparable<Item> {

	protected int[] components = { -1, -1, -1, -1 };
	protected Vector<Integer> recipe = new Vector<Integer>();
	protected int[] consequences = new int[4]; 
	protected boolean isPickup = true;
	protected int category, consumeValue;
	
	public static final int MISC = 0;
	public static final int FOOD = 1;
	public static final int FLUID = 2;
	public static final int TOOL = 3;
	public static final int COMPONENT = 4;
	public static final int TOOL_AND_COMPONENT = 5;
	
	 /* hej ni behöver en båt, en vild hund, ett svärd, tårtor, ett tält, en
	 * kompis, //Jaci */

	public Item(int objectType) {
		super(objectType);
		isItem = true;
		isObstacle = false;
		category = MISC;
		consumeValue = 0;
	}

	public int[] getComponents() {
		return components;
	}

	public Vector<Integer> getRecipe() {
		return recipe;
	}

	/**Get an array with the consequences of interacting with an specified object.
	 * <br><br>
	 * Array positions tasks list:
	 * <p>
	 * 1. What to do? ex. Interact.REMOVE <br>
	 * 2. What to get into bag? ex. ObjectType.LOG <br>
	 * 3. What to add as a new object into interacting tile? ex. ObjectType.FIRE <br>
	 * 4. Remove to interacting tool? ex. 1 for removing it or 0 to keep it<br>
	 * <br>
	 * For no interaction on a task put -1 as value.
	 * 
	 * @param objectType - the object to interact with's type value
	 * @return consequences - an four sized array with values for what to do
	 */
	public int[] getConsequences(int objectType) { 
		consequences[0] = -1;
		consequences[1] = -1;
		consequences[2] = -1;
		consequences[3] = -1;
		return consequences;
	}
	
	public boolean isPickup() {
		return isPickup;
	}
	
	public int getCategory(){
		return category;
	}

	public int getConsumeValue(){
		return consumeValue;
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
