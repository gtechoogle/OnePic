package com.gtechoogle.onepic.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.gtechoogle.app.utility.FileAccessManager;
import com.gtechoogle.app.utility.WallPaperManager;
import com.gtechoogle.onepic.R;
import com.gtechoogle.onepic.ad.AdManager;
import com.gtechoogle.onepic.manager.PicDownloadManager;
import com.gtechoogle.onepic.manager.PicUrlManager;

import uk.co.senab.photoview.PhotoViewAttacher;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "OnePic";
    private FloatingActionButton mSetWallPaper;
    private FloatingActionButton mShareBt;
    private ImageView mPic;
    private PhotoViewAttacher mPhotoAttacher;
    private PicDownloadManager mPicDownloadManager;
    private PicUrlManager mPicUrlManager;
    private AdManager mAdManager;
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
                        mPic.setImageBitmap(mBitmap);
                        mPhotoAttacher.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        mPhotoAttacher.update();
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
        mSetWallPaper = (FloatingActionButton) findViewById(R.id.set_wallpaper_bt);
        mShareBt = (FloatingActionButton) findViewById(R.id.save_bt);
        mShareBt.setOnClickListener(this);
        mPhotoAttacher = new PhotoViewAttacher(mPic);
        mSetWallPaper.setOnClickListener(this);
        mPicDownloadManager = new PicDownloadManager(this);
        mPicUrlManager = new PicUrlManager(this, mHandler);
        mPicUrlManager.sendRequest();
        mAdManager = new AdManager(this);
        mAdManager.initAd("ca-app-pub-6412462946252645/3612931211");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdManager.showAd();
        mPicDownloadManager.downloadPic(url, mHandler);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPicDownloadManager.cancelDownload();
        mAdManager.cancelAd();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.set_wallpaper_bt) {
            WallPaperManager manager = new WallPaperManager(this);
            manager.setWallpaper(mBitmap);
            Toast.makeText(this, R.string.wallpaper_hint,Toast.LENGTH_LONG).show();
        } else if (view.getId() == R.id.save_bt) {
            //FileAccessManager.verifyStoragePermissions(this);
            FileAccessManager fileAccessManager = new FileAccessManager(this, getString(R.string.foldr_name));
            fileAccessManager.saveWallpaper(mBitmap);
        }
    }
}
