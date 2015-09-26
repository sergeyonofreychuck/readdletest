package com.test.readdle.sergey.onofreychuck.readdletestapp.level;

import android.graphics.Bitmap;

/**
 * Created by sergey on 9/26/15.
 */
public abstract class DeviceAbstract {

    protected Room mRoom;
    protected Direction mDirection;
    private PositionChangedCallback mPositionCallback;
    private Bitmap mIcon;

    public DeviceAbstract(Room currentRoom, Direction direction, Bitmap icon) {
        if (currentRoom == null){
            throw new IllegalArgumentException("location");
        }
        if (direction == null) {
            throw new IllegalArgumentException("direction");
        }
        if (icon == null) {
            throw new IllegalArgumentException("icon");
        }

        mRoom = currentRoom;
        mDirection = direction;
        mIcon = icon;
    }

    public void turnLeft() {
        mDirection = mDirection.getDirectionToLeft();
        positionChanged();
    }

    public void turnRight() {
        mDirection = mDirection.getDirectionToRight();
        positionChanged();
    }

    public boolean canDoStepForward() {
        return mRoom.canGoForward(mDirection);
    }

    public void stepForward() {
        mRoom = mRoom.goForward(mDirection);
        positionChanged();
    }

    public void positionChanged(){
        if (mPositionCallback != null) {
            mPositionCallback.positionChanged(mRoom, mDirection);
        }
    }

    public Bitmap getmIcon() {
        return mIcon;
    }

    public void setActionsCallback(PositionChangedCallback callback) {
        mPositionCallback = callback;
    }

    public interface PositionChangedCallback {
        void positionChanged(Room room, Direction direction);
    }
}
