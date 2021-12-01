package com.fidget.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.fidget.game.DataStructures.Assets;
import com.fidget.game.FidgetGame;
import com.fidget.game.Objects.Button;
import com.fidget.game.Objects.CheckBoxButton;
import com.fidget.game.Settings;
import com.fidget.game.StoreSettings;

/**
 * Created by Jared on 6/21/17.
 */

public class SettingsScreen extends BaseScreen {
    CheckBoxButton vibrationButton;
    CheckBoxButton swipeButton;
    Button backButton;

    public SettingsScreen(FidgetGame game) {
        super(game);
        Gdx.input.setCatchBackKey(true);
        this.vibrationButton = new CheckBoxButton("settings/vibration_off.png", "settings/vibration_on.png", 0, 0);
        this.swipeButton = new CheckBoxButton("settings/swipes_off.png", "settings/swipes_on.png", 0, 0);
        this.backButton = new Button("settings/back_button.png", 0, 0);
        initializeLocationOfButtons();
        initializeCheckBoxStates();
    }

    public void initializeLocationOfButtons() {
        vibrationButton.setScale(1f, 1f);
        swipeButton.setScale(1f, 1f);
        backButton.setScale(1f, 1f);
        vibrationButton.setPosition(getScreenWidthHalf() - vibrationButton.getWidth() / 2,
                getScreenHeightHalf() + (vibrationButton.getHeight() + 50));
        swipeButton.setPosition(getScreenWidthHalf() - swipeButton.getWidth() / 2,
                getScreenHeightHalf() + (swipeButton.getHeight() / 2 - 80));
        backButton.setPosition(getScreenWidthHalf() - backButton.getWidth() / 2,
                250);
    }

    public void initializeCheckBoxStates() {
        swipeButton.setCheckBox(Settings.swipeEnabled);
        vibrationButton.setCheckBox(Settings.vibrationEnabled);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Assets.batch.begin();
        vibrationButton.draw();
        swipeButton.draw();
        backButton.draw();
        Assets.batch.end();
    }

    @Override
    public void resize(int width, int height) {

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

    }

    @Override
    public void dispose() {

    }

    @Override
    public void setBackPressButton() {
        Settings.save();
        game.setTheScreen(new PlayScreen(game));
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        if (swipeButton.isPressed(x, y)) {
            Settings.swipeEnabled = swipeButton.isChecked;
        }
        if (vibrationButton.isPressed(x, y)) {
            Settings.vibrationEnabled = vibrationButton.isChecked;
        }
        if (backButton.isPressed(x, y)) {
            game.setTheScreen(new PlayScreen(game));
            Settings.save();
        }
        return false;
    }
}
