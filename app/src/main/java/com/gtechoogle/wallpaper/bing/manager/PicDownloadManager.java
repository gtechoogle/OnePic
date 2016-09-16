package com.gtechoogle.wallpaper.bing.manager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by MTK54273 on 9/5/2016.
 */
public class PicDownloadManager {
    private Context mContext;
    private Handler mHandler;
    private String mUrl;
    private static final String TAG = "PicDownloadManager";
    public static final int MSG_LOAD_BIT_MAP_DONE = 100;
    public static final int MSG_LOAD_BIT_MAP_FAIL = MSG_LOAD_BIT_MAP_DONE + 1;
    public static final int MSG_LOAD_BIT_MAP_PREPARE = MSG_LOAD_BIT_MAP_DONE + 2;
    private Target mTarget = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            if (mHandler != null) {
                Log.d(TAG, "onBitmapLoaded");
                Message msg = Message.obtain();
                msg.what = MSG_LOAD_BIT_MAP_DONE;
                msg.obj = bitmap;
                mHandler.dispatchMessage(msg);
            }
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
            Log.d(TAG, "onBitmapFailed");
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
        }
    };

    public PicDownloadManager(Context context) {
        mContext = context;
    }
    public void downloadPic(String uri, Handler handler) {
        mHandler = handler;
        mUrl = uri;
        Picasso.with(mContext).load(uri).into(mTarget);
    }
    public void cancelDownload() {
        Picasso.with(mContext).cancelRequest(mTarget);
    }
}
