package com.brogames.bro;

import com.brogames.bro.objecttypes.ObjectGetter;

public class Interact {

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
		
		consequences = bag.getConsequencesData(objectLayer[interactX][interactY].getObject().getObjectType());
		if (consequences != null) {
			if (consequences[0] == 1) {
				objectLayer[interactX][interactY].removeObject();
			} else if (consequences[0] == 2) {
				topLayer[interactX][interactY - 1].removeObject();
				objectLayer[interactX][interactY].removeObject();
			}

			if (consequences[1] > 0) {
				bag.insertItem(ObjectGetter.setItem(consequences[1]));
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
