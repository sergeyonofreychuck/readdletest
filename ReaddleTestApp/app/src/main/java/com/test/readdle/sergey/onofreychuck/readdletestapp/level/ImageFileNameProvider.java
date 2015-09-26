package com.test.readdle.sergey.onofreychuck.readdletestapp.level;

import android.text.TextUtils;

import java.io.File;

/**
 * Created by sergey on 9/26/15.
 */
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
