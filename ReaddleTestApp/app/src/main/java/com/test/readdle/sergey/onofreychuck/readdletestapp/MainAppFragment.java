package com.test.readdle.sergey.onofreychuck.readdletestapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.test.readdle.sergey.onofreychuck.readdletestapp.level.DeviceCamera;
import com.test.readdle.sergey.onofreychuck.readdletestapp.level.DeviceDisplay;
import com.test.readdle.sergey.onofreychuck.readdletestapp.level.Direction;
import com.test.readdle.sergey.onofreychuck.readdletestapp.level.Level;
import com.test.readdle.sergey.onofreychuck.readdletestapp.level.Room;
import com.test.readdle.sergey.onofreychuck.readdletestapp.level.RoomCoordinates;
import com.test.readdle.sergey.onofreychuck.readdletestapp.storage.FilesImageProvider;
import com.test.readdle.sergey.onofreychuck.readdletestapp.storage.FilesImageSaver;
import com.test.readdle.sergey.onofreychuck.readdletestapp.storage.ImageFileNameProvider;
import com.test.readdle.sergey.onofreychuck.readdletestapp.storage.ImageProvider;
import com.test.readdle.sergey.onofreychuck.readdletestapp.storage.ImageSaver;
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
    private DeviceCamera mCamera;
    private ImageView mImageViewDisplay;

    public MainAppFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_app, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menu.clear();
        menuInflater.inflate(R.menu.menu_app_root, menu);
    }


    @Override
    public void onStart() {
        super.onStart();

        final MiniMap miniMap = (MiniMap) getView().findViewById(R.id.mini_map);
        miniMap.initGrid(Globals.LEVEL_X_DIMENSION, Globals.LEVEL_Y_DIMENSION);

        mImageViewDisplay = (ImageView)getView().findViewById(R.id.img_display);

        new LevelStructureFileStorage(getContext()).load(Globals.LEVEL_SAVE_KEY, new LevelStructureStorage.LoadCallback() {
            @Override
            public void success(List<RoomCoordinates> coordinates) {
                mLevel = Level.BuildLevel(coordinates, Globals.LEVEL_X_DIMENSION, Globals.LEVEL_Y_DIMENSION);
                miniMap.setRooms(new ArrayList<>(coordinates));
                initializeDevices(mLevel);
                miniMap.setTrackedObject(mDisplay);
                initializeButtonsListeners();
            }

            @Override
            public void failed() {
                //TODO handle errors
            }
        });
    }

    private void initializeDevices(Level level) {
        Room startRoom = level.getRoom(new RoomCoordinates(1, 1)); //FIXME

        Map<Direction, Bitmap> icons = new HashMap<>();
        icons.put(Direction.EAST, BitmapFactory.decodeResource(getResources(), R.drawable.arrow_east));
        icons.put(Direction.SOUTH, BitmapFactory.decodeResource(getResources(), R.drawable.arrow_south));
        icons.put(Direction.WEST, BitmapFactory.decodeResource(getResources(), R.drawable.arrow_west));
        icons.put(Direction.NORTH, BitmapFactory.decodeResource(getResources(), R.drawable.arrow_noth));

        ImageFileNameProvider imageFileNameProvider =
                new ImageFileNameProvider(getContext().getExternalFilesDir(android.os.Environment.DIRECTORY_PICTURES).getAbsolutePath());

        initializeDisplay(startRoom, imageFileNameProvider, icons);

        initializeCamera(startRoom, imageFileNameProvider, icons);
    }

    private void initializeDisplay(Room room, ImageFileNameProvider imageFileNameProvider, Map<Direction, Bitmap> icons) {
        Bitmap defaultImage = BitmapFactory.decodeResource(getResources(), R.drawable.no_image_available);

        ImageProvider imageProvider = new FilesImageProvider(imageFileNameProvider);

        mDisplay = new DeviceDisplay(
                room,
                Direction.NORTH,
                icons,
                imageProvider,
                defaultImage) {

            @Override
            public void process(Bitmap image) {
                Log.e(TAG, "process bitmap");
                mImageViewDisplay.setImageBitmap(image);
            }
        };

        mDisplay.start();
    }

    private void initializeCamera(Room room, ImageFileNameProvider imageFileNameProvider, Map<Direction, Bitmap> icons) {
        ImageSaver imageSaver = new FilesImageSaver(imageFileNameProvider);
        mCamera = new DeviceCamera(this, room, Direction.NORTH, icons, imageSaver);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCamera.processActivityForResult(requestCode, resultCode);
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

        getView().findViewById(R.id.btn_loadImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCamera.moveTo(mDisplay);
                mCamera.makePhoto();
            }
        });
    }
}
