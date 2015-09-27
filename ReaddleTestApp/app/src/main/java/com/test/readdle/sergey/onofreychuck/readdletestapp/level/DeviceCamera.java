package com.test.readdle.sergey.onofreychuck.readdletestapp.level;

import android.graphics.Bitmap;

import com.test.readdle.sergey.onofreychuck.readdletestapp.storage.ImageSaver;

import java.util.Map;

/**
 * Created by sergey on 9/26/15.
 */
public class DeviceCamera extends DeviceAbstract {

    private ImageSaver mImageSaver;

    public DeviceCamera(Room currentRoom, Direction direction, Map<Direction,Bitmap> icons, ImageSaver imageSaver) {
        super(currentRoom, direction, icons);

        if (imageSaver == null) {
            throw new IllegalArgumentException("imageSaver");
        }

        mImageSaver = imageSaver;
    }

    public void processImage(final Bitmap image){
        mImageSaver.saveImage(image, mRoom.getCoordinates(), mDirection, new ImageSaver.SaveImageCallback() {
            @Override
            public void success() {
                //TODO do something
            }

            @Override
            public void notSaved() {
                //TODO do something
            }
        });
    }
}
