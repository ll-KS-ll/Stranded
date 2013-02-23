package com.brogames.bro;

import com.brogames.bro.objecttypes.ObjectHandler;
import com.brogames.bro.objecttypes.items.Item;
import com.core.ks.Popup;

public class Interact {

	public static final int REMOVE = 0;
	public static final int REPLACE = 1;
	public static final int REMOVE_PALM = 2;
	public static final int ADD = 3;
	
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

	/* Worthless method!
	 * Could be done way better!
	 * I'm tired of this shit method and don't have time
	 * so to hell with it!
	 * 
	 *  Best regards K-S
	 */
	public void consequences(Popup popup) {
		Item tool = bag.getEquipedItem();
		int objectType = objectLayer[interactX][interactY].getObject().getObjectType();
		consequences = tool.getConsequences(objectType);
		
		if (consequences != null) {
			// What to do?
			switch (consequences[0]) {
			case REMOVE:
				objectLayer[interactX][interactY].removeObject();
				break;
			case REPLACE:
				objectLayer[interactX][interactY].addObject(consequences[2]);
			case REMOVE_PALM:
				topLayer[interactX][interactY-1].removeObject();
				objectLayer[interactX][interactY].removeObject();
				break;
			case ADD:
				objectLayer[interactX][interactY].addObject(consequences[2]);
			}
		
			// What to get?
			if (consequences[1] > 0){
				 bag.insertItem(ObjectHandler.setItem(consequences[1]));
				 popup.setPopup(3);
			}
				 
			// What to add?
			if (consequences[2] > 0)
				objectLayer[interactX][interactY].addObject(consequences[2]);
			
			// Remove equipped item?
			if (consequences[3] == 1)
				bag.removeItem(tool);
			
		}
	}

	public void down(int boardIndexX, int boardIndexY, Popup popup) {
		interactX = boardIndexX;
		interactY = boardIndexY + 1;
		consequences(popup);
	}

	public void up(int boardIndexX, int boardIndexY, Popup popup) {
		interactX = boardIndexX;
		interactY = boardIndexY - 1;
		consequences(popup);
	}

	public void right(int boardIndexX, int boardIndexY, Popup popup) {
		interactX = boardIndexX + 1;
		interactY = boardIndexY;
		consequences(popup);
	}

	public void left(int boardIndexX, int boardIndexY, Popup popup) {
		interactX = boardIndexX - 1;
		interactY = boardIndexY;
		consequences(popup);
	}
}
