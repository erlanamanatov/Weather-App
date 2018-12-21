package com.erkprog.weather.data.entity;

import com.google.gson.annotations.SerializedName;

public class Headline {

  @SerializedName("EffectiveDate")
  private String effectiveDate;
  @SerializedName("Text")
  private String text;
  @SerializedName("MobileLink")
  private String mobileLink;

  public String getEffectiveDate() {
    return effectiveDate;
  }

  public void setEffectiveDate(String effectiveDate) {
    this.effectiveDate = effectiveDate;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getMobileLink() {
    return mobileLink;
  }

  public void setMobileLink(String mobileLink) {
    this.mobileLink = mobileLink;
  }

}
