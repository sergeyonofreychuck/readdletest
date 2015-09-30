package com.test.readdle.sergey.onofreychuck.readdletestapp.storage;

import com.test.readdle.sergey.onofreychuck.readdletestapp.level.Direction;
import com.test.readdle.sergey.onofreychuck.readdletestapp.level.RoomCoordinates;

import java.io.File;

public class ImageFileNameProvider {

    private File mDestinationDirectory;

    public ImageFileNameProvider(File destinationDirectory) {
        if (destinationDirectory == null) {
            throw new IllegalArgumentException("destinationDirectory");
        }
        if (!destinationDirectory.exists()) {
            throw new IllegalArgumentException("destination directory not exists");
        }

        mDestinationDirectory = destinationDirectory;
    }

    public File getImageFile(RoomCoordinates coordinates, Direction direction) {
        return new File(mDestinationDirectory,
                coordinates.getX() + "_" + coordinates.getY() + "_" + direction + ".jpg");
    }
}
