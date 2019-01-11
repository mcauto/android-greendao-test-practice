package com.gofd.greendaopractice.utility;

import android.util.Log;

import com.gofd.greendaopractice.Constants;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * DateDeserializer
 * java.util.Date를 json deserialize 하기 위한 클래스
 * gson에 registerAdapter를 해서 사용 (테스트 케이스 참고)
 */
public class DateDeserializer implements JsonDeserializer<Date> {
  public static final String TAG = DateDeserializer.class.getName();
  @Override
  public Date deserialize(JsonElement element, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
    String date = element.getAsString();

    SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT);
    formatter.setTimeZone(TimeZone.getTimeZone("UTC"));

    try {
      return formatter.parse(date);
    }
    catch (ParseException pe) {
      Log.e(TAG, "deserialize: " + formatter.toPattern() + "이랑 format이 다릅니다.", pe);
      return null;
    }
  }
}