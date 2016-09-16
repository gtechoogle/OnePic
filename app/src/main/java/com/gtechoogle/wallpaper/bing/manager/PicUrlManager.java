package com.gtechoogle.wallpaper.bing.manager;

import android.content.Context;
import android.os.Handler;

import com.gtechoogle.wallpaper.bing.retrofit.BingHttpRequest;

/**
 * Created by MTK54273 on 9/9/2016.
 */
public class PicUrlManager {
    private Context mContext;
    private Handler mHandler;
    private BingHttpRequest mRequest;
    public static final int MSG_REQUEST_FAIL = 200;
    public static final int MSG_REQUEST_SUCCESS = MSG_REQUEST_FAIL + 1;
    public PicUrlManager(Context context, Handler handler) {
        mContext = context;
        mHandler = handler;
        mRequest = new BingHttpRequest(handler);
    }
    public void sendRequest() {
        mRequest.sendRequest();
    }
}
