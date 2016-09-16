package com.gtechoogle.wallpaper.bing.retrofit;

import android.util.Log;

import com.gtechoogle.wallpaper.bing.model.WallpaperDataInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by MTK54273 on 9/9/2016.
 */
public class BingPic {
    public static final String IMAGE_URL_KEY = "url";
    public static final String IMAGE_NAME_KEY = "fullstartdate";
    public static final String IMAGE_DESCRIPTION_KEY = "copyright";
    private static final String TAG = "BingPic";
    private List<Map> images;

    public List<WallpaperDataInfo> getPicInfo() {
        List<WallpaperDataInfo> wallpaperInfo = new ArrayList<>();
        for (Map map : images) {
            wallpaperInfo.add(setupInfo(map));
        }
        return wallpaperInfo;
    }

    private WallpaperDataInfo setupInfo(Map map) {
        WallpaperDataInfo info = new WallpaperDataInfo();
        String url = (String) map.get(IMAGE_URL_KEY);
        Log.d(TAG, "WallpaperDataInfo setupInfo url = " + url);
        info.setUri(getImageUrl(url));
        info.setDescription((String) map.get(IMAGE_DESCRIPTION_KEY));
        info.setName((String) map.get(IMAGE_NAME_KEY));
        return info;
    }
    // For China request, the url will include a special link, and other country will need to add
    // http://www.bing.com/
    private String getImageUrl(String url) {
        if (!url.contains("http")) {
            url = "http://www.bing.com" + url;
        }
        Log.d(TAG,"url = " + url);
        return url;
    }
}
