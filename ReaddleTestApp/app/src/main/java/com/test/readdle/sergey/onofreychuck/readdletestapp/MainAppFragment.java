package com.test.readdle.sergey.onofreychuck.readdletestapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.readdle.sergey.onofreychuck.readdletestapp.level.RoomCoordinates;
import com.test.readdle.sergey.onofreychuck.readdletestapp.storage.LevelStructureFileStorage;
import com.test.readdle.sergey.onofreychuck.readdletestapp.storage.LevelStructureStorage;
import com.test.readdle.sergey.onofreychuck.readdletestapp.widgets.MiniMap;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by sergey on 9/27/15.
 */
public class MainAppFragment extends Fragment {

    public MainAppFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_app, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        final MiniMap miniMap = (MiniMap) getView().findViewById(R.id.mini_map);
        miniMap.initGrid(Globals.LEVEL_X_DIMENSION, Globals.LEVEL_Y_DIMENSION);

        new LevelStructureFileStorage(getContext()).load(Globals.LEVEL_SAVE_KEY, new LevelStructureStorage.LoadCallback() {
            @Override
            public void success(List<RoomCoordinates> coordinates) {
                miniMap.setRooms(new ArrayList<>(coordinates));
            }

            @Override
            public void failed() {
                //TODO handle errors
            }
        });

    }
}
