package com.fidget.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.fidget.game.DataStructures.Assets;
import com.fidget.game.Screens.BaseScreen;
import com.fidget.game.Screens.PlayScreen;

/**
 * Created by leej431 on 7/3/17.
 */

public class FidgetGame extends Game implements GestureDetector.GestureListener {

    BaseScreen screen;
    GestureDetector gestureDetector;
    public static boolean mSupportsVibration;

    @Override
    public void create() {
        Settings.load();
        Assets.load();

        screen = new PlayScreen(this);

        gestureDetector = new GestureDetector(this);
        Gdx.input.setInputProcessor(gestureDetector);

        setScreen(screen);
    }

    public void setTheScreen(BaseScreen screen) {
        this.screen = screen;
        setScreen(screen);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return screen.fling(velocityX, velocityY, button);
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return screen.pan(x, y, deltaX, deltaY);
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return screen.tap(x, y, count, button);
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }

    public void setSupportsVibration(boolean b){
        mSupportsVibration = b;
    }
}
