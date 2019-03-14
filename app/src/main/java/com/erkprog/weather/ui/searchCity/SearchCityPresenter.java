package com.erkprog.weather.ui.searchCity;

import android.util.Log;

import com.erkprog.weather.data.entity.City;
import com.erkprog.weather.data.entity.CityResponse;
import com.erkprog.weather.data.weatherRepository.ApiInterface;
import com.erkprog.weather.util.MyUtil;

import java.util.ArrayList;
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
        if (response.isSuccessful() && response.body() != null) {
          List<City> foundCities = new ArrayList<>();
          for (CityResponse cityResponse : response.body()) {
            foundCities.add(MyUtil.formCity(cityResponse));
          }
          if (isAttached()) {
            Log.d(TAG, "onResponse: size: " + foundCities.size());
            mView.showFoundCities(foundCities);
          }
        } else {
          if (isAttached()) {
            mView.showMessage("Something went wrong fetching data");
          }
        }
      }

      @Override
      public void onFailure(Call<List<CityResponse>> call, Throwable t) {
        Log.e(TAG, "searchCityByName, onFailure: " + t);
        if (isAttached()) {
          mView.showMessage("Error fetching data");
        }
      }
    });
  }

  @Override
  public boolean isAttached() {
    return mView != null;
  }
}
