package com.fidget.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by j.lee on 9/2/17.
 */

public class ScreenUtilities {

    public static float getScreenPositionBasedOnPercentageX(float percentTowardsRightSide) {
        return (percentTowardsRightSide / 100.0f) * Gdx.graphics.getWidth();
    }

    public static float getScreenPositionBasedOnPercentageY(float percentTowardsTopSide) {
        return (percentTowardsTopSide / 100.0f) * Gdx.graphics.getHeight();
    }

    public static float getScaleByPercentOfScreenLowestDimension(float percentInDecimalOfScreen,
                                                                 TextureRegion textureRegion) {
        return getPercentToScaleTo(getTargetSize(percentInDecimalOfScreen),
                getStartingSize(textureRegion));
    }

    private static float getStartingSize(TextureRegion textureRegion) {
        return (textureRegion.getTexture().getWidth()
                >= textureRegion.getTexture().getHeight())
                ? textureRegion.getTexture().getWidth()
                : textureRegion.getTexture().getHeight();
    }

    private static float getTargetSize(float percentInDecimalOfScreenSize) {
        return ((Gdx.graphics.getWidth() >= Gdx.graphics.getHeight()) ? Gdx.graphics.getHeight()
                : Gdx.graphics.getWidth()) * percentInDecimalOfScreenSize;
    }

    private static float getPercentToScaleTo(float targetSize, float startingSize) {
        return targetSize / startingSize;
    }
}
