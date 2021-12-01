package com.fidget.game.DataStructures;

/**
 * Created by leej431 on 6/20/17.
 */

public class PointAndTime {
    public int draggerColorIndex;
    public int x;
    public int y;
    public long timeCreated;

    public PointAndTime(int x, int y, long timeCreated) {
        this.draggerColorIndex = 0;
        this.x = x;
        this.y = y;
        this.timeCreated = timeCreated;
    }

    public boolean equals(PointAndTime other) {
        return this.x == other.x && this.y == other.y && this.timeCreated == other.timeCreated;
    }
}
