package com.erkprog.weather.data.entity;

import com.google.gson.annotations.SerializedName;

public class DailyForecast {

  @SerializedName("Date")
  private String date;
  @SerializedName("Sun")
  private Sun sun;
  @SerializedName("Moon")
  private Moon moon;
  @SerializedName("Temperature")
  private Temperature temperature;
  @SerializedName("RealFeelTemperature")
  private RealFeelTemperature realFeelTemperature;
  @SerializedName("HoursOfSun")
  private Double hoursOfSun;
  @SerializedName("Day")
  private Day day;
  @SerializedName("Night")
  private Night night;
  @SerializedName("MobileLink")
  private String mobileLink;

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public Sun getSun() {
    return sun;
  }

  public void setSun(Sun sun) {
    this.sun = sun;
  }

  public Moon getMoon() {
    return moon;
  }

  public void setMoon(Moon moon) {
    this.moon = moon;
  }

  public Temperature getTemperature() {
    return temperature;
  }

  public void setTemperature(Temperature temperature) {
    this.temperature = temperature;
  }

  public RealFeelTemperature getRealFeelTemperature() {
    return realFeelTemperature;
  }

  public void setRealFeelTemperature(RealFeelTemperature realFeelTemperature) {
    this.realFeelTemperature = realFeelTemperature;
  }

  public Double getHoursOfSun() {
    return hoursOfSun;
  }

  public void setHoursOfSun(Double hoursOfSun) {
    this.hoursOfSun = hoursOfSun;
  }

  public Day getDay() {
    return day;
  }

  public void setDay(Day day) {
    this.day = day;
  }

  public Night getNight() {
    return night;
  }

  public void setNight(Night night) {
    this.night = night;
  }

  public String getMobileLink() {
    return mobileLink;
  }

  public void setMobileLink(String mobileLink) {
    this.mobileLink = mobileLink;
  }

}
