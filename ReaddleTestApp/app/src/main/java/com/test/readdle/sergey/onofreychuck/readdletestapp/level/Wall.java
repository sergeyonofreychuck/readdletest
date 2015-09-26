package com.test.readdle.sergey.onofreychuck.readdletestapp.level;

import android.graphics.Bitmap;

/**
 * Created by sergey on 9/26/15.
 */
public class Wall {

    private ImageProvider mImageProvider;
    private ImageSaver mImageSaver;
    private RoomAfterProvider mRoomAfterProvider;
    private Bitmap mDefaultBackground;

    public Wall(
            ImageProvider imageProvider,
            ImageSaver imageSaver,
            RoomAfterProvider roomAfterProvider,
            Bitmap defaultBackground) {

        if (imageProvider == null) {
            throw new IllegalArgumentException("imageProvider");
        }
        if (imageSaver == null) {
            throw new IllegalArgumentException("imageSaver");
        }
        if (defaultBackground == null) {
            throw new IllegalArgumentException("default background");
        }
        if (roomAfterProvider == null) {
            throw new IllegalArgumentException("roomAfterProvider");
        }

        mRoomAfterProvider = roomAfterProvider;
        mDefaultBackground = defaultBackground;
        mImageProvider = imageProvider;
        mImageSaver = imageSaver;
    }

    public Bitmap getImage() {
        if (mImageProvider.isImagePresent()) {
            return mDefaultBackground;
        }

        try {
            return mImageProvider.getImage();
        } catch (ImageNotLoadedException e) {
            //FIXME should be changed to runtime exception?
            return mDefaultBackground;
        }
    }

    public void saveImage(Bitmap image) {
        mImageSaver.saveImage(image);
    }

    public boolean canGoAcross() {
        return mRoomAfterProvider != null;
    }

    public Room getRoomAfter() {
        return mRoomAfterProvider.getRoom();
    }

    public interface RoomAfterProvider {
        Room getRoom();
    }
}
