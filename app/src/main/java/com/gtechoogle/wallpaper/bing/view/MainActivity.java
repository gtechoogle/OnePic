package com.gtechoogle.wallpaper.bing.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.gtechoogle.wallpaper.bing.R;
import com.gtechoogle.wallpaper.bing.ad.AdManager;
import com.gtechoogle.wallpaper.bing.manager.PicUrlManager;
import com.gtechoogle.wallpaper.bing.manager.PicDownloadManager;
import com.gtechoogle.wallpaper.bing.model.WallpaperDataInfo;
import com.gtechoogle.wallpaper.bing.view.fab.FloatButtonManager;

import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "OnePic";
    private FloatButtonManager mFloatBtManager;
    private ImageView mPic;
    private PhotoViewAttacher mPhotoAttacher;
    private PicDownloadManager mPicDownloadManager;
    private PicUrlManager mPicUrlManager;
    private AdManager mAdManager;
    private WallpaperDataInfo mCurrentInfo;
    String url = "http://girlatlas.b0.upaiyun.com/57c27be392d30228b02ca0d8/20160829/0742zfca9671qk2uyngb.jpg!lrg";
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
                        mFloatBtManager.setBitmapInfo(mBitmap, mCurrentInfo.getName());
                        mPic.setImageBitmap(mBitmap);
                        mPhotoAttacher.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        mPhotoAttacher.update();
                    }
                    break;
                case PicUrlManager.MSG_REQUEST_SUCCESS:
                    if (msg.obj instanceof List) {
                        handleReqSuccess(msg);
                    }
            }
        }
    };

    private void handleReqSuccess(Message msg) {
        List<WallpaperDataInfo> itemList = (List<WallpaperDataInfo>) msg.obj;
        mCurrentInfo = itemList.get(0);
        String imgUrl = mCurrentInfo.getUri();
        Log.d(TAG,"imgUrl = " + imgUrl);
        mPicDownloadManager.downloadPic(imgUrl, mHandler);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPic = (ImageView) findViewById(R.id.wallpaper);
        mPhotoAttacher = new PhotoViewAttacher(mPic);

        mPicDownloadManager = new PicDownloadManager(this);
        mPicUrlManager = new PicUrlManager(this, mHandler);
        mPicUrlManager.sendRequest();
        mAdManager = new AdManager(this);
        mAdManager.initAd();
        mFloatBtManager = new FloatButtonManager(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdManager.showAd();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPicDownloadManager.cancelDownload();
        mAdManager.cancelAd();
    }
}
