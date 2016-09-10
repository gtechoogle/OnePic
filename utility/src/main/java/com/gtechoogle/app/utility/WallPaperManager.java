package com.gtechoogle.app.utility;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import java.io.IOException;

/**
 * Created by edy on 2016/9/10.
 */

public class WallPaperManager {
    private Context mContext;
    private static final int MSG_SET = 1000;
    private HandlerThread handlerThread = new HandlerThread("wallpaperThread");
    private Handler mWallpaperHandler;
    public WallPaperManager(Context context) {
        mContext = context;
        handlerThread.start();
        mWallpaperHandler = new Handler(handlerThread.getLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                int type = msg.what;
                if (type == MSG_SET && msg.obj instanceof Bitmap) {
                    WallpaperManager myWallpaperManager
                            = WallpaperManager.getInstance(mContext);
                    try {
                        myWallpaperManager.setBitmap((Bitmap) msg.obj);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        });
    }
    public void setWallpaper(Bitmap bitmap) {
        Message msg = Message.obtain();
        msg.obj = bitmap;
        msg.what = MSG_SET;
        mWallpaperHandler.sendMessage(msg);
    }
}
