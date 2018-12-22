package com.erkprog.weather.data.entity;

import com.google.gson.annotations.SerializedName;

public class Country {

  @SerializedName("ID")
  private String iD;
  @SerializedName("EnglishName")
  private String englishName;

  public String getID() {
    return iD;
  }

  public void setID(String iD) {
    this.iD = iD;
  }

  public String getEnglishName() {
    return englishName;
  }

  public void setEnglishName(String englishName) {
    this.englishName = englishName;
  }
}