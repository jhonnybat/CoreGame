package ru.o2genum.coregame.game;

import android.view.*;
import android.graphics.*;

import ru.o2genum.coregame.framework.*;
import ru.o2genum.coregame.framework.impl.*;

public class GameActivity extends AndroidGame {
    @Override
    public Screen getStartScreen() {
        return new GameScreen(this); 
    }

	@Override
	public void onAttachedToWindow()
	{
		// May fix non-smooth gradient 
		// http://crazygui.wordpress.com/2010/09/05/high-quality-radial-gradient-in-android/
		super.onAttachedToWindow();
		Window window = getWindow();
		window.setFormat(PixelFormat.RGBA_8888);
	}
}
