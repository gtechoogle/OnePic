package com.gtechoogle.wallpaper.bing.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BingPicApi {
    @GET("/HPImageArchive.aspx?format=js&idx=23&n=8")
    Call<BingPic> getBingPic();
}
