package com.test.readdle.sergey.onofreychuck.readdletestapp.level;

import android.graphics.Bitmap;

/**
 * Created by sergey on 9/26/15.
 */
abstract public class DeviceDisplay extends DeviceAbstract {

    private ImageProvider mImageProvider;
    private Bitmap mDefaultBitmap;
    ImageProvider.GetImageCallback mGetImageCallback;

    public DeviceDisplay(Room currentRoom, Direction direction, Bitmap icon, ImageProvider imageProvider, Bitmap defaultBitmap) {
        super(currentRoom, direction, icon);

        if (imageProvider == null) {
            throw new IllegalArgumentException("imageProvider");
        }
        if (defaultBitmap == null) {
            throw new IllegalArgumentException("defaultBitmap");
        }

        mImageProvider = imageProvider;
        mDefaultBitmap = defaultBitmap;

        mGetImageCallback = new ImageProvider.GetImageCallback() {
            @Override
            public void success(Bitmap image) {
                process(image);
            }

            @Override
            public void notLoaded() {
                process(mDefaultBitmap);
            }
        };
    }

    @Override
    public void positionChanged(){
        mImageProvider.tryGetImage(mRoom.getCoordinates(), mDirection, mGetImageCallback);
    }

    abstract void process(Bitmap image);
}
