package com.test.readdle.sergey.onofreychuck.readdletestapp.level;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sergey on 9/26/15.
 */
public class Room {

    private Map<Direction, Wall> mWalls;

    public Room(Map<Direction, Wall> walls) {

        if (walls.size() != Direction.getLength()) {
            throw new IllegalArgumentException("walls");
        }
        
        mWalls = new HashMap<>(walls);
    }

    public Wall getNextWallToRight(Direction direction) {
        return mWalls.get(direction.getDirectionToRight());
    }

    public Wall getNextWallToLeft(Direction direction) {
        return mWalls.get(direction.getDirectionToLeft());
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

    public Wall getWall(Direction direction) {
        return mWalls.get(direction);
    }
}
