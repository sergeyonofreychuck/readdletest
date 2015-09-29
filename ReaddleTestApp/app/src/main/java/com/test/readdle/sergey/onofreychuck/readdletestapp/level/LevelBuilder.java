package com.test.readdle.sergey.onofreychuck.readdletestapp.level;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sergey on 9/26/15.
 */
class LevelBuilder {

    private Level mLevel;
    private List<RoomCoordinates> mLevelStructure;

    public LevelBuilder(Level level, List<RoomCoordinates> levelStructure) {
        if (levelStructure == null) {
            throw new IllegalArgumentException("levelStructure");
        }
        if (level == null) {
            throw new IllegalArgumentException("level");
        }

        mLevel = level;
        mLevelStructure = levelStructure;
    }

    public Level buildLevel(){
        Map<RoomCoordinates, Room> rooms = new HashMap<>();

        for (RoomCoordinates coordinates : mLevelStructure) {
            rooms.put(coordinates, buildRoom(coordinates));
        }

        mLevel.initializeWithRooms(rooms);
        return mLevel;
    }

    private Room buildRoom(final RoomCoordinates coordinates) {
        Map<Direction, Wall> walls = new HashMap<>();
        for (final Direction direction : Direction.values()){
            Wall.RoomAfterProvider roomAfterProvider = getRoomAfterProvider(coordinates, direction);
            walls.put(direction, roomAfterProvider == null ? new Wall() : new Wall(roomAfterProvider));
        }

        return new Room(coordinates, walls);
    }

    //Can return null
    private Wall.RoomAfterProvider getRoomAfterProvider(final RoomCoordinates coordinates, final Direction direction) {
        if (canBeСrossed(coordinates, direction)){
            return new Wall.RoomAfterProvider() {
                @Override
                public Room getRoom() {
                    return mLevel.getRoom(direction.getNextRoomLocation(coordinates));
                }
            };
        }
        return null;
    }

    private boolean canBeСrossed(RoomCoordinates coordinates, Direction direction) {
        return mLevelStructure.contains(direction.getNextRoomLocation(coordinates));
    }
}
