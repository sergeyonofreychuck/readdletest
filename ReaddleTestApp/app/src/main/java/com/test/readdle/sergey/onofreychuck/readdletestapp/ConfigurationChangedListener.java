package com.test.readdle.sergey.onofreychuck.readdletestapp;

import com.test.readdle.sergey.onofreychuck.readdletestapp.level.RoomCoordinates;

import java.util.List;

interface ConfigurationChangedListener {
    void onConfigurationChanged(List<RoomCoordinates> rooms);
}
