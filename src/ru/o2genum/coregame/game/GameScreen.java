package ru.o2genum.coregame.game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

import android.graphics.*;
import android.util.Log;

import ru.o2genum.coregame.framework.*;

public class GameScreen extends Screen {
    long startTime = System.nanoTime();
	World world;

	Paint paint = new Paint();
	RectF rect = new RectF();
        
    public GameScreen(Game game) {
        super(game);
		world = new World(game);	
		world.renew();
		rect.top = world.core.coords.y - world.core.shieldRadius;
		rect.left = world.core.coords.x - world.core.shieldRadius;
		rect.bottom = world.core.coords.y + world.core.shieldRadius;
		rect.right = world.core.coords.x + world.core.shieldRadius;
    }
    
    @Override
    public void update(float deltaTime) {
		world.update(deltaTime);
    }

    @Override
    public void present(float deltaTime) {
    Canvas c = game.getGraphics().getCanvas();    
	c.drawARGB(255, 0, 0, 0);
	paint.setAntiAlias(true);
	paint.setStyle(Paint.Style.FILL_AND_STROKE);
	if(world.core.shieldEnergy > 0.0F)
	{
		paint.setColor(0xff003cca);
		paint.setAlpha((int) (80.0F +
				   	(255.0F - 80.0F) * world.core.shieldEnergy));
	c.drawCircle(world.core.coords.x, world.core.coords.y,
			world.core.shieldRadius, paint);
	paint.setAlpha(255);
	}
	paint.setColor(0xff19dbe2);
	c.drawCircle(world.core.coords.x, world.core.coords.y,
		   	world.core.maxRadius * world.core.health,
			paint);
	paint.setStyle(Paint.Style.STROKE);
	paint.setColor(0xffffffff);
	c.drawArc(rect, world.core.angle,
			(360.0F - world.core.GAP_ANGLE), false, paint);
	paint.setStyle(Paint.Style.FILL_AND_STROKE);
	Iterator<Dot> iterator = world.dots.iterator();
	while(iterator.hasNext())
	{
		int color = 0;
		Dot dot = iterator.next();
		if(dot.type == Dot.Type.Enemy)
			color = 0xffe2192e;
		else if(dot.type == Dot.Type.Health)
			color = 0xff19dbe2;
		else if(dot.type == Dot.Type.Shield)
			color = 0xff003cca;
		else if(dot.type == Dot.Type.Bomb)
			color = 0xffecff13;
		paint.setColor(color);
		c.drawCircle(dot.coords.x, dot.coords.y,
				dot.maxRadius * dot.energy, paint);
    }

	if(world.state == World.GameState.Running)
		; // points, etc
	else if(world.state == World.GameState.Ready)
		drawMessage("Ready? Touch the screen!", c);
	else if(world.state == World.GameState.Paused)
		drawMessage("Game is paused. Touch the screen to resume.", c);
	else if(world.state == World.GameState.GameOver)
		drawMessage("Game over! Touch the screen to restart.", c);
	}

	private void drawMessage(String message, Canvas c)
	{
		paint.setColor(0xffffffff);
		c.drawText(message, 0, 100, paint);
	}

    @Override
    public void pause() {
		world.state = World.GameState.Paused;
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }            
}
