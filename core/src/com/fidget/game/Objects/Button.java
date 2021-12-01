package com.fidget.game.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
import com.fidget.game.DataStructures.Assets;
import com.fidget.game.ScreenUtilities;
import com.fidget.game.StoreSettings;

/**
 * Created by leej431 on 6/28/17.
 */

public class Button {
    public float scaleX;
    public float scaleY;
    public float rotationAngle;
    public TextureRegion textureRegion;
    public Rectangle rectangle;
    public int offsetX;
    public int offsetY;

    public int assignedID;

    public long lastPressed;
    public long cooldown = 100;

    public FidgetItemList fidgetItemList;

    public Button() {
        this.scaleX = 1.0f;
        this.scaleY = 1.0f;
        this.rotationAngle = 0.0f;
        lastPressed = TimeUtils.millis();
        assignedID = -1;
        offsetX = 0;
        offsetY = 0;
    }

    public Button(String textureName) {
        this();
        this.textureRegion = new TextureRegion(new Texture(textureName));
        this.rectangle = new Rectangle(0, 0, textureRegion.getTexture().getWidth(), textureRegion.getTexture().getHeight());
    }

    public Button(Texture texture) {
        this();
        this.textureRegion = new TextureRegion(texture);
        this.rectangle = new Rectangle(0, 0, texture.getWidth(), texture.getHeight());
    }

    public Button(String textureName, float x, float y) {
        this();
        this.textureRegion = new TextureRegion(new Texture(textureName));
        this.rectangle = new Rectangle(x, y, textureRegion.getTexture().getWidth(), textureRegion.getTexture().getHeight());
    }

    public Button(Texture texture, float x, float y) {
        this();
        this.textureRegion = new TextureRegion(texture);
        this.rectangle = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
    }

    public boolean isPressed(float x, float y) {
        boolean retBoolean = false;
        long currentTime = TimeUtils.millis();

        // get current touch with origin at bottom left of screen
        int currentTouchX = (int) Math.abs(x);
        int currentTouchY = (int) Math.abs(y - Gdx.graphics.getHeight());

        int xAfterOffset = (currentTouchX - offsetX);
        int yAfterOffset = (currentTouchY - offsetY);

        // only do something if new press is made
        if (currentTime - lastPressed > cooldown
                && this.rectangle.contains(
                        xAfterOffset,
                        yAfterOffset)) {
            retBoolean = true;
            lastPressed = currentTime;
        }

        return retBoolean;
    }

    public void draw() {
        Assets.batch.draw(
                textureRegion,  // texture region
                rectangle.x + offsetX,    // x-pos
                rectangle.y + offsetY,    // y-pos
                0,              // x-origin
                0,              // y-origin
                textureRegion.getRegionWidth(),       // width
                textureRegion.getRegionHeight(),      // height
                scaleX,           // x-scale
                scaleY,           // y-scale
                rotationAngle);               // rotation angle
    }

    public float getWidth() {
        return rectangle.getWidth();
    }

    public float getHeight() {
        return rectangle.getHeight();
    }


    public void setPosition(float x, float y) {
        rectangle.x = x;
        rectangle.y = y;
    }

    public void setOffset(int x, int y) {
        offsetX = x;
        offsetY = y;
    }

    public void setTextureRegion(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
        this.rectangle = new Rectangle(
                this.rectangle.getX() * scaleX,
                this.rectangle.getY() * scaleY,
                textureRegion.getTexture().getWidth(),
                textureRegion.getTexture().getHeight());
    }

    public void setScale(float scaleX, float scaleY) {
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.rectangle.width = (float) textureRegion.getRegionWidth() * this.scaleX;
        this.rectangle.height = (float) textureRegion.getRegionHeight() * this.scaleY;
    }

    public void setScaleByScreenPercentage(float scaleX, float scaleY) {
        this.scaleX = ScreenUtilities.getScreenPositionBasedOnPercentageX(scaleX) / textureRegion.getRegionWidth();
        this.scaleY = ScreenUtilities.getScreenPositionBasedOnPercentageY(scaleY) / textureRegion.getRegionHeight();
        this.rectangle.width = (float) textureRegion.getRegionWidth() * this.scaleX;
        this.rectangle.height = (float) textureRegion.getRegionHeight() * this.scaleY;
    }

    public void setAssignedID(int assignedID) {
        this.assignedID = assignedID;
    }
}
