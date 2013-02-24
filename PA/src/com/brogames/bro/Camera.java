package com.brogames.bro;

import android.graphics.Canvas;

public class Camera {
	
	private float cx, cy, px, py;
	private int tileWidth, tileHeight, screenWidth, screenHeight;
	private Tile[][] backgroundLayer;
	private Tile[][] objectLayer;
	private Tile[][] topLayer;
	private int xStart, xEnd, yStart, yEnd ,xView, yView, boardIndexX, boardIndexY;
	
	public Camera() {
		tileWidth = LaunchView.TILE_WIDTH;
		tileHeight = LaunchView.TILE_HEIGHT;
		screenWidth = LaunchView.SCREEN_WIDTH;
		screenHeight = LaunchView.SCREEN_HEIGHT;
	}

	public void draw(Canvas canvas, Player player) {
		
		for (int y = yStart; y < yEnd; y++){
			for (int x = xStart; x < xEnd; x++){
				backgroundLayer[x][y].draw(canvas, (tileWidth*x) - cx, (tileHeight*y) - cy);
				objectLayer[x][y].draw(canvas, (tileWidth*x) - cx, (tileHeight*y) - cy);
			}
		}
		
		player.draw(canvas, px, py);
		
		for (int y = yStart; y < yEnd; y++)
			for (int x = xStart; x < xEnd; x++)
				topLayer[x][y].draw(canvas, (tileWidth*x) - cx, (tileHeight*y) - cy);
	}

	public void tick(Layer[] layer, Player player) {
		cx = player.getX() - screenWidth / 2;
		cy = player.getY() - screenHeight / 2;
		
		backgroundLayer = layer[0].getTiles();
		objectLayer = layer[1].getTiles();
		topLayer = layer[2].getTiles();
		
		boardIndexX = player.getBoardIndexX();
		boardIndexY = player.getBoardIndexY();
		
		xView = (int)((screenWidth/2)/tileWidth)+2;
		yView = (int)((screenHeight/2)/tileHeight)+2;
		
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
			xEnd = screenWidth/tileWidth + 1;
		}else if (player.getX() > (LaunchView.MAP_WIDTH*tileWidth)-(screenWidth/2)){
			px = player.getX() - ((LaunchView.MAP_WIDTH*tileWidth)-(screenWidth));
			cx = (LaunchView.MAP_WIDTH*tileWidth)-(screenWidth);
			xStart = LaunchView.MAP_WIDTH - screenWidth/tileWidth - 1;
		}else
			px = (screenWidth/2);
		
		if (player.getY() < screenHeight/2){
			py = player.getY();
			cy = 0;
			yEnd = screenHeight/tileHeight + 1;
		}else if (player.getY() > (LaunchView.MAP_HEIGHT*tileHeight)-(screenHeight/2)){
			py = player.getY() - ((LaunchView.MAP_HEIGHT*tileHeight)-(screenHeight));
			cy = (LaunchView.MAP_HEIGHT*tileHeight)-(screenHeight);
			yStart = LaunchView.MAP_HEIGHT - screenHeight/tileHeight - 1;
		}else
			py = screenHeight/2;

	}

}

