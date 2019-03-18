package com.erkprog.weather.ui.main;

import android.util.Log;

import com.erkprog.weather.data.Defaults;
import com.erkprog.weather.data.LocationHelper;
import com.erkprog.weather.data.entity.City;
import com.erkprog.weather.data.entity.DailyForecast;
import com.erkprog.weather.data.entity.ForecastDetailed;
import com.erkprog.weather.data.weatherRepository.ApiInterface;
import com.erkprog.weather.util.MyUtil;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter implements MainActivityContract.Presenter {
  private static final String TAG = "MainPresenter";

  private ApiInterface mApiService;
  private MainActivityContract.View mView;
  private LocationHelper mLocationHelper;
  private List<DailyForecast> dailyForecastList;
  private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

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

    Single<ForecastDetailed> forecast = mApiService.get5dayForecast(
        cityKeyId,
        Defaults.WEATHER_API_KEY,
        Defaults.WEATHER_DETAILED_FORECAST,
        Defaults.WEATHER_METRIC_UNIT)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());

    mCompositeDisposable.add(forecast.subscribeWith(new DisposableSingleObserver<ForecastDetailed>() {
      @Override
      public void onSuccess(ForecastDetailed forecastDetailed) {
        if (!isViewAttached()) {
          return;
        }
        mView.dismissProgress();
        dailyForecastList = forecastDetailed.getDailyForecasts();
        mView.showData(dailyForecastList);
      }

      @Override
      public void onError(Throwable e) {
        if (isViewAttached()) {
          mView.dismissProgress();
          mView.showMessage(" Failure " + e.getMessage());
          mView.displayError();
        }
      }
    }));
  }

  @Override
  public void getCurrentLocation() {
    mView.onGettingLocation();
    mLocationHelper.getLocation(location -> {
      if (isViewAttached()) {
        getCity(location.getLatitude(), location.getLongitude());
      }
    });
  }

  private void getCity(double latitude, double longitude) {
    if (mApiService == null) {
      return;
    }

    String geoQueryParam = String.format("%s,%s", String.valueOf(latitude), String.valueOf(longitude));

    Single<City> cityResponse = mApiService.getCityByGeoposition(Defaults.WEATHER_API_KEY, geoQueryParam)
        .subscribeOn(Schedulers.io())
        .map(MyUtil::formCity)
        .observeOn(AndroidSchedulers.mainThread());

    mCompositeDisposable.add(cityResponse.subscribeWith(new DisposableSingleObserver<City>() {
      @Override
      public void onSuccess(City city) {
        if (!isViewAttached()) {
          return;
        }
        mView.onLocationFound();
        mView.addNewCity(city);
      }

      @Override
      public void onError(Throwable e) {
        if (isViewAttached()) {
          mView.onLocationFound();
          mView.showMessage("Geoposition failure" + e.getMessage());
        }
      }
    }));
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
    mCompositeDisposable.clear();
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
