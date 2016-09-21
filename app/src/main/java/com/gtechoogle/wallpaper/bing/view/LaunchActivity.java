package com.gtechoogle.wallpaper.bing.view;

import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gtechoogle.app.utility.DevicesManager;
import com.gtechoogle.app.utility.NetworkManager;
import com.gtechoogle.wallpaper.bing.R;
import com.gtechoogle.wallpaper.bing.ad.AdManager;
import com.gtechoogle.wallpaper.bing.manager.PicUrlManager;
import com.gtechoogle.wallpaper.bing.model.WallpaperDataInfo;
import com.gtechoogle.wallpaper.bing.view.listcardview.WallpaperCardViewAdapter;
import com.gtechoogle.wallpaper.bing.view.listcardview.WallpaperCardViewInfo;

import java.util.ArrayList;
import java.util.List;

public class LaunchActivity extends AppCompatActivity {
    private static final String TAG = "LaunchActivity";
    private RecyclerView mRecyclerView;
    private PicUrlManager mPicUrlManager;
    private AdManager mAdManager;
    private List<WallpaperCardViewInfo> mWallpaperInfoList = new ArrayList<>();
    private WallpaperCardViewAdapter mAdapter;
    private ProgressBar mLoading;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int type = msg.what;
            Log.d(TAG,"type = " + type);
            switch (type) {
                case PicUrlManager.MSG_REQUEST_SUCCESS:
                    if (msg.obj instanceof List) {
                        mLoading.setVisibility(View.GONE);
                        setupWallpaperData(msg);
                        mAdapter.notifyDataSetChanged();
                    }
                    break;
                case PicUrlManager.MSG_REQUEST_FAIL:
                    mPicUrlManager.sendRequest();
            }
        }
    };

    private void setupWallpaperData(Message msg) {
        List<WallpaperDataInfo> data = (List<WallpaperDataInfo>) msg.obj;
        mWallpaperInfoList.clear();
        for(WallpaperDataInfo item : data) {
            WallpaperCardViewInfo info = convertToCardViewInfo(item);
            mWallpaperInfoList.add(info);
        }
    }

    private WallpaperCardViewInfo convertToCardViewInfo(WallpaperDataInfo item) {
        String date = item.getName();
        String location = item.getDescription();
        String imageUrl = item.getUri();
        WallpaperCardViewInfo info = new WallpaperCardViewInfo(date, location, imageUrl);
        return info;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initVariable();
        mPicUrlManager.sendRequest();
        mAdManager.initAd();
    }

    private void initVariable() {
        mLoading = (ProgressBar) findViewById(R.id.progress_loading);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new WallpaperCardViewAdapter(this, mWallpaperInfoList);
        setupRecyclerView();
        mPicUrlManager = new PicUrlManager(this, mHandler);
        mAdManager = new AdManager(this);
    }

    private void setupRecyclerView() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!NetworkManager.isNetworkAvailable(this)) {
            Toast toast = Toast.makeText(this,R.string.no_connection, Toast.LENGTH_LONG);
            toast.show();
        }
        mAdManager.showAd();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAdManager.cancelAd();
    }
}
