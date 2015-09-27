package com.test.readdle.sergey.onofreychuck.readdletestapp.storage;

import android.graphics.Bitmap;

import com.test.readdle.sergey.onofreychuck.readdletestapp.level.Direction;
import com.test.readdle.sergey.onofreychuck.readdletestapp.level.RoomCoordinates;

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
