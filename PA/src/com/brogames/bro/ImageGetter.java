package com.brogames.bro;

import com.brogames.bro.objecttypes.ObjectType;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

public class ImageGetter {

	private static Bitmap objBitmap, itemBitmap, bitmap;
	private static int bw, bh;

	public ImageGetter(Context context, Bundle boardSize) {
		objBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.environment);
		itemBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.items);
		bw = boardSize.getInt("boardWidth");
		bh = boardSize.getInt("boardHeight");
	}
	
	/**
	 * 
	 * @param isItem - true if it's an item image to get, false otherwise
	 * @param type - Item or object type for the item or object
	 * @param frame - 
	 * @return A bitmap image for that item or object type. 
	 */
	public static Bitmap getImage(boolean isItem, int type, int frame){
		int row, col;
		
		if (isItem){
			type -= ObjectType.FIRST_GRID;
			row = (int) Math.ceil(type/10);
			if (row*10 == type){
				row = row - 1;
				col = 9;
			}else
				col = type-row*10+frame-1;
			bitmap = itemBitmap;
		}else{
			row = (int) Math.ceil(type/30);
			if (row*30 == type){
				row = row-1;
				col = 29;
			}else
				col = type-row*30+frame-1;
			bitmap = objBitmap;
		}
		Bitmap bmp = null;
		String id = Integer.toString(type+frame);
		if (MemoryCache.get(id) == null){
				MemoryCache.put(id,Bitmap.createBitmap(bitmap, bw*col, bh * row, bw, bh));
			bmp = MemoryCache.get(id);
		}else{
			bmp = MemoryCache.get(id);
		}
		return bmp;
	}
	
	public static Bitmap[] getImageAnim(int type, int frames){
		Bitmap[] bmp = new Bitmap[frames];
		int row, col;
		row = (int) Math.ceil(type/30);
		col = type-row*30-1;
		
		for(int i=0; i<bmp.length; i++){
			if (MemoryCache.get(Integer.toString(type+i)) == null){
				MemoryCache.put(Integer.toString(type+i),Bitmap.createBitmap(objBitmap, bw*(i+col), bh * row, bw, bh));
				bmp[i] = MemoryCache.get(Integer.toString(type+i));
			}else{
				bmp[i] = MemoryCache.get(Integer.toString(type+i));
			}
		}
		return bmp;
	}
	
}
