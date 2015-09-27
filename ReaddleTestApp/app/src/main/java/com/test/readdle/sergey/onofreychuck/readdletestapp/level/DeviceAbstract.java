package com.test.readdle.sergey.onofreychuck.readdletestapp.level;

import android.graphics.Bitmap;
import android.util.Log;

import com.test.readdle.sergey.onofreychuck.readdletestapp.widgets.Trackable;

/**
 * Created by sergey on 9/26/15.
 */
public abstract class DeviceAbstract implements Trackable {

    private final String TAG = getClass().getSimpleName();

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
        Log.d(TAG, "turn left");
        mDirection = mDirection.getDirectionToLeft();
        positionChanged();
    }

    public void turnRight() {
        Log.d(TAG, "turn right");
        mDirection = mDirection.getDirectionToRight();
        positionChanged();
    }

    public boolean canDoStepForward() {
        return mRoom.canGoForward(mDirection);
    }

    public void stepForward() {
        Log.d(TAG, "step forward");
        mRoom = mRoom.goForward(mDirection);
        positionChanged();
    }

    public void positionChanged(){
        if (mPositionCallback != null) {
            mPositionCallback.positionChanged(mRoom, mDirection);
        }
    }

    public Bitmap getIcon() {
        return mIcon;
    }

    @Override
    public void setActionsCallback(PositionChangedCallback callback) {
        mPositionCallback = callback;
    }

    @Override
    public RoomCoordinates getPosition() {
        return mRoom.getCoordinates();
    }

    @Override
    public Direction getDirection() {
        return mDirection;
    }

    public interface PositionChangedCallback {
        void positionChanged(Room room, Direction direction);
    }
}
