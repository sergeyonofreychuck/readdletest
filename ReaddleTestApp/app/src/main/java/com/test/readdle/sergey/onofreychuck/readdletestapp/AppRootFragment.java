package com.test.readdle.sergey.onofreychuck.readdletestapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.readdle.sergey.onofreychuck.readdletestapp.level.RoomCoordinates;
import com.test.readdle.sergey.onofreychuck.readdletestapp.storage.LevelStructureFileStorage;
import com.test.readdle.sergey.onofreychuck.readdletestapp.storage.LevelStructureStorage;
import com.test.readdle.sergey.onofreychuck.readdletestapp.widgets.MiniMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class AppRootFragment extends Fragment {

    MiniMap mMiniMap;
    List<RoomCoordinates> mRooms;
    LevelStructureStorage mLevelStructureStorage;

    public AppRootFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLevelStructureStorage = new LevelStructureFileStorage(getContext());

        mLevelStructureStorage.load(Globals.LEVEL_SAVE_KEY, new LevelStructureStorage.LoadCallback() {
            @Override
            public void success(List<RoomCoordinates> coordinates) {
                mRooms = new ArrayList<>(coordinates);
                if (mMiniMap != null) {
                    mMiniMap.setRooms(mRooms);
                }
            }

            @Override
            public void failed() {
                //TODO handle errors
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_app_root, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        mMiniMap = (MiniMap) getView().findViewById(R.id.mini_map);
        mMiniMap.initGrid(Globals.LEVEL_X_DIMENSION, Globals.LEVEL_Y_DIMENSION);

        if (mRooms != null) {
            mMiniMap.setRooms(mRooms);
        } else {
            mRooms = new ArrayList<>();
        }

        mMiniMap.setOnTouchListener(new MiniMap.MiniMapOnTouchListener() {
            @Override
            public void onTouch(final RoomCoordinates coordinates) {
                if (mRooms.contains(coordinates)){
                    mRooms.remove(coordinates);
                } else {
                    mRooms.add(coordinates);
                }
                mMiniMap.setRooms(mRooms);

                mLevelStructureStorage.save(
                        Globals.LEVEL_SAVE_KEY,
                        mRooms, LevelStructureStorage.EMPTY_CALLBACK);
            }
        });
    }
}
