package com.erkprog.weather.data.entity;

public class City {
  private String name;
  private int key;
  private String country;
  private double latitude;
  private double longitude;

  public City(int key, String name, String country, double latitude, double longitude) {
    this.name = name;
    this.key = key;
    this.country = country;
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getKey() {
    return key;
  }

  public void setKey(int key) {
    this.key = key;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
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
        ", country='" + country + '\'' +
        ", latitude=" + latitude +
        ", longitude=" + longitude +
        '}';
  }
}
