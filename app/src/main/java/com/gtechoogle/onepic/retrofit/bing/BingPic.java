package com.gtechoogle.onepic.retrofit.bing;

import com.gtechoogle.onepic.model.WallpaperDataInfo;

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
        info.setUri((String) map.get(IMAGE_URL_KEY));
        info.setDescription((String) map.get(IMAGE_DESCRIPTION_KEY));
        info.setName((String) map.get(IMAGE_NAME_KEY));
        return info;
    }
}
