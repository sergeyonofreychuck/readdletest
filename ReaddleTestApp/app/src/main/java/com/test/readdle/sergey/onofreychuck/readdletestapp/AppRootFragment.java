package com.test.readdle.sergey.onofreychuck.readdletestapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.readdle.sergey.onofreychuck.readdletestapp.level.RoomCoordinates;
import com.test.readdle.sergey.onofreychuck.readdletestapp.widgets.MiniMap;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class AppRootFragment extends Fragment {

    MiniMap mMiniMap;
    ArrayList<RoomCoordinates> mRooms = new ArrayList<>();

    public AppRootFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_app_root, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        mMiniMap = (MiniMap) getActivity().findViewById(R.id.mini_map);

        mMiniMap.initGrid(8, 8);
        mMiniMap.setRooms(mRooms);

        mMiniMap.setOnTouchListener(new MiniMap.MiniMapOnTouchListener() {
            @Override
            public void onTouch(final RoomCoordinates coordinates) {
                if (mRooms.contains(coordinates)){
                    mRooms.remove(coordinates);
                } else {
                    mRooms.add(coordinates);
                }
                mMiniMap.setRooms(mRooms);
            }
        });
    }
}
