package com.gtechoogle.wallpaper.bing.view.listcardview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gtechoogle.app.utility.DevicesManager;
import com.gtechoogle.app.utility.MiscUtility;
import com.gtechoogle.wallpaper.bing.R;
import com.gtechoogle.wallpaper.bing.model.WallpaperInfo;
import com.gtechoogle.wallpaper.bing.view.FullWallpaperActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class WallpaperCardViewAdapter extends RecyclerView.Adapter<WallpaperCardViewHolder> {
    private static final String TAG = "WallpaperCardViewAdapter";
    private Context mContext;
    private List<WallpaperInfo> mList;

    public WallpaperCardViewAdapter(Context context, List<WallpaperInfo> data) {
        mContext = context;
        mList = data;
    }
    @Override
    public WallpaperCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.wallpaper_card_view, parent, false);

        return new WallpaperCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WallpaperCardViewHolder holder, int position) {
        final WallpaperInfo info = mList.get(position);
        ImageView imageView = holder.getImageView();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra(WallpaperInfo.KEY_WALLPAPER_INFO, info);
                intent.setClass(mContext, FullWallpaperActivity.class);
                mContext.startActivity(intent);
            }
        });
        Picasso.with(mContext).load(modifyResolution(info.getUri())).into(imageView);
        holder.setTitle(
                mContext.getString(R.string.title_time, MiscUtility.getDateInFormat(info.getDate())));
        holder.setSummary(mContext.getString(R.string.summary_location,info.getDescription()));
    }

    private String modifyResolution(String imgUrl) {
        String tempUrl = imgUrl;
        int width = DevicesManager.getDeviceWidth((Activity) mContext);
        Log.d("TEST","width = " + width);
        if (width >= 720) {
            tempUrl = tempUrl.replace("1920x1080","480x360");
        } else {
            tempUrl = tempUrl.replace("1920x1080","320x240");
        }
        return tempUrl;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
