package com.erkprog.weather.data.weatherRepository;

import com.erkprog.weather.data.entity.ForecastDetailed;
import com.erkprog.weather.data.entity.ForecastResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

  @GET("forecasts/v1/daily/5day/{locId}")
  Call<ForecastResponse> get5dayForecast(@Path("locId") String locId,
                                         @Query("apikey") String apikey);

  @GET("/simple5day")
  Call<ForecastResponse> getMock5dayForecast();

  @GET("/detailed5day")
  Call<ForecastDetailed> getMock5dayDetailedForecast();
}
