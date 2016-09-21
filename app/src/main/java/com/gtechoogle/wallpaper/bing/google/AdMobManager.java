package com.gtechoogle.wallpaper.bing.google;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.gtechoogle.wallpaper.bing.ad.AdInterface;

public class AdMobManager implements AdInterface {
    private static final String TAG = "AdMobManager";
    private static final String ADID = "ca-app-pub-6412462946252645/3612931211";
    private Context mContext;
    private InterstitialAd mInterstitialAd;
    private boolean mAdOpen;
    private boolean mAdCancel;
    private static final String TEST_ID = "C91B6379D9E06A72CA6DEAAAC0198144";

    public AdMobManager(Context context) {
        mContext = context;
    }

    @Override
    public void initAd() {
        mInterstitialAd = new InterstitialAd(mContext);
        mInterstitialAd.setAdUnitId(ADID);
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                Log.d(TAG,"onAdClosed");
            }

            @Override
            public void onAdLoaded() {
                Log.d(TAG,"onAdLoaded");
                super.onAdLoaded();
                if (!mAdCancel) {
                    mInterstitialAd.show();
                }
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                Log.d(TAG,"onAdFailedToLoad");
                requestNewInterstitial();
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
                Log.d(TAG,"onAdLeftApplication");
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
                Log.d(TAG,"onAdOpened");
                mAdOpen = true;
            }
        });
    }

    public void showAd() {
        mAdCancel = false;
        if (!mAdOpen) {
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
        }
    }
    public void cancelAd() {
        mAdCancel = true;
    }
}
