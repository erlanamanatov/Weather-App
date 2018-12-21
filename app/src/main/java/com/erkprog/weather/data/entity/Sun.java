package com.erkprog.weather.data.entity;

import com.google.gson.annotations.SerializedName;

public class Sun {

  @SerializedName("Rise")
  private String rise;
  @SerializedName("Set")
  private String set;

  public String getRise() {
    return rise;
  }

  public void setRise(String rise) {
    this.rise = rise;
  }

  public String getSet() {
    return set;
  }

  public void setSet(String set) {
    this.set = set;
  }

}