package com.brogames.bro;

import java.util.StringTokenizer;

import android.os.Bundle;

public class Layer {
	private int x, y, n;
	private Tile[][] tile = new Tile[LaunchView.MAP_WIDTH][LaunchView.MAP_HEIGHT];
	private String token;
	private StringTokenizer tokens;
	
	public Layer(String res, Bundle sizes){
		x = y = n = 0;
		tokens = new StringTokenizer(res, ",");
		while (n < LaunchView.MAP_WIDTH*LaunchView.MAP_HEIGHT) {
			token = tokens.nextToken();
			tile[x][y] = new Tile(Integer.valueOf(token), x, y, sizes);
			x++;
			if (x == LaunchView.MAP_WIDTH) {
				y++;
				x = 0;
			}
			n++;
		}
	}
	
	public Tile[][] getTiles(){
		return tile;
	}
	
	
}
