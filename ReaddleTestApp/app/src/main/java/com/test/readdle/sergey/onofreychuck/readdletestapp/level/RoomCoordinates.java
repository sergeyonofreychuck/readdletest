package com.test.readdle.sergey.onofreychuck.readdletestapp.level;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sergey on 9/26/15.
 */
public class RoomCoordinates {

    @SerializedName("x")
    private int mX;
    @SerializedName("y")
    private int mY;

    public RoomCoordinates(int x, int y) {
        mX = x;
        mY = y;
    }

    public int getX() {
        return mX;
    }

    public int getY() {
        return mY;
    }

    @Override
    public int hashCode() {
        return new Integer(mX).hashCode() ^ new Integer(mY);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RoomCoordinates)){
            return false;
        }
        RoomCoordinates rc = (RoomCoordinates) obj;
        return rc.mX == mX && rc.mY == mY;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        return sb.append("Room coordinates. X:")
                .append(mX)
                .append(" Y:")
                .append(mY)
                .toString();
    }
}
