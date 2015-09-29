package com.test.readdle.sergey.onofreychuck.readdletestapp.level;

public class Wall {

    private final RoomAfterProvider mRoomAfterProvider;

    public Wall(RoomAfterProvider roomAfterProvider) {
        mRoomAfterProvider = roomAfterProvider;
    }

    public Wall() {
        this(null);
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
