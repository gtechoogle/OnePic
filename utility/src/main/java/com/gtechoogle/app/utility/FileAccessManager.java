package com.gtechoogle.app.utility;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.text.format.Time;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by edy on 2016/9/11.
 */

public class FileAccessManager {
    private static final String TAG = "FileAccessManager";
    private static final int MSG_SAVE_BITMAP = 400;
    private static final String mExternalPath = Environment.getExternalStorageDirectory().getPath();
    private File mFolder;
    private File mSaveFilePath;
    private HandlerThread handlerThread = new HandlerThread("saveFileThread");
    private Handler mFileHanlder;
    private Context mContext;

    public FileAccessManager(Context context, String folderName) {
        if (!isExternalStorageAvailable()) {
            return ;
        }
        mContext = context;
        mFolder = new File(mExternalPath + "/" + folderName);
        if (!mFolder.exists()) {
            mFolder.mkdirs();
        }
        handlerThread.start();
        mFileHanlder = new Handler(handlerThread.getLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                int type = msg.what;
                if (type == MSG_SAVE_BITMAP && msg.obj instanceof Bitmap) {
                    saveBitmapFile((Bitmap) msg.obj);
                }
                return false;
            }
        });
    }

    public void saveWallpaper(Bitmap bitmap) {
        if (!isExternalStorageAvailable()) {
            return;
        }
        mSaveFilePath = new File(mFolder.getPath() + "/" + getFileNameByDate());
        if(mSaveFilePath.exists()){
            mSaveFilePath.delete();
        }
        Message msg = Message.obtain();
        msg.what = MSG_SAVE_BITMAP;
        msg.obj = bitmap;
        mFileHanlder.sendMessage(msg);
    }

    private String getFileNameByDate() {
        Time time=new Time();
        time.setToNow();
        Log.d(TAG,"year="+time.year+" month="+(time.month+1)+" day="+time.monthDay+" hour="+time.hour+" minute="+time.minute);
        return  String.valueOf(time.year) + String.valueOf(time.month) + ".png";

    }

    public void saveWallpaper(String fileName, Bitmap bitmap) {
        if (!isExternalStorageAvailable()) {
            return;
        }
        mSaveFilePath = new File(mFolder.getPath() + "/" + fileName);
        if(mSaveFilePath.exists()){
            mSaveFilePath.delete();
        }
        Message msg = Message.obtain();
        msg.what = MSG_SAVE_BITMAP;
        msg.obj = bitmap;
        mFileHanlder.sendMessage(msg);
    }

    private void saveBitmapFile(Bitmap bitmap) {
        FileOutputStream out;
        try {
            out = new FileOutputStream(mSaveFilePath);
            if(bitmap.compress(Bitmap.CompressFormat.PNG, 80, out)) {
                out.flush();
                out.close();
                Log.d(TAG,"Save successfully");
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e)  {
            e.printStackTrace();
        }
    }


    public static boolean isExternalStorageAvailable(){
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }
    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}
