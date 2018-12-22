package com.erkprog.weather.data.entity;

import com.google.gson.annotations.SerializedName;

public class GeopositionResponse {

  @SerializedName("Version")
  private Integer version;
  @SerializedName("Key")
  private String key;
  @SerializedName("EnglishName")
  private String englishName;
  @SerializedName("Country")
  private Country country;
  @SerializedName("GeoPosition")
  private GeoPosition geoPosition;

  public Integer getVersion() {
    return version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getEnglishName() {
    return englishName;
  }

  public void setEnglishName(String englishName) {
    this.englishName = englishName;
  }

  public Country getCountry() {
    return country;
  }

  public void setCountry(Country country) {
    this.country = country;
  }

  public GeoPosition getGeoPosition() {
    return geoPosition;
  }

  public void setGeoPosition(GeoPosition geoPosition) {
    this.geoPosition = geoPosition;
  }

}