package com.brogames.bro;

import java.util.Vector;

import com.brogames.bro.objecttypes.ObjectGetter;
import com.brogames.bro.objecttypes.ObjectType;
import com.brogames.bro.objecttypes.items.Item;

public class Interact {

	public static final int REMOVE_OBJECT = 0;
	public static final int REPLACE_OBJECT = 1;
	public static final int REMOVE_MULTIPLE = 2;
	public static final int ADD_OBJECT = 3;
	
	private Bag bag;
	private Tile[][] objectLayer, topLayer;
	private int[] consequences;
	private int interactX, interactY;

	public Interact(Player player, Tile[][] objectLayer, Tile[][] topLayer) {
		super(); // Call super or die!

		this.bag = player.getBag();

		this.objectLayer = objectLayer;
		this.topLayer = topLayer;

	}

	public void consequences() {
		if (objectLayer[interactX][interactY].getObject() == null)
			return;
		
		Item tool = bag.getEquipedItem();
		int object;
		if (objectLayer[interactX][interactY].getObject() != null)
			object = objectLayer[interactX][interactY].getObject().getObjectType();
		else
			object = ObjectType.GRASS;
		consequences = tool.getConsequences();
		Vector<Integer> interactableObjects = tool.getInterectableObjects();
		boolean match = false;
		
		for(int n=0; n < interactableObjects.size(); n++){
			if(object == interactableObjects.elementAt(n)){
				match = true;
			}
		}
		
		if (consequences != null && match) {

			// What to do?
			switch (consequences[0]) {
			case REMOVE_OBJECT:
				objectLayer[interactX][interactY].removeObject();
				break;
			case REPLACE_OBJECT:
				objectLayer[interactX][interactY].add(consequences[2]);
				return;
			case REMOVE_MULTIPLE:
				topLayer[interactX][interactY-1].removeObject();
				objectLayer[interactX][interactY].removeObject();
				break;
			case ADD_OBJECT:
				objectLayer[interactX][interactY].add(consequences[2]);
				return;
			}
		
			// What to get?
			if (consequences[1] > 0){
				 bag.insertItem(ObjectGetter.setItem(consequences[1]));
			}
			
			// What to add?
			if (consequences[2] > 0){
				objectLayer[interactX][interactY].add(consequences[2]);
			}
			
			// Remove equipped item?
			if (consequences[3] == 1){
				// Remove the item from bag
			}
			
		}
	}

	public void down(int boardIndexX, int boardIndexY) {
		interactX = boardIndexX;
		interactY = boardIndexY + 1;
		consequences();
	}

	public void up(int boardIndexX, int boardIndexY) {
		interactX = boardIndexX;
		interactY = boardIndexY - 1;
		consequences();
	}

	public void right(int boardIndexX, int boardIndexY) {
		interactX = boardIndexX + 1;
		interactY = boardIndexY;
		consequences();
	}

	public void left(int boardIndexX, int boardIndexY) {
		interactX = boardIndexX - 1;
		interactY = boardIndexY;
		consequences();
	}
}
