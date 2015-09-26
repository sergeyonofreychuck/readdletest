package com.test.readdle.sergey.onofreychuck.readdletestapp.level;

import android.graphics.Bitmap;

/**
 * Created by sergey on 9/26/15.
 */
public interface ImageProvider {
    void tryGetImage(RoomCoordinates coordinates, Direction direction, GetImageCallback callback);

    interface GetImageCallback {
        void success(Bitmap image);
        void notLoaded();
    }
}
