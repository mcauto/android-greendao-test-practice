package com.gofd.greendaopractice.utility;

import com.gofd.greendaopractice.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertNotEquals;

public class DateDeserializerTest extends TestCase {
  public static final String TAG = DateDeserializerTest.class.getName();
  @Test
  public void testDateDeserialize(){
    Gson gson = new GsonBuilder()
            .setDateFormat(Constants.DATE_FORMAT)
            .registerTypeAdapter(Date.class, new DateDeserializer())
            .create();

    // 현재 Date 불러오기
    Date date = Calendar.getInstance().getTime();

    String sDate = gson.toJson(date);

    Date target= new Date(0); // 1970년 어쩌구

    target = gson.fromJson(sDate, target.getClass());

    assertNotEquals(target, new Date(0));
  }

}