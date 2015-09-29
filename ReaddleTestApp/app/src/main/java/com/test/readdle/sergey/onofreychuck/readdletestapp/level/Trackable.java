package com.test.readdle.sergey.onofreychuck.readdletestapp.level;

import android.graphics.Bitmap;

/**
 * Created by sergey on 9/27/15.
 */
public interface Trackable {
    void setActionsCallback(DeviceAbstract.PositionChangedCallback callback);
    Room getRoom();
    Direction getDirection();
    Bitmap getIcon();
}
