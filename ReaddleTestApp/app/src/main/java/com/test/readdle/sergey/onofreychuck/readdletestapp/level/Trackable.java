package com.test.readdle.sergey.onofreychuck.readdletestapp.level;

import android.graphics.Bitmap;

public interface Trackable {
    void setActionsCallback(DeviceAbstract.PositionChangedCallback callback);
    Room getRoom();
    Direction getDirection();
    Bitmap getIcon();
}
