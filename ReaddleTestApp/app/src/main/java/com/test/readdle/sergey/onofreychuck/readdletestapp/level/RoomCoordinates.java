package com.test.readdle.sergey.onofreychuck.readdletestapp.level;

/**
 * Created by sergey on 9/26/15.
 */
public class RoomCoordinates {

    private int mHashCode;

    private int mX;
    private int mY;

    public RoomCoordinates(int x, int y) {
        mX = x;
        mY = y;

        mHashCode = new Integer(mX).hashCode() ^ new Integer(mY);
    }

    public int getX() {
        return mX;
    }

    public int getY() {
        return mY;
    }

    @Override
    public int hashCode() {
        return mHashCode;
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
