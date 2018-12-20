package com.erkprog.weather.ui.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.erkprog.weather.R;
import com.erkprog.weather.data.entity.ForecastResponse;
import com.erkprog.weather.data.weatherRepository.ApiClient;
import com.erkprog.weather.data.weatherRepository.ApiInterface;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View{

  private static final String TAG = "MainActivity";

  MainActivityContract.Presenter mPresenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mPresenter = new MainPresenter(ApiClient.getClient(this));
    mPresenter.bind(this);
    mPresenter.loadData();

  }

  @Override
  public void showData() {
  }

  @Override
  public void showMessage(String message) {
    Log.d(TAG, "showMessage: " + message);
  }
}
