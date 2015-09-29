package com.test.readdle.sergey.onofreychuck.readdletestapp.storage;

import android.graphics.Bitmap;

import com.test.readdle.sergey.onofreychuck.readdletestapp.level.Direction;
import com.test.readdle.sergey.onofreychuck.readdletestapp.level.RoomCoordinates;

public interface ImageProvider {
    void tryGetImage(RoomCoordinates coordinates, Direction direction, GetImageCallback callback);

    interface GetImageCallback {
        void success(Bitmap image);
        //TODO add error codes
        void notLoaded();
    }
}
