package com.fidget.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.fidget.game.DataStructures.Assets;
import com.fidget.game.FidgetGame;

/**
 * Created by leej431 on 7/3/17.
 */

public class MainMenu implements Screen {
    private FidgetGame game;
    Texture texture;

    public MainMenu(FidgetGame game) {
        this.game = game;
        texture = new Texture("background/splash_title.png");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Assets.batch.begin();
        Assets.batch.draw(texture, 0, 0);
        Assets.batch.end();
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
}
