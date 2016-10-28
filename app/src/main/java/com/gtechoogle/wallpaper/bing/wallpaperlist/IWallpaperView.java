package com.gtechoogle.wallpaper.bing.wallpaperlist;

import com.gtechoogle.wallpaper.bing.view.listcardview.WallpaperCardViewAdapter;

/**
 * Created by MTK54273 on 10/28/2016.
 */

public interface IWallpaperView {
    void showLoading(boolean show);
    void bindAdapter(WallpaperCardViewAdapter adapter);
}
