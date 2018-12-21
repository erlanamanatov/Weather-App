package com.erkprog.weather.util;

import com.erkprog.weather.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MyUtil {

  private static Date getDate(String stringDate) {
    if (stringDate == null) {
      throw new IllegalArgumentException("Parsing error");
    }
    Locale locale = new Locale("en");
//    SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM y H:mm:ss Z", locale);
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", locale);

    try {
      return formatter.parse(stringDate);
    } catch (ParseException e) {
      throw new IllegalArgumentException("Parsing error");
    }
  }

  public static String getFormattedDate(String resourceDate) throws IllegalArgumentException {
    SimpleDateFormat formatter = new SimpleDateFormat("MMM d", new Locale("en"));
    Date date = getDate(resourceDate);
    return formatter.format(date);
  }

  public static int getIcon(Integer iconId) {
    switch (iconId) {
      case 1:
        return R.drawable.icon1;
      case 2:
        return R.drawable.icon2;
      case 3:
        return R.drawable.icon3;
      case 4:
        return R.drawable.icon4;
      case 5:
        return R.drawable.icon5;
      case 6:
        return R.drawable.icon6;
      case 7:
        return R.drawable.icon7;
      case 8:
        return R.drawable.icon8;
      case 11:
        return R.drawable.icon11;
      case 12:
        return R.drawable.icon12;
      case 13:
        return R.drawable.icon13;
      case 14:
        return R.drawable.icon14;
      case 15:
        return R.drawable.icon15;
      case 16:
        return R.drawable.icon16;
      case 17:
        return R.drawable.icon17;
      case 18:
        return R.drawable.icon18;
      case 19:
        return R.drawable.icon19;
      case 20:
        return R.drawable.icon20;
      case 21:
        return R.drawable.icon21;
      case 22:
        return R.drawable.icon22;
      case 23:
        return R.drawable.icon23;
      case 24:
        return R.drawable.icon24;
      case 25:
        return R.drawable.icon25;
      case 26:
        return R.drawable.icon26;
      case 29:
        return R.drawable.icon29;
      case 30:
        return R.drawable.icon30;
      case 31:
        return R.drawable.icon31;
      case 32:
        return R.drawable.icon32;
      case 33:
        return R.drawable.icon33;
      case 34:
        return R.drawable.icon34;
      case 35:
        return R.drawable.icon35;
      case 36:
        return R.drawable.icon36;
      case 37:
        return R.drawable.icon37;
      case 38:
        return R.drawable.icon38;
      case 39:
        return R.drawable.icon39;
      case 40:
        return R.drawable.icon40;
      case 41:
        return R.drawable.icon41;
      case 42:
        return R.drawable.icon42;
      case 43:
        return R.drawable.icon43;
      case 44:
        return R.drawable.icon44;
      default:
        return R.drawable.image_placeholder;
    }
  }
}
