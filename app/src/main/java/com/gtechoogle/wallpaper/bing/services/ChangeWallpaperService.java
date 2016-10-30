package com.gtechoogle.wallpaper.bing.services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.gtechoogle.app.utility.WallPaperManager;
import com.gtechoogle.wallpaper.bing.manager.PicDownloadManager;
import com.gtechoogle.wallpaper.bing.manager.PicUrlManager;
import com.gtechoogle.wallpaper.bing.model.WallpaperInfo;

import java.util.Calendar;
import java.util.List;

public class ChangeWallpaperService extends Service {
    private PicUrlManager mPicUrlManager;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (Intent.ACTION_TIME_TICK.equals(action)) {
                Calendar c = Calendar.getInstance();
                int hr = c.get(Calendar.HOUR_OF_DAY);
                int min = c.get(Calendar.MINUTE);
                //if (min  == 18) {
                    mPicUrlManager.sendRequest();
               // }
                Log.d("TEST","hr = " + hr + " min = " + min);
            }

        }
    };
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int type = msg.what;
            switch (type) {
                case PicUrlManager.MSG_REQUEST_SUCCESS:
                    if (msg.obj instanceof List) {
                        List<WallpaperInfo> data = (List<WallpaperInfo>) msg.obj;
                        String url = data.get(0).getUri();
                        PicDownloadManager temp = new PicDownloadManager(getApplicationContext());
                        temp.downloadPic(url, mHandler);
                    }
                    break;
                case PicDownloadManager.MSG_LOAD_BIT_MAP_DONE:
                    if (msg.obj instanceof Bitmap) {
                        Bitmap bitmap = (Bitmap)msg.obj;
                        WallPaperManager manager = new WallPaperManager(getApplicationContext());
                        manager.setWallpaper(bitmap);
                    }
                    break;
            }
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //startForeground(1,null);
        mPicUrlManager = new PicUrlManager(mHandler);
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_TIME_TICK);
        registerReceiver(mReceiver, filter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }
}
