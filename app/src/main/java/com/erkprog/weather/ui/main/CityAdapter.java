package com.erkprog.weather.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.support.annotation.NonNull;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.erkprog.weather.R;
import com.erkprog.weather.data.entity.City;

import java.util.List;

public class CityAdapter extends ArrayAdapter<City> {

  private List<City> mCities;
  private Context mContext;
  private int mResource;

  CityAdapter(@NonNull Context context, @LayoutRes int resource,
              @NonNull List<City> cityList) {
    super(context, resource, 0, cityList);
    mContext = context;
    mCities = cityList;
    mResource = resource;
  }

  @Override
  public View getDropDownView(int position, @Nullable View convertView,
                              @NonNull ViewGroup parent) {
    return createItemView(position, convertView, parent);
  }

  @Override
  public @NonNull
  View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    return createItemView(position, convertView, parent);
  }

  private View createItemView(int position, View convertView, ViewGroup parent) {
    final View view = LayoutInflater.from(mContext).inflate(mResource, parent, false);
    TextView cityName = view.findViewById(R.id.spinner_city_name);
    City city = mCities.get(position);
    String name = String.format("%s, %s", city.getName(), city.getCountryName());
    cityName.setText(name);
    return view;
  }

  @Nullable
  @Override
  public City getItem(int position) {
    return mCities.get(position);
  }
}
