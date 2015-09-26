package com.test.readdle.sergey.onofreychuck.readdletestapp.level;

import android.graphics.Bitmap;

/**
 * Created by sergey on 9/26/15.
 */
abstract public class DeviceDisplay extends DeviceAbstract {

    public DeviceDisplay(Room currentRoom, Direction direction, Bitmap icon) {
        super(currentRoom, direction, icon);
    }

    abstract void process(Bitmap image);
}
