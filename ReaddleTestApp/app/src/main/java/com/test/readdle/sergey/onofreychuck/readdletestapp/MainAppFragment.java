package com.test.readdle.sergey.onofreychuck.readdletestapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.readdle.sergey.onofreychuck.readdletestapp.level.DeviceDisplay;
import com.test.readdle.sergey.onofreychuck.readdletestapp.level.Direction;
import com.test.readdle.sergey.onofreychuck.readdletestapp.level.Level;
import com.test.readdle.sergey.onofreychuck.readdletestapp.level.Room;
import com.test.readdle.sergey.onofreychuck.readdletestapp.level.RoomCoordinates;
import com.test.readdle.sergey.onofreychuck.readdletestapp.storage.FilesImageProvider;
import com.test.readdle.sergey.onofreychuck.readdletestapp.storage.ImageFileNameProvider;
import com.test.readdle.sergey.onofreychuck.readdletestapp.storage.ImageProvider;
import com.test.readdle.sergey.onofreychuck.readdletestapp.storage.LevelStructureFileStorage;
import com.test.readdle.sergey.onofreychuck.readdletestapp.storage.LevelStructureStorage;
import com.test.readdle.sergey.onofreychuck.readdletestapp.widgets.MiniMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by sergey on 9/27/15.
 */
public class MainAppFragment extends Fragment {

    private Level mLevel;
    private DeviceDisplay mDisplay;

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
                mLevel = Level.BuildLevel(coordinates, Globals.LEVEL_X_DIMENSION, Globals.LEVEL_Y_DIMENSION);
                miniMap.setRooms(new ArrayList<>(coordinates));
                initializeDisplay(mLevel);
                miniMap.setTrackedObject(mDisplay);
                initializeButtonsListeners();
            }

            @Override
            public void failed() {
                //TODO handle errors
            }
        });


    }

    private void initializeDisplay(Level level) {
        Room room = level.getRoom(new RoomCoordinates(1, 1));

        Map<Direction, Bitmap> icons = new HashMap<>();
        icons.put(Direction.EAST, BitmapFactory.decodeResource(getResources(), R.drawable.arrow_east));
        icons.put(Direction.SOUTH, BitmapFactory.decodeResource(getResources(), R.drawable.arrow_south));
        icons.put(Direction.WEST, BitmapFactory.decodeResource(getResources(), R.drawable.arrow_west));
        icons.put(Direction.NORTH, BitmapFactory.decodeResource(getResources(), R.drawable.arrow_noth));

        Bitmap defaultImage = BitmapFactory.decodeResource(getResources(), R.drawable.arrow_west);

        ImageFileNameProvider imageFileNameProvider =
                new ImageFileNameProvider(getContext().getExternalFilesDir(android.os.Environment.DIRECTORY_PICTURES).getAbsolutePath());
        ImageProvider imageProvider = new FilesImageProvider(imageFileNameProvider);

        mDisplay = new DeviceDisplay(
                room,
                Direction.NORTH,
                icons,
                imageProvider,
                defaultImage) {

            @Override
            public void process(Bitmap image) {
                Log.e("zzzzzzzzzzzzzzzz", "process bitmap");
            }
        };

        mDisplay.start();
    }

    private void initializeButtonsListeners() {
        getView().findViewById(R.id.btn_forward).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDisplay.canDoStepForward()){
                    mDisplay.stepForward();
                }
            }
        });

        getView().findViewById(R.id.btn_turn_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDisplay.turnLeft();
            }
        });

        getView().findViewById(R.id.btn_turn_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDisplay.turnRight();
            }
        });
    }
}
