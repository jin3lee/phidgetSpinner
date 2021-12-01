package com.fidget.game.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Created by leej431 on 7/4/17.
 */

public class CheckBoxButton extends Button {

    public TextureRegion uncheckedTextureRegion;
    public TextureRegion checkedTextureRegion;
    public boolean isChecked;
    public long lastPressed;

    public CheckBoxButton(String uncheckedTextureName, String checkedTextureName) {
        this(uncheckedTextureName, checkedTextureName, 0, 0);
    }

    public CheckBoxButton(Texture uncheckedTextureName, Texture checkedTextureName) {
        this(uncheckedTextureName, checkedTextureName, 0, 0);
    }

    public CheckBoxButton(String uncheckedTextureName, String checkedTextureName, float x, float y) {
        super(uncheckedTextureName, x, y);
        this.uncheckedTextureRegion = new TextureRegion(new Texture(uncheckedTextureName));
        this.checkedTextureRegion = new TextureRegion(new Texture(checkedTextureName));
        lastPressed = TimeUtils.millis();
        isChecked = false;
    }

    public CheckBoxButton(Texture uncheckedTextureName, Texture checkedTextureName, float x, float y) {
        super(uncheckedTextureName, x, y);
        this.uncheckedTextureRegion = new TextureRegion(uncheckedTextureName);
        this.checkedTextureRegion = new TextureRegion(checkedTextureName);
        lastPressed = TimeUtils.millis();
        isChecked = false;
    }

    @Override
    public boolean isPressed(float x, float y) {
        if (super.isPressed(x, y)) {
            super.setTextureRegion((isChecked) ? uncheckedTextureRegion : checkedTextureRegion);
            isChecked = !isChecked;
            return true;
        }
        return false;
    }

    public void setCheckBox(boolean isCheck) {
        if (isCheck) {
            super.setTextureRegion(checkedTextureRegion);
            this.isChecked = true;
        } else {
            super.setTextureRegion(uncheckedTextureRegion);
            this.isChecked = false;
        }
    }
}
