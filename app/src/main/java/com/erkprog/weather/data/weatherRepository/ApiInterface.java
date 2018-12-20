package com.erkprog.weather.data.weatherRepository;

import com.erkprog.weather.data.Defaults;
import com.erkprog.weather.data.entity.ForecastResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

  @GET("forecasts/v1/daily/5day/{locId}?apikey=JdP6loNQcFSCuSWop5uNDPZFZx80kmMn")

  Call<ForecastResponse> get5dayForecast(@Path("locId") String locId);
}
