package com.test.readdle.sergey.onofreychuck.readdletestapp.level;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public abstract class DeviceAbstract implements Trackable {

    protected final String TAG = getClass().getSimpleName();

    private Room mRoom;
    private Direction mDirection;
    private PositionChangedCallback mPositionCallback;
    private Map<Direction,Bitmap> mIcons;

    protected DeviceAbstract(Room currentRoom, Direction direction, Map<Direction,Bitmap> icons) {
        if (currentRoom == null){
            throw new IllegalArgumentException("location");
        }
        if (direction == null) {
            throw new IllegalArgumentException("direction");
        }
        if (icons == null) {
            throw new IllegalArgumentException("icons");
        }
        if (icons.size() != Direction.getLength()) {
            throw new IllegalArgumentException("icons. invalid directions");
        }

        mRoom = currentRoom;
        mDirection = direction;
        mIcons = new HashMap<>(icons);
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

    protected void positionChanged(){
        if (mPositionCallback != null) {
            mPositionCallback.positionChanged(mRoom, mDirection);
        }
    }

    public void moveTo(Trackable trackable) {
        mRoom = trackable.getRoom();
        mDirection = trackable.getDirection();
        positionChanged();
    }

    @Override
    public Bitmap getIcon() {
        return mIcons.get(mDirection);
    }

    @Override
    public void setActionsCallback(PositionChangedCallback callback) {
        mPositionCallback = callback;
    }

    @Override
    public Room getRoom() {
        return mRoom;
    }

    @Override
    public Direction getDirection() {
        return mDirection;
    }

    public interface PositionChangedCallback {
        void positionChanged(Room room, Direction direction);
    }
}
