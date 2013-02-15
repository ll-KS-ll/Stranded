package com.brogames.bro;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

public class Map extends Activity {

	private static int layerCounter;
	private static String FILENAME, line, res;
	private static boolean mapValue = false;
	private static Layer[] layer = new Layer[4];

	public static void loadMap(Context context, String file, boolean first, Bundle sizes) {
		res = "";
		layerCounter = 0;
		FILENAME = "section" + file + ".tmx";
		
		try{
			if (first){
				
				InputStream in = context.getResources().getAssets().open(FILENAME);
				InputStreamReader input = new InputStreamReader(in);
				BufferedReader buffreader = new BufferedReader(input);
				while ((line = buffreader.readLine()) != null) {
					if (line.equals("</data>")) {
						layer[layerCounter] = new Layer(res, sizes);
						layerCounter++;
						mapValue = false;
						res = "";
					}
					if (mapValue == true)
						res += line;
					if (line.equals("  <data encoding=\"csv\">"))
						mapValue = true;
				}
				in.close();
			}else{
				FileInputStream in = context.openFileInput(FILENAME);
				InputStreamReader input = new InputStreamReader(in);
				BufferedReader buffreader = new BufferedReader(input);
				res = buffreader.readLine();
				in.close();

				StringTokenizer tokens = new StringTokenizer(res, "|");
				for (int n = 0; n < 4; n++) {
					layer[n] = new Layer(tokens.nextToken(), sizes);
				}
			}
		}catch(IOException ex){
			Log.e("Map", "Couldn't open section. \n" + ex);
		}
		
		
	}

	public static Layer[] getLayers() {
		return layer;
	}

	public static Layer getLayer(int layerIndex) {
		return layer[layerIndex];

	}

	public static int MapLenght() {
		return layerCounter;
	}
}