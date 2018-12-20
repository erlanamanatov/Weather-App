package com.erkprog.weather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.erkprog.weather.data.entity.ForecastResponse;
import com.erkprog.weather.data.weatherRepository.ApiClient;
import com.erkprog.weather.data.weatherRepository.ApiInterface;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

  private static final String TAG = "MainActivity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);


    ApiInterface mApi = ApiClient.getClient(this);
    mApi.get5dayForecast("222844").enqueue(new Callback<ForecastResponse>() {
      @Override
      public void onResponse(Call<ForecastResponse> call, Response<ForecastResponse> response) {
        Toast.makeText(MainActivity.this, "OnResponse", Toast.LENGTH_SHORT).show();
        Log.d(TAG,new GsonBuilder()
            .setPrettyPrinting().create().toJson(response));
      }

      @Override
      public void onFailure(Call<ForecastResponse> call, Throwable t) {
        Toast.makeText(MainActivity.this, "OnFailure", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onFailure: " + t.getMessage());
      }
    });

  }
}
