package ru.o2genum.coregame.framework;

public interface Input
{
	public boolean isKeyPressed(int keyCode);

	public float getTouchX();
	
	public float getTouchY();

	public boolean isTouched();

	public float getAzimuth();
}
