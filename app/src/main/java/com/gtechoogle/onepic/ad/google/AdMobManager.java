package com.gtechoogle.onepic.ad.google;

import android.content.Context;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.gtechoogle.onepic.ad.AdInterface;

public class AdMobManager implements AdInterface {
    private Context mContext;
    private InterstitialAd mInterstitialAd;
    private boolean mAdShowed;
    private boolean mAdCancel;
    private static final String TEST_ID = "C91B6379D9E06A72CA6DEAAAC0198144";

    public AdMobManager(Context context) {
        mContext = context;
    }

    @Override
    public void initAd(String key) {
        mInterstitialAd = new InterstitialAd(mContext);
        mInterstitialAd.setAdUnitId(key);
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                //requestNewInterstitial();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                if (!mAdCancel) {
                    mInterstitialAd.show();
                    mAdShowed = true;
                }
            }
        });
    }

    public void showAd() {
        mAdCancel = false;
        if (!mAdShowed) {
            requestNewInterstitial();
        }
    }
    private void requestNewInterstitial() {
        if (!mInterstitialAd.isLoaded()) {
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice(TEST_ID)
                    .build();
            mInterstitialAd.loadAd(adRequest);
        } else {
            mInterstitialAd.show();
            mAdShowed = true;
        }
    }
    public void cancelAd() {
        mAdCancel = true;
    }
}
