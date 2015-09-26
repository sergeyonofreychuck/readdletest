package com.test.readdle.sergey.onofreychuck.readdletestapp.level;

import android.graphics.Bitmap;

/**
 * Created by sergey on 9/26/15.
 */
public class DeviceCamera extends DeviceAbstract {

    public DeviceCamera(Room currentRoom, Direction direction, Bitmap icon) {
        super(currentRoom, direction, icon);
    }

    public void draw(final Bitmap image){
        new Thread(new Runnable() {
            @Override
            public void run() {
                mRoom.getWall(mDirection).saveImage(image);
            }
        }).start();
    }
}
