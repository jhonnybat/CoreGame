package ru.o2genum.coregame.game;

import android.graphics.PointF;

public class VectorF extends PointF
{
	public void add(VectorF vector)
	{
		this.set(this.x + vector.x, this.y + vector.y);
	}

	public void multiply(float factor)
	{
		this.set(this.x * factor, this.y * factor);
	}

	public void divide(float factor)
	{
		this.multiply(1.0F / factor);
	}

	public void substract(VectorF vector)
	{
		this.add(vector.negate());
	}
}
