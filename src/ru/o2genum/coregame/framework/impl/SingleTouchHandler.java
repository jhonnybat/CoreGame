package ru.o2genum.coregame.framework.impl;

import java.util.ArrayList;
import java.util.List;

import android.view.MotionEvent;
import android.view.View;

public class SingleTouchHandler implements TouchHandler {
    boolean isTouched;
    float touchX;
    float touchY;
    
    public SingleTouchHandler(View view) {
        view.setOnTouchListener(this);
    }
    
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        synchronized(this) {
            switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isTouched = true;
                break;
            case MotionEvent.ACTION_MOVE:
                isTouched = true;
                break;
            case MotionEvent.ACTION_CANCEL:                
            case MotionEvent.ACTION_UP:
                isTouched = false;
                break;
            }
            
            touchX = event.getX();
	    touchY = event.getY();                     
            
            return true;
        }
    }

    @Override
    public boolean isTouchDown() {
        synchronized(this) {
                return isTouched;
        }
    }

    @Override
    public float getTouchX() {
        synchronized(this) {
            return touchX;
        }
    }

    @Override
    public float getTouchY() {
        synchronized(this) {
            return touchY;
        }
    }
}
