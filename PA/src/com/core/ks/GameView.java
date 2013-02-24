package com.core.ks;

import java.io.BufferedWriter;
import java.util.concurrent.ArrayBlockingQueue;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class GameView extends SurfaceView {

	// Core stuff
	protected SurfaceHolder holder;
	protected Paint background = new Paint();
	public static final int PAUSED = 0;
	public static final int RUNNING = 1;
	public static final int STOP = 2;
	public static final int RESTART = 3;
	private int state = GameView.RUNNING;
	// Input
	private ArrayBlockingQueue<InputObject> inputQueue = new ArrayBlockingQueue<InputObject>(20);
	private Object inputQueueMutex = new Object();
	protected float eventX, eventY, eventLX, eventLY = -1;
	// Pop up
	protected Popup popup;
	private boolean popIsUp = false;
	
	public GameView(Context context) {
		super(context);
		setKeepScreenOn(true);
		holder = getHolder(); // SurfaceHolder
		this.setOnTouchListener(new InputHandler(this)); // Input
		background.setColor(Color.BLACK);
		popup = new Popup();
		popup.close();
	}

	/** Update core logics*/
	public void tick() {
		switch(state){
		case PAUSED: 
			processInput(); // Update inputs
			break;
		case RUNNING:
			processInput(); // Update inputs
			update();
			break;
		case STOP:
			processInput(); // Update inputs
			break;
		case RESTART:
			break;
		}
	}
	
	/** Update game logic. */
	public void update(){
		popIsUp = popup.getState();
		if (popIsUp)
			popup.tick();
		
		checkPopup();
	}

	/**Check if there is a pop up window to open.*/
	public void checkPopup(){}
	
	/** Render method called by GameLoop. 
	 * Don't override this method, see {@link public void render(Canvas canvas)} instead.
	 * If the holder isn't valid yet then overriding this will cause an runtime error.
	 * 
	 * @param canvas - canvas to render graphics on 
	 */
	public void renderGraphics(Canvas canvas) {
		if (holder.getSurface().isValid()) {
			canvas = holder.lockCanvas(); // Prepare canvas to be used

			// Repaint all with black
			canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), background);
			
			render(canvas);
			
			if (popIsUp)
				popup.render(canvas);
			
			holder.unlockCanvasAndPost(canvas); // Show the painted canvas
		}
	}
	
	/** This method should be overridden with the game's render.
	 *  Here is where all graphics should be rendered and then shown
	 *  on screen. 
	 * @param canvas - canvas graphics will be rendered on.
	 */
	public void render(Canvas canvas){}

	
	/**Feed inputs for later processing. 
	 * @param input - InputObject to add to processing
	 */
	public void feedInput(InputObject input) {
		synchronized (inputQueueMutex) {
			try {
				inputQueue.put(input);
			} catch (InterruptedException e) {}
		}
	}
	

	private void processInput() {
		synchronized (inputQueueMutex) {
			ArrayBlockingQueue<InputObject> inputQueue = this.inputQueue;
			while (!inputQueue.isEmpty()) {
				try {
					InputObject input = inputQueue.take();
					processMotionEvent(input);
					input.returnToPool();
				} catch (InterruptedException e) {}
			}
		}
	}

	/** protected void processMotionEvent(InputObject input) <p>
	 *  
	 *  Process inputs made by user
	 *  
	 *  @param input - An InputObject with the input data.
	 */
	private void processMotionEvent(InputObject input) {
		if (popIsUp) {
			popup.processMotionEvent(input);
		}else{
			onClick(input);
		}
	}
	
	/**Process what to do with touch inputs on the device's screen.
	 * 
	 * @param input - An InputObject with the input data.
	 */
	public void onClick(InputObject input){}
	
	/**Handle swipe gesture to the right. 
	 */
	protected void onSwipeRight() {
    	Log.e("InputHandler", "Right swipe");
    }

	/**Handle swipe gesture to the right. 
	 */
	protected void onSwipeLeft() {
    	Log.e("InputHandler", "Left swipe");
    }

	/**Handle swipe gesture to the right. 
	 */
	protected void onSwipeTop() {
    	Log.e("InputHandler", "Top swipe");
    }

	/**Handle swipe gesture to the right. 
	 */
	protected void onSwipeBottom() {
    	Log.e("InputHandler", "Bottom swipe");
    }
	
	/** Pause the game until public void resume() is called*/
	public void pause(){state = PAUSED;}
	/** Resumes the game from paused state*/
	public void resume(){state = RUNNING;}
	/** Stops the game and quits*/
	public void stop(){state = STOP;}
	/** Restarts the game*/
	public void restart(){state = RESTART;}
	
	/** Get in which state the game is
	 * 	<br>
	 *  Example: running or paused 
	 *  @return Game state*/
	public int getState(){return state;}
	
	
	/** public boolean terminate()
	 * 	<br>
	 *  Check if the game should be terminated.
	 *  @return true if game should be terminated else false*/
	public boolean terminate(){
		if(state == STOP)
			return true;
		return false;
	}
	
	
	/** public int getIntData(String key)
	 * 	<br>
	 *  Get integer values to save
	 *  @param key - key for the value to save
	 *  @return value to save*/
	public int getIntData(String key){return -1;}
	
	
	/** public String getStringData(String key)
	 * 	<br>
	 *  Get string values to save
	 *  @param key - key for the value to save
	 *  @return value to save*/
	public String getStringData(String key){return "";}
	
	
	/** public int getBooleanData(String key)<p>
	 * 	<br>
	 *  Get booleans to save
	 *  @param key - key for the value to save
	 *  @return value to save*/
	public boolean getBooleanData(String key){return false;}
	
	
	/** Save the data to an file that can be loaded when application restarts
	 * 
	 * @param writer - A BufferedWriter to write data to an file
	 */
	public void saveFile(BufferedWriter writer){}
	
}
