package com.brogames.bro;

import android.app.Activity;
import com.brogames.bro.R;
import com.core.ks.GameActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
//import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;

public class StartMenu extends Activity implements OnClickListener{

	private Button start, score, options, exit; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
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
		
		score.setEnabled(false);
		options.setEnabled(false);
		score.setPaintFlags(score.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
		options.setPaintFlags(options.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
		
		start.setOnClickListener(this);
		//score.setOnClickListener(this);
		//options.setOnClickListener(this);
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
			Intent openOptions = new Intent("com.brogames.bro.OPTIONS");
			startActivity(openOptions);
			break;
		case R.id.bExit:
			finish();
			break;
		}
	}

	
}
