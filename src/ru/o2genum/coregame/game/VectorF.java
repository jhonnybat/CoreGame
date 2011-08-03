package ru.o2genum.coregame.game;

import android.graphics.PointF;

public class VectorF extends PointF
{
	public VectorF add(VectorF vector)
	{
		return new VectorF(this.x + vector.x, this.y + vector.y);
	}

	public VectorF multiply(float factor)
	{
		return new VectorF(this.x * factor, this.y * factor);
	}

	public VectorF divide(float factor)
	{
		return multiply(1.0F / factor);
	}

	public VectorF substract(VectorF vector)
	{
		return new VectorF(this.x - vector.x, this.y - vector.y);
	}
}
