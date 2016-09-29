package com.gtechoogle.wallpaper.bing.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BingPicApi {
    @GET ("/HPImageArchive.aspx")
    Call<BingPic> getBingPic(@Query("format") String format, @Query("idx") int idx, @Query("n")
                             int number);
//    @GET("/HPImageArchive.aspx?format=js&idx=0&n=8")
//    Call<BingPic> getBingPic();
//    @GET("/HPImageArchive.aspx?format=js&n=8")
//    Call<BingPic> getBingPic(@Query("idx") int offset);
}
