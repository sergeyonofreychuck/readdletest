package com.test.readdle.sergey.onofreychuck.readdletestapp.level;

import android.graphics.Bitmap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sergey on 9/26/15.
 */
public class LevelBuilder {

    private Level mLevel;
    private List<RoomCoordinates> mLevelStructure;
    private ImageStorageFactory mImageStorageFactory;
    private Bitmap mDefaultImage;

    public LevelBuilder(Level level, List<RoomCoordinates> levelStructure, ImageStorageFactory imageStorage, Bitmap defaultImage) {
        if (levelStructure == null) {
            throw new IllegalArgumentException("levelStructure");
        }
        if (imageStorage == null) {
            throw new IllegalArgumentException("imageStorage");
        }
        if (defaultImage == null) {
            throw new IllegalArgumentException("defaultImage");
        }
        if (level == null) {
            throw new IllegalArgumentException("level");
        }

        mLevel = level;
        mLevelStructure = levelStructure;
        mImageStorageFactory = imageStorage;
        mDefaultImage = defaultImage;
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
            walls.put(direction, new Wall(
                    mImageStorageFactory.getImageProvider(coordinates, direction),
                    mImageStorageFactory.getImageSaver(coordinates, direction),
                    roomAfterProvider,
                    mDefaultImage));
        }

        return new Room(walls);
    }

    //Can return null
    private Wall.RoomAfterProvider getRoomAfterProvider(final RoomCoordinates coordinates, final Direction direction) {
        if (canBeAcrossed(coordinates, direction)){
            return new Wall.RoomAfterProvider() {
                @Override
                public Room getRoom() {
                    return mLevel.getRoom(direction.getNextRoomLoacation(coordinates));
                }
            };
        }
        return null;
    }

    private boolean canBeAcrossed(RoomCoordinates coordinates, Direction direction) {
        return mLevelStructure.contains(direction.getNextRoomLoacation(coordinates));
    }
}
