package ru.o2genum.coregame.game;

public class Dot
{
	public enum Type {Enemy, Health, 
		Shield, // Protects the core 
		Bomb} // Destroys all points within defined radius

	Type type;
	VectorF speed;
	float energy;
	VectorF coordinates;
	float radius;
}
