package com.test.readdle.sergey.onofreychuck.readdletestapp.level;

import android.graphics.Bitmap;

/**
 * Created by sergey on 9/26/15.
 */
public class DeviceCamera extends DeviceAbstract {

    private ImageSaver mImageSaver;

    public DeviceCamera(Room currentRoom, Direction direction, Bitmap icon, ImageSaver imageSaver) {
        super(currentRoom, direction, icon);

        if (imageSaver == null) {
            throw new IllegalArgumentException("imageSaver");
        }

        mImageSaver = imageSaver;
    }

    @Override
    public void positionChanged(){

    }

    public void processImage(final Bitmap image){
        mImageSaver.saveImage(image, mRoom.getCoordinates(), mDirection);
    }
}
