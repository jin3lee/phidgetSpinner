package com.fidget.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.TimeUtils;
import com.fidget.game.DataStructures.Assets;
import com.fidget.game.FidgetGame;
import com.fidget.game.DataStructures.Pan;
import com.fidget.game.DataStructures.PointAndTime;
import com.fidget.game.Settings;

public class PlayScreen extends BaseScreen {
    boolean DEBUG = false;

    public PlayScreen(FidgetGame game) {
        super(game);
        Gdx.input.setCatchBackKey(false);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1); // sets background to black
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Assets.batch.begin();
        // draw settings
        Assets.fidgetSettingsButton.draw();
        Assets.settingsButton.draw();

        // draw fidgets
        Assets.fidget.draw();

        Assets.spinCounter.draw();

        // draw dragger
        Assets.dragger.draw(TimeUtils.millis());
        Assets.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        Settings.save();
    }

    @Override
    public void pause() {
        Settings.save();
    }

    @Override
    public void resume() {
        Settings.load();
    }

    @Override
    public void hide() {
        Settings.save();
    }

    @Override
    public void dispose() {
        Assets.batch.dispose();
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        Assets.fidget.fling(Assets.lastPan, velocityX, velocityY);
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        // send pan data to fidget
        Assets.currentPan.setPan(x, y, deltaX, deltaY);

        // send pan data to Dragger
        if (!Assets.currentPan.equals(Assets.lastPan)) {
            Assets.dragger.addPointAndTime(
                    new PointAndTime(
                            (int) Assets.currentPan.x, Gdx.graphics.getHeight() - (int) Assets.currentPan.y, TimeUtils.millis()
                    )
            );
            Assets.fidget.pan(Assets.currentPan);
            Assets.lastPan = new Pan(Assets.currentPan);
        }
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        if (Assets.settingsButton.isPressed(x, y)) {
            game.setTheScreen(new SettingsScreen(game));
            Assets.fidget.rotationVelocity = 0;
        } else if (Assets.fidgetSettingsButton.isPressed(x, y)) {
            game.setTheScreen(new FidgetSettingsScreen(game));
            Assets.fidget.rotationVelocity = 0;
        } else {
            Assets.fidget.slowDownSpin();
        }

        return false;
    }

    @Override
    public void setBackPressButton() {
        // do nothing
        Gdx.app.exit();
    }
}
