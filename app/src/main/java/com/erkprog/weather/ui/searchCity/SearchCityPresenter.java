package com.erkprog.weather.ui.searchCity;

import android.util.Log;

import com.erkprog.weather.data.Defaults;
import com.erkprog.weather.data.entity.City;
import com.erkprog.weather.data.weatherRepository.ApiInterface;
import com.erkprog.weather.util.MyUtil;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class SearchCityPresenter implements SearchCityContract.Presenter {
  private static final String TAG = "SearchCityPresenter";

  private ApiInterface mApiService;
  private SearchCityContract.View mView;

  private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

  public SearchCityPresenter(ApiInterface apiService) {
    mApiService = apiService;
  }

  @Override
  public void unbind() {
    mView = null;
  }

  @Override
  public void bind(SearchCityContract.View view) {
    mView = view;
  }

  @Override
  public void searchCityByName(String text) {
    mCompositeDisposable.clear();
    mView.onLoadingData();

    Single<List<City>> resp = mApiService.getCityByName(Defaults.WEATHER_API_KEY, text)
        .subscribeOn(Schedulers.io())
        .flatMap(Observable::fromIterable)
        .map(MyUtil::formCity)
        .toList()
        .observeOn(AndroidSchedulers.mainThread());

    mCompositeDisposable.add(resp.subscribeWith(new DisposableSingleObserver<List<City>>() {
      @Override
      public void onSuccess(List<City> cities) {
        if (!isAttached()) {
          return;
        }
        mView.onDataLoaded();
        if (cities.size() == 0) {
          mView.clearList();
          mView.showMessage("City not found");
          return;
        }
        mView.showFoundCities(cities);
      }

      @Override
      public void onError(Throwable e) {
        Log.e(TAG, "onError: Search city by Name, onFailure " + e);
        if (isAttached()) {
          mView.onDataLoaded();
          mView.showError();
        }
      }
    }));
  }

  @Override
  public boolean isAttached() {
    return mView != null;
  }
}
