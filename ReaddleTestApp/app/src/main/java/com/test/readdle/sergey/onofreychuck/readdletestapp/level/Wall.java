package com.test.readdle.sergey.onofreychuck.readdletestapp.level;

import android.graphics.Bitmap;

/**
 * Created by sergey on 9/26/15.
 */
public class Wall {

    private RoomAfterProvider mRoomAfterProvider;

    public Wall(
            RoomAfterProvider roomAfterProvider) {

        mRoomAfterProvider = roomAfterProvider;
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
