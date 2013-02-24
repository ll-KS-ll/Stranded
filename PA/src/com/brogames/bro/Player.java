package com.brogames.bro;

import java.util.LinkedList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Bundle;

import com.brogames.bro.objecttypes.ObjectType;
import com.brogames.bro.objecttypes.items.Item;
import com.core.ks.Popup;

public class Player {

	private float xPos, yPos, lxPos, lyPos;
	private float tileWidth, tileHeight;
	private float speed, vX = 0, vY = 0;
	private int dir;
	private long lastMoved = System.currentTimeMillis();
	private Bitmap bmpChar;
	private Bitmap[] bmpCharUp, bmpCharRight, bmpCharLeft, bmpCharDown;
	private int frames, animFrame;
	private int boardIndexX;
	private int boardIndexY;
	private PathFinder pathfinder;
	private LinkedList<Node> path;
	private boolean readyToMove, stop, foundPath;
	private Node node;
	private Bag bag;
	private int hunger, thirst;
	private long lastTimer;
	private static final int playerHealthUpdateTime = 3000;
	private boolean changeSection;
	private Tile[][] objectLayer;
	
	public static final int DOWN = 0;
	public static final int UP = 1;
	public static final int RIGHT = 2;
	public static final int LEFT = 3;

	public Player(Bitmap character, Bundle bundle, Popup popup, Tile[][] objectLayer) {
		tileWidth = LaunchView.TILE_WIDTH;
		tileHeight = LaunchView.TILE_HEIGHT;
		
		this.objectLayer = objectLayer;

		speed = tileWidth / 300f;

		int playerPosX = bundle.getInt("playerPosX");
		int playerPosY = bundle.getInt("playerPosY");
		
		if(playerPosX != -1)
			boardIndexX = playerPosX;
		else
			boardIndexX = 20;
		
		if(playerPosY != -1)
			boardIndexY = playerPosY;
		else
			boardIndexY = 16;

		xPos = tileWidth * boardIndexX;
		yPos = tileHeight * boardIndexY;
		lxPos = lyPos = -1;

		bmpCharUp = new Bitmap[3];
		bmpCharLeft = new Bitmap[3];
		bmpCharDown = new Bitmap[3];
		bmpCharRight = new Bitmap[3];

		for (int i = 0; i < 3; i++) {
			bmpCharUp[i] = Bitmap.createBitmap(character, (int)(tileWidth * i), 0, (int)(tileWidth), (int)(2 * tileHeight));
		}
		for (int i = 0; i < 3; i++) {
			bmpCharLeft[i] = Bitmap.createBitmap(character, (int)tileWidth * i, 2 * (int)tileHeight, (int)tileWidth, 2 * (int)tileHeight);
		}
		for (int i = 0; i < 3; i++) {
			bmpCharDown[i] = Bitmap.createBitmap(character, (int)tileWidth * i,4 * (int)tileHeight, (int)tileWidth, 2 * (int)tileHeight);
		}
		for (int i = 0; i < 3; i++) {
			bmpCharRight[i] = Bitmap.createBitmap(character, (int)tileWidth * i, (int)(6 * tileHeight), (int)tileWidth, (int)(2 * tileHeight));
		}

		bmpChar = bmpCharDown[0];
		dir = DOWN;
		animFrame = 1;
		frames = 0;
		
		bag = new Bag(bundle.getString("bag"), bundle.getInt("equip"));
		
		pathfinder = new PathFinder();
		readyToMove = true;
		stop = false;
		
		this.hunger = bundle.getInt("hunger");
		this.thirst = bundle.getInt("thirst");;
		lastTimer = System.currentTimeMillis();
		
		changeSection = false;
	}

	public void tick(Tile[][] itemLayer, Popup popup) {
		long timePassed = System.currentTimeMillis() - lastMoved;

		if (lxPos == -1 && lyPos == -1) {
			lxPos = xPos;
			lyPos = yPos;
		}

		xPos += vX * timePassed;
		yPos += vY * timePassed;
		
		if (!readyToMove() && frames >= 7) {
			switch (dir) {
			case DOWN:
				bmpChar = bmpCharDown[animFrame];
				break;
			case UP:
				bmpChar = bmpCharUp[animFrame];
				break;
			case RIGHT:
				bmpChar = bmpCharRight[animFrame];
				break;
			case LEFT:
				bmpChar = bmpCharLeft[animFrame];
				break;
			}
			frames = 0;
			animFrame++;
			if (animFrame > 2)
				animFrame = 1;
		}

		if (Math.abs(xPos - lxPos) >= tileWidth || Math.abs(yPos - lyPos) >= tileHeight) {
			switch (dir) {
			case DOWN:
				yPos = lyPos + tileHeight;
				break;
			case UP:
				yPos = lyPos - tileHeight;
				break;
			case RIGHT:
				xPos = lxPos + tileWidth;
				break;
			case LEFT:
				xPos = lxPos - tileWidth;
				break;
			}
			bmpChar = bmpCharDown[0];
			lxPos = -1;
			lyPos = -1;
			vX = 0;
			vY = 0;
			if (!path.isEmpty() && !stop) {
				itemLayer[node.getX()][node.getY()].removeWaypoint();
				node = path.removeFirst();
				if (node.getX() > boardIndexX)
					moveRight();
				if (node.getX() < boardIndexX)
					moveLeft();
				if (node.getY() > boardIndexY)
					moveDown();
				if (node.getY() < boardIndexY)
					moveUp();
			}else{
				itemLayer[node.getX()][node.getY()].removeWaypoint();
				readyToMove = true;
				stop = false;
				if(itemLayer[boardIndexX][boardIndexY].getItem() != null){
					Item pickup = itemLayer[boardIndexX][boardIndexY].getItem();
					if(pickup.isPickup()){
						bag.insertItem(pickup);
						popup.setPopup(3);
						itemLayer[boardIndexX][boardIndexY].removeItem();
					}else{
						if(pickup.getObjectType() == ObjectType.PORTAL)
							changeSection = true;
					}
				}
				
			}
				
		}

		lastMoved = System.currentTimeMillis();
		
		if (System.currentTimeMillis() - lastTimer > playerHealthUpdateTime) {
			lastTimer += playerHealthUpdateTime;
			hunger++;
			thirst+=2;
		}
	}

	public void draw(Canvas canvas, float xPos, float yPos) {
		canvas.drawBitmap(bmpChar, xPos, yPos - tileHeight, null);
		frames++;
	}

	public void move(Tile[][] itemLayer, Point endPos) {
		readyToMove = false;
		Point startPos = new Point(boardIndexX, boardIndexY);
		path = (LinkedList<Node>) pathfinder.searchPath(objectLayer, startPos, endPos);

		if (path != null && !path.isEmpty()) {	
			foundPath = true; //Yay! :D
			for(int i=0; i<path.size(); i++){
				Node node = path.get(i);
				if (i == path.size()-1)
					itemLayer[node.getX()][node.getY()].addWaypoint(true);
				else
					itemLayer[node.getX()][node.getY()].addWaypoint(false);
			}
			if(!stop){
			node = path.removeFirst();
			if (node.getX() > boardIndexX)
				moveRight();
			if (node.getX() < boardIndexX)
				moveLeft();
			if (node.getY() > boardIndexY)
				moveDown();
			if (node.getY() < boardIndexY)
				moveUp();
			}else
				stop = false;
			
		}else{
			foundPath = false; //Oh no! :(
			readyToMove = true;
		}
	}

	public void stop(Tile[][] itemLayer){
		stop = true;
		itemLayer[node.getX()][node.getY()].removeWaypoint();
		while(!path.isEmpty()){
			node = path.removeFirst();
			itemLayer[node.getX()][node.getY()].removeWaypoint();
		}
		readyToMove = true;
	}
	
	public void moveRight() {
		bmpChar = bmpCharRight[0];
		dir = RIGHT;
		vX = speed;
		vY = 0;
		boardIndexX++;
	}

	public void moveLeft() {
		bmpChar = bmpCharLeft[0];
		dir = LEFT;
		vX = -speed;
		vY = 0;
		boardIndexX--;
	}

	public void moveUp() {
		bmpChar = bmpCharUp[0];
		dir = UP;
		vX = 0;
		vY = -speed;
		boardIndexY--;
	}

	public void moveDown() {
		bmpChar = bmpCharDown[0];
		dir = DOWN;
		vX = 0;
		vY = speed;
		boardIndexY++;
	}

	public float getX() {
		return xPos;
	}

	public float getY() {
		return yPos;
	}

	public void onClick(Popup popup){
		popup.setPopup(1);
	}
	
	public int getBoardIndexX() {
		return boardIndexX;
	}

	public int getBoardIndexY() {
		return boardIndexY;
	}
	
	public void setBoardIndexX(int index) {
		boardIndexX = index;
	}

	public void setBoardIndexY(int index) {
		boardIndexY = index;
	}

	public boolean foundPath(){
		return foundPath;
	}
	
	public boolean readyToMove() {
		return readyToMove;
	}
	
	public String getBagStringData(){
		return bag.getStringData();
	}
	
	public int getThirst(){
		return thirst;
	}
	
	public int getHunger(){
		return hunger;
	}
	
	public void eat(int nutrition){
		hunger-=nutrition;
	}
	
	public void drink(int amount){
		thirst-= amount;
	}
	
	public boolean isAlive(){
		// What do we say to the god of death?
		if(thirst >= 10000 || hunger >= 10000)
			return false;
		// Not today!
		return true;
	}
	
	public boolean changeSection(){
		return changeSection;
	}
	
	public void sectionChangeProcessed(){
		changeSection = false;
	}
	
	public Bitmap getBitmap(){
		return bmpChar;
	}
	
	public Bag getBag(){
		return bag;
	}
}
