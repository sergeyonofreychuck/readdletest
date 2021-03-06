package com.test.readdle.sergey.onofreychuck.readdletestapp.level;

import java.util.List;
import java.util.Map;

public class Level {

    private Map<RoomCoordinates, Room> mRooms;

    public static Level BuildLevel(List<RoomCoordinates> levelStructure) {
        LevelBuilder levelBuilder = new LevelBuilder(new Level(), levelStructure);

        return levelBuilder.buildLevel();
    }

    private Level() {}

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
}
