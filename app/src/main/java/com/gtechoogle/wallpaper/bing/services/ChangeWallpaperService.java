package com.gtechoogle.wallpaper.bing.services;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import com.gtechoogle.app.utility.WallPaperManager;
import com.gtechoogle.wallpaper.bing.manager.PicDownloadManager;
import com.gtechoogle.wallpaper.bing.manager.PicUrlManager;
import com.gtechoogle.wallpaper.bing.model.WallpaperInfo;

import java.util.List;

public class ChangeWallpaperService extends Service {
    private PicUrlManager mPicUrlManager;
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

    public ChangeWallpaperService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("TEST","ChangeWallpaperService");
        //startForeground(1,null);
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_TIME_TICK);
        ChangeWallpaperReceiver receiver = new ChangeWallpaperReceiver();
        registerReceiver(receiver, filter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d("TEST","ChangeWallpaperService 1");
        mPicUrlManager = new PicUrlManager(mHandler);
        mPicUrlManager.sendRequest();
        return super.onStartCommand(intent, flags, startId);
    }
}
