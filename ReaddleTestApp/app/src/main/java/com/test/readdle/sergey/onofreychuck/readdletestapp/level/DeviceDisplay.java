package com.test.readdle.sergey.onofreychuck.readdletestapp.level;

import android.graphics.Bitmap;

import com.test.readdle.sergey.onofreychuck.readdletestapp.storage.ImageProvider;

import java.util.Map;

/**
 * Created by sergey on 9/26/15.
 */
abstract public class DeviceDisplay extends DeviceAbstract {

    private ImageProvider mImageProvider;
    private Bitmap mDefaultBitmap;
    private ImageProvider.GetImageCallback mGetImageCallback;

    public DeviceDisplay(Room currentRoom, Direction direction, Map<Direction,Bitmap> icons, ImageProvider imageProvider, Bitmap defaultBitmap) {
        super(currentRoom, direction, icons);

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
        super.positionChanged();
        mImageProvider.tryGetImage(getRoom().getCoordinates(), getDirection(), mGetImageCallback);
    }

    public void start() {
        positionChanged();
    }

    public abstract void process(Bitmap image);
}
