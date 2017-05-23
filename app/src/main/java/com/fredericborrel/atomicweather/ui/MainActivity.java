package com.fredericborrel.atomicweather.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.fredericborrel.atomicweather.R;
import com.fredericborrel.atomicweather.ui.forecast.ForecastFragment;
import com.fredericborrel.atomicweather.ui.forecast.ForecastFragment_;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Frederic on 12/04/16.
 */
public class MainActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {

    // Constants
    private static final String TAG = "MainActivity";

    // FireBase
    private FirebaseUser mUser;

    // GUI
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private TextView mUserNameMenu;
    private TextView mEmailMenu;
    private CircleImageView mProfilePictureMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUser = FirebaseAuth.getInstance().getCurrentUser();

        // Setup the mn_drawer menu
        setupDrawer();

        // Setup the ActionBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        // Listen to the BackStack to adapt the ActionBar
        getSupportFragmentManager().addOnBackStackChangedListener(this);

        if(findViewById(R.id.main_fragment_container) != null){
            if (savedInstanceState != null){
                return;
            }

            ForecastFragment feedFragment = ForecastFragment_.builder().build();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.main_fragment_container, feedFragment)
                    .commit();
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
        shouldDisplayHomeUp();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
        shouldDisplayHomeUp();
    }

    @Override
    public void onBackStackChanged() {
        shouldDisplayHomeUp();
    }

    private void shouldDisplayHomeUp(){
        boolean canGoBack = getSupportFragmentManager().getBackStackEntryCount() > 0;
        mDrawerToggle.setDrawerIndicatorEnabled(!canGoBack);
    }

    @Override
    public boolean onSupportNavigateUp(){
        getSupportFragmentManager().popBackStack();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Activate the navigation mn_drawer toggle
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    private void setupDrawer(){
        // Retrieve Views
        mDrawerLayout = (DrawerLayout)findViewById(R.id.main_drawer_layout);
        mNavigationView = (NavigationView)findViewById(R.id.main_navList);

        View header = mNavigationView.getHeaderView(0);
        mUserNameMenu = (TextView)header.findViewById(R.id.menu_username);
        mEmailMenu = (TextView)header.findViewById(R.id.menu_email);
        mProfilePictureMenu = (CircleImageView)header.findViewById(R.id.menu_image);

        // Adding behavior when we are opening/closing the mn_drawer
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.menu_open, R.string.menu_close){
            // Called when a mn_drawer has settled in a completely open state.
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            // Called when a mn_drawer has settled in a completely closed state.
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        item.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        switch (item.getItemId()){
                            case R.id.sign_out_menu_item :
                                signOut();
                        }
                        return true;
                    }
                }
        );

        // Set Google Account Information to the header
        mEmailMenu.setText(mUser.getEmail());
        mUserNameMenu.setText(mUser.getDisplayName());
        Picasso.with(getApplicationContext())
                .load(mUser.getPhotoUrl())
                .into(mProfilePictureMenu);
    }

    // Sign out the user and send him back to the Authentication Activity
    private void signOut(){
        FirebaseAuth.getInstance().signOut();
        Intent authActivity = new Intent(MainActivity.this, AuthActivity.class);
        MainActivity.this.startActivity(authActivity);
        finish();
    }
}
