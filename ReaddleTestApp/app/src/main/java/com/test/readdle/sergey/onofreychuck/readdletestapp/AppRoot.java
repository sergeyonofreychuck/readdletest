package com.test.readdle.sergey.onofreychuck.readdletestapp;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class AppRoot extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_root);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_app_root, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            LevelSetupFragment levelSetupFragment = new LevelSetupFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment, levelSetupFragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.addToBackStack(null);
            ft.commit();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
