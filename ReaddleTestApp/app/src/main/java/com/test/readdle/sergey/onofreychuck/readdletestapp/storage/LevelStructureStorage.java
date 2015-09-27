package com.test.readdle.sergey.onofreychuck.readdletestapp.storage;

import com.test.readdle.sergey.onofreychuck.readdletestapp.level.RoomCoordinates;

import java.util.List;

/**
 * Created by sergey on 9/27/15.
 */
public interface LevelStructureStorage {

    SaveCallback EMPTY_CALLBACK = new SaveCallback() {
        @Override
        public void success() {

        }

        @Override
        public void failed() {
            throw new RuntimeException("failed saving level structure");
        }
    } ;

    void save(String id, List<RoomCoordinates> coordinates, SaveCallback storageCallback);
    void load(String id,LoadCallback callback);

    interface SaveCallback {
        void success();
        void failed();
    }
    interface LoadCallback {
        void success(List<RoomCoordinates> coordinates);
        void failed();
    }
}
