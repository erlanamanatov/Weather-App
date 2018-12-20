package com.erkprog.weather.data.weatherRepository;

import android.content.Context;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
  private static ApiInterface mApiService = null;
  private static final String BASE_URL = "http://dataservice.accuweather.com/";

  public static ApiInterface getClient(Context context) {
    if (mApiService == null) {
      final OkHttpClient client = new OkHttpClient
          .Builder()
          .build();

      final Retrofit retrofit = new Retrofit.Builder()
          .addConverterFactory(GsonConverterFactory.create())
          .baseUrl(BASE_URL)
          .client(client)
          .build();

      mApiService = retrofit.create(ApiInterface.class);
    }
    return mApiService;
  }
}
