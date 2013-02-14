package com.brogames.bro.popups;

import com.brogames.bro.LaunchView;
import com.core.ks.InputObject;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;

public class GameOverScreen extends Popup{

	LaunchView launchView;
	
	public GameOverScreen(LaunchView lv){
		super();
		launchView = lv;
	}
	
	@Override
	public void tick(){
		// I'm dead and can't do anything :(
	}
	
	@Override
	public void render(Canvas canvas){
		super.render(canvas);
		Paint color = new Paint();
		color.setARGB(150, 0, 0, 0);
		canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), color);
		
		Paint text = new Paint();
		text.setAntiAlias(true);
		text.setColor(Color.RED);
		text.setTextAlign(Align.CENTER);
		text.setTextSize(45);
		canvas.drawText("Game Over", canvas.getWidth()/2, canvas.getHeight()/2, text);
		text.setColor(Color.WHITE);
		text.setTextAlign(Align.CENTER);
		text.setTextSize(20);
		canvas.drawText("(Press anywhere to quit)", canvas.getWidth()/2, canvas.getHeight()/2 + 45, text);
	}
	
	@Override
	public void processMotionEvent(InputObject input){
		// Terminate the whole ducking game!
		// It wanted to change the f-word to ducking
		launchView.stop();
	}
}
