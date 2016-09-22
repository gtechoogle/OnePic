package com.gtechoogle.wallpaper.bing.retrofit;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.gtechoogle.wallpaper.bing.manager.PicUrlManager;
import com.gtechoogle.wallpaper.bing.model.WallpaperInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BingHttpRequest {

    private static final String TAG = "BingHttpRequest";
    private static final String BASE_URL = "http://www.bing.com/";
    private Handler mHandler;

    public BingHttpRequest(Handler handler) {
        mHandler = handler;
    }

    public void sendRequest() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        BingPicApi service = retrofit.create(BingPicApi.class);

        Call<BingPic> call = service.getBingPic();

        call.enqueue(new Callback<BingPic>() {
            @Override
            public void onResponse(Call<BingPic> call, Response<BingPic> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG,"Get successful response");
                    BingPic data = response.body();
                    if (data == null) {
                        sendImageUrl(getFailMessage());
                        return;
                    }
                    List<WallpaperInfo> info = data.getPicInfo();
                    Log.d(TAG,"info  = " + info);
                    sendImageUrl(getSuccessMessage(info));
                }
            }

            @Override
            public void onFailure(Call<BingPic> call, Throwable t) {
                Log.d(TAG,"onFailure");
                sendImageUrl(getFailMessage());
            }
        });
    }
    private void sendImageUrl(Message msg) {
        mHandler.sendMessage(msg);
    }
    private Message getFailMessage() {
        Message msg = Message.obtain();
        msg.what = PicUrlManager.MSG_REQUEST_FAIL;
        return msg;
    }
    private Message getSuccessMessage(List<WallpaperInfo> data) {
        Message msg = Message.obtain();
        msg.what = PicUrlManager.MSG_REQUEST_SUCCESS;
        msg.obj = data;
        return msg;
    }
}
