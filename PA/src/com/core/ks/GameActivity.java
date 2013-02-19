package com.core.ks;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Window;
import android.view.WindowManager;

public class GameActivity extends Activity {

	protected GameView view = null;
	protected Thread t = null;
	protected GameLoop gameloop = null;
	protected SharedPreferences settings;
	protected SharedPreferences.Editor editor;
	public static final String PATH = "GameScores";
	private boolean wasTerminated = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Fullscreen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		settings = getSharedPreferences(PATH, 0);
		settings.edit().putBoolean("active", true).commit();
		editor = settings.edit();
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

		if (gameloop.wasTerminated()) {
			settings.edit().clear().commit();
			wasTerminated = true;
		}
		gameloop = null;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onStop() {
		super.onStop();
		if(!wasTerminated)
			save();
	}

	public void save(){}
	
	public void restart() {
		Intent intent = getIntent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		save();
		finish();
		startActivity(intent);
	}
}