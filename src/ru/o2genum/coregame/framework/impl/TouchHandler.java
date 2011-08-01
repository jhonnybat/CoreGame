package ru.o2genum.coregame.framework.impl;

import android.view.View.OnTouchListener;

public interface TouchHandler extends OnTouchListener
{
	public boolean isTouchDown();

	public float getTouchX();

	public float getTouchY();
}
