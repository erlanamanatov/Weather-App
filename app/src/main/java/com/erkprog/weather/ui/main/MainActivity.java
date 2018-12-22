package com.erkprog.weather.ui.main;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.erkprog.weather.R;
import com.erkprog.weather.WeatherApplication;
import com.erkprog.weather.data.LocationHelper;
import com.erkprog.weather.data.entity.DailyForecast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View {

  private static final String TAG = "MainActivity";
  private static final int GPS_PERMISSION_CODE = 1;

  MainActivityContract.Presenter mPresenter;
  RecyclerView dailyRecyclerView;
  DailyForecastAdapter mAdapter;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    initRecyclerView();

    mPresenter = new MainPresenter(WeatherApplication.getInstance().getApiService(),
        new LocationHelper(this));
    mPresenter.bind(this);
    mPresenter.loadData();


    int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission
        .ACCESS_FINE_LOCATION);
    if (permission == PackageManager.PERMISSION_GRANTED) {
      getLocation();
    } else {
      requestGpsPermission();
    }
  }


  private void initRecyclerView() {
    dailyRecyclerView = findViewById(R.id.daily_forecast_recycler_view);
    LinearLayoutManager layoutManager
        = new LinearLayoutManager(this);
    dailyRecyclerView.setLayoutManager(layoutManager);
  }

  @Override
  public void showData(List<DailyForecast> data) {
    mAdapter = new DailyForecastAdapter(data, this);
    dailyRecyclerView.setAdapter(mAdapter);
  }

  @Override
  public void showMessage(String message) {
    Log.d(TAG, "showMessage: " + message);
    Toast.makeText(this, message, Toast.LENGTH_LONG).show();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mPresenter.unbind();
  }

  private void requestGpsPermission() {
    ActivityCompat.requestPermissions(
        this,
        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
        GPS_PERMISSION_CODE
    );
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    if (requestCode == GPS_PERMISSION_CODE) {
      if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        getLocation();
      } else {
        Toast.makeText(this, "Location Permission denied", Toast.LENGTH_SHORT).show();
      }
    }
  }

  private void getLocation() {
    int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission
        .ACCESS_FINE_LOCATION);
    if (permission == PackageManager.PERMISSION_GRANTED) {
      mPresenter.getCurrentLocation();
    }
  }
}
