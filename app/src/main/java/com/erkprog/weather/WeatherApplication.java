package com.erkprog.weather;

import android.app.Application;

import com.erkprog.weather.data.weatherRepository.ApiClient;
import com.erkprog.weather.data.weatherRepository.ApiInterface;

public class WeatherApplication extends Application {

  private static WeatherApplication instance;
  private ApiInterface mApi;

  @Override
  public void onCreate() {
    super.onCreate();
    instance = this;
    mApi = ApiClient.getClient(this);
  }

  public static WeatherApplication getInstance() {
    return  instance;
  }

  public ApiInterface getApiService(){
    return mApi;
  }
}