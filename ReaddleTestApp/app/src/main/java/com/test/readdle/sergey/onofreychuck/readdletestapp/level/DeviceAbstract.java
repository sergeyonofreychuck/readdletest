package com.test.readdle.sergey.onofreychuck.readdletestapp.level;

import android.graphics.Bitmap;

/**
 * Created by sergey on 9/26/15.
 */
public abstract class DeviceAbstract {

    protected Room mRoom;
    protected Direction mDirection;
    private ActionsCallback mActionsCallback;
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
    }

    public void turnRight() {
        mDirection = mDirection.getDirectionToRight();
    }

    public boolean canDoStepForward() {
        return mRoom.canGoForward(mDirection);
    }

    public void stepForward() {
        mRoom = mRoom.goForward(mDirection);
    }

    public void setActionsCallback(ActionsCallback callback) {
        mActionsCallback = callback;
    }

    public interface ActionsCallback {
        void turned(Direction newDiewction);
        void positionChanged(Room room);
    }
}
