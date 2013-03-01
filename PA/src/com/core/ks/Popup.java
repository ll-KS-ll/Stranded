package com.core.ks;

import com.brogames.bro.LaunchView;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Class for making pop ups in the game
 */
public class Popup {

	protected Paint color = new Paint();
	private boolean state = false;
	private int popupToOpen = -1;
	protected int tileWidth, tileHeight, screenWidth, screenHeight;
	protected int x, y, width, height;
	private Paint imageObserver = new Paint(Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG);
	private Bitmap popupBitmap;
	private Canvas scaledCanvas;
	private float scale;

	/**
	 * Initialize default pop up window. <br>
	 * Please set the width and height of the pop up 
	 * through {@link setSize(int width, int height)}.
	 * Should always be called for every pop up.
	 */
	public Popup() {
		setup();
	}

	/**Initialize a pop up window with specified dimensions. <br>
	 * Super should always be called for every pop up.
	 * @param width - the specified width of the pop up 
	 * @param height - the specified height of the pop up 
	 */
	public Popup(int width, int height) {
		setup();
		setSize(width, height);
	}

	private void setup(){
		tileWidth = LaunchView.TILE_WIDTH;
		tileHeight = LaunchView.TILE_HEIGHT;
		screenWidth = LaunchView.SCREEN_WIDTH;
		screenHeight = LaunchView.SCREEN_HEIGHT;

		color.setARGB(180, 0, 0, 0);
		setPopup(-1);
		state = true;
	}
	
	/** Initialize the pop up. Is called by {@link setSize(int width, int height)}.
	 */
	public void init() {
		if (width > screenWidth || height > screenHeight) {
			float temp = (float) screenHeight / (float) (height);
			scale = (float) screenWidth / (float) (width);
			if (temp < scale)
				scale = temp;
		} else {
			scale = 1;
		}
		
		x = (int)(screenWidth - width * scale) / 2;
		y = (int)(screenHeight - height * scale) / 2;
		
		popupBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		scaledCanvas = new Canvas(popupBitmap);
		scaledCanvas.scale(scale, scale);
	}

	/** Set the size of the pop up window.*/
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
		init();
	}
	
	/** Set the location on screen for the pop up window */
	public void setLocation(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	/**Make the area around the pop up fully transparent or
	 * a bit darker. 
	 * 
	 * @param transparent - true to set full transparency.
	 */
	public void setFullTransparency(boolean transparent){
		if(transparent)
			color.setARGB(0, 0, 0, 0);
		else
			color.setARGB(180, 0, 0, 0);
	}

	public void tick() {
		// Other random code
		update();
	}

	/** Updates the logics for current pop up */
	public void update(){}
	
	public void render(Canvas canvas) {
		canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), color);

		draw(scaledCanvas);

		canvas.drawBitmap(popupBitmap, x, y, imageObserver);
	}

	/**
	 * Draws graphics for pop up.<br>
	 * Should always be called for every pop up.
	 * 
	 * @param canvas - canvas to paint graphics on
	 */
	public void draw(Canvas canvas) {}

	public void processMotionEvent(InputObject input) {
		if (input.x < x || input.x > x+width)
			close();
		else if (input.y < y || input.y > y+height)
			close();
		else{
			input.x /= scale;
			input.y /= scale;
			input.x -= x;
			input.y -= y;
			onClick(input);
		}
	}

	/** Handle click events on the pop up window.
	 * 
	 * @param input - values for touched location
	 */
	public void onClick(InputObject input) {}
	
	/**
	 * Set a specified pop up to be opened.
	 * 
	 * @param popupValue
	 *            - value for the pop up to open
	 */
	public void setPopup(int popupValue) {
		popupToOpen = popupValue;
	}

	/**
	 * Check if there is a pop up to be opened.
	 * 
	 * @return popupToOpen - value for the pop up to open
	 */
	public int checkPopup() {
		return popupToOpen;
	}
	
	/** Shows the pop up window. */
	public void show() {
		state = true;
	}
	
	/** Closes the pop up */
	public void close() {
		state = false;
	}
	
	/**
	 * public void getState()
	 * <p>
	 * Returns the state of the pop up
	 * 
	 * @return state - returns false when it should be closed and true when to
	 *         be kept open
	 */
	public boolean getState() {
		return state;
	}
	
}