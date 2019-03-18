package com.erkprog.weather.ui.main;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
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
import com.erkprog.weather.ui.searchCity.SearchCityActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View {

  private static final String TAG = "MainActivity";
  private static final int GPS_PERMISSION_CODE = 1;
  private static final String CITIES_LIST = "cities list";
  private static final String SPINNER_POSITION = "position";
  private static final int REQUEST_CODE_CITY = 11;

  MainActivityContract.Presenter mPresenter;
  RecyclerView dailyRecyclerView;
  DailyForecastAdapter mAdapter;
  Spinner citySpinner;
  CityAdapter cityAdapter;
  ArrayList<City> cityList;
  ProgressBar gpsProgressBar, mainProgressBar;
  ImageView getLocationIcon, errorImg;
  TextView gpsInfoText, errorTextView;
  private SpinnerInteractionListener spinnerListener;
  ImageView imgSearchCity;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    checkIntent();
    attachPresenter();
    init(savedInstanceState);
  }


  private void attachPresenter() {
    mPresenter = (MainActivityContract.Presenter) getLastCustomNonConfigurationInstance();
    if (mPresenter == null) {
      mPresenter = new MainPresenter(WeatherApplication.getInstance().getApiService(),
          new LocationHelper(this));
    }
    mPresenter.bind(this);
  }

  private void checkIntent() {
    if (getIntent() != null) {
      if (getIntent().getExtras() != null) {
        for (String key : getIntent().getExtras().keySet()) {
          Object value = getIntent().getExtras().get(key);
          Log.d(TAG, "Key: " + key + " Value: " + value);
        }
      }
    }
  }

  private void init(Bundle savedInstance) {
    initRecyclerView();
    gpsProgressBar = findViewById(R.id.main_gps_progress);
    mainProgressBar = findViewById(R.id.main_progress_bar);
    gpsInfoText = findViewById(R.id.main_gps_textinfo);
    getLocationIcon = findViewById(R.id.get_location_img);
    errorImg = findViewById(R.id.main_error_img);
    errorTextView = findViewById(R.id.main_error_text);
    imgSearchCity = findViewById(R.id.img_search_city);
    imgSearchCity.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, SearchCityActivity.class);
        startActivityForResult(intent, REQUEST_CODE_CITY);
      }
    });

    errorImg.setVisibility(View.GONE);
    errorTextView.setVisibility(View.GONE);

    onLocationFound();
    dismissProgress();
    getLocationIcon.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (isGpsPersmissionGranted()) {
          if (isGpsEnabled()) {
            mPresenter.getCurrentLocation();
          } else {
            showTurnGpsOnDialog();
          }
        } else {
          requestGpsPermission();
        }
      }
    });
    initSpinner(savedInstance);
  }

  private void initSpinner(Bundle savedInstanceState) {
    citySpinner = findViewById(R.id.main_city_spinner);
    spinnerListener = new SpinnerInteractionListener();
    citySpinner.setOnTouchListener(spinnerListener);
    citySpinner.setOnItemSelectedListener(spinnerListener);
    if (savedInstanceState == null || !savedInstanceState.containsKey(CITIES_LIST)) {
      cityList = new ArrayList<>(Defaults.CITY_LIST);
      cityAdapter = new CityAdapter(this, R.layout.spinner_city_item, cityList);
      citySpinner.setAdapter(cityAdapter);
      spinnerListener.userSelect = true;
      citySpinner.setSelection(0);
    } else {
      cityList = savedInstanceState.getParcelableArrayList(CITIES_LIST);
      cityAdapter = new CityAdapter(this, R.layout.spinner_city_item, cityList);
      citySpinner.setAdapter(cityAdapter);
      citySpinner.setSelection(savedInstanceState.getInt(SPINNER_POSITION));
      mPresenter.onScreenRotated();
    }
  }

  private void initRecyclerView() {
    dailyRecyclerView = findViewById(R.id.daily_forecast_recycler_view);
    LinearLayoutManager layoutManager
        = new LinearLayoutManager(this);
    dailyRecyclerView.setLayoutManager(layoutManager);
  }

  private void showTurnGpsOnDialog() {
    AlertDialog.Builder builder = new AlertDialog.Builder(this)
        .setMessage("Turn on gps in settings")
        .setTitle("Gps is disabled")
        .setPositiveButton("Go to settings", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
          }
        })
        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            Toast.makeText(MainActivity.this, "Turn GPS on to get forecast for current location", Toast.LENGTH_SHORT).show();
          }
        });
    builder.show();
  }

  private boolean isGpsEnabled() {
    LocationManager locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
  }

  private boolean isGpsPersmissionGranted() {
    return ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
  }

  @Override
  public void showData(List<DailyForecast> data) {
    hideError();
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
  public void onLocationFound() {
    getLocationIcon.setVisibility(View.VISIBLE);
    getLocationIcon.setEnabled(true);
    gpsProgressBar.setVisibility(View.GONE);
    gpsInfoText.setVisibility(View.GONE);
  }

  @Override
  public void addNewCity(City city) {
    spinnerListener.userSelect = true;
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
    Log.d(TAG, "dismissProgress: starts");
    mainProgressBar.setVisibility(View.GONE);
    dailyRecyclerView.setVisibility(View.VISIBLE);
  }

  @Override
  public void displayError() {
    Log.d(TAG, "displayError: starts");
    dailyRecyclerView.setVisibility(View.GONE);
    errorImg.setVisibility(View.VISIBLE);
    errorTextView.setVisibility(View.VISIBLE);
  }

  private void hideError() {
    dailyRecyclerView.setVisibility(View.VISIBLE);
    errorImg.setVisibility(View.GONE);
    errorTextView.setVisibility(View.GONE);
  }

  @Override
  public Object onRetainCustomNonConfigurationInstance() {
    return mPresenter;
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

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putParcelableArrayList(CITIES_LIST, cityList);
    outState.putInt(SPINNER_POSITION, citySpinner.getSelectedItemPosition());
  }

  public class SpinnerInteractionListener implements AdapterView.OnItemSelectedListener, View.OnTouchListener {
    boolean userSelect = false;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
      userSelect = true;
      return false;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
      if (userSelect) {
        Log.d(TAG, "onItemSelected: triggered " + pos);
        mPresenter.onCitySelected(cityAdapter.getItem(pos));
        userSelect = false;
      }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
      if (userSelect) {
        userSelect = false;
      }
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    if (requestCode == REQUEST_CODE_CITY && resultCode == RESULT_OK && data != null) {
      City city = data.getParcelableExtra(SearchCityActivity.EXTRA_CITY);
      if (city != null) {
        addNewCity(city);
      }
    }
  }
}
