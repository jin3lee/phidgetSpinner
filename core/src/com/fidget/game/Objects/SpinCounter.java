package com.fidget.game.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.fidget.game.DataStructures.Assets;
import com.fidget.game.ScreenUtilities;
import com.fidget.game.Settings;
import com.fidget.game.StoreSettings;

/**
 * Created by j.lee on 9/2/17.
 */

public class SpinCounter {
    BitmapFont font;
    public int posX;
    public int posY;

    public SpinCounter() {
        font = new BitmapFont();
        font.getData().scale(5);
    }

    public void draw() {
        setSpinCountToCenterTop();
        font.draw(Assets.batch, "" + Settings.points + "pts" + "(+" + StoreSettings.pointsPerSpinBonus + ")", posX, posY);
    }

    private void setSpinCountToCenterTop(){
        posY = (int) ScreenUtilities.getScreenPositionBasedOnPercentageY(95);
        posX = (int) ScreenUtilities.getScreenPositionBasedOnPercentageX(20);
    }
}
