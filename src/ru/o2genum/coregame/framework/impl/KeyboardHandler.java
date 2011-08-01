package ru.o2genum.coregame.framework.impl;

import java.util.ArrayList;
import java.util.List;

import android.view.*;
import android.view.View.OnKeyListener;

public class KeyboardHandler implements OnKeyListener {
    boolean[] pressedKeys = new boolean[128];

    public KeyboardHandler(View view) {
        view.setOnKeyListener(this);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() == android.view.KeyEvent.ACTION_MULTIPLE)
            return false;

        synchronized (this) {
            if (event.getAction() == android.view.KeyEvent.ACTION_DOWN) {
                if(keyCode > 0 && keyCode < 127)
                    pressedKeys[keyCode] = true;
            }
            if (event.getAction() == android.view.KeyEvent.ACTION_UP) {
                if(keyCode > 0 && keyCode < 127)
                    pressedKeys[keyCode] = false;
            }
        }
        return false;
    }

    public boolean isKeyPressed(int keyCode) {
        if (keyCode < 0 || keyCode > 127)
            return false;
        return pressedKeys[keyCode];
    }
}
