package com.core.ks;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class GameActivity extends Activity {

	protected GameView view = null;
	protected Thread t = null;
	protected GameLoop gameloop = null;
	public static final String PATH = "GameScores";
	private boolean isGameOver = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}

	@Override
	protected void onResume() {
		super.onResume();
		view.resume();
		gameloop = new GameLoop();
		t = new Thread(gameloop);
		gameloop.start();
		t.start();
	}

	@Override
	protected void onPause() {
		super.onPause();

		view.pause();
		gameloop.stop();
		while (true) {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			break;
		}
		t = null;

		if(gameloop.isGameOver())
			isGameOver = true;
		
		gameloop = null;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onStop() {
		super.onStop();
		if(!isGameOver)
			view.stop();
	}

	/**
	 * Restarts the activity without any animation. 
	 */
	public void restart() {
		Intent intent = getIntent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		view.stop();
		finish();
		startActivity(intent);
	}
	
	public class GameLoop implements Runnable{
		
		private boolean running;
		private int fps = 0;
		private static final int framerate = 30;
		private Canvas canvas;
		private double unprocessed;
		private int toTick, frames;
		private long lastTime, lastTimer1, lastRenderTime;
		private int max, min;
		private int state;

		public static final int IDLE = 0;
		public static final int LOOP = 1;
		public static final int RESTART = 2;
		public static final int GAME_OVER = 3;
		
		public GameLoop() {}

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
					restart();
					break;
				case GAME_OVER:
					finish();
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
				view.tick();
				shouldRender = true;
			}

			if (shouldRender) {
				frames++;

				view.renderGraphics(canvas);

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
			
			if(view.getState() != GameView.RUNNING)
				state = IDLE;
		}

		public void idle() {
			view.tick();
			if(view.getState() == GameView.RUNNING)
				state = LOOP;
			if(view.getState() == GameView.RESTART)
				state = RESTART;
			if(view.getState() == GameView.GAME_OVER)
				state = GAME_OVER;
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		public boolean isGameOver(){
			if(state == GAME_OVER)
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
}