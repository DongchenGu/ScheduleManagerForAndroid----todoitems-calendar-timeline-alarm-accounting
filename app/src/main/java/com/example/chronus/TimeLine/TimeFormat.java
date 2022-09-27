package com.example.chronus.TimeLine;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wen on 2017/6/14.
 */

public class TimeFormat {
    public static String formatDate(String format, String date) {
        String result = "";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date mDate = df.parse(date);
            SimpleDateFormat df1 = new SimpleDateFormat(format);
            result = df1.format(mDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Date toDate(String dateTime) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd-HH-mm").parse(dateTime);
        } catch (ParseException ignored) {
        }
        return new Date();
    }
    public static String toDate(Date date){
        return new SimpleDateFormat("yyyy.MM.dd").format(date);
    }
    public static String toTime(Date date){
        return new SimpleDateFormat("HH:mm").format(date);
    }

}
