package com.example.chronus;

import java.util.Calendar;

public class TimeCounter {
    //Calender include second minute hour month year;
    //The time of setting will be detected, if old than current time -> to inform the user.
    //
    public static Integer getSecondsOfSetTime(Calendar num) {
//        Calendar cal = Calendar.getInstance();
//        if (cal.get(Calendar.HOUR_OF_DAY) - num.get(Calendar.HOUR_OF_DAY) >= 0) {
//            //如果当前时间大于等于num点 就计算第二天的num点的
//            cal.add(Calendar.DAY_OF_YEAR, 1);
//        } else {
//            cal.add(Calendar.DAY_OF_YEAR, 0);
//        }
//        cal.set(Calendar.HOUR_OF_DAY, num);
//        cal.set(Calendar.SECOND, 0);
//        cal.set(Calendar.MINUTE, 0);
//        cal.set(Calendar.MILLISECOND, 0);
//        Long seconds = (cal.getTimeInMillis() - System.currentTimeMillis());
        Long seconds = num.getTimeInMillis() - System.currentTimeMillis();

        return seconds.intValue();
    }
}
