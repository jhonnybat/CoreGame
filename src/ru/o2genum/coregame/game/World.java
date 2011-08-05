package ru.o2genum.coregame.game;

import java.util.*;

import ru.o2genum.coregame.framework.*;
import ru.o2genum.coregame.framework.impl.*;
import ru.o2genum.coregame.framework.Input.KeyEvent;

import android.util.*;

// Not fully implemented!

public class World
{
	Random random = new Random();
	Game game;
	public List<Dot> dots = new LinkedList<Dot>();
	public Core core = new Core();
	public float offScreenRadius;
	private float shieldCoeff = 20.0F;
	private float energyCoeff = 6.0F;
	private float deltaAngle = 0.0F;

	public enum GameState {Ready, Running, Paused, GameOver}

	public GameState state = GameState.Ready;

	private float difficulty = 0.03F; // Max 0.1F

	public World(Game game)
	{
		this.game = game;
		Graphics g = game.getGraphics();
		// Construct core
		core.coords = new VectorF((float) g.getWidth() / 2,
			   	(float) g.getHeight() / 2);
		core.shieldRadius = (float) g.getWidth() / 4;
		core.maxRadius = core.shieldRadius * 0.7F;
		core.angle = 0.0F;
		core.health = 1.0F;
		core.shieldEnergy = 0.0F;
		// Set offScreenRadius
		offScreenRadius = (float) Math.hypot((double) g.getWidth() / 2,
				(double) g.getHeight() / 2);
		// Max dot radius (when it's energy is 1.0F)
		Dot.maxRadius = core.maxRadius / 8.0F;
	}
		
	public void renew()
	{
		dots.clear();
		core.health = 1.0F;
		core.shieldEnergy = 0.0F;
		state = GameState.Ready;
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

	// Don't read this method's code! It's bad for your brain!
	private void doInput()
	{
	    float orientAngle = (360.0F - game.getInput().getAzimuth());
		if(game.getInput().isTouchDown())
		{
			double touchX = (double) game.getInput().getTouchX();
			double touchY = (double) game.getInput().getTouchY();
			core.angle = normAngle((-(float) (Math.atan2(touchX - core.coords.x,
					   	touchY - core.coords.y) / (Math.PI * 2) *
				360.0)) - 180.0F - 0.5F * Core.GAP_ANGLE);
			deltaAngle = core.angle - orientAngle;
		}
		else
		{
			core.angle = normAngle(orientAngle + deltaAngle);
		}
	}

	private void updateReady(float deltaTime)
	{
		if(game.getInput().isTouchDown() || checkMenuUp())
			state = GameState.Running;
	}
	
	private boolean checkMenuUp()
	{
		List<KeyEvent> events = game.getInput().getKeyEvents();
		Iterator<KeyEvent> i = events.iterator();
		while(i.hasNext())
		{
			KeyEvent e = i.next();
			if((e.type == KeyEvent.KEY_UP) && (e.keyCode == 
						android.view.KeyEvent.KEYCODE_MENU))
			{
				return true;
			}
		}
		return false;
	}

	private void updatePaused(float deltaTime)
	{
		if(game.getInput().isTouchDown() || checkMenuUp())
			state = GameState.Running;
	}

	private void updateGameOver(float deltaTime)
	{
		if(game.getInput().isTouchDown() || checkMenuUp())
			renew();
	}

	private void updateRunning(float deltaTime)
	{
		doInput();

		generateNewDots(8);

		handleCollisions();
		moveDots(deltaTime);
		decreaseShieldEnergy(deltaTime);
		if(checkMenuUp())
			state = GameState.Paused;
	}

	private void decreaseShieldEnergy(float deltaTime)
	{
		if(core.shieldEnergy > 0.0F)
		{
			core.shieldEnergy -= deltaTime / shieldCoeff;
			if(core.shieldEnergy < 0.0F)
			core.shieldEnergy = 0.0F;
		}
	}

	private void generateNewDots(int neededCount)
	{
		if(neededCount > dots.size())
			generateNewDot();
	}

	private void generateNewDot()
	{
		difficulty += 0.0001F;
		float linearSpeed = 10.0F * difficulty;
		double angle = random.nextDouble() * 2 * Math.PI;
		VectorF speed = new VectorF(- ((float) Math.cos(angle)) * linearSpeed,
			   	- ((float) Math.sin(angle)) * linearSpeed);
		Dot dot = new Dot();
		dot.speed = speed;
		VectorF coords =
		   	new VectorF(offScreenRadius * ((float) Math.cos(angle)),
					offScreenRadius * ((float) Math.sin(angle)));
		coords = coords.add(core.coords);
		dot.coords = coords;
		dot.energy = random.nextFloat();
		if(dot.energy <= 0.3F)
			dot.energy = 0.3F;
		float typeRand = random.nextFloat();
		Dot.Type type;
		if(typeRand >= 0.95)
			type = Dot.Type.Bomb;
		else if (typeRand >= 0.9)
			type = Dot.Type.Shield;
		else if (typeRand >= 0.8)
			type = Dot.Type.Health;
		else
			type = Dot.Type.Enemy;
		dot.type = type;
		dots.add(dot);
	}

	private void moveDots(float deltaTime)
	{
		Iterator<Dot> iterator = dots.iterator();
		while(iterator.hasNext())
		{
			Dot dot = iterator.next();
			dot.coords = dot.coords.add(dot.speed.multiply(deltaTime * 100.0F));
		}
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
		else if (lengthToCoreCenter - core.maxRadius * core.health <=
			   	dot.maxRadius * dot.energy)
			handleCollisionWithCore(dot, iterator);
	}

	private void checkCollisionWithShield(Dot dot, Iterator<Dot> iterator)
	{
		// I normalize (move into (0; 360) interval) angles
		// in some places. Don't know if it's needed.
		if(core.shieldEnergy > 0.0F)
		{
			iterator.remove();
		}
		else
		{
		VectorF v = dot.coords.substract(core.coords);
		// Pay attention at -v.y! Y-axis is inverted, 
		// because it points downwards.
		float dotAngle = (float) Math.atan2((double) v.x, (double) -v.y);
		dotAngle = dotAngle / (((float) Math.PI) * 2.0F) * 360.0F;
		dotAngle = normAngle(dotAngle);
		Log.d("LOL", "core.angle = " + core.angle + "; dotAngle = " + dotAngle);
		// For example, dotAngle = 3, and core.angle = 365
		// We need to solve this somehow:
		if(dotAngle < core.angle)
			dotAngle += 360.0F;
		// OK, and check if dotAngle is within the gap
		if(!((dotAngle > core.angle) &&
				   	(dotAngle < core.angle + core.GAP_ANGLE)))
		{
			iterator.remove();
		}
		}
	}

	private float normAngle(float angle)
	{
		float angle2 = angle;
		if(angle2 < 0.0F)
		{
			while(angle2 < 0.0F)
				angle2 += 360.0F;
		}
		else if(angle2 > 360.0F)
		{
			while(angle2 > 360.0F)
				angle2 -= 360.0F;
		}
		return angle2;
	}

	private void handleCollisionWithCore(Dot dot, Iterator<Dot> iterator)
	{
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
			core.shieldEnergy += dot.energy;
			if(core.shieldEnergy > 1.0F)
				core.shieldEnergy = 1.0F;
		}
		else if(dot.type == Dot.Type.Bomb)
		{
			explosion(dot.energy);
		}
		iterator.remove();
	}

	private void explosion(float energy)
	{
		// Boom!
	}
}
