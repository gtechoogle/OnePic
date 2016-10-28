package com.gtechoogle.wallpaper.bing.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ChangeWallpaperReceiver extends BroadcastReceiver {
    private static final String TAG = "ChangeWallpaperReceiver";
    public ChangeWallpaperReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG,"action = " + intent.getAction());
    }
}
