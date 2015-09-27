package com.test.readdle.sergey.onofreychuck.readdletestapp.storage;

/**
 * Created by sergey on 9/27/15.
 */
public interface LevelStructureStorage {

    void save(LevelStructureDO levelStructureDO, SaveStorageCallback storageCallback);
    void load(LoadStorageCallback callback);

    interface SaveStorageCallback {
        void success();
        void failed();
    }
    interface LoadStorageCallback {
        void success(LevelStructureDO levelStructureDO);
        void failed();
    }
}
