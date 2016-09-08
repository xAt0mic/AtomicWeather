package com.fredericborrel.atomicrss.view;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.fredericborrel.atomicrss.R;

/**
 * Created by Frederic on 12/04/16.
 */
public class MainActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup the ActionBar
        ActionBar actionBar = getSupportActionBar();
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
    public void onBackStackChanged() {
        shouldDisplayHomeUp();
    }

    private void shouldDisplayHomeUp(){
        boolean canGoBack = getSupportFragmentManager().getBackStackEntryCount() > 0;
        getSupportActionBar().setDisplayHomeAsUpEnabled(canGoBack);
    }

    @Override
    public boolean onSupportNavigateUp(){
        getSupportFragmentManager().popBackStack();
        return true;
    }
}
