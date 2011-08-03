package ru.o2genum.coregame.game;

public class Dot
{
	public enum Type {Enemy, Health, 
		Shield, // Protects the core 
		Bomb} // Destroys all points within defined radius

	public Type type;
	public VectorF speed;
	public float energy; // Max 1.0F
	public VectorF coords;
	public static float maxRadius;
	public static final float GAP_ANGLE = 90.0F;
}
