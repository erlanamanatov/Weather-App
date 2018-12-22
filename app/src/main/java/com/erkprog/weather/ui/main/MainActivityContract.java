package com.erkprog.weather.ui.main;

import com.erkprog.weather.data.entity.City;
import com.erkprog.weather.data.entity.DailyForecast;

import java.util.List;

public interface MainActivityContract {

  interface View {

    void showData(List<DailyForecast> data);

    void showMessage(String message);

  }

  interface Presenter {
    void loadData(String cityKeyId);

    void bind(View v);

    void unbind();

    boolean isViewAttached();

    void getCurrentLocation();

    void onCityClicked(City city);
  }
}
