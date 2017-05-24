package com.fredericborrel.atomicweather.ui.forecast;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fredericborrel.atomicweather.R;
import com.fredericborrel.atomicweather.business.manager.ForecastManager;
import com.fredericborrel.atomicweather.business.manager.ForecastManagerFactory;
import com.fredericborrel.atomicweather.data.model.ConditionCode;
import com.fredericborrel.atomicweather.data.model.Location;
import com.fredericborrel.atomicweather.data.model.WeatherCondition;
import com.fredericborrel.atomicweather.databinding.FragmentForecastBinding;
import com.fredericborrel.atomicweather.utils.NetworkAvailability;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;

import static android.app.Activity.RESULT_OK;


/**
 * Created by Frederic on 12/04/16.
 */
@EFragment
public class ForecastFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "ForecastActivity";
    private static final int REQUEST_SELECT_PLACE_CODE = 1234;
    private static final String PLACES_COUNTRY_FILTER = "US";
    private static final String[] DEFAULT_LOCAL = {"atlanta", "united states", "ga"};

    private FragmentForecastBinding mBinding;
    private ForecastManager mForecastManager;
    private ForecastAdapter mItemAdapter;
    private Location mCurrentLocation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_forecast, container, false);
        mBinding.refreshLayout.setOnRefreshListener(this);

        mItemAdapter = new ForecastAdapter();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        mBinding.forecastView.setHasFixedSize(true);
        mBinding.forecastView.setLayoutManager(layoutManager);
        mBinding.forecastView.setAdapter(mItemAdapter);

        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(mBinding.forecastView);

        setHasOptionsMenu(true);

        mForecastManager = ForecastManagerFactory.getForecastManager();

        mCurrentLocation = new Location(DEFAULT_LOCAL[0], DEFAULT_LOCAL[1], DEFAULT_LOCAL[2]);

        if(NetworkAvailability.isNetworkAvailable()) {
            getWeatherCondition();
        }
        else if(mItemAdapter.getItemCount() < 1){
            mBinding.tvNetworkNotAvailable.setVisibility(View.VISIBLE);
        }

        return mBinding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.mn_forecast, menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_item_search) {
            try {
                AutocompleteFilter filter = new AutocompleteFilter.Builder()
                        .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                        .setCountry(PLACES_COUNTRY_FILTER)
                        .build();
                Intent intent = new PlaceAutocomplete
                        .IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                        .setFilter(filter)
                        .build(getActivity());
                startActivityForResult(intent, REQUEST_SELECT_PLACE_CODE);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SELECT_PLACE_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(getActivity(), data);
                String[] address = place.getAddress().toString().split(", ");
                mCurrentLocation = new Location(address[0], address[2], address[1]);
                getWeatherCondition();
            }
        }
    }

    @Override
    public void onRefresh() {
        if(NetworkAvailability.isNetworkAvailable()) {
            getWeatherCondition();
            if(mBinding.tvNetworkNotAvailable.getVisibility() == View.VISIBLE)
                mBinding.tvNetworkNotAvailable.setVisibility(View.GONE);
        } else {
            Toast.makeText(getContext(), this.getString(R.string.tv_network_not_available),Toast.LENGTH_LONG).show();
            mBinding.refreshLayout.setRefreshing(false);
            mBinding.layoutLoader.setVisibility(View.GONE);
        }
    }

    @Background
    protected void getWeatherCondition() {
        updateData(mForecastManager.getWeatherCondition(mCurrentLocation));
    }

    @UiThread
    protected void updateData(WeatherCondition weatherCondition) {
        if (weatherCondition != null) {
            mBinding.layoutGeneralDetail.setVisibility(View.VISIBLE);
            mItemAdapter.setForecastList(weatherCondition.getForecastList());
            mBinding.tvLocation.setText(weatherCondition.getLocation().getFormattedLocation());
            mBinding.tvDetailTemperature.setText(getContext().getString(R.string.temperature_formatter, weatherCondition.getCurrentTemp()));
            mBinding.tvDetailCondition.setText(ConditionCode.getConditionString(weatherCondition.getCurrentCode()));
            Picasso.with(getContext())
                    .load(ConditionCode.getConditionImage(getActivity(), weatherCondition.getCurrentCode()))
                    .fit()
                    .centerInside()
                    .into(mBinding.ivDetailCondition);
        }
        mBinding.refreshLayout.setRefreshing(false);
        mBinding.layoutLoader.setVisibility(View.GONE);
    }
}
