package com.gtechoogle.wallpaper.bing.wallpaperlist;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.gtechoogle.wallpaper.bing.manager.PicUrlManager;
import com.gtechoogle.wallpaper.bing.model.WallpaperInfo;
import com.gtechoogle.wallpaper.bing.view.listcardview.WallpaperCardViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MTK54273 on 10/28/2016.
 */

public class WallpaperListPresenter implements IWallpaperListPresenter {

    private static final String TAG = "WallpaperListPresenter";
    private PicUrlManager mPicUrlManager;
    private WallpaperCardViewAdapter mAdapter;
    private List<WallpaperInfo> mWallpaperInfoList = new ArrayList<>();

    private IWallpaperView mView;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int type = msg.what;
            Log.d(TAG,"type = " + type);
            switch (type) {
                case PicUrlManager.MSG_REQUEST_SUCCESS:
                    if (msg.obj instanceof List) {
                        mView.showLoading(false);
                        setupWallpaperData(msg);
                        mAdapter.notifyDataSetChanged();
                    }
                    break;
                case PicUrlManager.MSG_REQUEST_FAIL:
                        mPicUrlManager.sendRequest();
            }
        }
    };

    public WallpaperListPresenter(IWallpaperView view) {
        this.mView= view;
        mPicUrlManager = new PicUrlManager(mHandler);
        mPicUrlManager.sendRequest();
    }

    @Override
    public void initAdapter(Context context) {
        mAdapter = new WallpaperCardViewAdapter(context, mWallpaperInfoList);
        mView.bindAdapter(mAdapter);
    }

    private void setupWallpaperData(Message msg) {
        List<WallpaperInfo> data = (List<WallpaperInfo>) msg.obj;
        mWallpaperInfoList.clear();
        for(WallpaperInfo item : data) {
            mWallpaperInfoList.add(item);
        }
    }
}
