package com.erkprog.weather.data.entity;

import com.google.gson.annotations.SerializedName;

public class Temperature {

  @SerializedName("Minimum")
  private Minimum minimum;
  @SerializedName("Maximum")
  private Maximum maximum;

  public Minimum getMinimum() {
    return minimum;
  }

  public void setMinimum(Minimum minimum) {
    this.minimum = minimum;
  }

  public Maximum getMaximum() {
    return maximum;
  }

  public void setMaximum(Maximum maximum) {
    this.maximum = maximum;
  }

}
