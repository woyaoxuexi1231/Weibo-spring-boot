package com.cqut.weibo;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class md5Test {
    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        System.out.println(calendar.getTimeInMillis());
        System.out.println(System.currentTimeMillis());
        System.out.println(TimeUnit.SECONDS);
    }
}
