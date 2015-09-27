package com.test.readdle.sergey.onofreychuck.readdletestapp.widgets;

import android.graphics.Bitmap;

import com.test.readdle.sergey.onofreychuck.readdletestapp.level.DeviceAbstract;
import com.test.readdle.sergey.onofreychuck.readdletestapp.level.Direction;
import com.test.readdle.sergey.onofreychuck.readdletestapp.level.RoomCoordinates;

/**
 * Created by sergey on 9/27/15.
 */
public interface Trackable {
    void setActionsCallback(DeviceAbstract.PositionChangedCallback callback);
    RoomCoordinates getPosition();
    Direction getDirection();
    Bitmap getIcon();

}
