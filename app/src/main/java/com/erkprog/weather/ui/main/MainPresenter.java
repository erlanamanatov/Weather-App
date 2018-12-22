package com.erkprog.weather.ui.main;

import com.erkprog.weather.data.Defaults;
import com.erkprog.weather.data.entity.ForecastDetailed;
import com.erkprog.weather.data.entity.Headline;
import com.erkprog.weather.data.weatherRepository.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter implements MainActivityContract.Presenter {

  private ApiInterface mApiService;
  private MainActivityContract.View mView;

  MainPresenter(ApiInterface service) {
    mApiService = service;
  }

  @Override
  public void loadData() {

    if (mApiService == null) {
      return;
    }

//    mApiService.get5dayForecast(
//        "222844",
//        Defaults.WEATHER_API_KEY,
//        Defaults.WEATHER_DETAILED_FORECAST,
//        Defaults.WEATHER_METRIC_UNIT)
//        .enqueue(new Callback<ForecastDetailed>() {
    mApiService.getMock5dayDetailedForecast().enqueue(new Callback<ForecastDetailed>() {
      @Override
      public void onResponse(Call<ForecastDetailed> call, Response<ForecastDetailed> response) {
        if (isViewAttached()) {
          mView.showMessage("successfull response");
          if (response.body() != null && response.body().getHeadline() != null) {
            Headline headline = response.body().getHeadline();
            mView.showMessage(headline.getText());
            mView.showData(response.body().getDailyForecasts());
          }
        }
      }

      @Override
      public void onFailure(Call<ForecastDetailed> call, Throwable t) {
        if (isViewAttached()) {
          mView.showMessage(" Failure " + t.getMessage());
        }
      }
    });

  }

  @Override
  public void bind(MainActivityContract.View view) {
    this.mView = view;
  }

  @Override
  public void unbind() {
    mView = null;
  }

  @Override
  public boolean isViewAttached() {
    return mView != null;
  }
}