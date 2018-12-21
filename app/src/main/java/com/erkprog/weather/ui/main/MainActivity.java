package com.erkprog.weather.ui.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.erkprog.weather.R;
import com.erkprog.weather.WeatherApplication;
import com.erkprog.weather.data.entity.DailyForecast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View{

  private static final String TAG = "MainActivity";

  MainActivityContract.Presenter mPresenter;
  RecyclerView dailyRecyclerView;
  DailyForecastAdapter mAdapter;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    
    initRecyclerView();

    mPresenter = new MainPresenter(WeatherApplication.getInstance().getApiService());
    mPresenter.bind(this);
    mPresenter.loadData();
  }

  private void initRecyclerView() {
    dailyRecyclerView = findViewById(R.id.daily_forecast_recycler_view);
    LinearLayoutManager layoutManager
        = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
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
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mPresenter.unbind();
  }
}
