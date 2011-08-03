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
	World world;
        
    public TestScreen(Game game) {
        super(game);
		world = new World(game.getGraphics());	
    }
    
    @Override
    public void update(float deltaTime) {
		world.update(deltaTime);
    }

    @Override
    public void present(float deltaTime) {
    Canvas c = game.getGraphics().getCanvas();    
    }

    @Override
    public void pause() {
		world.pause();
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }            
}
