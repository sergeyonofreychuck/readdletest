package com.test.readdle.sergey.onofreychuck.readdletestapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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

public class MainAppFragment extends Fragment {

    private DeviceDisplay mDisplay;
    private DeviceCamera mCamera;
    private ImageView mImageViewDisplay;
    private MiniMap mMiniMap;

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
        final View rootView = inflater.inflate(R.layout.main_app, container, false);

        mMiniMap = (MiniMap) rootView.findViewById(R.id.mini_map);
        mMiniMap.initGrid(Globals.LEVEL_X_DIMENSION, Globals.LEVEL_Y_DIMENSION);

        mImageViewDisplay = (ImageView)rootView.findViewById(R.id.img_display);

        new LevelStructureFileStorage(getContext()).load(Globals.LEVEL_SAVE_KEY, new LevelStructureStorage.LoadCallback() {
            @Override
            public void success(List<RoomCoordinates> coordinates) {
                initializeLevel(coordinates, coordinates.get(coordinates.size() - 1), Globals.DEFAULT_DEVICE_DIRECTION);
                initializeButtonsListeners(rootView);
            }

            @Override
            public void failed() {
                throw new RuntimeException("failed loading level structure");
                //TODO handle errors
            }
        });
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        //noinspection ConstantConditions In this aaplication this code cant be invoken in detached stat
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        menu.clear();
        menuInflater.inflate(R.menu.menu_app_root, menu);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCamera.processActivityForResult(requestCode, resultCode);
    }

    public void onConfigurationChanged(List<RoomCoordinates> rooms) {
        initializeLevel(rooms, mDisplay.getRoom().getCoordinates(), mDisplay.getDirection());
    }

    private void initializeLevel(List<RoomCoordinates> coordinates, RoomCoordinates startCoordinates, Direction startDirection){
        Level level = Level.BuildLevel(coordinates);
        mMiniMap.setRooms(new ArrayList<>(coordinates));
        Room startRoom = level.getRoom(startCoordinates);
        initializeDevices(startRoom, startDirection);
        mMiniMap.setTrackedObject(mDisplay);
    }

    private void initializeDevices(Room startRoom, Direction startDirection) {
        Map<Direction, Bitmap> icons = new HashMap<>();
        icons.put(Direction.EAST, BitmapFactory.decodeResource(getResources(), R.drawable.arrow_east));
        icons.put(Direction.SOUTH, BitmapFactory.decodeResource(getResources(), R.drawable.arrow_south));
        icons.put(Direction.WEST, BitmapFactory.decodeResource(getResources(), R.drawable.arrow_west));
        icons.put(Direction.NORTH, BitmapFactory.decodeResource(getResources(), R.drawable.arrow_noth));

        @SuppressWarnings({"In this aaplication this code cant be invoked in detached state", "ConstantConditions"})
        ImageFileNameProvider imageFileNameProvider =
                new ImageFileNameProvider(getContext().getExternalFilesDir(android.os.Environment.DIRECTORY_PICTURES).getAbsolutePath());

        initializeDisplay(startRoom, startDirection, imageFileNameProvider, icons);

        initializeCamera(startRoom, startDirection, imageFileNameProvider, icons);
    }

    private void initializeDisplay(
            Room room,
            Direction startDirection,
            ImageFileNameProvider imageFileNameProvider,
            Map<Direction, Bitmap> icons) {
        Bitmap defaultImage = BitmapFactory.decodeResource(getResources(), R.drawable.no_image_available);

        ImageProvider imageProvider = new FilesImageProvider(imageFileNameProvider);

        mDisplay = new DeviceDisplay(
                room,
                startDirection,
                icons,
                imageProvider,
                defaultImage) {

            @Override
            public void process(Bitmap image) {
                Log.d(TAG, "process bitmap");
                mImageViewDisplay.setImageBitmap(image);
            }
        };

        mDisplay.start();
    }

    private void initializeCamera(
            Room room,
            Direction startDirection,
            ImageFileNameProvider imageFileNameProvider,
            Map<Direction, Bitmap> icons) {
        ImageSaver imageSaver = new FilesImageSaver(imageFileNameProvider);
        mCamera = new DeviceCamera(this, room, startDirection, icons, imageSaver);
    }

    private void initializeButtonsListeners(View rootView) {
        rootView.findViewById(R.id.btn_forward).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDisplay.canDoStepForward()){
                    mDisplay.stepForward();
                }
            }
        });

        rootView.findViewById(R.id.btn_turn_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDisplay.turnLeft();
            }
        });

        rootView.findViewById(R.id.btn_turn_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDisplay.turnRight();
            }
        });

        rootView.findViewById(R.id.btn_loadImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCamera.moveTo(mDisplay);
                mCamera.makePhoto();
            }
        });
    }
}
