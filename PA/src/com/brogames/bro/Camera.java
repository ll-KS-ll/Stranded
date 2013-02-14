package com.brogames.bro;

import android.graphics.Canvas;
import android.os.Bundle;

public class Camera {
	
	private float cx, cy;
	private int boardWidth, boardHeight, screenWidth, screenHeight;
	private Tile[][] backgroundLayer;
	private Tile[][] objectLayer;
	private Tile[][] itemLayer;
	private Tile[][] topLayer;
	private int xStart, xEnd, yStart, yEnd ,xView, yView, boardIndexX, boardIndexY;
	
	public Camera(Bundle boardSize) {
		this.boardWidth = boardSize.getInt("boardWidth");
		this.boardHeight = boardSize.getInt("boardHeight");
		
	}

	public void draw(Canvas canvas, Player player) {
		
		for (int y = yStart; y < yEnd; y++){
			for (int x = xStart; x < xEnd; x++){
				backgroundLayer[x][y].draw(canvas, (boardWidth*x) - cx, (boardHeight*y) - cy);
				objectLayer[x][y].draw(canvas, (boardWidth*x) - cx, (boardHeight*y) - cy);
				itemLayer[x][y].draw(canvas, (boardWidth*x) - cx, (boardHeight*y) - cy);
			}
		}
		
		player.draw(canvas, screenWidth / 2, screenHeight / 2);
		
		for (int y = yStart; y < yEnd; y++)
			for (int x = xStart; x < xEnd; x++)
				topLayer[x][y].draw(canvas, (boardWidth*x) - cx, (boardHeight*y) - cy);
	}

	public void tick(Layer[] layer, Player player, int width, int height) {
		screenWidth = width;
		screenHeight = height;
		
		cx = player.getX() - screenWidth / 2;
		cy = player.getY() - screenHeight / 2;
		
		backgroundLayer = layer[0].getTiles();
		objectLayer = layer[1].getTiles();
		itemLayer = layer[2].getTiles();
		topLayer = layer[3].getTiles();
		
		boardIndexX = player.getBoardIndexX();
		boardIndexY = player.getBoardIndexY();
		
		xView = (int)((screenWidth/2)/boardWidth)+ 2;
		yView = (int)((screenHeight/2)/boardHeight)+ 2;
		
		if (boardIndexX - xView <= 0)
			xStart = 0;
		else
			xStart = boardIndexX - xView;
		
		if (boardIndexX + xView >= LaunchView.MAP_WIDTH)
			xEnd = LaunchView.MAP_WIDTH;
		else
			xEnd = boardIndexX + xView;
		
		if (boardIndexY - yView <= 0)
			yStart = 0;
		else
			yStart = boardIndexY - yView;
		
		if (boardIndexY + yView >= LaunchView.MAP_HEIGHT)
			yEnd = LaunchView.MAP_HEIGHT;
		else
			yEnd = boardIndexY + yView;
	}

}
