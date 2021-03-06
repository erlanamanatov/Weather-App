package com.erkprog.weather.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class City implements Parcelable {
  private String name;
  private String key;
  private String countryName;
  private double latitude;
  private double longitude;

  public City() {

  }

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

  @Override
  public boolean equals(Object other) {
    if (other == null) return false;
    if (other == this) return true;
    if (!(other instanceof City))return false;
    City otherCity = (City) other;
    return this.key.equals(otherCity.getKey());
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + name.hashCode();
    result = 31 * result + countryName.hashCode();
    return result;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(name);
    dest.writeString(key);
    dest.writeString(countryName);
    dest.writeDouble(latitude);
    dest.writeDouble(longitude);
  }

  private City (Parcel source){
    name = source.readString();
    key = source.readString();
    countryName = source.readString();
    latitude = source.readDouble();
    longitude = source.readDouble();
  }

  public static final Parcelable.Creator<City> CREATOR = new Parcelable.Creator<City>(){
    @Override
    public City createFromParcel(Parcel source) {
      return new City(source);
    }

    @Override
    public City[] newArray(int size) {
      return new City[size];
    }
  };
}
