package com.fredericborrel.atomicrss.view;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fredericborrel.atomicrss.R;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

/**
 * Created by Frederic on 12/04/16.
 */
public class MainActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {

    public static final  String GOOGLE_ACCOUNT_KEY = "GOOGLE_ACCOUNT_KEY";
    private static final String TAG = "MainActivity";

    private ListView drawerList;
    private ArrayAdapter<String> drawerAdapter;
    private GoogleSignInAccount userAccount;
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get Google Account Information
        Bundle b = getIntent().getExtras();
        userAccount = b.getParcelable(GOOGLE_ACCOUNT_KEY);
        Log.d(TAG, "userAccount: " + userAccount.getDisplayName());

        // Setup the drawer menu
        drawerList = (ListView)findViewById(R.id.main_navList);
        drawerLayout = (DrawerLayout)findViewById(R.id.main_drawer_layout);
        setupDrawer();
        addDrawerItems();

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

            FeedFragment feedFragment = new FeedFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.main_fragment_container, feedFragment)
                    .commit();
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
        shouldDisplayHomeUp();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
        shouldDisplayHomeUp();
    }

    @Override
    public void onBackStackChanged() {
        shouldDisplayHomeUp();
    }

    private void shouldDisplayHomeUp(){
        boolean canGoBack = getSupportFragmentManager().getBackStackEntryCount() > 0;
        drawerToggle.setDrawerIndicatorEnabled(!canGoBack);
    }

    @Override
    public boolean onSupportNavigateUp(){
        getSupportFragmentManager().popBackStack();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Activate the navigation drawer toggle
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addDrawerItems(){
        String[] menuArray = getResources().getStringArray(R.array.main_drawer_menu_items);
        drawerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, menuArray);
        drawerList.setAdapter(drawerAdapter);
    }

    private void setupDrawer(){
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.menu_open, R.string.menu_close){
            // Called when a drawer has settled in a completely open state.
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            // Called when a drawer has settled in a completely closed state.
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }
        };
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(drawerToggle);
    }
}
