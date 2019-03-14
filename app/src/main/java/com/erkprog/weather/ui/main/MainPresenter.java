package com.erkprog.weather.ui.main;

import android.location.Location;
import android.util.Log;

import com.erkprog.weather.data.LocationHelper;
import com.erkprog.weather.data.entity.City;
import com.erkprog.weather.data.entity.CityResponse;
import com.erkprog.weather.data.entity.DailyForecast;
import com.erkprog.weather.data.entity.ForecastDetailed;
import com.erkprog.weather.data.weatherRepository.ApiInterface;
import com.erkprog.weather.util.MyUtil;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter implements MainActivityContract.Presenter {
  private static final String TAG = "MainPresenter";

  private ApiInterface mApiService;
  private MainActivityContract.View mView;
  private LocationHelper mLocationHelper;
  private List<DailyForecast> dailyForecastList;

  MainPresenter(ApiInterface service, LocationHelper locationHelper) {
    mApiService = service;
    mLocationHelper = locationHelper;
  }

  @Override
  public void loadData(String cityKeyId) {
    mView.showProgress();
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
          mView.dismissProgress();
          if (response.isSuccessful()) {
            ForecastDetailed forecastResponse = response.body();
            if (forecastResponse != null && forecastResponse.getDailyForecasts() != null) {
              dailyForecastList = forecastResponse.getDailyForecasts();
              mView.showData(dailyForecastList);
            }
          } else {
            mView.showMessage("failed to get forecast data");
            mView.displayError();
          }
        }
      }

      @Override
      public void onFailure(Call<ForecastDetailed> call, Throwable t) {
        if (isViewAttached()) {
          mView.dismissProgress();
          mView.showMessage(" Failure " + t.getMessage());
          mView.displayError();
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

//    mApiService.getCityByGeoposition(Defaults.WEATHER_API_KEY, geoQueryParam).enqueue(new Callback<CityResponse>() {
    mApiService.getMockGeoPosition().enqueue(new Callback<CityResponse>() {
      @Override
      public void onResponse(Call<CityResponse> call, Response<CityResponse> response) {
        if (isViewAttached()) {
          mView.onLocationFound();
          if (response.body() != null) {
            City newCity = MyUtil.formCity(response.body());
            if (newCity != null) {
              Log.d(TAG, "geo response: " + newCity);
              mView.addNewCity(newCity);
            } else {
              mView.showMessage("Error getting city from georesponse");
            }
          }
        }
      }

      @Override
      public void onFailure(Call<CityResponse> call, Throwable t) {
        if (isViewAttached()) {
          mView.onLocationFound();
          mView.showMessage("Geoposition failure" + t.getMessage());
        }
      }
    });
  }

  @Override
  public void searchCityByText(String text) {
    if (mApiService == null) {
      return;
    }

//    mApiService.getCityByName(Defaults.WEATHER_API_KEY, text).enqueue(new Callback<List<CityResponse>>() {
    mApiService.getMockCitiesByName().enqueue(new Callback<List<CityResponse>>() {
      @Override
      public void onResponse(Call<List<CityResponse>> call, Response<List<CityResponse>> response) {
        Log.d(TAG, "onResponse: " + new GsonBuilder().setPrettyPrinting().create().toJson(response));
      }

      @Override
      public void onFailure(Call<List<CityResponse>> call, Throwable t) {
        Log.e(TAG, "search City by text failure: " + t);
      }
    });
  }

  @Override
  public void onCitySelected(City city) {
    Log.d(TAG, "onCitySelected: triggered");
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

  @Override
  public void onScreenRotated() {
    mView.showData(dailyForecastList);
  }
}
