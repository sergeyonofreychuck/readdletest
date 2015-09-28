package com.test.readdle.sergey.onofreychuck.readdletestapp.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.test.readdle.sergey.onofreychuck.readdletestapp.level.RoomCoordinates;

import java.util.Arrays;
import java.util.List;

/**
 * Created by sergey on 9/27/15.
 */
public class LevelStructureFileStorage implements LevelStructureStorage {

    private static final String ID_PREFIX = "LevelStructure";

    private SharedPreferences mPreferences;
    private Handler mHandler;

    public LevelStructureFileStorage(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context");
        }

        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void save(String id, List<RoomCoordinates> coordinates, final SaveCallback callback) {
        if (TextUtils.isEmpty(id)) {
            throw new IllegalArgumentException("id");
        }
        if (callback == null) {
            throw new IllegalArgumentException("callback");
        }
        if (coordinates == null) {
            throw new IllegalArgumentException("coordinates");
        }

        final boolean success = mPreferences.edit().putString(
                getPreferencesKey(id),
                new Gson().toJson(coordinates.toArray(new RoomCoordinates[0]))
        ).commit();

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (success) {
                    callback.success();
                } else {
                    callback.failed();
                }
            }
        });
    }

    @Override
    public void load(String id, LoadCallback callback) {
        if (TextUtils.isEmpty(id)) {
            throw new IllegalArgumentException("id");
        }
        if (callback == null) {
            throw new IllegalArgumentException("callback");
        }

        String preferencesKey = getPreferencesKey(id);

        String structure = mPreferences.getString(preferencesKey, null);
        if (structure == null){
            postLoadResult(callback, null, false);
            return;
        }

        Gson gson = new Gson();
        RoomCoordinates[] ret = gson.fromJson(structure, RoomCoordinates[].class);

        postLoadResult(callback, Arrays.asList(ret), true);
    }

    private void postLoadResult(final LoadCallback callback, final List<RoomCoordinates> coordinates, final boolean success) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (success) {
                    callback.success(coordinates);
                } else {
                    callback.failed();
                }
            }
        });
    }

    private static String getPreferencesKey(String levelId){
        return ID_PREFIX + levelId;
    }
}
