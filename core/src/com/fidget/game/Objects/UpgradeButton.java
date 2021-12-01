package com.fidget.game.Objects;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by j.lee on 9/24/17.
 */

public class UpgradeButton extends CheckBoxButton {
    public boolean isEnabled;
    public int assignedID;

    public UpgradeButton(String uncheckedTextureName, String checkedTextureName) {
        super(uncheckedTextureName, checkedTextureName);
    }

    public UpgradeButton(Texture uncheckedTextureName, Texture checkedTextureName) {
        super(uncheckedTextureName, checkedTextureName);
    }

    public UpgradeButton(String uncheckedTextureName, String checkedTextureName, float x, float y) {
        super(uncheckedTextureName, checkedTextureName, x, y);
    }

    public UpgradeButton(Texture uncheckedTextureName, Texture checkedTextureName, float x, float y) {
        super(uncheckedTextureName, checkedTextureName, x, y);
    }

    @Override
    public boolean isPressed(float x, float y) {
        if (super.isPressed(x, y)) {
            return true;
        }
        return false;
    }

    public void setAssignedID(int id) {
        this.assignedID = id;
    }
}
