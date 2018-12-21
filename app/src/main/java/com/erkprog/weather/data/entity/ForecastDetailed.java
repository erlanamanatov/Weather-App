package com.erkprog.weather.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ForecastDetailed {

  @SerializedName("Headline")
  private Headline headline;
  @SerializedName("DailyForecasts")
  private List<DailyForecast> dailyForecasts = null;

  public Headline getHeadline() {
    return headline;
  }

  public void setHeadline(Headline headline) {
    this.headline = headline;
  }

  public List<DailyForecast> getDailyForecasts() {
    return dailyForecasts;
  }

  public void setDailyForecasts(List<DailyForecast> dailyForecasts) {
    this.dailyForecasts = dailyForecasts;
  }

}