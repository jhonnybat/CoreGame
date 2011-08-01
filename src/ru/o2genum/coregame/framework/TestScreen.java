package ru.o2genum.coregame.framework;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

import android.graphics.*;
import android.util.Log;

public class TestScreen extends Screen {
    long startTime = System.nanoTime();
    int frames;
	Paint p = new Paint();
        
    public TestScreen(Game game) {
        super(game);    
		p.setColor(0xffffffff);
       
        try {
            Log.d("MrNom", "" );
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void update(float deltaTime) {
    }

    @Override
    public void present(float deltaTime) {
    Canvas c = game.getGraphics().getCanvas();    
	c.drawRGB(0, 100, 0);
	if(game.getInput().isTouched())
	{
	c.drawCircle(game.getInput().getTouchX(),
			game.getInput().getTouchY(),
			40.0F, p);
	}
    }

    @Override
    public void pause() {
        Log.d("MrNom", "pause");                
    }

    @Override
    public void resume() {
        Log.d("MrNom", "resume");   
    }

    @Override
    public void dispose() {
        Log.d("MrNom", "dispose");
    }            
}
