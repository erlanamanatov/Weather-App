package com.erkprog.weather.ui.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.erkprog.weather.R;
import com.erkprog.weather.data.entity.DailyForecast;
import com.erkprog.weather.util.MyUtil;

import java.util.Calendar;
import java.util.List;

public class DailyForecastAdapter extends RecyclerView.Adapter<DailyForecastAdapter
    .DailyViewHolder> {

  private static final String TAG = "DailyForecastAdapter";
  private List<DailyForecast> mData;
  private Context mContext;

  DailyForecastAdapter(List<DailyForecast> data, Context context) {
    mData = data;
    mContext = context;
  }


  @NonNull
  @Override
  public DailyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

    View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.daily_forecast,
        viewGroup, false);
    return new DailyViewHolder(v);
  }

  @Override
  public void onBindViewHolder(@NonNull DailyViewHolder viewHolder, int i) {

    DailyForecast item = mData.get(i);
    try {
      viewHolder.date.setText(item.getDate() != null ? MyUtil.getFormattedDate(item.getDate()) : "");
    } catch (IllegalArgumentException e) {
      viewHolder.date.setText("");
    }
    if (item.getTemperature() != null) {
      if (item.getTemperature().getMaximum() != null) {
        viewHolder.maxTemp.setText(Double.toString(item.getTemperature().getMaximum().getValue()) + " \u2103");
      }
      if (item.getTemperature().getMinimum() != null) {
        viewHolder.minTemp.setText(Double.toString(item.getTemperature().getMinimum().getValue()) + " \u00B0");
      }
    }

    if (item.getDay() != null) {
      if (item.getDay().getIcon() != null) {
        viewHolder.icon.setImageDrawable(mContext.getDrawable(MyUtil.getIcon(item.getDay().getIcon())));
      }
      if (item.getDay().getWind() != null && item.getDay().getWind().getSpeed() != null) {
        viewHolder.wind.setText(Double.toString(item.getDay().getWind().getSpeed().getValue()) +
            " " + item.getDay().getWind().getSpeed().getUnit());
      }
      viewHolder.description.setText(item.getDay().getIconPhrase());
      viewHolder.precipitation.setText(
          Integer.toString(item.getDay().getPrecipitationProbability()) + "%");
    }
    viewHolder.sunriseSunset.setText(MyUtil.getSunriseSunset(item.getSun()));

    if (i == 0) {
      ((CardView) viewHolder.itemView).setCardBackgroundColor(
          mContext.getResources().getColor(R.color.currentDayBackground));
      viewHolder.date.setText(mContext.getString(R.string.today));
    }
  }

  @Override
  public int getItemCount() {
    return mData.size();
  }

  class DailyViewHolder extends RecyclerView.ViewHolder {
    private TextView date;
    private ImageView icon;
    private TextView maxTemp;
    private TextView minTemp;
    private TextView description;
    private TextView sunriseSunset;
    private TextView wind;
    private TextView precipitation;
    View v;

    DailyViewHolder(@NonNull View itemView) {
      super(itemView);
      v = itemView;
      date = itemView.findViewById(R.id.daily_date);
      maxTemp = itemView.findViewById(R.id.daily_temp_max);
      minTemp = itemView.findViewById(R.id.daily_temp_min);
      description = itemView.findViewById(R.id.daily_description);
      icon = itemView.findViewById(R.id.daily_icon);
      sunriseSunset = itemView.findViewById(R.id.daily_sunrise_sunset);
      wind = itemView.findViewById(R.id.daily_wind);
      precipitation = itemView.findViewById(R.id.daily_precipitation);
    }
  }
}
