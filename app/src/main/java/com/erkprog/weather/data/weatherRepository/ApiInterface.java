package com.erkprog.weather.data.weatherRepository;

import com.erkprog.weather.data.entity.CityResponse;
import com.erkprog.weather.data.entity.ForecastDetailed;
import com.erkprog.weather.data.entity.ForecastResponse;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

  @GET("forecasts/v1/daily/5day/{locId}")
  Single<ForecastDetailed> get5dayForecast(@Path("locId") String locId,
                                         @Query("apikey") String apikey,
                                         @Query("details") Boolean details,
                                         @Query("metric") Boolean metric);

  @GET("locations/v1/cities/geoposition/search")
  Single<CityResponse> getCityByGeoposition(@Query("apikey") String apikey,
                                            @Query("q") String latLong);

  @GET("http://dataservice.accuweather.com/locations/v1/cities/search")
  Observable<List<CityResponse>> getCityByName(@Query("apikey") String apikey,
                                               @Query("q") String cityName);

  @GET("simple5day")
  Call<ForecastResponse> getMock5dayForecast();

  @GET("detailed5day")
  Call<ForecastDetailed> getMock5dayDetailedForecast();

  @GET("geoposition")
  Call<CityResponse> getMockGeoPosition();

  @GET("cityByName")
  Call<List<CityResponse>> getMockCitiesByName();
}
