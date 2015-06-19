package ru.o2genum.coregame.game;

import java.util.*;

import android.graphics.*;
import android.graphics.drawable.*;
import android.content.*;

import ru.o2genum.coregame.framework.*;
import ru.o2genum.coregame.R;

public class GameScreen extends Screen {
    long startTime = System.nanoTime();
	World world;

	Paint paint = new Paint();
	RectF rect = new RectF();

	GradientDrawable gradient;

	Context context;
        
    public GameScreen(Game game) {
        super(game);
		context = (Context) game;
		world = new World(game);	
		world.renew();
		rect.top = world.core.coords.y - world.core.shieldRadius;
		rect.left = world.core.coords.x - world.core.shieldRadius;
		rect.bottom = world.core.coords.y + world.core.shieldRadius;
		rect.right = world.core.coords.x + world.core.shieldRadius;

		paint.setAntiAlias(true);
		paint.setStrokeWidth(0.0F);
		
		// This gradient looks quite smooth, but not perfect
		gradient = new GradientDrawable(GradientDrawable.Orientation.TL_BR,
				new int[]{R.color.black_blue, R.color.blue_black});
		gradient.setGradientType(GradientDrawable.RADIAL_GRADIENT);
		gradient.setGradientRadius((int) world.offScreenRadius);
		gradient.setDither(false);
		gradient.setGradientCenter(0.5F, 0.5F);
		gradient.setBounds(new Rect(0, 0, game.getGraphics().getWidth(),
				   	game.getGraphics().getHeight()));
		
		paint.setTextSize(((float)game.getGraphics().getHeight()) / 16F);
		paint.setTextAlign(Paint.Align.CENTER);
    }
    
    @Override
    public void update(float deltaTime) {
		world.update(deltaTime);
    }

    @Override
    public void present(float deltaTime) {
    Canvas c = game.getGraphics().getCanvas();    
	gradient.draw(c);
	paint.setStyle(Paint.Style.FILL_AND_STROKE);
	if(world.core.shieldEnergy > 0.0F)
	{
		paint.setColor(R.color.blue);
		paint.setAlpha((int) (80.0F +
				   	(255.0F - 80.0F) * world.core.shieldEnergy));
		c.drawCircle(world.core.coords.x, world.core.coords.y,
			world.core.shieldRadius, paint);
		paint.setAlpha(255);
	}
	paint.setColor(R.color.light_blue);
	c.drawCircle(world.core.coords.x, world.core.coords.y,
		   	world.core.maxRadius * world.core.health,
			paint);
	paint.setStyle(Paint.Style.STROKE);
	paint.setColor(R.color.white);
	paint.setStrokeWidth(Core.SHIELD_WIDTH);
	c.drawArc(rect, (360.0F - world.core.angle),
			(360.0F - world.core.GAP_ANGLE), false, paint);
	paint.setStrokeWidth(0.0F);
	paint.setStyle(Paint.Style.FILL_AND_STROKE);
	Iterator<Dot> iterator = world.dots.iterator();
	while(iterator.hasNext())
	{
		int color = 0;
		Dot dot = iterator.next();
		if(dot.type == Dot.Type.Enemy)
			color = R.color.red;
		else if(dot.type == Dot.Type.Health)
			color = R.color.light_blue;
		else if(dot.type == Dot.Type.Shield)
			color = R.color.blue;
		paint.setColor(color);
		c.drawCircle(dot.coords.x, dot.coords.y,
				dot.maxRadius * dot.energy, paint);
    }

	if(world.state == World.GameState.Running)
		drawMessage(world.getTime(), c);
	else if(world.state == World.GameState.Ready)
		drawMessage(context.getString(R.string.ready), c);
	else if(world.state == World.GameState.Paused)
		drawMessage(context.getString(R.string.paused), c);
	else if(world.state == World.GameState.GameOver)
		drawMessage(context.getString(R.string.game_over)+
				"\n"+
				context.getString(R.string.your_time) +  " " + world.getTime() +
				"\n\n" + context.getString(R.string.game_url), c);
	}

	private void drawMessage(String message, Canvas c)
	{
		float y = paint.getTextSize();
		for(String line: message.split("\n"))
		{
		// Draw black stroke
		paint.setStrokeWidth(2F);
		paint.setColor(R.color.black);
	    paint.setStyle(Paint.Style.STROKE);

		c.drawText(line, c.getWidth()/2F, y, paint);
		// Draw white text
		paint.setStrokeWidth(0.0F);
		paint.setColor(R.color.white);
	    paint.setStyle(Paint.Style.FILL);

		c.drawText(line, c.getWidth()/2F, y, paint);

		y += paint.getTextSize();
		}
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
