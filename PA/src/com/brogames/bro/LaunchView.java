package com.brogames.bro;

import java.io.BufferedWriter;
import java.util.StringTokenizer;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import com.brogames.bro.popups.GameOverScreen;
import com.brogames.bro.popups.Interact;
import com.brogames.bro.popups.Inventory;
import com.brogames.bro.popups.Message;
import com.brogames.bro.popups.PickupAnimation;
import com.core.ks.GameView;
import com.core.ks.InputObject;

public class LaunchView extends GameView {

	public static final int MAP_WIDTH = 60;
	public static final int MAP_HEIGHT = 40;

	private int tileWidth, tileHeight;
	private Bundle sizes;
	private String current_section, new_section; 
	private Player player;
	private Camera camera;
	private Layer[] layer;
	private Tile[][] backgroundLayer;
	private Tile[][] objectLayer;
	private Tile[][] itemLayer;
	private Tile[][] topLayer;
	@SuppressWarnings("unused")
	// It's actually used retard! -.-
	private ImageGetter getImage;
	private Menu menu;
	// Filip stuff, should be in Bundle sizes.
	private int screenWidth, screenHeight;
	private int x, y;
	
	public LaunchView(Context context) {super(context);}
	
	// Constructor
	public LaunchView(Context context, Bundle bundle) {
		super(context);

		tileWidth = Math.round(32.0f * getContext().getResources().getDisplayMetrics().density);
		tileHeight = Math.round(32.0f * getContext().getResources().getDisplayMetrics().density);

		screenHeight = getContext().getResources().getDisplayMetrics().heightPixels;
		screenWidth = getContext().getResources().getDisplayMetrics().widthPixels;
		
		sizes = new Bundle();
		sizes.putInt("boardWidth", tileWidth);
		sizes.putInt("boardHeight", tileHeight);
		sizes.putInt("screenWidth", screenWidth);
		sizes.putInt("screenHeight", screenHeight);
		
		getImage = new ImageGetter(context, sizes);

		current_section = new_section = bundle.getString("section");
		Map.loadMap(context, current_section, sizes);
		layer = Map.getLayers();
		backgroundLayer = layer[0].getTiles();
		objectLayer = layer[1].getTiles();
		itemLayer =  layer[2].getTiles();
		topLayer = layer[3].getTiles();
		
		Bitmap bmpChar = BitmapFactory.decodeResource(getResources(), R.drawable.character);
		Bitmap menuSheet = BitmapFactory.decodeResource(getResources(), R.drawable.ui);
		player = new Player(bmpChar, sizes, bundle, popup, objectLayer);
		camera = new Camera(sizes);
		menu = new Menu(menuSheet, sizes);
		bmpChar.recycle();
		menuSheet.recycle();
		
		if(bundle.getBoolean("menuIsOpen"))
			menu.open();
	}

	// Update
	public void tick() {
		super.tick();
		player.tick(itemLayer, popup);
		camera.tick(layer, player, getWidth(), getHeight());
		menu.tick(player.getHunger(), player.getThirst());
		
		if(player.changeSection()){
			StringTokenizer token = new StringTokenizer(current_section, "_");
			int x = Integer.valueOf(token.nextToken());
			int y = Integer.valueOf(token.nextToken());
			if(player.getBoardIndexX() <= 1){
				x--;
				player.setBoardIndexX(MAP_WIDTH - 2);
			}else if(player.getBoardIndexX() >= MAP_WIDTH - 2){
				x++;
				player.setBoardIndexX(1);
			}else if(player.getBoardIndexY() <= 1){
				y--;
				player.setBoardIndexY(MAP_HEIGHT - 2);
			}else if(player.getBoardIndexY() >= MAP_HEIGHT - 2){
				y++;
				player.setBoardIndexY(1);
			}
			new_section = x + "_" + y;
			player.sectionChangeProcessed();
			restart();
		}
		
		if(!player.isAlive())
			popup.setPopup(4);
	}

	// Draw graphics
	@Override
	public void render(Canvas canvas) {
		canvas = getReadyCanvas(canvas);
		if (ready()) {
			canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), background);
			camera.draw(canvas, player);
			menu.render(canvas);
		}
		postCanvas(canvas);
	}

	// Input
	public void onClick(InputObject input) {
		if(menu.isOpen()){
			if(getHeight() - input.y < tileHeight || input.x < tileWidth*2 && input.y > getHeight() - tileHeight*3)
				menu.processInput(input, popup);
			else
				move(input);
		}else{
			if (getWidth() - input.x < tileWidth && getHeight() - input.y < tileHeight)
				menu.open();
			else
				move(input);
		}
	}
	
	protected void onSwipeRight() {
    	Log.e("InputHandler", "Right swipe");
    }

	protected void onSwipeLeft() {
    	Log.e("InputHandler", "Left swipe");
    }

	protected void onSwipeTop() {
    	Log.e("InputHandler", "Top swipe");
    }

	protected void onSwipeBottom() {
    	Log.e("InputHandler", "Bottom swipe");
    }
	
	public void move(InputObject input){
			if (player.readyToMove()) {
			
			if (player.getX() < screenWidth/2)
				x = (int) (input.x/tileWidth);
			else if (player.getX() > (LaunchView.MAP_WIDTH*tileWidth)-(screenWidth/2))
				x = (int) (LaunchView.MAP_WIDTH-(screenWidth/tileWidth) + input.x/tileWidth);
			else
				x = (int) (((input.x - screenWidth / 2) + player.getX())/tileWidth);
			
			if (player.getY() < screenHeight/2)
				y = (int) (input.y/tileHeight);
			else if (player.getY() > (LaunchView.MAP_HEIGHT*tileHeight)-(screenHeight/2))
				y = (int) (LaunchView.MAP_HEIGHT-(screenHeight/tileHeight) + input.y/tileHeight);
			else
				y = (int) (((input.y - screenHeight / 2) + player.getY())/tileHeight);
			
			if (x >= 0 && x < MAP_WIDTH && y >=  0 && y < MAP_HEIGHT)
				if (!objectLayer[x][y].isObstacle()) 
					if(x == player.getBoardIndexX() && y == player.getBoardIndexY()){
						popup = new Interact(player.getBag(), sizes, objectLayer, topLayer, player);
					}else{
						Point target = new Point(x, y);
						player.move(itemLayer, target);
						if (!player.foundPath())
							popup = new Message("I can't see a way.", getWidth(), getHeight());
					}
		} else {
			player.stop(itemLayer);
		}
	}
	
	public void checkPopup(){
		switch (popup.checkPopup()){
		case 1: // Opens interact
			popup = new Interact(player.getBag(), sizes, objectLayer, topLayer, player);
			break;
		case 3: // Opens PickupAnimation
			popup = new PickupAnimation(player.getBag().ItemFound(), sizes);
			break;
		case 4: // Opens GameOver-screen
			popup = new GameOverScreen(this);
			if (!player.readyToMove())
				player.stop(itemLayer);
			break;
		case 5: // Opens a message from Menu
			popup = new Message(menu.getMessage(), getWidth(), getHeight());
			break;
		case 6: // Opens inventory
			popup = new Inventory(getResources(), player.getBag(), sizes, itemLayer[player.getBoardIndexX()][player.getBoardIndexY()]);
			break;
		}
	}
	
	public int getIntData(String key){
		if(key.equals("playerPosX"))
			return player.getBoardIndexX();
		if(key.equals("playerPosY"))
			return player.getBoardIndexY();
		if(key.equals("hunger"))
			return player.getHunger();
		if(key.equals("thirst"))
			return player.getThirst();
		if(key.equals("equip"))
			return player.getBag().getIntData();
		return -1;
	}
	
	public String getStringData(String key){
		if(key.equals("bag"))
			return player.getBagStringData();
		if(key.equals("current_section"))
			return current_section;
		if(key.equals("new_section"))
			return new_section;
		return "";
	}
	
	public boolean getBooleanData(String key){
		if(key.equals("menuIsOpen"))
			return menu.isOpen();
		return false;
	}
	
	public void saveFile(BufferedWriter writer){

		for (int n = 0; n < 4; n++){
			try{
				if (n>0)
					writer.write("|");
				for (int y = 0; y < MAP_HEIGHT; y++) {
					for (int x = 0; x < MAP_WIDTH; x++) {
						if (n==0)
							writer.write(backgroundLayer[x][y].getObjectType() + ",");
						if(n==1)
							writer.write(objectLayer[x][y].getObjectType() + ",");
						if(n==2)
							writer.write(itemLayer[x][y].getObjectType() + ",");
						if(n==3)
							writer.write(topLayer[x][y].getObjectType() + ",");
					}
				}
			}catch(Exception ex){}
		}
	}
	
	public Bundle getSizesBundle(){
		return sizes;
	}
}
