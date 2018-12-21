package com.erkprog.weather.ui.main;

import com.erkprog.weather.data.entity.ForecastResponse;
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

//    mApiService.get5dayForecast("222844").enqueue(new Callback<ForecastResponse>() {
    mApiService.getMock5dayForecast().enqueue(new Callback<ForecastResponse>() {
      @Override
      public void onResponse(Call<ForecastResponse> call, Response<ForecastResponse> response) {
        if (isViewAttached()) {
          mView.showMessage("successfull response");
          if (response.body() != null && response.body().getHeadline() != null) {
            ForecastResponse.Headline headline = response.body().getHeadline();
            mView.showMessage(headline.getText());
            mView.showData(response.body().getDailyForecasts());
          }
        }
      }

      @Override
      public void onFailure(Call<ForecastResponse> call, Throwable t) {
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