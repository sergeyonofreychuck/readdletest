package com.test.readdle.sergey.onofreychuck.readdletestapp.storage;

import android.text.TextUtils;

import com.test.readdle.sergey.onofreychuck.readdletestapp.level.Direction;
import com.test.readdle.sergey.onofreychuck.readdletestapp.level.RoomCoordinates;

import java.io.File;

public class ImageFileNameProvider {

    private File mDestinationDirectory;

    public ImageFileNameProvider(String path) {
        if (TextUtils.isEmpty(path)) {
            throw new IllegalArgumentException("path");
        }

        mDestinationDirectory = new File(path);
        if (!mDestinationDirectory.exists()) {
            throw new IllegalArgumentException("destination path not exists " + path);
        }
    }

    public File getImageFile(RoomCoordinates coordinates, Direction direction) {
        return new File(mDestinationDirectory,
                coordinates.getX() + "_" + coordinates.getY() + "_" + direction + ".jpg");
    }
}
