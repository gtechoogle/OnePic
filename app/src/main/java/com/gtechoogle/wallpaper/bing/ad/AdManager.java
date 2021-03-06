package com.gtechoogle.wallpaper.bing.ad;

import android.content.Context;

import com.gtechoogle.wallpaper.bing.google.AdMobManager;


/**
 * Created by MTK54273 on 9/12/2016.
 */

public class AdManager implements AdInterface {
    private Context mContext;
    private AdMobManager mAdMobManager;
    public AdManager(Context context) {
        mContext = context;
        mAdMobManager = new AdMobManager(context);
    }
    @Override
    public void initAd() {
        mAdMobManager.initAd();
    }

    @Override
    public void showAd() {
        mAdMobManager.showAd();
    }

    public void cancelAd() {
        mAdMobManager.cancelAd();
    }
}
