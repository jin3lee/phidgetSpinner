package com.fidget.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.fidget.game.FidgetGame;

/**
 * Created by leej431 on 7/4/17.
 */

public abstract class BaseScreen implements Screen {

    FidgetGame game;

    public BaseScreen(FidgetGame game) {
        this.game = game;
    }

    public static float getScreenWidthHalf() {
        return Gdx.graphics.getWidth() / 2;
    }

    public static float getScreenHeightHalf() {
        return Gdx.graphics.getHeight() / 2;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            setBackPressButton();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public abstract void setBackPressButton();

    public abstract boolean fling(float velocityX, float velocityY, int button);

    public abstract boolean pan(float x, float y, float deltaX, float deltaY);

    public abstract boolean tap(float x, float y, int count, int button);
}
