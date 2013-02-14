package com.core.ks;

import com.brogames.bro.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class SplashScreen extends Activity{

	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Fullscreen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//----
		
		setContentView(R.layout.splash_screen);
		
		Thread timer = new Thread(){
			public void run(){
				try{
					sleep(3500);
				}catch(InterruptedException e){
					e.printStackTrace();
				}finally{
					Intent openStartingPoint = new Intent("com.brogames.bro.STARTMENU");
					startActivity(openStartingPoint);
				}
			}
		};
		timer.start();
	}

	protected void onPause() {
		super.onPause();
		finish();
	}
	
}