package com.erkprog.weather.ui.searchCity;

public interface SearchCityContract {

  interface View {

  }

  interface Presenter {

    void unbind();

    void bind(View view);

    void searchCityByName(String text);

    boolean isAttached();
  }

}
