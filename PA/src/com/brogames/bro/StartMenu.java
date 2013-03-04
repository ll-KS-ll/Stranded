package com.brogames.bro;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.core.ks.GameActivity;
//import android.graphics.Typeface;

public class StartMenu extends Activity implements OnClickListener{

	private Button start, score, options, exit; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		String orientation = prefs.getString("orientation", "auto");
		
		if(orientation.equals("landscape")){
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}else if(orientation.equals("portrait")){
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
		
		setContentView(R.layout.start_menu);
		
		start = (Button) findViewById(R.id.bStart);
		score = (Button) findViewById(R.id.bScore);
		options = (Button) findViewById(R.id.bOptions);
		exit = (Button) findViewById(R.id.bExit);
		
		/*Typeface font = Typeface.createFromAsset(getAssets(), "electrictoaster.TTF");
		start.setTypeface(font);
		score.setTypeface(font);
		options.setTypeface(font);
		exit.setTypeface(font);*/
		
		start.setOnClickListener(this);
		score.setOnClickListener(this);
		options.setOnClickListener(this);
		exit.setOnClickListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		SharedPreferences settings = getSharedPreferences(GameActivity.PATH, 0);
		if(settings.getBoolean("active", false))
			start.setText("Continue");
		else
			start.setText("New Game");
	}
	
	@Override
	public void onClick(View view) {
		switch(view.getId()){
		case R.id.bStart:
			Intent openStartingPoint = new Intent("com.brogames.bro.LAUNCHACTIVITY");
			startActivity(openStartingPoint);
			break;
		case R.id.bScore:
			Intent openScore = new Intent("com.brogames.bro.SCORE");
			startActivity(openScore);
			break;
		case R.id.bOptions:
			Intent openOptions = new Intent("com.brogames.bro.SETTINGS");
			startActivity(openOptions);
			break;
		case R.id.bExit:
			finish();
			break;
		}
	}

	
}
