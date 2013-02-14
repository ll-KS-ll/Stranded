package com.brogames.bro.popups;

import com.core.ks.InputObject;
import android.graphics.Canvas;
import android.graphics.Paint;

/**public class Popup<p>
 *  Class for making pop ups in the game
 */
public class Popup {

	/**protected Paint color<p>
	 *  Color for the transparency around the window
	 */
	protected Paint color = new Paint();
	/**protected boolean state<p>
	 *  State for the pop up.
	 *  @value true - pop up is about to open or should be kept open<br>
	 *  		false - pop up is closed or should be closed	
	 */
	protected boolean state = false;
	protected int popupToOpen = 0;
	/** public Popup()<p>
	 *  Initialize default pop up window. <br>
	 *  Should always be called for every pop up.
	 */
	public Popup(){
		color.setARGB(180, 0, 0, 0);
		setPopup(0);
		state = true;
	}
	
	/** public void getState()<p>
	 *  Returns the state of the pop up
	 *  @return state - returns false when it should be closed and true when to be kept open 
	 */
	public boolean getState(){
		return state;
	}
	
	/** public void tick()<p>
	 *  Updates the logics for current pop up 
	 */
	public void tick(){
		
	}
	
	/** public void show()<p>
	 *  Shows the pop up window.
	 */
	public void show(){
		state = true;
	}
	
	/** public void close()<p>
	 *  Closes the pop up
	 *  
	 */
	//Change to better names if you like, I'm not happy with them :/
	public void setPopup(int open){//set equip true if you want to open inventory next time LaunchView runs
		popupToOpen = open;
	}
	
	public int checkPopup (){//Used by LaunchView to check if inventory should be open or not
		return popupToOpen;
	}
	
	public void close(){
		state = false;
	}
	
	/** public void render(Canvas canvas)<p>
	 *  Draws graphics for pop up.<br>
	 *  Should always be called for every pop up.
	 *  @param canvas - canvas to paint graphics on
	 */
	public void render(Canvas canvas){
		canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), color);
	}
	
	/** public void processMotionEvent(InputObject input)<p>
	 *  Processes inputs for pop up
	 *  @param input - values for touched location
	 */
	public void processMotionEvent(InputObject input){
		
	}
}