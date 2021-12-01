package com.fidget.game.Objects;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.fidget.game.DataStructures.Assets;
import com.fidget.game.ScreenUtilities;
import com.fidget.game.Settings;

/**
 * Created by j.lee on 10/1/17.
 */

public class Text {
    public BitmapFont font;
    public int x;
    public int y;

    public int drawOffsetX;
    public int drawOffsetY;

    public Text(float scale, int x, int y) {
        font = new BitmapFont();
        font.getData().scale(scale);
        this.x = x;
        this.y = y;
    }

    public void setOffset(int x, int y) {
        drawOffsetX = x;
        drawOffsetY = y;
    }

    public void draw(String text) {
        font.draw(Assets.batch,
                text,
                x + this.drawOffsetX,
                y + this.drawOffsetY
        );
    }
}
