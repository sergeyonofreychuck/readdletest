package com.test.readdle.sergey.onofreychuck.readdletestapp;

import com.test.readdle.sergey.onofreychuck.readdletestapp.level.RoomCoordinates;

import java.util.List;

/**
 * Created by sergey on 9/28/15.
 */
interface ConfigurationChangedListener {
    void onConfigurationChanged(List<RoomCoordinates> rooms);
}
