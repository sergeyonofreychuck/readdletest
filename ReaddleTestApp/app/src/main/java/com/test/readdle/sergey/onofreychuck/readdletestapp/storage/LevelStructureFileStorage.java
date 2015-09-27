package com.test.readdle.sergey.onofreychuck.readdletestapp.storage;

import android.content.Context;

/**
 * Created by sergey on 9/27/15.
 */
public class LevelStructureFileStorage {

    private Context mContext;

    public LevelStructureFileStorage(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context");
        }

        mContext = context;
    }
}
