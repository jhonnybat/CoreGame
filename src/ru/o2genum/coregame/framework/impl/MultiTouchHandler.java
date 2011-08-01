package ru.o2genum.coregame.framework.impl;

import java.util.ArrayList;
import java.util.List;

import android.view.MotionEvent;
import android.view.View;
import android.util.*;

public class MultiTouchHandler implements TouchHandler {
    boolean[] isTouched = new boolean[20];
    float[] touchX = new float[20];
    float[] touchY = new float[20];
    int pointerCount = 0;

    public MultiTouchHandler(View view) {
        view.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        synchronized (this) {
	        pointerCount = event.getPointerCount();
            int action = event.getAction() & MotionEvent.ACTION_MASK;
            int pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_ID_MASK) >> MotionEvent.ACTION_POINTER_ID_SHIFT;
            int pointerId = event.getPointerId(pointerIndex);

            switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
		touchX[pointerId] = (float) (event
                        .getX(pointerId));
        touchY[pointerId] = (float) (event
                        .getY(pointerId));
		Log.d("TOUCH", "Pointer " + pointerId + " is DOWN");
                isTouched[pointerId] = true;
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL:
                touchX[pointerId] = (float) (event
                        .getX(pointerId));
                touchY[pointerId] = (float) (event
                        .getY(pointerId));
		Log.d("TOUCH", "Pointer " + pointerId + " is UP");
                isTouched[pointerId] = false;
                break;

            case MotionEvent.ACTION_MOVE:
                int pointerCount = event.getPointerCount();
                for (int i = 0; i < pointerCount; i++) {
                    pointerIndex = i;
                    pointerId = event.getPointerId(pointerIndex);
		    touchX[pointerId] = (float) (event
                            .getX(pointerId));
		    touchY[pointerId] = (float) (event
                            .getY(pointerId));
                }
                break;
            }

            return true;
        }
    }

    @Override
    public boolean isTouchDown() {
        synchronized (this) {
    		return (getMaxPointer() > -1);
    }
    }

    private int getMaxPointer()
    {
    for(int i = 20 - 1; i >= 0; i--)
    {
    if(isTouched[i])
    return i;
    }
    return -1;
    }

    @Override
    public float getTouchX() {
        synchronized (this) {
    int pointer = getMaxPointer();
            if (pointer < 0 || pointer >= 20)
                return 0;
            else
                return touchX[pointer];
        }
    }

    @Override
    public float getTouchY() {
    int pointer = getMaxPointer();
        synchronized (this) {
            if (pointer < 0 || pointer >= 20)
                return 0;
            else
                return touchY[pointer];
        }
    }
}
