package com.erkprog.weather.data.entity;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForecastResponse {

  @SerializedName("Headline")
  @Expose
  private Headline headline;
  @SerializedName("DailyForecasts")
  @Expose
  private List<DailyForecast> dailyForecasts = null;

  public Headline getHeadline() {
    return headline;
  }

  public void setHeadline(Headline headline) {
    this.headline = headline;
  }

  public List<DailyForecast> getDailyForecasts() {
    return dailyForecasts;
  }

  public void setDailyForecasts(List<DailyForecast> dailyForecasts) {
    this.dailyForecasts = dailyForecasts;
  }

  public class Headline {

    @SerializedName("EffectiveDate")
    @Expose
    private String effectiveDate;
    @SerializedName("EffectiveEpochDate")
    @Expose
    private Integer effectiveEpochDate;
    @SerializedName("Severity")
    @Expose
    private Integer severity;
    @SerializedName("Text")
    @Expose
    private String text;
    @SerializedName("Category")
    @Expose
    private String category;
    @SerializedName("EndDate")
    @Expose
    private String endDate;
    @SerializedName("EndEpochDate")
    @Expose
    private Integer endEpochDate;
    @SerializedName("MobileLink")
    @Expose
    private String mobileLink;
    @SerializedName("Link")
    @Expose
    private String link;

    public String getEffectiveDate() {
      return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
      this.effectiveDate = effectiveDate;
    }

    public Integer getEffectiveEpochDate() {
      return effectiveEpochDate;
    }

    public void setEffectiveEpochDate(Integer effectiveEpochDate) {
      this.effectiveEpochDate = effectiveEpochDate;
    }

    public Integer getSeverity() {
      return severity;
    }

    public void setSeverity(Integer severity) {
      this.severity = severity;
    }

    public String getText() {
      return text;
    }

    public void setText(String text) {
      this.text = text;
    }

    public String getCategory() {
      return category;
    }

    public void setCategory(String category) {
      this.category = category;
    }

    public String getEndDate() {
      return endDate;
    }

    public void setEndDate(String endDate) {
      this.endDate = endDate;
    }

    public Integer getEndEpochDate() {
      return endEpochDate;
    }

    public void setEndEpochDate(Integer endEpochDate) {
      this.endEpochDate = endEpochDate;
    }

    public String getMobileLink() {
      return mobileLink;
    }

    public void setMobileLink(String mobileLink) {
      this.mobileLink = mobileLink;
    }

    public String getLink() {
      return link;
    }

    public void setLink(String link) {
      this.link = link;
    }

  }


  public class DailyForecast {

    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("EpochDate")
    @Expose
    private Integer epochDate;
    @SerializedName("Temperature")
    @Expose
    private Temperature temperature;
    @SerializedName("Day")
    @Expose
    private Day day;
    @SerializedName("Night")
    @Expose
    private Night night;
    @SerializedName("Sources")
    @Expose
    private List<String> sources = null;
    @SerializedName("MobileLink")
    @Expose
    private String mobileLink;
    @SerializedName("Link")
    @Expose
    private String link;

    public String getDate() {
      return date;
    }

    public void setDate(String date) {
      this.date = date;
    }

    public Integer getEpochDate() {
      return epochDate;
    }

    public void setEpochDate(Integer epochDate) {
      this.epochDate = epochDate;
    }

    public Temperature getTemperature() {
      return temperature;
    }

    public void setTemperature(Temperature temperature) {
      this.temperature = temperature;
    }

    public Day getDay() {
      return day;
    }

    public void setDay(Day day) {
      this.day = day;
    }

    public Night getNight() {
      return night;
    }

    public void setNight(Night night) {
      this.night = night;
    }

    public List<String> getSources() {
      return sources;
    }

    public void setSources(List<String> sources) {
      this.sources = sources;
    }

    public String getMobileLink() {
      return mobileLink;
    }

    public void setMobileLink(String mobileLink) {
      this.mobileLink = mobileLink;
    }

    public String getLink() {
      return link;
    }

    public void setLink(String link) {
      this.link = link;
    }


    public class Day {

      @SerializedName("Icon")
      @Expose
      private Integer icon;
      @SerializedName("IconPhrase")
      @Expose
      private String iconPhrase;

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

    }

    public class Night {

      @SerializedName("Icon")
      @Expose
      private Integer icon;
      @SerializedName("IconPhrase")
      @Expose
      private String iconPhrase;

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

    }


    public class Temperature {

      @SerializedName("Minimum")
      @Expose
      private Minimum minimum;
      @SerializedName("Maximum")
      @Expose
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


    public class Maximum {

      @SerializedName("Value")
      @Expose
      private Double value;
      @SerializedName("Unit")
      @Expose
      private String unit;
      @SerializedName("UnitType")
      @Expose
      private Integer unitType;

      public Double getValue() {
        return value;
      }

      public void setValue(Double value) {
        this.value = value;
      }

      public String getUnit() {
        return unit;
      }

      public void setUnit(String unit) {
        this.unit = unit;
      }

      public Integer getUnitType() {
        return unitType;
      }

      public void setUnitType(Integer unitType) {
        this.unitType = unitType;
      }

    }


    public class Minimum {

      @SerializedName("Value")
      @Expose
      private Double value;
      @SerializedName("Unit")
      @Expose
      private String unit;
      @SerializedName("UnitType")
      @Expose
      private Integer unitType;

      public Double getValue() {
        return value;
      }

      public void setValue(Double value) {
        this.value = value;
      }

      public String getUnit() {
        return unit;
      }

      public void setUnit(String unit) {
        this.unit = unit;
      }

      public Integer getUnitType() {
        return unitType;
      }

      public void setUnitType(Integer unitType) {
        this.unitType = unitType;
      }

    }
  }
}

