package com.brogames.bro.popups;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Bundle;

import com.brogames.bro.Bag;
import com.brogames.bro.Player;
import com.brogames.bro.Tile;
import com.brogames.bro.objecttypes.ObjectGetter;
import com.core.ks.InputObject;
import com.core.ks.Popup;

public class Interact extends Popup {
	
	private int boardWidth, boardHeight, screenWidth, screenHeight;
	private int objWidth, objHeight;
	private int x1, x2, y1, y2;
	private Path arrowDown,arrowUp,arrowRight,arrowLeft;
	private Paint paint = new Paint();
	private Paint border = new Paint();
	@SuppressWarnings("unused")
	private RectF bounds, borderBounds, inputBounds;
	private Bag bag;
	private Tile[][] objectLayer, topLayer;
	private int boardIndexX,boardIndexY;
	private int[] consequences;
	private int interactX, interactY;

	public Interact(Bag bag, Bundle sizes, Tile[][] objectLayer, Tile[][] topLayer, Player player) {
		super(); // Call super or die!
		
		this.bag = bag;
		
		boardWidth = sizes.getInt("boardWidth");
		boardHeight = sizes.getInt("boardHeight");
		screenWidth = sizes.getInt("screenWidth");
		screenHeight = sizes.getInt("screenHeight");
		objWidth = 2 * boardWidth;
		objHeight = 2 * boardHeight;
		this.objectLayer = objectLayer;
		this.topLayer = topLayer;
		
		boardIndexX = player.getBoardIndexX();
		boardIndexY = player.getBoardIndexY();
		
		color.setARGB(0, 0, 0, 0);
		paint.setColor(Color.WHITE);
		border.setColor(Color.BLACK);
		
		x1 = screenWidth/2 - boardWidth*3;
		x2 = screenWidth/2 - boardWidth;
		y1 = screenHeight/2 - boardHeight*2;
		y2 = screenHeight/2 - boardHeight*4;
		
		
		bounds = new RectF(x1,y1,x2, y2);
		borderBounds = new RectF(x1 -3, y1 +3, x2 + 3, y2 - 3);
		
		arrowDown = new Path(); //Arrow down
		arrowDown.moveTo(screenWidth/2 - objWidth/2, screenHeight/2 + objHeight);
		arrowDown.lineTo(screenWidth/2 + objWidth, screenHeight/2 + objHeight);
		arrowDown.lineTo(screenWidth/2 + boardWidth/2, screenHeight/2 + objHeight*2);
		
		arrowUp = new Path(); //Arrow up
		arrowUp.moveTo(screenWidth/2 - objWidth/2, screenHeight/2 - boardHeight);
		arrowUp.lineTo(screenWidth/2 + objWidth, screenHeight/2 - boardHeight);
		arrowUp.lineTo(screenWidth/2 + boardWidth/2, screenHeight/2 - boardHeight*3);
		
		arrowRight = new Path(); //Arrow right
		arrowRight.moveTo(screenWidth/2 + objWidth, screenHeight/2 + objHeight);
		arrowRight.lineTo(screenWidth/2 + objWidth, screenHeight/2 - objHeight/2);
		arrowRight.lineTo(screenWidth/2 + objWidth*2, screenHeight/2 + boardHeight/2);
		
		arrowLeft = new Path(); //Arrow left
		arrowLeft.moveTo(screenWidth/2 - boardWidth, screenHeight/2 + objHeight);
		arrowLeft.lineTo(screenWidth/2 - boardWidth, screenHeight/2 - objHeight/2);
		arrowLeft.lineTo(screenWidth/2 - boardWidth*3, screenHeight/2 + boardHeight/2);
	}

	public void tick() {
		
	}

	public void render(Canvas canvas) {
		super.render(canvas); // It's proper manor to always call super.render
		
		canvas.drawPath(arrowDown, paint);
		canvas.drawPath(arrowUp, paint);
		canvas.drawPath(arrowRight, paint);
		canvas.drawPath(arrowLeft, paint);
		
		canvas.drawRect(borderBounds, border);
		canvas.drawRect(bounds, paint);
		if (bag.getEquipedItem() != null)
			canvas.drawBitmap(bag.getEquipedItem().getBmp(), x1 + boardWidth/2 , y2 + boardHeight/2, paint);
			//canvas.drawBitmap(bag.getEquipedItem().getBmp(), null,bounds, null);   //Does not work for some reason :s
	}

	public void processMotionEvent(InputObject input) {
		
		//inputBounds = new RectF((int) (input.x - 3),(int) (input.y - 3),(int) (input.x + 3), (int) (input.y + 3));
		
		if (input.x >= x1 && input.x <= x2 && input.y <= y1 && input.y >= y2) { 
			setPopup(6);
			close();
		}else if (bag.getEquipedItem() != null){
		if (input.x >= screenWidth/2 - objWidth/2 && input.x <= screenWidth/2 + objWidth && input.y <= screenHeight/2 - boardHeight && input.y >= screenHeight/2 - boardHeight*3) //Up
			interactUp();
		else if (input.x >= screenWidth/2 - objWidth/2 && input.x <= screenWidth/2 + objWidth && input.y <= screenHeight/2 + objHeight*2 && input.y >= screenHeight/2 + objHeight)//Down
			interactDown();
		else if (input.x >= screenWidth/2 + objWidth && input.x <= screenWidth/2 + objWidth*2 && input.y <= screenHeight/2 + objHeight && input.y >= screenHeight/2 - objHeight/2)//Right
			interactRight();
		else if (input.x >= screenWidth/2 - boardWidth*3 && input.x <= screenWidth/2 - boardWidth && input.y <= screenHeight/2 + objHeight && input.y >= screenHeight/2 - objHeight/2)//Left
			interactLeft();
		else
			close();
		}else
			close();
	}
	
	public void consequences(){
		if(objectLayer[interactX][interactY].getObject() == null)
			return;
			
		consequences = bag.getConsequencesData(objectLayer[interactX][interactY].getObject().getObjectType());
		if (consequences != null){
		if (consequences[0] == 1){
			objectLayer[interactX][interactY].removeObject();
		}else if (consequences[0] == 2){
			topLayer[interactX][interactY-1].removeObject();
			objectLayer[interactX][interactY].removeObject();
		}
		
		if (consequences[1] > 0){
			bag.insertItem(ObjectGetter.setItem(consequences[1]));
			setPopup(3);
		}
		close();
		}
	}
	
	public void interactDown(){
		interactX = boardIndexX;
		interactY = boardIndexY+1;
		consequences();
	}
	
	public void interactUp(){
		interactX = boardIndexX;
		interactY = boardIndexY-1;
		consequences();
	}
	
	public void interactRight(){
		interactX = boardIndexX+1;
		interactY = boardIndexY;
		consequences();
	}
	
	public void interactLeft(){
		interactX = boardIndexX-1;
		interactY = boardIndexY;
		consequences();
	}
}
	
