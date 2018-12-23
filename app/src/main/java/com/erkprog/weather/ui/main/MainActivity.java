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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.erkprog.weather.R;
import com.erkprog.weather.WeatherApplication;
import com.erkprog.weather.data.Defaults;
import com.erkprog.weather.data.LocationHelper;
import com.erkprog.weather.data.entity.City;
import com.erkprog.weather.data.entity.DailyForecast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View {

  private static final String TAG = "MainActivity";
  private static final int GPS_PERMISSION_CODE = 1;

  MainActivityContract.Presenter mPresenter;
  RecyclerView dailyRecyclerView;
  DailyForecastAdapter mAdapter;
  Spinner citySpinner;
  CityAdapter cityAdapter;
  List<City> cityList;
  ProgressBar gpsProgressBar, mainProgressBar;
  ImageView getLocationIcon;
  TextView gpsInfoText;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mPresenter = new MainPresenter(WeatherApplication.getInstance().getApiService(),
        new LocationHelper(this));
    mPresenter.bind(this);

    init();

    if (savedInstanceState == null) {
      mPresenter.loadData(String.valueOf(((City) citySpinner.getSelectedItem()).getKey()));
    }
  }

  private void init() {
    initRecyclerView();
    initSpinner();
    gpsProgressBar = findViewById(R.id.main_gps_progress);
    mainProgressBar = findViewById(R.id.main_progress_bar);
    gpsInfoText = findViewById(R.id.main_gps_textinfo);
    getLocationIcon = findViewById(R.id.get_location_img);
    setIconsDefaultState();
    dismissProgress();
    getLocationIcon.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
          mPresenter.getCurrentLocation();
        } else {
          requestGpsPermission();
        }
      }
    });
  }

  private void initSpinner() {
    citySpinner = findViewById(R.id.main_city_spinner);
    cityList = new ArrayList<>(Defaults.CITY_LIST);
    cityAdapter = new CityAdapter(this, R.layout.spinner_city_item, cityList);
    citySpinner.setAdapter(cityAdapter);
    citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mPresenter.onCitySelected(cityAdapter.getItem(position));
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {
      }
    });
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
    mAdapter.notifyDataSetChanged();
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
    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, GPS_PERMISSION_CODE);
  }

  @Override
  public void onGettingLocation() {
    getLocationIcon.setVisibility(View.INVISIBLE);
    getLocationIcon.setEnabled(false);
    gpsProgressBar.setVisibility(View.VISIBLE);
    gpsInfoText.setVisibility(View.VISIBLE);
  }

  @Override
  public void setIconsDefaultState() {
    getLocationIcon.setVisibility(View.VISIBLE);
    getLocationIcon.setEnabled(true);
    gpsProgressBar.setVisibility(View.GONE);
    gpsInfoText.setVisibility(View.GONE);
  }

  @Override
  public void addNewCity(City city) {
    if (!cityList.contains(city)) {
      cityList.add(city);
      cityAdapter.notifyDataSetChanged();
      citySpinner.setSelection(cityList.size() - 1);
    } else {
      citySpinner.setSelection(cityList.indexOf(city));
    }
  }

  @Override
  public void showProgress() {
    dailyRecyclerView.setVisibility(View.INVISIBLE);
    mainProgressBar.setVisibility(View.VISIBLE);
  }

  @Override
  public void dismissProgress() {
    mainProgressBar.setVisibility(View.GONE);
    dailyRecyclerView.setVisibility(View.VISIBLE);
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    if (requestCode == GPS_PERMISSION_CODE) {
      if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        mPresenter.getCurrentLocation();
      } else {
        Toast.makeText(this, "Location Permission denied", Toast.LENGTH_SHORT).show();
      }
    }
  }
}
