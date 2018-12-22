package com.erkprog.weather.ui.main;

import android.location.Location;
import android.util.Log;

import com.erkprog.weather.data.Defaults;
import com.erkprog.weather.data.LocationHelper;
import com.erkprog.weather.data.entity.City;
import com.erkprog.weather.data.entity.ForecastDetailed;
import com.erkprog.weather.data.entity.GeopositionResponse;
import com.erkprog.weather.data.entity.Headline;
import com.erkprog.weather.data.weatherRepository.ApiInterface;
import com.erkprog.weather.util.MyUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter implements MainActivityContract.Presenter {
  private static final String TAG = "MainPresenter";

  private ApiInterface mApiService;
  private MainActivityContract.View mView;
  private LocationHelper mLocationHelper;

  MainPresenter(ApiInterface service, LocationHelper locationHelper) {
    mApiService = service;
    mLocationHelper = locationHelper;
  }

  @Override
  public void loadData(String cityKeyId) {

    if (mApiService == null) {
      return;
    }

//    mApiService.get5dayForecast(
//        cityKeyId,
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
  public void getCurrentLocation() {
    mView.onGettingLocation();
    mLocationHelper.getLocation(new LocationHelper.OnLocationChangedListener() {
      @Override
      public void onLocationChanged(Location location) {
        if (isViewAttached()) {
          getCity(location.getLatitude(), location.getLongitude());
        }
      }
    });
  }

  private void getCity(double latitude, double longitude) {
    if (mApiService == null) {
      return;
    }

    String geoQueryParam = String.format("%s,%s", String.valueOf(latitude), String.valueOf(longitude));

//    mApiService.getCityByGeoposition(Defaults.WEATHER_API_KEY, geoQueryParam).enqueue(new Callback<GeopositionResponse>() {
    mApiService.getMockGeoPosition().enqueue(new Callback<GeopositionResponse>() {
      @Override
      public void onResponse(Call<GeopositionResponse> call, Response<GeopositionResponse> response) {
        if (isViewAttached()) {
          mView.setIconsDefaultState();
          if (response.body() != null) {
            City newCity = MyUtil.getCity(response.body());
            Log.d(TAG, "geo response: " + newCity);
            mView.addNewCity(newCity);
          }
        }
      }

      @Override
      public void onFailure(Call<GeopositionResponse> call, Throwable t) {
        mView.setIconsDefaultState();
        mView.showMessage("Geoposition failure" + t.getMessage());
      }
    });
  }

  @Override
  public void onCitySelected(City city) {
    loadData(city.getKey());
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