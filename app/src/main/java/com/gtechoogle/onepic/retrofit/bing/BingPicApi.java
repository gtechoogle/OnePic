package com.gtechoogle.onepic.retrofit.bing;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by MTK54273 on 9/9/2016.
 */
public interface BingPicApi {
    @GET("/HPImageArchive.aspx?format=js&idx=0&n=1")
    Call<BingPic> getBingPic();
}
