package com.erkprog.weather.data.entity;

import com.google.gson.annotations.SerializedName;

public class GeoPosition {

  @SerializedName("Latitude")
  private Double latitude;
  @SerializedName("Longitude")
  private Double longitude;

  public Double getLatitude() {
    return latitude;
  }

  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }

  public Double getLongitude() {
    return longitude;
  }

  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }

}
