package com.erkprog.weather.ui.searchCity;

import android.util.Log;

import com.erkprog.weather.data.entity.CityResponse;
import com.erkprog.weather.data.weatherRepository.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchCityPresenter implements SearchCityContract.Presenter {
  private static final String TAG = "SearchCityPresenter";

  private ApiInterface mApiService;
  private SearchCityContract.View mView;

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
    mApiService.getMockCitiesByName().enqueue(new Callback<List<CityResponse>>() {
      @Override
      public void onResponse(Call<List<CityResponse>> call, Response<List<CityResponse>> response) {
        Log.d(TAG, "searchCityByName, onResponse: Success");
      }

      @Override
      public void onFailure(Call<List<CityResponse>> call, Throwable t) {
        Log.e(TAG, "searchCityByName, onFailure: " + t);

      }
    });
  }

  @Override
  public boolean isAttached() {
    return mView != null;
  }
}
