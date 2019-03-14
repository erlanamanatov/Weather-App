package com.erkprog.weather.ui.searchCity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.erkprog.weather.R;
import com.erkprog.weather.WeatherApplication;
import com.erkprog.weather.data.entity.City;
import com.erkprog.weather.ui.main.CityAdapter;

import java.util.List;

public class SearchCityActivity extends AppCompatActivity implements SearchCityContract.View {
  private static final String TAG = "SearchCityActivity";

  public static final String EXTRA_CITY_KEY = "city_key";
  private SearchCityContract.Presenter mPresenter;
  ListView foundCitiesListView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search_city);
    foundCitiesListView = findViewById(R.id.found_cities_listview);
    mPresenter = new SearchCityPresenter(WeatherApplication.getInstance().getApiService());
    mPresenter.bind(this);
  }

  @Override
  public void showFoundCities(List<City> foundCities) {
    Log.d(TAG, "showFoundCities: size " + foundCities.size());
    CityAdapter cc = new CityAdapter(this, R.layout.spinner_city_item, foundCities);
    foundCitiesListView.setAdapter(cc);
  }

  @Override
  public void showMessage(String message) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.search_menu, menu);
    MenuItem item = menu.findItem(R.id.action_search);
    final SearchView searchView = (SearchView) item.getActionView();
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
    return true;
  }

  @Override
  protected void onDestroy() {
    mPresenter.unbind();
    super.onDestroy();
  }
}
