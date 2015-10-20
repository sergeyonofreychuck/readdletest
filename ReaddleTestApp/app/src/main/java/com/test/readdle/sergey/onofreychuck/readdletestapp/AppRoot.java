package com.test.readdle.sergey.onofreychuck.readdletestapp;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.test.readdle.sergey.onofreychuck.readdletestapp.level.RoomCoordinates;
import com.test.readdle.sergey.onofreychuck.readdletestapp.storage.LevelStructureFileStorage;
import com.test.readdle.sergey.onofreychuck.readdletestapp.storage.LevelStructureStorage;

import java.util.List;

public class AppRoot extends AppCompatActivity implements ConfigurationChangedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_root);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        new LevelStructureFileStorage(this).load(Globals.LEVEL_SAVE_KEY, new LevelStructureStorage.LoadCallback() {
            @Override
            public void success(List<RoomCoordinates> coordinates) {
                if (coordinates.size() == 0) {
                    switchToLevelSetupFragment();
                }
            }

            @Override
            public void failed() {
                switchToLevelSetupFragment();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            switchToLevelSetupFragment();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void switchToLevelSetupFragment() {
        LevelSetupFragment levelSetupFragment = new LevelSetupFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_in_left, R.anim.slide_in_left, R.anim.slide_in_right);
        ft.add(R.id.root_container, levelSetupFragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onConfigurationChanged(List<RoomCoordinates> rooms) {
        MainAppFragment mainFragment = (MainAppFragment)getSupportFragmentManager().findFragmentById(R.id.main_app_fragment);
        mainFragment.onConfigurationChanged(rooms);
    }
}
