package ru.o2genum.coregame.game;

import java.util.*;

import ru.o2genum.coregame.framework;
import ru.o2genum.coregame.framework.impl;

// Not fully implemented!

public class World
{
	Game game;
	List<Dot> dots = new LinkedList<Dot>();

	public enum GameState {Ready, Running, Paused, GameOver}

	GameState state;
	
	public void update(float deltaTime)
	{
		// updateRunning/Ready/Paused/GameOver
	}

	public void updateRunning(float deltaTime)
	{
		generateNewDots();
		moveDots(deltaTime);
		handleCollisions();
		updateGameState(); // Game over? Or not?
	}
}
