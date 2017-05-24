package com.fredericborrel.atomicweather.ui.forecast;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.fredericborrel.atomicweather.R;
import com.fredericborrel.atomicweather.data.model.ConditionCode;
import com.fredericborrel.atomicweather.data.model.Forecast;
import com.fredericborrel.atomicweather.databinding.ItemForecastBinding;
import com.fredericborrel.atomicweather.utils.Constant;
import com.fredericborrel.atomicweather.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frederic on 12/04/16.
 */
public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder> {

    private List<Forecast> mForecastList = new ArrayList<>();
    private Context mContext;

    public class ForecastViewHolder extends RecyclerView.ViewHolder {
        private ItemForecastBinding mItemForecastBinding;

        public ForecastViewHolder(ItemForecastBinding binding) {
            super(binding.getRoot());
            mItemForecastBinding = binding;
        }

        public void bind(Context context, Forecast forecast) {
            String formattedDate = Utils.convertStringDate(forecast.getDate(), Constant.YAHOO_DATE_FORMAT_INPUT, Constant.CARDVIEW_DATE_FORMAT_OUTPUT);

            mItemForecastBinding.itemLowTemp.setText(context.getString(R.string.temperature_formatter,forecast.getLow()));
            mItemForecastBinding.itemHighTemp.setText(context.getString(R.string.temperature_formatter,forecast.getHigh()));
            mItemForecastBinding.itemDate.setText(formattedDate);
            Picasso.with(context)
                    .load(ConditionCode.getConditionImage(context, forecast.getCode()))
                    .fit()
                    .centerCrop()
                    .into(mItemForecastBinding.itemWeatherImage);
        }
    }

    @Override
    public ForecastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemForecastBinding itemForecastBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_forecast, parent, false);
        return new ForecastViewHolder(itemForecastBinding);
    }

    @Override
    public void onBindViewHolder(ForecastViewHolder holder, int position) {
        final Forecast forecast = mForecastList.get(position);
        if (forecast != null) {
            holder.bind(mContext, forecast);
        }
    }

    @Override
    public int getItemCount() {
        return mForecastList != null ? mForecastList.size() : 0;
    }

    public void setForecastList(List<Forecast> forecastList) {
        mForecastList.clear();
        mForecastList.addAll(forecastList);
        notifyDataSetChanged();
    }
}
