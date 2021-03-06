package com.test.readdle.sergey.onofreychuck.readdletestapp.storage;

import com.test.readdle.sergey.onofreychuck.readdletestapp.level.Direction;
import com.test.readdle.sergey.onofreychuck.readdletestapp.level.RoomCoordinates;

import java.io.File;

public interface ImageSaver {
    void saveImage(File imageFile, RoomCoordinates coordinates, Direction direction, SaveImageCallback callback);

    interface SaveImageCallback {
        void success();
        //TODO add error codes
        void notSaved();
    }
}
