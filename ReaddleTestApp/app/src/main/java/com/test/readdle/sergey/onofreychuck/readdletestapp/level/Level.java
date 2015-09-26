package com.test.readdle.sergey.onofreychuck.readdletestapp.level;

import java.util.List;
import java.util.Map;

/**
 * Created by sergey on 9/26/15.
 */
public class Level {

    private Map<RoomCoordinates, Room> mRooms;
    private int mWidth;
    private int mHeight;

    public static Level BuildLevel(List<RoomCoordinates> levelStructure, int width, int height) {
        LevelBuilder levelBuilder = new LevelBuilder(new Level(width, height), levelStructure);

        return levelBuilder.buildLevel();
    }

    private Level(int width, int height) {
        if (width == 0 || height == 0) {
            throw new IllegalArgumentException("dimension can be 0. width: " + width + " height: " + height);
        }

        mWidth = width;
        mHeight = height;
    }

    public void initializeWithRooms(Map<RoomCoordinates, Room> rooms) {
        if (rooms == null) {
            throw new IllegalArgumentException("rooms");
        }
        if (mRooms != null) {
            throw new IllegalStateException("the level can be initialized only once");
        }

        mRooms = rooms;
    }

    public Room getRoom(RoomCoordinates coordinates) {
        if (mRooms == null) {
            throw new IllegalStateException("the level still not contracted");
        }
        if (!mRooms.containsKey(coordinates)) {
            throw new IllegalArgumentException("invalidCoordinates");
        }

        return mRooms.get(coordinates);
    }

    public int hetWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }
}
