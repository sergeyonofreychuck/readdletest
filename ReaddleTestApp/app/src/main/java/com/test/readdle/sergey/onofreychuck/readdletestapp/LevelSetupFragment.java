package com.test.readdle.sergey.onofreychuck.readdletestapp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
public class LevelSetupFragment extends Fragment {

    private ConfigurationChangedListener mConfigurationChangedListener;
    private List<RoomCoordinates> mRooms;

    public LevelSetupFragment() {
        mRooms = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View rootView = inflater.inflate(R.layout.level_setup, container, false);

        final MiniMap miniMap = (MiniMap) rootView.findViewById(R.id.mini_map);
        miniMap.initGrid(Globals.LEVEL_X_DIMENSION, Globals.LEVEL_Y_DIMENSION);

        final LevelStructureStorage levelStructureStorage = new LevelStructureFileStorage(getContext());
        levelStructureStorage.load(Globals.LEVEL_SAVE_KEY, new LevelStructureStorage.LoadCallback() {
            @Override
            public void success(List<RoomCoordinates> coordinates) {
                mRooms.clear();
                mRooms.addAll(coordinates);
                miniMap.setRooms(mRooms);
            }

            @Override
            public void failed() {
                throw new RuntimeException("failed saving level structure");
                //TODO handle errors
            }
        });

        miniMap.setOnTouchListener(new MiniMap.MiniMapOnTouchListener() {
            @Override
            public void onTouch(final RoomCoordinates coordinates) {
                if (mRooms.contains(coordinates)) {
                    mRooms.remove(coordinates);
                } else {
                    mRooms.add(coordinates);
                }
                miniMap.setRooms(mRooms);
                levelStructureStorage.save(
                        Globals.LEVEL_SAVE_KEY,
                        mRooms, LevelStructureStorage.EMPTY_CALLBACK);
            }
        });

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        menu.clear();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                getActivity().getSupportFragmentManager().popBackStack();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mConfigurationChangedListener = (ConfigurationChangedListener) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mConfigurationChangedListener.onConfigurationChanged(mRooms);
    }
}
