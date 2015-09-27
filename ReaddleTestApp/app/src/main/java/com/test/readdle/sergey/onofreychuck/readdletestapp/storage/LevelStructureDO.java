package com.test.readdle.sergey.onofreychuck.readdletestapp.storage;

import com.google.gson.annotations.SerializedName;
import com.test.readdle.sergey.onofreychuck.readdletestapp.level.RoomCoordinates;

import java.util.Arrays;
import java.util.List;

/**
 * Created by sergey on 9/27/15.
 */
public class LevelStructureDO {

    @SerializedName("coordinates")
    RoomCoordinates[] mCoordinates;

    public LevelStructureDO(List<RoomCoordinates> coordinates) {
        if (coordinates == null) {
            throw new IllegalArgumentException("coordinates");
        }

        mCoordinates = coordinates.toArray(new RoomCoordinates[0]);
    }

    public List<RoomCoordinates> getCoordinates() {
        return Arrays.asList(mCoordinates);
    }

}
