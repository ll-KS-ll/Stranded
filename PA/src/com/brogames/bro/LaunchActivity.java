package com.brogames.bro;

import android.os.Bundle;

import com.core.ks.GameActivity;

public class LaunchActivity extends GameActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		view = new LaunchView(this);
		setContentView(view);
	}

}
