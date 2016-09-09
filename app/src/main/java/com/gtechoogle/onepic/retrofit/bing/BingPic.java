package com.gtechoogle.onepic.retrofit.bing;

import android.util.Log;

import java.util.List;
import java.util.Map;

/**
 * Created by MTK54273 on 9/9/2016.
 */
public class BingPic {
    private static final String IMAGE_URL_KEY = "url";
    List<Map> images;
    String url;
    public String getImagesUrl() {
        String url = null;
        if (images.size() > 0) {
            url = (String) images.get(0).get(IMAGE_URL_KEY);
        }
        return url;
    }

}
