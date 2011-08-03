package ru.o2genum.coregame.game;

import java.util.*;

import ru.o2genum.coregame.framework;
import ru.o2genum.coregame.framework.impl;

// Not fully implemented!

public class World
{
	Game game;
	public List<Dot> dots = new LinkedList<Dot>();
	public Core core = new Core();
	public float offScreenRadius;

	public enum GameState {Ready, Running, Paused, GameOver}

	GameState state = GameState.Ready;

	public World(Game game)
	{
		this.game = game;
		Graphics g = game.getGraphics();
		// Construct core
		core.coords = new VectorF((float) g.getWidth() / 2,
			   	(float) g.getHeight() / 2);
		core.shieldRadius = (float) g.getWidth() / 4;
		core.maxRadius = core.shieldRadius * 0.7;
		core.angle = 0.0F;
		core.health = 1.0F;
		core.shieldEnergy = 0.0F;
		// Set offScreenRadius
		offScreenRadius = (float) Math.hypot((double) g.getWidth() / 2,
				(double) g.getHeight() / 2);
		// Max dot radius (when it's energy is 1.0F)
		Dot.maxRadius = core.maxRadius / 6.0F;
	}
		
	
	public void update(float deltaTime)
	{
		if(state == GameState.Ready)
			updateReady(deltaTime);
		if(state == GameState.Running)
			updateRunning(deltaTime);
		if(state == GameState.Paused)
			updatePaused(deltaTime);
		if(state == GameState.GameOver)
			updateGameOver(deltaTime);
	}

	public void pause()
	{
		if(state == GameState.Running)
			state = GameState.Paused;
	}

	private void updateRunning(float deltaTime)
	{
		if(dots.size() <= 7)
		generateNewDots();

		handleCollisions();
		moveDots(deltaTime);
	}

	private void moveDots(float deltaTime)
	{
		// Move dots
	}

	private void handleCollisions()
	{
		Iterator<Dot> iterator = dots.iterator();
		while(iterator.hasNext())
		{
			handleCollision(iterator.next(), iterator);
		}
	}

	private void handleCollision(Dot dot, Iterator<Dot> iterator)
	{
		float lengthToCoreCenter = dot.coords.substract(core.coords).length(); 
		if(Math.abs(lengthToCoreCenter - 
					core.shieldRadius) <= dot.maxRadius * dot.energy)
			checkCollisionWithShield(dot, iterator);
		else if (lengthToCoreCenter - core.maxRadius * health <=
			   	dot.maxRadius * dot.energy)
			handleCollisionWithCore(dot, iterator);
	}

	private void checkCollisionWithShield(Dot dot, Iterator<Dot> iterator)
	{
		VectorF v = core.coords.substract(dot.coords);
		float dotAngle = (float) atan2((double) v.x, (double) v.y);
		dotAngle = dotAngle / ((float) Math.PI) * 360.0F;
		dotAngle %= 360.0F;

		if(dotAngle > core.angle && dotAngle < core.angle + 90.0F)
		{
			iterator.remove();
		}
	}

	private void handleCollisionWithCore(Dot dot, Iterator<Dot> iterator)
	{
		float energyCoeff = 6.0;
		if(dot.type == Dot.Type.Enemy)
		{
		core.health -= dot.energy / energyCoeff;
		if(core.health < 0.0F)
		{
			state = GameState.GameOver;
			core.health = 0.0F;
		}
		}
		else if (dot.type == Dot.Type.Health)
		{
		core.health += dot.energy / energyCoeff;
		if(core.health > 1.0F)	
		{
			core.health = 1.0F;
		}
		}
		else if(dot.type == Dot.Type.Shield)
		{
			core.shieldEnergy += dot.energy / energyCoeff;
			if(core.shieldEnergy > 1.0F)
				core.shieldEnergy = 1.0F;
		}
		else if(dot.type == Dot.Type.Bomb)
		{
			explosion(dot.energy)
		}
		iterator.remove();
	}

	private void explosion(float energy)
	{
		// Boom!
	}
}
