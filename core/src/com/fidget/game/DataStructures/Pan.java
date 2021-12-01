package com.fidget.game.DataStructures;

/**
 * This class keeps track of a singular Pan when user interacts with screen.
 * <p>
 * Created by leej431 on 6/16/17.
 */

public class Pan {
    public float x;
    public float y;
    public float dX;
    public float dY;

    public Pan() {
        this.x = 0.0f;
        this.y = 0.0f;
        this.dX = 0.0f;
        this.dY = 0.0f;
    }

    public Pan(float x, float y, float dX, float dY) {
        this.x = x;
        this.y = y;
        this.dX = dX;
        this.dY = dY;
    }

    public Pan(Pan other) {
        this.x = other.x;
        this.y = other.y;
        this.dX = other.dX;
        this.dY = other.dY;
    }

    public void setPan(float x, float y, float dX, float dY) {
        this.x = x;
        this.y = y;
        this.dX = dX;
        this.dY = dY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Pan pan = (Pan) o;

        if (Float.compare(pan.x, x) != 0)
            return false;
        if (Float.compare(pan.y, y) != 0)
            return false;
        if (Float.compare(pan.dX, dX) != 0)
            return false;
        return Float.compare(pan.dY, dY) == 0;

    }

    @Override
    public int hashCode() {
        int result = (x != +0.0f ? Float.floatToIntBits(x) : 0);
        result = 31 * result + (y != +0.0f ? Float.floatToIntBits(y) : 0);
        result = 31 * result + (dX != +0.0f ? Float.floatToIntBits(dX) : 0);
        result = 31 * result + (dY != +0.0f ? Float.floatToIntBits(dY) : 0);
        return result;
    }
}
