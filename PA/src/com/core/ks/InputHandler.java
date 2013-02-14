package com.core.ks;

import java.util.concurrent.ArrayBlockingQueue;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class InputHandler implements OnTouchListener {

	private GameView main; 
	private ArrayBlockingQueue<InputObject> inputObjectPool;

	public InputHandler(GameView gameview) {
		main = gameview;
		createInputObjectPool();
	}

	private void createInputObjectPool() {
		inputObjectPool = new ArrayBlockingQueue<InputObject>(20);
		for (int i = 0; i < 20; i++) {
			inputObjectPool.add(new InputObject(inputObjectPool));
		}
	}
	
	@Override
	public boolean onTouch(View view, MotionEvent event) {
		try {
			InputObject input = inputObjectPool.take();
			input.useEvent(event);
			main.feedInput(input);
		} catch (InterruptedException e) {}
		
		try {
			Thread.sleep(16);
		} catch (InterruptedException e) {}
		return false;
	}
	
}
