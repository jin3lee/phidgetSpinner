package com.fidget.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.fidget.game.DataStructures.Assets;
import com.fidget.game.FidgetGame;
import com.fidget.game.Objects.FidgetItem;
import com.fidget.game.Objects.FidgetItemList;
import com.fidget.game.Settings;
import com.fidget.game.StoreSettings;

import java.util.ArrayList;

import static com.fidget.game.DataStructures.Assets.backButton;

/**
 * Created by Jared on 7/11/17.
 */

public class FidgetSettingsScreen extends BaseScreen {

    public FidgetSettingsScreen(FidgetGame game) {
        super(game);
        Gdx.input.setCatchBackKey(true);
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
        backButton.draw();

        StoreSettings.sFidgetItemList.draw();

        Assets.spinCounter.draw();
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
        Settings.save();
    }

    @Override
    public void dispose() {

    }

    @Override
    public void setBackPressButton() {
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
        if (backButton.isPressed(x, y)) {
            game.setTheScreen(new PlayScreen(game));
            Settings.save();
        }

        StoreSettings.sFidgetItemList.isPressed(x, y);

        ArrayList<FidgetItem> fidgetItemArrayList = StoreSettings.sFidgetItemList.fidgetItemArrayList;
        for (int i = 0; i < fidgetItemArrayList.size(); i++) {
            FidgetItem fidgetItem = fidgetItemArrayList.get(i);
            if (fidgetItem.btnBuy.isPressed(x, y)) {
                StoreSettings.sFidgetItemList.updateBuyPressed(fidgetItem.id);
            }
        }
        return false;
    }

}
