package com.brogames.bro;

import java.util.StringTokenizer;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Bundle;

import com.brogames.bro.popups.GameOverScreen;
import com.brogames.bro.popups.Inventory;
import com.brogames.bro.popups.Message;
import com.brogames.bro.popups.PickupNotification;
import com.brogames.bro.popups.PlayerPopup;
import com.core.ks.GameView;
import com.core.ks.InputObject;

public class LaunchView extends GameView {

	public static final int MAP_WIDTH = 60;
	public static final int MAP_HEIGHT = 40;
	public static int TILE_WIDTH, TILE_HEIGHT, SCREEN_WIDTH, SCREEN_HEIGHT;
	
	private int tileWidth, tileHeight, screenHeight, screenWidth;
	private String current_section, new_section;
	private Layer[] layer;
	private Tile[][] backgroundLayer;
	private Tile[][] objectLayer;
	private Tile[][] topLayer;
	@SuppressWarnings("unused")
	// It's actually used retard! -.-
	private ImageGetter getImage;
	private Camera camera;
	private Menu menu;
	private Player player;
	
	// Constructor
	public LaunchView(Context context) {
		super(context);

		Bundle bundle = new Bundle();
		bundle.putInt("playerPosX", savedData.getInt("playerPosX", -1));
		bundle.putInt("playerPosY", savedData.getInt("playerPosY", -1));
		bundle.putInt("hunger", savedData.getInt("hunger", 0));
		bundle.putInt("thirst", savedData.getInt("thirst", 0));
		bundle.putInt("equip", savedData.getInt("equip", -1));
		bundle.putInt("stamina", savedData.getInt("stamina", Player.MAX_STAMINA));
		bundle.putString("bag", savedData.getString("bag", ""));
		
		tileWidth = TILE_WIDTH = Math.round(32.0f * getContext().getResources().getDisplayMetrics().density);
		tileHeight = TILE_HEIGHT = Math.round(32.0f * getContext().getResources().getDisplayMetrics().density);
		screenWidth = SCREEN_WIDTH = getContext().getResources().getDisplayMetrics().widthPixels;
		screenHeight = SCREEN_HEIGHT = getContext().getResources().getDisplayMetrics().heightPixels;

		getImage = new ImageGetter(getResources());

		current_section = new_section = savedData.getString("section", "0_0");
		Map.loadMap(context, current_section);
		layer = Map.getLayers();
		backgroundLayer = layer[0].getTiles();
		objectLayer = layer[1].getTiles();
		topLayer = layer[2].getTiles();

		Bitmap bmpChar = BitmapFactory.decodeResource(getResources(), R.drawable.character);
		Bitmap menuSheet = BitmapFactory.decodeResource(getResources(), R.drawable.ui);
		player = new Player(bmpChar, bundle, popup, objectLayer, topLayer);
		camera = new Camera();
		menu = new Menu(menuSheet, savedData.getInt("equip", -1));
		bmpChar.recycle();
		menuSheet.recycle();

		if (savedData.getBoolean("menuIsOpen", false))
			menu.open();
	}

	// Update
	public void update() {
		super.update();
		player.tick(objectLayer, popup);
		camera.tick(layer, player);
		menu.tick(player.getBag().getEquipedItem(), player.getHunger(), player.getThirst());

		if (player.changeSection()) {
			StringTokenizer token = new StringTokenizer(current_section, "_");
			int x = Integer.valueOf(token.nextToken());
			int y = Integer.valueOf(token.nextToken());
			if (player.getBoardIndexX() <= 1) {
				x--;
				player.setBoardIndexX(MAP_WIDTH - 2);
			} else if (player.getBoardIndexX() >= MAP_WIDTH - 2) {
				x++;
				player.setBoardIndexX(1);
			} else if (player.getBoardIndexY() <= 1) {
				y--;
				player.setBoardIndexY(MAP_HEIGHT - 2);
			} else if (player.getBoardIndexY() >= MAP_HEIGHT - 2) {
				y++;
				player.setBoardIndexY(1);
			}
			new_section = x + "_" + y;
			player.sectionChangeProcessed();
			restart();
		}

		if (!player.isAlive())
			popup.setPopup(4);
	}

	// Draw graphics
	@Override
	public void render(Canvas canvas) {
		camera.draw(canvas, player);
		menu.render(canvas);
	}

	// Input
	public void onClick(InputObject input) {
		if (menu.isOpen()) {
			if (getHeight() - input.y < tileHeight || input.x < tileWidth * 2
					&& input.y > getHeight() - tileHeight * 3)
				menu.processInput(input, popup);
			else
				move(input);
		} else {
			if (getWidth() - input.x < tileWidth
					&& getHeight() - input.y < tileHeight)
				menu.open();
			else
				move(input);
		}
	}

	protected void onSwipeRight() {
		player.interactRight(popup);
	}

	protected void onSwipeLeft() {
		player.interactLeft(popup);
	}

	protected void onSwipeTop() {
		player.interactUp(popup);
	}

	protected void onSwipeBottom() {
		player.interactDown(popup);
	}

	public void move(InputObject input) {
		if (player.readyToMove()) {
			int x, y;
			if (player.getX() < screenWidth / 2)
				x = (int) (input.x / tileWidth);
			else if (player.getX() > (LaunchView.MAP_WIDTH * tileWidth) - (screenWidth / 2))
				x = (int) (LaunchView.MAP_WIDTH - (screenWidth / tileWidth) + input.x / tileWidth);
			else
				x = (int) (((input.x - screenWidth / 2) + player.getX()) / tileWidth);

			if (player.getY() < screenHeight / 2)
				y = (int) (input.y / tileHeight);
			else if (player.getY() > (LaunchView.MAP_HEIGHT * tileHeight)
					- (screenHeight / 2))
				y = (int) (LaunchView.MAP_HEIGHT - (screenHeight / tileHeight) + input.y
						/ tileHeight);
			else
				y = (int) (((input.y - screenHeight / 2) + player.getY()) / tileHeight);

			if (x >= 0 && x < MAP_WIDTH && y >= 0 && y < MAP_HEIGHT)
				if (!objectLayer[x][y].isObstacle())
					if (x == player.getBoardIndexX()
							&& y == player.getBoardIndexY()) {
						player.onClick(popup);
					} else {
						Point target = new Point(x, y);
						player.move(objectLayer, target);
						if (!player.foundPath())
							popup = new Message("I can't see a way.",
									getWidth(), getHeight());
					}
		} else {
			player.stop(objectLayer);
		}
	}

	public void checkPopup() {
		switch (popup.checkPopup()) {
		case 1: // Opens player info screen
			popup = new PlayerPopup(getResources(), player);
			break;
		case 2: // Opens a player tiered message
			popup = new Message("I'm to tired right now.", getWidth(), getHeight());
			break;
		case 3: // Opens PickupNotigication
			popup = new PickupNotification(player.getBag().ItemFound());
			break;
		case 4: // Opens GameOver-screen
			popup = new GameOverScreen(this);
			if (!player.readyToMove())
				player.stop(objectLayer);
			break;
		case 5: // Opens a message from Menu
			popup = new Message(menu.getMessage(), getWidth(), getHeight());
			break;
		case 6: // Opens inventory
			popup = new Inventory(getResources(), player.getBag(), objectLayer[player.getBoardIndexX()][player.getBoardIndexY()]);
			break;
		}
	}

	@Override
	public void save(SharedPreferences.Editor editor){
		editor.putBoolean("first", false);
		editor.putBoolean("menuIsOpen", menu.isOpen());
		editor.putInt("playerPosX", player.getBoardIndexX());
		editor.putInt("playerPosY", player.getBoardIndexY());
		editor.putInt("hunger", player.getHunger());
		editor.putInt("thirst", player.getThirst());
		editor.putInt("equip", player.getBag().getIntData());
		editor.putInt("stamina", player.getStamina());
		editor.putString("bag", player.getBagStringData());
		editor.putString("section", new_section);
		saveFile("section" + current_section + ".tmx");
	}
	
	public void saveFile(String fileName) {
		openFile(fileName);
		for (int n = 0; n < 4; n++) {
			try {
				if (n > 0)
					writer.write("|");
				for (int y = 0; y < MAP_HEIGHT; y++) {
					for (int x = 0; x < MAP_WIDTH; x++) {
						if (n == 0)
							writer.write(backgroundLayer[x][y].getObjectType()
									+ ",");
						if (n == 1)
							writer.write(objectLayer[x][y].getObjectType()
									+ ",");
						if (n == 2)
							writer.write(topLayer[x][y].getObjectType() + ",");
					}
				}
			} catch (Exception ex) {
			}
		}
		closeFile();
	}

}
