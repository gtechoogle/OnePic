package com.gtechoogle.wallpaper.bing.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by MTK54273 on 9/13/2016.
 */

public class WallpaperInfo implements Parcelable {
    public static final String KEY_WALLPAPER_INFO = "wallpaper_info";
    private String imageUrl;
    private String date;
    private String description;

    public WallpaperInfo () {

    }
    protected WallpaperInfo(Parcel in) {
        imageUrl = in.readString();
        date = in.readString();
        description = in.readString();
    }

    public static final Creator<WallpaperInfo> CREATOR = new Creator<WallpaperInfo>() {
        @Override
        public WallpaperInfo createFromParcel(Parcel in) {
            return new WallpaperInfo(in);
        }

        @Override
        public WallpaperInfo[] newArray(int size) {
            return new WallpaperInfo[size];
        }
    };

    public String getDate() {
        return date;
    }

    public void setDate(String name) {
        this.date = name;
    }

    public String getUri() {
        return imageUrl;
    }

    public void setUri(String uri) {
        this.imageUrl = uri;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(imageUrl);
        parcel.writeString(date);
        parcel.writeString(description);
    }
}
