package com.core.ks;

import java.util.concurrent.ArrayBlockingQueue;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class InputHandler implements OnTouchListener {

	private GameView main; 
	private ArrayBlockingQueue<InputObject> inputObjectPool;
	private GestureDetector gestureDetector;
	private boolean isSwipe = false;
	
	public InputHandler(GameView gameview) {
		main = gameview;
		createInputObjectPool();
		gestureDetector = new GestureDetector(main.getContext(), new GestureListener());
	}

	private void createInputObjectPool() {
		inputObjectPool = new ArrayBlockingQueue<InputObject>(20);
		for (int i = 0; i < 20; i++) {
			inputObjectPool.add(new InputObject(inputObjectPool));
		}
	}
	
	@Override
	public boolean onTouch(View view, MotionEvent event) {
		boolean b = gestureDetector.onTouchEvent(event);
		try {
			if(event.getAction() == MotionEvent.ACTION_UP && !isSwipe){
				InputObject input = inputObjectPool.take();
				input.useEvent(event);
				main.feedInput(input);
			}
		} catch (InterruptedException e) {}
		
		try {
			Thread.sleep(16);
		} catch (InterruptedException e) {}
		return b;
	}
	
	private final class GestureListener extends SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 20;
        private static final int SWIPE_VELOCITY_THRESHOLD = 50;

        @Override
        public boolean onDown(MotionEvent e) {
        	isSwipe = false;
        	return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            isSwipe = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    	isSwipe = true;
                    	if (diffX > 0) {
                    		main.onSwipeRight();
                        } else {
                        	main.onSwipeLeft();
                        }
                    }
                } else {
                    if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    	isSwipe = true;
                    	if (diffY > 0) {
                    		main.onSwipeBottom();
                        } else {
                        	main.onSwipeTop();
                        }
                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return false;
        }
    }

}
