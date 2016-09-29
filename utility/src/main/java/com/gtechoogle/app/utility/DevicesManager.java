package com.gtechoogle.app.utility;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by MTK54273 on 9/21/2016.
 */

public class DevicesManager {
    public static int getDeviceWidth(Activity activity) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics.widthPixels;
    }
    public static int getDeviceHeight(Activity activity) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics.heightPixels;
    }
}
