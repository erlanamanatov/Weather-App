package com.erkprog.weather.util;

import com.erkprog.weather.data.entity.City;
import com.erkprog.weather.data.entity.Country;
import com.erkprog.weather.data.entity.GeoPosition;
import com.erkprog.weather.data.entity.GeopositionResponse;
import com.erkprog.weather.data.entity.Sun;

import org.junit.Test;

import static org.junit.Assert.*;

public class MyUtilTest {

  @Test
  public void getFormattedDate() {
    assertEquals("Friday, Dec 21", MyUtil.getFormattedDate("2018-12-21T07:00:00+06:00"));
    assertEquals("Saturday, Dec 22", MyUtil.getFormattedDate("2018-12-22T17:23:00+06:00"));
  }

  @Test
  public void getSunriseSunset() {
    Sun sun = new Sun();
    sun.setRise("2018-12-22T08:29:00+06:00");
    sun.setSet("2018-12-22T17:31:00+06:00");
    assertEquals("08:29 AM, 05:31 PM", MyUtil.getSunriseSunset(sun));
    sun.setRise("2018-12-24T06:15:00+06:00");
    sun.setSet("2018-12-24T20:20:00+06:00");
    assertEquals("06:15 AM, 08:20 PM", MyUtil.getSunriseSunset(sun));
  }

  @Test
  public void formCity() {
    GeopositionResponse geopositionResponse = new GeopositionResponse() {
      {
        Country country = new Country();
        country.setEnglishName("Kyrgyzstan");
        country.setID("123");
        GeoPosition geoPosition = new GeoPosition();
        geoPosition.setLatitude(12345.123);
        geoPosition.setLongitude(12345.567);
        setCountry(country);
        setGeoPosition(geoPosition);
        setKey("223344");
        setEnglishName("Bishkek");
        setVersion(1);
      }
    };

    City city = new City() {{
      setCountryName("Kyrgyzstan");
      setKey("223344");
      setLatitude(12345.123);
      setLongitude(12345.567);
      setName("Bishkek");
    }};
    assertEquals(city, MyUtil.formCity(geopositionResponse));
  }
}