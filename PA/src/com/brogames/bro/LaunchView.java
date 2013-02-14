package com.brogames.bro;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Bundle;

import com.brogames.bro.popups.GameOverScreen;
import com.brogames.bro.popups.Interact;
import com.brogames.bro.popups.Inventory;
import com.brogames.bro.popups.Message;
import com.brogames.bro.popups.PickupAnimation;
import com.brogames.bro.popups.Popup;
import com.core.ks.GameView;
import com.core.ks.InputObject;

@SuppressLint("ViewConstructor")
public class LaunchView extends GameView {

	public static final int MAP_WIDTH = 60;
	public static final int MAP_HEIGHT = 40;

	private int tileWidth, tileHeight;
	private Bundle sizes;
	private String section; 
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
	private Popup popup;
	private boolean popIsUp = false;
	
	// Constructor
	public LaunchView(Context context, Bundle bundle) {
		super(context);

		tileWidth = Math.round(32.0f * getContext().getResources().getDisplayMetrics().density);
		tileHeight = Math.round(32.0f * getContext().getResources().getDisplayMetrics().density);

		int screenHeight = getContext().getResources().getDisplayMetrics().heightPixels;
		int screenWidth = getContext().getResources().getDisplayMetrics().widthPixels;
		
		sizes = new Bundle();
		sizes.putInt("boardWidth", tileWidth);
		sizes.putInt("boardHeight", tileHeight);
		sizes.putInt("screenWidth", screenWidth);
		sizes.putInt("screenHeight", screenHeight);
		
		getImage = new ImageGetter(context, sizes);

		section = bundle.getString("section");
		Map.loadMap(context, "section" + section, bundle.getBoolean("first"), sizes);
		layer = Map.getLayers();
		backgroundLayer = layer[0].getTiles();
		objectLayer = layer[1].getTiles();
		itemLayer =  layer[2].getTiles();
		topLayer = layer[3].getTiles();
		
		Bitmap bmpChar = BitmapFactory.decodeResource(getResources(), R.drawable.character);
		Bitmap menuSheet = BitmapFactory.decodeResource(getResources(), R.drawable.ui);
		popup = new Popup();
		popup.close();
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
		
		popIsUp = popup.getState();
		if (popIsUp)
			popup.tick();
		
		switch (popup.checkPopup()){
			case 1: // Opens interact
				popup = new Interact(player.getBag(), sizes, objectLayer, player);
				break;
			case 2: // Opens inventory for equipping
				popup = new Inventory(getResources(), player.getBag(), sizes, itemLayer[player.getBoardIndexX()][player.getBoardIndexY()]);
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
			case 6: // Opens inventory for crafting
				popup = new Inventory(getResources(), player.getBag(), sizes, itemLayer[player.getBoardIndexX()][player.getBoardIndexY()]);
				break;
		}
		
		if(player.changeSection()){
			int x = Integer.valueOf(section.substring(0, 1));
			int y = Integer.valueOf(section.substring(2, 3));
			if(player.getBoardIndexX() == 0){
				x--;
				player.setBoardIndexX(MAP_WIDTH - 2);
			}else if(player.getBoardIndexX() == MAP_WIDTH - 1){
				x++;
				player.setBoardIndexX(1);
			}else if(player.getBoardIndexY() == 0){
				y--;
				player.setBoardIndexY(MAP_HEIGHT - 2);
			}else if(player.getBoardIndexY() == MAP_HEIGHT - 1){
				y++;
				player.setBoardIndexY(1);
			}
			section = x + "_" + y;
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

			if (popIsUp)
				popup.render(canvas);
		}
		postCanvas(canvas);
	}

	// Input
	protected void processMotionEvent(InputObject input) {
		if (!popIsUp) {
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
			
		} else
			popup.processMotionEvent(input);
	}
	
	public void move(InputObject input){
		if (player.readyToMove()) { 
			int x = (int) (((input.x - this.getWidth() / 2) + player.getX())/tileWidth);
			int y = (int) (((input.y - this.getHeight() / 2) + player.getY())/tileHeight);
			
			if (x >= 0 && x < MAP_WIDTH && y >=  0 && y < MAP_HEIGHT)
				if (!objectLayer[x][y].isObstacle()) 
					if(x == player.getBoardIndexX() && y == player.getBoardIndexY()){
						popup = new Interact(player.getBag(), sizes, objectLayer, player);
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
		if(key.equals("section"))
			return section;
		if(key.equals("save_section_bg"))
			return saveBackgroundLayer();
		if(key.equals("save_section_object"))
			return saveObjectLayer();
		if(key.equals("save_section_item"))
			return saveItemLayer();
		if(key.equals("save_section_top"))
			return saveTopLayer();
		return "";
	}
	
	public boolean getBooleanData(String key){
		if(key.equals("menuIsOpen"))
			return menu.isOpen();
		return false;
	}
	
	public String saveBackgroundLayer(){
		String s = "";
		for (int y = 0; y < MAP_HEIGHT; y++) {
			for (int x = 0; x < MAP_WIDTH; x++) {
				s += backgroundLayer[x][y].getObjectType() + ",";
			}
		}
		s = s.substring(0, s.length()-1);
		s += "|";
		return s;
	}
	
	public String saveObjectLayer(){
		String s = "";
		for (int y = 0; y < MAP_HEIGHT; y++) {
			for (int x = 0; x < MAP_WIDTH; x++) {
				s += objectLayer[x][y].getObjectType() + ",";
			}
		}
		s = s.substring(0, s.length()-1);
		s += "|";
		return s;
	}
	
	public String saveItemLayer(){
		String s = "";
		for (int y = 0; y < MAP_HEIGHT; y++) {
			for (int x = 0; x < MAP_WIDTH; x++) {
				s += itemLayer[x][y].getObjectType() + ",";
			}
		}
		s = s.substring(0, s.length()-1);
		s += "|";
		return s;
	}
	
	public String saveTopLayer(){
		String s = "";
		for (int y = 0; y < MAP_HEIGHT; y++) {
			for (int x = 0; x < MAP_WIDTH; x++) {
				s += topLayer[x][y].getObjectType() + ",";
			}
		}
		s = s.substring(0, s.length()-1);
		return s;
	}
	
	// Old file saving
	public String getSection(){
		String s = "";
		for (int n = 0; n < 4; n++){
			if (n>0)
				s += "|";
			for (int y = 0; y < MAP_HEIGHT; y++) {
				for (int x = 0; x < MAP_WIDTH; x++) {
					if (n==0)
					s += backgroundLayer[x][y].getObjectType() + ",";
					if(n==1)
						s += objectLayer[x][y].getObjectType() + ",";
					if(n==2)
						s += itemLayer[x][y].getObjectType() + ",";
					if(n==3)
						s += topLayer[x][y].getObjectType() + ",";
				}
			}
		}
		
		return s.substring(0, s.length()-1);
	}
	
	// Failed file saving try
	public String saveSectionToFile(int n, int x, int y){
		switch(n){
		case 0:
			return backgroundLayer[x][y].getObjectType() + ",";	
		case 1:
			return objectLayer[x][y].getObjectType() + ",";
		case 2:
			return itemLayer[x][y].getObjectType() + ",";
		case 3:
			return topLayer[x][y].getObjectType() + ",";
		}
		return "";
	}
	
	public Bundle getSizesBundle(){
		return sizes;
	}
}