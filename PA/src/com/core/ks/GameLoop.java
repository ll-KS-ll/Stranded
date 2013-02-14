package com.core.ks;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.util.Log;

public class GameLoop implements Runnable {

	private boolean running;
	private int fps = 0;
	private final int framerate = 30;
	private GameView gameview;
	private GameActivity gameactivity;
	private Canvas canvas;
	private double unprocessed;
	private int toTick, frames;
	private long lastTime, lastTimer1, lastRenderTime;
	private int max, min;
	private int state;

	public static final int LOOP = 1;
	public static final int IDLE = 0;
	public static final int RESTART = 2;
	
	public GameLoop(GameActivity gameactivity, GameView gameview) {
		this.gameview = gameview;
		this.gameactivity = gameactivity;
	}

	@SuppressLint("NewApi")
	@Override
	public void run() {
		lastTime = System.nanoTime();
		unprocessed = 0;
		frames = 0;
		lastTimer1 = System.currentTimeMillis();
		toTick = 0;
		lastRenderTime = System.nanoTime();
		min = 999999999;
		max = 0;
		state = LOOP;
		
		while (running) {
			switch(state){
			case LOOP:
				loop();
				break;
			case IDLE:
				idle();
				break;
			case RESTART:
				gameactivity.restart();
				break;
			case -1:
				gameactivity.finish();
				break;
			}
		}
		
	}

	public void loop() {
		double nsPerTick = 1000000000.0 / framerate;
		boolean shouldRender = false;
		canvas = null;

		while (unprocessed >= 1) {
			toTick++;
			unprocessed -= 1;
		}

		int tickCount = toTick;
		if (toTick > 0 && toTick < 3) {
			tickCount = 1;
		}
		if (toTick > 20) {
			toTick = 20;
		}

		for (int i = 0; i < tickCount; i++) {
			toTick--;
			gameview.tick();
			shouldRender = true;
		}

		if (shouldRender) {
			frames++;

			gameview.render(canvas);

			long renderTime = System.nanoTime();
			int timePassed = (int) (renderTime - lastRenderTime);
			if (timePassed < min) {
				min = timePassed;
			}
			if (timePassed > max) {
				max = timePassed;
			}
			lastRenderTime = renderTime;
		}

		long now = System.nanoTime();
		unprocessed += (now - lastTime) / nsPerTick;
		lastTime = now;

		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if (System.currentTimeMillis() - lastTimer1 > 10000) {
			lastTimer1 += 10000;
			fps = frames/10;
			Log.d("FPS", Integer.toString(fps));
			frames = 0;
		}
		
		if(gameview.getState() != GameView.RUNNING)
			state = IDLE;
	}

	public void idle() {
		gameview.tick();
		if(gameview.getState() == GameView.RUNNING)
			state = LOOP;
		if(gameview.getState() == GameView.RESTART)
			state = RESTART;
		if(gameview.terminate())
			state = -1;
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public boolean wasTerminated(){
		if(state == -1)
			return true;
		return false;
	}
	
	public void start() {
		running = true;
	}
	
	public void stop() {
		running = false;
	}

}
