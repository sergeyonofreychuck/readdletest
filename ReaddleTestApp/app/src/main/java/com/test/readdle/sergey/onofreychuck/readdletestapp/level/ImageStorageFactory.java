package com.test.readdle.sergey.onofreychuck.readdletestapp.level;

import android.graphics.Bitmap;

/**
 * Created by sergey on 9/26/15.
 */
public interface ImageStorageFactory {
    ImageSaver getImageSaver(RoomCoordinates coordinates, Direction direction);
    ImageProvider getImageProvider(RoomCoordinates coordinates, Direction direction);
}
