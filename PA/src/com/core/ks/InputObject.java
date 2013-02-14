package com.core.ks;

import java.util.concurrent.ArrayBlockingQueue;
import android.view.MotionEvent;

public class InputObject {
	public static final byte EVENT_TYPE_TOUCH = 1;
	public static final int ACTION_TOUCH_DOWN = 2;
	public static final int ACTION_TOUCH_MOVE = 3;
	public static final int ACTION_TOUCH_UP = 4;
	public ArrayBlockingQueue<InputObject> pool;
	public byte eventType;
	public long time;
	public int action;
	public int keyCode;
	public float x;
	public float y;

	public InputObject(ArrayBlockingQueue<InputObject> pool) {
		this.pool = pool;
	}

	public void useEvent(MotionEvent event) {
		eventType = EVENT_TYPE_TOUCH;
		int a = event.getAction();
		switch (a) {
		case MotionEvent.ACTION_DOWN:
			action = ACTION_TOUCH_DOWN;
		break;
		case MotionEvent.ACTION_MOVE:
			action = ACTION_TOUCH_MOVE;
			break;
		case MotionEvent.ACTION_UP:
			action = ACTION_TOUCH_UP;
			break;
		default:
			action = 0;
		}
		time = event.getEventTime();
		x = event.getX();
		y = event.getY();
	}

	public void returnToPool() {
		pool.add(this);
	}
}