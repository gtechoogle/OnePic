package com.gtechoogle.onepic.view;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.gtechoogle.onepic.manager.PicDownloadManager;
import com.gtechoogle.onepic.R;
import com.gtechoogle.onepic.manager.PicUrlManager;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "OnePic";
    private ImageView mSetWallPaper;
    private ImageView mPic;
    private PicDownloadManager mPicDownloadManager;
    private PicUrlManager mPicUrlManager;
    String url = "http://s.cn.bing.net/az/hprichbg/rb/Dongjiang_ZH-CN10434068279_1920x1080.jpg";
    private Bitmap mBitmap = null;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int type = msg.what;
            Log.d(TAG,"type = " + type);
            switch (type) {
                case PicDownloadManager.MSG_LOAD_BIT_MAP_DONE:
                    if (msg.obj instanceof Bitmap) {
                        mBitmap = (Bitmap)msg.obj;
                        mPic.setImageBitmap(mBitmap);
                    }
                    break;
                case PicUrlManager.MSG_REQUEST_SUCCESS:
                    if (msg.obj instanceof String) {
                        String imgUrl = (String) msg.obj;
                        mPicDownloadManager.downloadPic(imgUrl, mHandler);
                    }

            }

            if (type == PicDownloadManager.MSG_LOAD_BIT_MAP_DONE) {
                if (msg.obj instanceof Bitmap) {
                    mBitmap = (Bitmap)msg.obj;
                    mPic.setImageBitmap(mBitmap);
                }
            } else if (type == -1) {
                String uri = msg.obj.toString();
                mPicDownloadManager.downloadPic(uri,mHandler);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPic = (ImageView) findViewById(R.id.wallpaper);
        mSetWallPaper = (ImageView) findViewById(R.id.set_wallpaper);
        mSetWallPaper.setOnClickListener(this);
        mPicDownloadManager = new PicDownloadManager(this);
        mPicUrlManager = new PicUrlManager(this, mHandler);
        mPicUrlManager.sendRequest();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPicDownloadManager.downloadPic(url, mHandler);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPicDownloadManager.cancelDownload();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.set_wallpaper) {
            WallpaperManager myWallpaperManager
                    = WallpaperManager.getInstance(getApplicationContext());
            try {
                myWallpaperManager.setBitmap(mBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
