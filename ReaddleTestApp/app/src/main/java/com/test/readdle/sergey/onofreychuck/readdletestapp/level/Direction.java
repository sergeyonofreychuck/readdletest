package com.test.readdle.sergey.onofreychuck.readdletestapp.level;

/**
 * Created by sergey on 9/26/15.
 */
public enum Direction {
    SOUTH (0, 1),
    WEST (-1, 0),
    NORTH (0, -1),
    EAST (1, 0);

    private static final int mLength = values().length;

    private final int mXDirectionModificator;
    private final int mYDirectionModificator;

    Direction(int xModificator, int yModificator) {
        mXDirectionModificator = xModificator;
        mYDirectionModificator = yModificator;
    }

    public static int getLength() {
        return mLength;
    }

    public Direction getDirectionToRight(){
        if (ordinal() == mLength - 1) {
            return values()[0];
        }
        return values()[ordinal() + 1];
    }

    public Direction getDirectionToLeft(){
        if (ordinal() == 0) {
            return values()[mLength - 1];
        }
        return values()[ordinal() - 1];
    }

    public RoomCoordinates getNextRoomLoacation(RoomCoordinates coordinates) {
        return new RoomCoordinates(
                coordinates.getX() + mXDirectionModificator,
                coordinates.getY() + mYDirectionModificator);
    }
}
