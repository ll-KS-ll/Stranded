package com.brogames.bro;

import java.util.StringTokenizer;

import android.os.Bundle;

public class Layer {
	private int x, y, n;
	private int width = 60, height = 40;
	private Tile[][] tile = new Tile[width][height];
	private String token;
	private StringTokenizer tokens;
	
	public Layer(String res, Bundle sizes){
		x = y = n = 0;
		tokens = new StringTokenizer(res, ",");
		while (n < 2400) {
			token = tokens.nextToken();
			tile[x][y] = new Tile(Integer.valueOf(token), x, y, sizes);
			x++;
			if (x == 60) {
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
