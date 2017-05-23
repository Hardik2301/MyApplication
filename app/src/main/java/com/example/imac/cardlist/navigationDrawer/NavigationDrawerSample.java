package com.example.imac.cardlist.navigationDrawer;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.imac.cardlist.R;

public class NavigationDrawerSample extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,FragmentSample.OnFragmentInteractionListener {

    public int mBudgecounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer_sample);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation_drawer_sample, menu);

        BadgeCounter.update(this,menu.findItem(R.id.action_settings),R.drawable.ic_delete_black_24dp,BadgeCounter.BadgeColor.DEEP_ORANGE,
                mBudgecounter);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Log.e("onOptionsItemSelected: ", "called");
            Toast.makeText(getApplicationContext(),mBudgecounter+" Counted",Toast.LENGTH_SHORT);
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentSample fragment=new FragmentSample();
        Bundle bundle=new Bundle();

        if (id == R.id.nav_camera) {
            bundle.putString(FragmentSample.ARG_PARAM1,"Camera");
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            bundle.putString(FragmentSample.ARG_PARAM1,"Gallery");
        } else if (id == R.id.nav_slideshow) {
            bundle.putString(FragmentSample.ARG_PARAM1,"SlideShow");
        } else if (id == R.id.nav_manage) {
            bundle.putString(FragmentSample.ARG_PARAM1,"Manage");
        } else if (id == R.id.nav_share) {
            bundle.putString(FragmentSample.ARG_PARAM1,"Share");
        } else if (id == R.id.nav_send) {
            bundle.putString(FragmentSample.ARG_PARAM1,"Send");
        }

        fragment.setArguments(bundle);
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_frame,fragment);
        ft.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(String uri) {
        FragmentSample1 fragment=new FragmentSample1();
        Bundle bundle=new Bundle();
        bundle.putString(FragmentSample.ARG_PARAM1, uri+" Clicked");
        fragment.setArguments(bundle);
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_frame,fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    public int getmBudgecounter()
    {
        return mBudgecounter;
    }
}
