package com.erkprog.weather.ui.searchCity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.erkprog.weather.R;
import com.erkprog.weather.WeatherApplication;
import com.erkprog.weather.data.entity.City;
import com.erkprog.weather.ui.main.CityAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchCityActivity extends AppCompatActivity implements SearchCityContract.View {
  private static final String TAG = "SearchCityActivity";

  public static final String EXTRA_CITY = "city";
  private SearchCityContract.Presenter mPresenter;
  ListView foundCitiesListView;
  CityAdapter mAdapter;
  private ProgressBar mProgressBar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search_city);
    init();
    mPresenter = new SearchCityPresenter(WeatherApplication.getInstance().getApiService());
    mPresenter.bind(this);
  }

  @Override
  public void showFoundCities(List<City> foundCities) {
    Log.d(TAG, "showFoundCities: size " + foundCities.size());
    mAdapter.clear();
    mAdapter.addAll(foundCities);
  }

  @Override
  public void showMessage(String message) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.search_menu, menu);
    final MenuItem item = menu.findItem(R.id.action_search);
    final SearchView searchView = (SearchView) item.getActionView();
    searchView.setQueryHint("Search places");
    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override
      public boolean onQueryTextSubmit(String text) {
        mPresenter.searchCityByName(text);
        return true;
      }

      @Override
      public boolean onQueryTextChange(String s) {
        return false;
      }
    });
    item.expandActionView();
    return true;
  }

  @Override
  public void onLoadingData() {
    mProgressBar.setVisibility(View.VISIBLE);
  }

  @Override
  public void onDataLoaded() {
    mProgressBar.setVisibility(View.GONE);
  }

  @Override
  public void clearList() {
    mAdapter.clear();
  }

  private void init() {
    mProgressBar = findViewById(R.id.search_city_progress_bar);
    foundCitiesListView = findViewById(R.id.found_cities_listview);
    mAdapter = new CityAdapter(this, R.layout.spinner_city_item, new ArrayList<City>());
    foundCitiesListView.setAdapter(mAdapter);
    foundCitiesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        City city = mAdapter.getItem(position);
        if (city == null) {
          return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_CITY, city);
        setResult(RESULT_OK, intent);
        finish();
      }
    });
  }

  @Override
  protected void onDestroy() {
    mPresenter.unbind();
    super.onDestroy();
  }
}
