package com.gtechoogle.wallpaper.bing.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.gtechoogle.wallpaper.bing.R;
import com.gtechoogle.wallpaper.bing.manager.PicDownloadManager;
import com.gtechoogle.wallpaper.bing.model.WallpaperInfo;
import com.gtechoogle.wallpaper.bing.view.fab.FloatButtonManager;

import uk.co.senab.photoview.PhotoViewAttacher;

public class FullWallpaperActivity extends AppCompatActivity {

    private static final String TAG = "OnePic";
    private FloatButtonManager mFloatBtManager;
    private ImageView mPic;
    private PhotoViewAttacher mPhotoAttacher;
    private PicDownloadManager mPicDownloadManager;
    private Bitmap mBitmap = null;
    private WallpaperInfo mInfo;
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
                        mFloatBtManager.setBitmapInfo(mBitmap, mInfo.getDate());
                        mPic.setImageBitmap(mBitmap);
                        mPhotoAttacher.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        mPhotoAttacher.update();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent != null) {
            mInfo = intent.getParcelableExtra(WallpaperInfo.KEY_WALLPAPER_INFO);
        }
        setContentView(R.layout.activity_main);
        mPic = (ImageView) findViewById(R.id.wallpaper);
        mPhotoAttacher = new PhotoViewAttacher(mPic);
        mFloatBtManager = new FloatButtonManager(this);
        mPicDownloadManager = new PicDownloadManager(this);
        mPicDownloadManager.downloadPic(mInfo.getUri(), mHandler);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPicDownloadManager.cancelDownload();

    }
}
