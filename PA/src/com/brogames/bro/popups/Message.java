package com.brogames.bro.popups;

import java.util.StringTokenizer;
import com.core.ks.InputObject;
import com.core.ks.Popup;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;

public class Message extends Popup{

	private Paint paint = new Paint();
	private Paint bg = new Paint();
	private Paint border = new Paint();
	private String msg;
	private int screenWidth, screenHeight, rows = 0;
	private float x1, x2, y1, y2, width;
	@SuppressWarnings("unused")
	private RectF bounds, borderBounds; //Is borderBounds used somewhere? Otherwise please remove it :)
	Path path = new Path();
	String[] textArray = new String [15]; //Array that holds every row
     
	public Message(String message, int screenWidth, int screenHeight){
		super();
		paint.setColor(Color.BLACK);
		paint.setTextSize(40);
		paint.setAntiAlias(true);
		paint.setTextAlign(Align.LEFT);
		
		bg.setColor(Color.WHITE);
		bg.setStyle(Style.FILL);
		border.setColor(Color.BLACK);
		
		msg = message;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		width = paint.measureText(msg);//Get the width of the string(msg)
		
		//The code that wraps the text! It was actually quite hard to make! :O
		if (width>screenWidth*0.6f){
			width = screenWidth*0.6f;
			StringTokenizer tokens = new StringTokenizer(msg, " ");
			String token = tokens.nextToken();
			textArray[rows] = token;
			while (tokens.hasMoreTokens()) {
				token = tokens.nextToken();
				if (paint.measureText(textArray[rows] + " " + token) > screenWidth*0.6f){
					rows++;
					textArray[rows] = token;
				}else{
					if (textArray[rows] == null){
						textArray[rows] = "";
					}
					textArray[rows] += " " + token;
				}
				}
		}else{
			textArray[rows] = msg;
		}
		
		x1 = screenWidth/2 - width*0.6f;
		x2 = screenWidth/2 + width*0.6f;
		y1 = screenHeight/2 - 80 - 50*(rows);
		y2 = screenHeight/2 + 65;
		
		bounds = new RectF(x1, y1, x2, y2);
		//borderBounds = new RectF(x1-3, y1 -3, x2 + 3, y2 + 3);
		
		//The coordinates for the triangle in the speech bubble
	     path.moveTo(screenWidth/2+width/4, screenHeight/2);
	     path.lineTo(screenWidth/2+width/2, screenHeight/2 - 10);
	     path.lineTo(screenWidth/2+width/2 + 20, screenHeight/2 + 150);
	}
	
	@Override
	public void tick() {
		super.tick();
	}

	@Override
	public void render(Canvas canvas) {
		super.render(canvas);
		
		//Frame around text
			canvas.drawRoundRect(bounds, 50, 50, bg);
			//canvas.drawOval(borderBounds, border);
			//canvas.drawOval(bounds, bg);
		canvas.drawPath(path, bg);// Triangle ;)
		//Maybe draw a talk bubble instead, that would be cool and the person who makes that possible is also cool! ^^
		for (int i = 0; i < (rows+1); i++ )
			if (textArray[rows-i] != null)
			canvas.drawText(textArray[rows-i],screenWidth/2 - (width/2) , screenHeight/2 - 45*i , paint);
	}

	@Override
	public void processMotionEvent(InputObject input) {
		super.processMotionEvent(input);
		close();
	}
}