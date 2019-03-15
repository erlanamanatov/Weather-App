package com.erkprog.weather.ui.searchCity;

import com.erkprog.weather.data.entity.City;

import java.util.List;

public interface SearchCityContract {

  interface View {

    void showMessage(String message);

    void showFoundCities(List<City> foundCities);

    void onLoadingData();

    void onDataLoaded();
  }

  interface Presenter {

    void unbind();

    void bind(View view);

    void searchCityByName(String text);

    boolean isAttached();
  }

}
