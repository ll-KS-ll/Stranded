package com.core.ks;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
		gameloop = new GameLoop(this, view);
		t = new Thread(gameloop);
		gameloop.start();
		t.start();
	}

	@Override
	protected void onPause() {
		super.onPause();

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
}