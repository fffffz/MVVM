package org.icegeneral.mvvm.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by jianjun.lin on 2017/1/10.
 */

public class DateUtils {

    public static final SimpleDateFormat DAY_FORMAT = new SimpleDateFormat("yyyyMMdd");
    public static final SimpleDateFormat ITEM_HEADER_FORMAT = new SimpleDateFormat("MM月dd日");
    public static final String[] DAY_OF_WEEK_CN = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

    public static String getDate(Calendar calendar) {
        return DAY_FORMAT.format(calendar.getTime());
    }

    public static boolean isToday(String date) {
        return DAY_FORMAT.format(Calendar.getInstance().getTime()).equals(date);
    }

    public static boolean isTomorrow(String date) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return DAY_FORMAT.format(calendar.getTime()).equals(date);
    }

    public static String getItemHeaderDate(String dateStr) {
        try {
            Date date = DAY_FORMAT.parse(dateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return ITEM_HEADER_FORMAT.format(date) + " " + DAY_OF_WEEK_CN[calendar.get(Calendar.DAY_OF_WEEK) - 1];
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

}
