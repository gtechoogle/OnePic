package com.gtechoogle.app.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by MTK54273 on 9/20/2016.
 */

public class MiscUtility {
    public static String getDateInFormat(String data) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmm");
        Date date = null;
        try {
            date = fmt.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat fmtOut = new SimpleDateFormat("yyyy-MMM-d E", Locale.getDefault());
        return fmtOut.format(date);
    }
}
