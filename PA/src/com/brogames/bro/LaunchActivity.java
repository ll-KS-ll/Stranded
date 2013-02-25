package com.brogames.bro;

import android.os.Bundle;

import com.core.ks.GameActivity;
import com.core.ks.GameLoop;

public class LaunchActivity extends GameActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		view = new LaunchView(this);
		setContentView(view);
		gameloop = new GameLoop(this, view);
	}

}
