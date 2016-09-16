package com.gtechoogle.wallpaper.bing.view.fab;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.gtechoogle.app.utility.FileAccessManager;
import com.gtechoogle.app.utility.ShareFunction;
import com.gtechoogle.app.utility.WallPaperManager;
import com.gtechoogle.wallpaper.bing.R;

public class FloatButtonManager implements View.OnClickListener {
    private FloatingActionMenu mMenu;
    private FloatingActionButton mSetWallpaperBt;
    private FloatingActionButton mDownloadBt;
    private FloatingActionButton mShareBt;
    private Bitmap mBitmap;
    private String mFileName;
    private Context mContext;


    public FloatButtonManager(Activity activity) {
        mContext = activity;
        mMenu = (FloatingActionMenu) activity.findViewById(R.id.menu);
        mSetWallpaperBt = (FloatingActionButton) activity.findViewById(R.id.menu_set_wallpaper);
        mDownloadBt = (FloatingActionButton) activity.findViewById(R.id.menu_download);
        mShareBt = (FloatingActionButton) activity.findViewById(R.id.menu_share);
        mSetWallpaperBt.setOnClickListener(this);
        mDownloadBt.setOnClickListener(this);
        mShareBt.setOnClickListener(this);
        createCustomAnimation(mMenu);
    }
    public void setBitmapInfo(Bitmap bitmap, String name) {
        mBitmap = bitmap;
        mFileName = name;
    }
    private void createCustomAnimation(final FloatingActionMenu item) {
        AnimatorSet set = new AnimatorSet();

        ObjectAnimator scaleOutX = ObjectAnimator.ofFloat(item.getMenuIconView(), "scaleX", 1.0f, 0.2f);
        ObjectAnimator scaleOutY = ObjectAnimator.ofFloat(item.getMenuIconView(), "scaleY", 1.0f, 0.2f);

        ObjectAnimator scaleInX = ObjectAnimator.ofFloat(item.getMenuIconView(), "scaleX", 0.2f, 1.0f);
        ObjectAnimator scaleInY = ObjectAnimator.ofFloat(item.getMenuIconView(), "scaleY", 0.2f, 1.0f);

        scaleOutX.setDuration(50);
        scaleOutY.setDuration(50);

        scaleInX.setDuration(150);
        scaleInY.setDuration(150);

        scaleInX.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                item.getMenuIconView().setImageResource(item.isOpened()
                        ? R.drawable.menu_close : R.drawable.menu);
            }
        });

        set.play(scaleOutX).with(scaleOutY);
        set.play(scaleInX).with(scaleInY).after(scaleOutX);
        set.setInterpolator(new OvershootInterpolator(2));

        item.setIconToggleAnimatorSet(set);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        mMenu.close(true);
        switch (id) {
            case R.id.menu_set_wallpaper:
                setWallpaper();
                break;
            case R.id.menu_download:
                downloadWallpaper();
                break;
            case R.id.menu_share:
                handleShare();
                break;
        }
    }

    private void handleShare() {
        ShareFunction share = new ShareFunction(mContext);
        share.shareText();
    }

    private void downloadWallpaper() {
        FileAccessManager fileAccessManager = new FileAccessManager(mContext,
                mContext.getString(R.string.folder_name));
        fileAccessManager.saveWallpaper(mFileName, mBitmap);
    }

    private void setWallpaper() {
        WallPaperManager manager = new WallPaperManager(mContext);
        manager.setWallpaper(mBitmap);
        Toast.makeText(mContext, R.string.wallpaper_hint, Toast.LENGTH_LONG).show();
    }
}
