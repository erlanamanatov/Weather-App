package com.erkprog.weather.data.entity;

import com.google.gson.annotations.SerializedName;

public class Day {

  @SerializedName("Icon")
  
  private Integer icon;
  @SerializedName("IconPhrase")
  
  private String iconPhrase;
  @SerializedName("LongPhrase")
  
  private String longPhrase;
  @SerializedName("PrecipitationProbability")
  
  private Integer precipitationProbability;
  @SerializedName("Wind")
  
  private Wind wind;

  public Integer getIcon() {
    return icon;
  }

  public void setIcon(Integer icon) {
    this.icon = icon;
  }

  public String getIconPhrase() {
    return iconPhrase;
  }

  public void setIconPhrase(String iconPhrase) {
    this.iconPhrase = iconPhrase;
  }

  public String getLongPhrase() {
    return longPhrase;
  }

  public void setLongPhrase(String longPhrase) {
    this.longPhrase = longPhrase;
  }

  public Integer getPrecipitationProbability() {
    return precipitationProbability;
  }

  public void setPrecipitationProbability(Integer precipitationProbability) {
    this.precipitationProbability = precipitationProbability;
  }

  public Wind getWind() {
    return wind;
  }

  public void setWind(Wind wind) {
    this.wind = wind;
  }

}
