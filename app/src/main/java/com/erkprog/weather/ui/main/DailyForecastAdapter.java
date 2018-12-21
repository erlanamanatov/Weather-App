package com.erkprog.weather.ui.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.erkprog.weather.R;
import com.erkprog.weather.data.entity.DailyForecast;
import com.erkprog.weather.util.MyUtil;

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
    viewHolder.date.setText(item.getDate() != null ? item.getDate() : "");
    if (item.getTemperature() != null) {
      if (item.getTemperature().getMaximum() != null) {
        viewHolder.maxTemp.setText(Double.toString(item.getTemperature().getMaximum().getValue()));
      }
      if (item.getTemperature().getMinimum() != null) {
        viewHolder.minTemp.setText(Double.toString(item.getTemperature().getMinimum().getValue()));
      }
    }

    if (item.getDay() != null) {
      if (item.getDay().getIcon() != null) {
        viewHolder.icon.setImageDrawable(mContext.getDrawable(MyUtil.getIcon(item.getDay().getIcon())));
        // todo: seticon here
      }
      viewHolder.description.setText(item.getDay().getIconPhrase());
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

    DailyViewHolder(@NonNull View itemView) {
      super(itemView);
      date = itemView.findViewById(R.id.daily_date);
      maxTemp = itemView.findViewById(R.id.daily_temp_day);
      minTemp = itemView.findViewById(R.id.daily_temp_night);
      description = itemView.findViewById(R.id.daily_description);
      icon = itemView.findViewById(R.id.daily_icon);
    }
  }
}
