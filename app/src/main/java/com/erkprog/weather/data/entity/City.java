package com.erkprog.weather.data.entity;

public class City {
  private String name;
  private String key;
  private String countryName;
  private double latitude;
  private double longitude;

  public City(String key, String name, String countryName, double latitude, double longitude) {
    this.name = name;
    this.key = key;
    this.countryName = countryName;
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getCountryName() {
    return countryName;
  }

  public void setCountryName(String countryName) {
    this.countryName = countryName;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  @Override
  public String toString() {
    return "City{" +
        "name='" + name + '\'' +
        ", key=" + key +
        ", countryName='" + countryName + '\'' +
        ", latitude=" + latitude +
        ", longitude=" + longitude +
        '}';
  }


}
