package com.erkprog.weather.data;

import com.erkprog.weather.data.entity.City;

import java.util.ArrayList;
import java.util.List;

public class DefaultsExample {

  public static final String WEATHER_API_KEY = "<ACCUWEATHER API KEY HERE>";
  public static final Boolean WEATHER_DETAILED_FORECAST = true;
  public static final Boolean WEATHER_METRIC_UNIT = true;

  public static final List<City> CITY_LIST = new ArrayList<City>() {{
    add(new City("101924", "Beijing", "China", 39.917, 116.407));
    add(new City("178087", "Berlin", "Germany", 52.518, 13.406));
    add(new City("308526", "Madrid", "Spain", 40.41, -3.686));
    add(new City("328328", "London", "England", 51.514, -0.107));
  }};
}
