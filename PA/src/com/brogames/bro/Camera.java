package com.brogames.bro;

import android.graphics.Canvas;
import android.os.Bundle;

public class Camera {
	
	private float cx, cy, px, py;
	private int boardWidth, boardHeight, screenWidth, screenHeight;
	private Tile[][] backgroundLayer;
	private Tile[][] objectLayer;
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
			}
		}
		
		player.draw(canvas, px, py);
		
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
		topLayer = layer[2].getTiles();
		
		boardIndexX = player.getBoardIndexX();
		boardIndexY = player.getBoardIndexY();
		
		xView = (int)((screenWidth/2)/boardWidth)+2;
		yView = (int)((screenHeight/2)/boardHeight)+2;
		
		if (boardIndexX - xView > 0)
			xStart = boardIndexX - xView;
		else
			xStart = 0;	
		if (boardIndexX + xView < LaunchView.MAP_WIDTH)
			xEnd = boardIndexX + xView;
		else
			xEnd = LaunchView.MAP_WIDTH;
		if (boardIndexY - yView > 0)
			yStart = boardIndexY - yView;
		else
			yStart = 0; 
		if (boardIndexY + yView < LaunchView.MAP_HEIGHT)
			yEnd = boardIndexY + yView;
		else
			yEnd = LaunchView.MAP_HEIGHT;
		
		if (player.getX() < screenWidth/2){
			px = player.getX();
			cx = 0;
			xEnd = screenWidth/boardWidth + 1;
		}else if (player.getX() > (LaunchView.MAP_WIDTH*boardWidth)-(screenWidth/2)){
			px = player.getX() - ((LaunchView.MAP_WIDTH*boardWidth)-(screenWidth));
			cx = (LaunchView.MAP_WIDTH*boardWidth)-(screenWidth);
			xStart = LaunchView.MAP_WIDTH - screenWidth/boardWidth - 1;
		}else
			px = (screenWidth/2);
		
		if (player.getY() < screenHeight/2){
			py = player.getY();
			cy = 0;
			yEnd = screenHeight/boardHeight + 1;
		}else if (player.getY() > (LaunchView.MAP_HEIGHT*boardHeight)-(screenHeight/2)){
			py = player.getY() - ((LaunchView.MAP_HEIGHT*boardHeight)-(screenHeight));
			cy = (LaunchView.MAP_HEIGHT*boardHeight)-(screenHeight);
			yStart = LaunchView.MAP_HEIGHT - screenHeight/boardHeight - 1;
		}else
			py = screenHeight/2;

	}

}

