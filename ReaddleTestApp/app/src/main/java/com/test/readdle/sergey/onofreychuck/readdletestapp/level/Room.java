package com.test.readdle.sergey.onofreychuck.readdletestapp.level;

import java.util.HashMap;
import java.util.Map;

public class Room {

    private Map<Direction, Wall> mWalls;
    private RoomCoordinates mRoomCoordinates;

    public Room(RoomCoordinates coordinates, Map<Direction, Wall> walls) {
        if (coordinates == null) {
            throw new IllegalArgumentException("coordinates");
        }
        if (walls.size() != Direction.getLength()) {
            throw new IllegalArgumentException("walls");
        }

        mRoomCoordinates = coordinates;
        mWalls = new HashMap<>(walls);
    }

    public boolean canGoForward(Direction direction) {
        return mWalls.get(direction).canGoAcross();
    }

    public Room goForward(Direction direction) {
        Room ret = mWalls.get(direction).getRoomAfter();

        if (ret == null) {
            throw new IllegalArgumentException("cant move to " + direction);
        }

        return ret;
    }

    public RoomCoordinates getCoordinates(){
        return mRoomCoordinates;
    }
}
