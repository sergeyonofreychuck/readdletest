package com.test.readdle.sergey.onofreychuck.readdletestapp.level;

import android.graphics.Bitmap;

/**
 * Created by sergey on 9/26/15.
 */
public interface ImageSaver {
    void saveImage(Bitmap image, RoomCoordinates coordinates, Direction direction, SaveImageCallback callback);

    interface SaveImageCallback {
        void success();
        //TODO add error codes
        void notSaved();
    }

}
