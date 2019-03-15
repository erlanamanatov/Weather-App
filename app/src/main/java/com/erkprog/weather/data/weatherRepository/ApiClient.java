package com.erkprog.weather.data.weatherRepository;

import android.content.Context;


import com.erkprog.weather.util.FakeInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
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

      final OkHttpClient mockClient = new OkHttpClient
          .Builder()
          .addInterceptor(new FakeInterceptor(context))
          .build();

      final Retrofit retrofit = new Retrofit.Builder()
          .addConverterFactory(GsonConverterFactory.create())
          .baseUrl(BASE_URL)
          .client(client)
          .build();

//      final Retrofit retrofit = new Retrofit.Builder()
//          .addConverterFactory(GsonConverterFactory.create())
//          .baseUrl("http://mock.api/")
//          .client(mockClient)
//          .build();

      mApiService = retrofit.create(ApiInterface.class);
    }
    return mApiService;
  }

}
