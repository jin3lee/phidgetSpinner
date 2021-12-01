package com.fidget.game.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.TimeUtils;
import com.fidget.game.DataStructures.Assets;
import com.fidget.game.DataStructures.Dragger;
import com.fidget.game.DataStructures.Pan;
import com.fidget.game.FidgetGame;
import com.fidget.game.ScreenUtilities;
import com.fidget.game.Settings;
import com.fidget.game.StoreSettings;

/**
 * Created by josephlee on 6/5/17.
 */

public class Fidget {
    public com.fidget.game.DataStructures.PointAndTime stoppingCenter;
    public float degreeChangeCounter;
    public int stoppingRadius = 50;     // radius of dead zone a finger can move without affecting fling gesture
    public final static int ONE_SECOND = 1000;
    public final static int ROTATION_VELOCITY_MAX = 100;
    public final static int FLING_HYPOTENUSE_MAX = 15000;
    public final static int DRAW_METHOD_CALLED_PER_SECOND = 60;
    public final static float PANNING_SENSITIVITY = 120f;
    public final static int VIBRATION_DURATION = (int) (ONE_SECOND / DRAW_METHOD_CALLED_PER_SECOND);
    public State state;
    public Dragger dragger;
    public final static float PERCENT_OF_SCREEN_FIDGET_SHOULD_BE = .8f;

    public enum State {
        IDLE,
        TICKLING,
        PANNING,
        SPINNING
    }

    public int x;
    public int y;
    public float img_scale;
    public int deadZoneRadius;
    public float rotationAngle;
    public float rotationVelocity;
    public float airResistance;
    public TextureRegion textureRegion;

    // default constructor
    public Fidget(int fidgetIndex, Dragger dragger) {
        degreeChangeCounter = 0.0f;
        this.state = state.IDLE;
        this.x = 0;
        this.y = 0;
        rotationAngle = 0;
        deadZoneRadius = 10;
        airResistance = .10f;
        rotationVelocity = 0;
        this.textureRegion = new TextureRegion(new Texture("fidget/on_" + fidgetIndex + ".png"));

        this.img_scale =
                ScreenUtilities
                        .getScaleByPercentOfScreenLowestDimension(PERCENT_OF_SCREEN_FIDGET_SHOULD_BE,
                                textureRegion);
        this.dragger = dragger;
    }


    public int getOriginX() {
        return x;
    }

    public int getOriginY() {
        return y;
    }

    public void setFidgetModel(int index) {
        this.textureRegion = new TextureRegion(new Texture("fidget/on_" + index + ".png"));
    }

    public void draw() {

        if (state == State.SPINNING) {
            // update rotational physics
            rotationAngle += rotationVelocity;
            if (Math.abs(rotationVelocity) < airResistance) {
                rotationVelocity = 0;
                if(FidgetGame.mSupportsVibration && Settings.vibrationEnabled) {
                    Gdx.input.cancelVibrate();
                }
                state = State.IDLE;
            } else if (rotationVelocity < 0) {
                rotationVelocity += airResistance;
                if (FidgetGame.mSupportsVibration && Settings.vibrationEnabled) {
                    Gdx.input.vibrate(VIBRATION_DURATION);
                }
            } else if (rotationVelocity > 0) {
                rotationVelocity -= airResistance;
                if (FidgetGame.mSupportsVibration && Settings.vibrationEnabled)
                    Gdx.input.vibrate(VIBRATION_DURATION);
            }
        }
        updatePoints(rotationVelocity);

        // draw onto canvas
        textureRegion = StoreSettings.sFidgetItemList.fidgetItemArrayList.get(Settings.fidgetModel).fidgetModel;

        Assets.batch.draw(
                textureRegion,                      // texture region
                (x - textureRegion.getRegionWidth() / 2), // x-pos
                (y - textureRegion.getRegionHeight() / 2),// y-pos
                textureRegion.getRegionWidth() / 2,   // x-origin
                textureRegion.getRegionHeight() / 2,  // y-origin
                textureRegion.getRegionWidth(),     // width
                textureRegion.getRegionHeight(),    // height
                img_scale,                               // x-scale
                img_scale,                               // y-scale
                rotationAngle);                     // rotation angle
    }

    public void pan(com.fidget.game.DataStructures.Pan pan) {
        // System.out.println("pan: " + pan.x + " " + pan.y);
        if (state == State.PANNING) {
            rotationVelocity = 0;
            float degreeChange = calculateDegreeChange(pan);
            float prevX = pan.x + pan.dX;
            float prevY = pan.y + pan.dY;
            if (getAngle(pan.x, pan.y) > getAngle(prevX, prevY)) {
                rotationAngle += degreeChange * PANNING_SENSITIVITY;
            } else {
                rotationAngle -= degreeChange * PANNING_SENSITIVITY;
            }
            stoppingCenter = null;
        } else if (state == State.IDLE || state == State.SPINNING) {
            // record tickle center
            stoppingCenter = new com.fidget.game.DataStructures.PointAndTime((int) pan.x, (int) pan.y, TimeUtils.millis());
            // change state to tickling
            state = State.TICKLING;
        } else if (state == State.TICKLING) {
            rotationVelocity = 0;
            if (getDistanceBetween(stoppingCenter, pan) > stoppingRadius) {
                state = State.PANNING;
            }
        }
    }

    public void fling(com.fidget.game.DataStructures.Pan pan, float vX, float vY) {
        float degreeChange = calculateDegreeChange(pan);
        float prevX = pan.x + pan.dX;
        float prevY = pan.y + pan.dY;

        // default NaN degree changes to 2.0f
        if (degreeChange == 0.0f) {
            degreeChange = 2.0f;
        }

        // increases minimum rotational velocity
        if (degreeChange < .5) {
            degreeChange = 0.5f;
        }

        // caps the maximum rotational velocity
        if (degreeChange > 5.0f) {
            degreeChange = 5.0f;
        }

        float farthestDistanceFromCenter = getDistanceBetween(getOriginX(), getOriginY(), Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        float flingDistanceFromCenter = getDistanceBetween(getOriginX(), getOriginY(), pan.x, pan.y);
        float inverseMultiplier = 1 - ((farthestDistanceFromCenter - flingDistanceFromCenter) / farthestDistanceFromCenter);
        float changeInRotationalVelocity = degreeChange * PANNING_SENSITIVITY * inverseMultiplier;

        if (state == State.PANNING) {
            rotationVelocity = (getAngle(pan.x, pan.y) > getAngle(prevX, prevY))
                    ? rotationVelocity + changeInRotationalVelocity
                    : rotationVelocity - changeInRotationalVelocity;
            state = State.SPINNING;
        } else if (state == State.TICKLING) {
            state = State.SPINNING;
            stoppingCenter = null;
        }

        dragger.changeSwipeToRandomDifferent();
    }

    /**
     * Calculates the angle of a pan and returns it.
     *
     * @param pan
     * @return angle of pan
     */
    private float calculateDegreeChange(com.fidget.game.DataStructures.Pan pan) {
        // get prev point
        float prevX = pan.x + pan.dX;
        float prevY = pan.y + pan.dY;

        // if slope is same for prev & current then return 0 degree change
        if (prevX / prevY == pan.x / pan.y) {
            return 0.0f;
        }

        // get distances from origin for both prev and current pan points
        float deltaOriginPrev = getDistanceBetween(getOriginX(), getOriginY(), prevX, prevY);
        float deltaOriginCurrent = getDistanceBetween(getOriginX(), getOriginY(), pan.x, pan.y);

        // determine which point is closer to the origin to get the hypotenuse
        boolean isPrevCloserToOrigin = (deltaOriginPrev <= deltaOriginCurrent) ?
                true :
                false;

        // find the cooridinate for the farther point with the same angle from origin
        float correspondingX;
        float correspondingY;
        float percentToReduceToMeetHypotenuseLength;
        if (isPrevCloserToOrigin) {
            percentToReduceToMeetHypotenuseLength = deltaOriginPrev / deltaOriginCurrent;
            correspondingX = pan.x * percentToReduceToMeetHypotenuseLength;
            correspondingY = pan.y * percentToReduceToMeetHypotenuseLength;
        } else {
            percentToReduceToMeetHypotenuseLength = deltaOriginCurrent / deltaOriginPrev;
            correspondingX = prevX * percentToReduceToMeetHypotenuseLength;
            correspondingY = prevY * percentToReduceToMeetHypotenuseLength;
        }

        float hypotenuse = (isPrevCloserToOrigin) ? deltaOriginPrev : deltaOriginCurrent;
        float opposite = (
                (isPrevCloserToOrigin) ? getDistanceBetween(prevX, prevY, correspondingX, correspondingY)
                        : getDistanceBetween(pan.x, pan.y, correspondingX, correspondingY)) / 2;
        double angle = Math.asin(opposite / hypotenuse);
        return (
                angle < Float.MAX_VALUE && angle > 0
                        || angle > Float.MIN_VALUE && angle < 0) ? (float) (angle) : 0.0f;
    }

    private float getDistanceBetween(float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt((Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2)));
    }

    private float getDistanceBetween(com.fidget.game.DataStructures.PointAndTime pointAndTime, Pan pan) {
        if (pointAndTime != null && pan != null) {
            return getDistanceBetween(pointAndTime.x, pointAndTime.y, pan.x, pan.y);
        }
        return 0;
    }

    public float getAngle(float x, float y) {
        float angle = (float) Math.toDegrees(Math.atan2(y - getOriginY(), x - getOriginX()));

        if (angle < 0) {
            angle += 360;
        }

        return angle;
    }

    public void stopSpin() {
        rotationVelocity = 0;
    }

    public void slowDownSpin() {
        rotationVelocity /= 2;
    }

    private int calculateVelocity(float x, float y) {
        return (int) ((Math.hypot(x, y) / FLING_HYPOTENUSE_MAX) * ROTATION_VELOCITY_MAX);
    }

    private boolean isStopGesture() {
        boolean retBoolean = true;

//        System.out.print("Average: " + dragger.averagePointAndTime.x + "/" + dragger.pointAndTimeCounter + " = " + (
//                dragger.averagePointAndTime.x / dragger
//                        .pointAndTimeCounter));
//        System.out.println(" " + dragger.averagePointAndTime.y + "/" + dragger.pointAndTimeCounter + " = " + (
//                dragger.averagePointAndTime.y / dragger
//                        .pointAndTimeCounter));

        return retBoolean;
    }

    private void updatePoints(float deltaAngle) {
        degreeChangeCounter += Math.abs(deltaAngle);

        if (degreeChangeCounter >= 360.0f) {
            // reset degree counter
            degreeChangeCounter -= 360.0f;
            // increment point to settings.point
            Settings.points += StoreSettings.pointsPerSpinBonus;
        }
    }
}
