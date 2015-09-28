package com.test.readdle.sergey.onofreychuck.readdletestapp;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.test.readdle.sergey.onofreychuck.readdletestapp.level.RoomCoordinates;

import java.util.List;

/**
 * Created by sergey on 9/27/15.
 */
public class AppRoot extends AppCompatActivity implements ConfigurationChangedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_root);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        setSupportActionBar(toolbar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        menu.clear();
        getMenuInflater().inflate(R.menu.menu_app_root, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            LevelSetupFragment levelSetupFragment = new LevelSetupFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.root_container, levelSetupFragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.addToBackStack(null);
            ft.commit();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(List<RoomCoordinates> rooms) {
        MainAppFragment mainFragment = (MainAppFragment)getSupportFragmentManager().findFragmentById(R.id.main_app_fragment);
        mainFragment.onConfigurationChanged(rooms);
    }
}
