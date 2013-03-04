package com.brogames.bro;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import com.core.ks.GameActivity;

public class LaunchActivity extends GameActivity {

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		String orientation = prefs.getString("orientation", "auto");
		
		if(orientation.equals("landscape")){
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}else if(orientation.equals("portrait")){
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
		
		view = new LaunchView(this);
		setContentView(view);
	}

}
