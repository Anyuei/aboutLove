package com.r136a1.door.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static long getDaysToTime(String ago){
        Date d=new Date();
        //1.日期格式
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        //2.某天的日期
        Date da1= null;
        try {
            da1 = sdf.parse(ago);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long day = (d.getTime() - da1.getTime()) / 24 / 60 / 60 / 1000;
        return day;
    }
}
