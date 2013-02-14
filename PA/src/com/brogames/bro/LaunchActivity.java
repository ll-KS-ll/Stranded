package com.brogames.bro;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import com.core.ks.GameActivity;
import com.core.ks.GameLoop;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

public class LaunchActivity extends GameActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle bundle = new Bundle();

		bundle.putInt("playerPosX", settings.getInt("playerPosX", -1));
		bundle.putInt("playerPosY", settings.getInt("playerPosY", -1));
		bundle.putInt("hunger", settings.getInt("hunger", 0));
		bundle.putInt("thirst", settings.getInt("thirst", 0));
		bundle.putInt("equip", settings.getInt("equip", -1));
		bundle.putString("bag", settings.getString("bag", ""));
		bundle.putString("section", settings.getString("section", "0_0"));
		bundle.putBoolean("first", settings.getBoolean("first", true));
		bundle.putBoolean("menuIsOpen", settings.getBoolean("menuIsOpen", false));
		
		view = new LaunchView(this, bundle);
		setContentView(view);
		gameloop = new GameLoop(this, view);
	}

	public void onStop() {
		super.onStop();

		if (view.getIntData("hunger") > 100 || view.getIntData("thirst") > 100)
			settings.edit().clear().commit();
	}

	public void save() {
		if (!wasTerminated) {
			// Small values
			editor = settings.edit();
			editor.putBoolean("first", false);
			editor.putBoolean("menuIsOpen", view.getBooleanData("menuIsOpen"));
			editor.putInt("playerPosX", view.getIntData("playerPosX"));
			editor.putInt("playerPosY", view.getIntData("playerPosY"));
			editor.putInt("hunger", view.getIntData("hunger"));
			editor.putInt("thirst", view.getIntData("thirst"));
			editor.putInt("equip", view.getIntData("equip"));
			editor.putString("bag", view.getStringData("bag"));
			editor.putString("section", view.getStringData("section"));
			editor.commit();
			// Whole section
			/*String filename = "section" + view.getStringData("section");
			FileOutputStream outputStream;
			try {
			  outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
			  outputStream.write(view.getStringData("save_section").getBytes());
			  outputStream.close();
			} catch (Exception e) {
			  e.printStackTrace();
			}*/
			
			String filename = "section" + view.getStringData("section");
			try {
				FileOutputStream outputStream = openFileOutput(filename, Context.MODE_PRIVATE);;
				OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
				BufferedWriter writer = new BufferedWriter(outputStreamWriter);
				
				Log.d(this.toString(), "Start saving layers");
				writer.write(view.getStringData("save_section_bg"));
				Log.d(this.toString(), "Background layer saved");
				writer.write(view.getStringData("save_section_object"));
				Log.d(this.toString(), "Object layer saved");
				writer.write(view.getStringData("save_section_item"));
				Log.d(this.toString(), "Item layer saved");
				writer.write(view.getStringData("save_section_top"));
				Log.d(this.toString(), "Top layer saved");
				
				Log.d(this.toString(), "Done saving");
				
				writer.close();
				outputStreamWriter.close();
				outputStream.close();
				
				Log.d(this.toString(), "Cleaned up buffers");
			} catch (Exception e) {
				  e.printStackTrace();
			}
			/*String filename = "section" + view.getStringData("section");
			
			try {
			  
				FileOutputStream outputStream = openFileOutput(filename, Context.MODE_PRIVATE);;
				OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
				BufferedWriter writer = new BufferedWriter(outputStreamWriter);
			  
				for (int n = 0; n < 4; n++){
					for (int y = 0; y < LaunchView.MAP_HEIGHT; y++) {
						for (int x = 0; x < LaunchView.MAP_WIDTH; x++) {
							switch(n){
							case 0:
								writer.write(view.getStringData("save_section"));
							case 1:
								writer.write(view.getStringData("save_section"));
							case 2:
								writer.write(view.getStringData("save_section"));
							case 3:
								writer.write(view.getStringData("save_section"));
							}
						}
					}
					if(n < 4)
						outputStream.write("|".getBytes());
					}
			  
				writer.close();
				outputStreamWriter.close();
				outputStream.close();
			} catch (Exception e) {
			  e.printStackTrace();
			}*/
		}
	}
}
