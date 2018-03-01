package com.itfan.analy.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ralap on 2017/8/22.
 */
public class DateUtils {

    public static String dateToStr(Date date, String formart) {
        SimpleDateFormat sdf = new SimpleDateFormat(formart);
        String dateStr = sdf.format(date);
        return dateStr;
    }

}
