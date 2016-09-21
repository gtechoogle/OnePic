package com.gtechoogle.wallpaper.bing.view.listcardview;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gtechoogle.app.utility.MiscUtility;
import com.gtechoogle.wallpaper.bing.R;
import com.gtechoogle.wallpaper.bing.view.FullWallpaperActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class WallpaperCardViewAdapter extends RecyclerView.Adapter<WallpaperCardViewHolder> {
    private static final String TAG = "WallpaperCardViewAdapter";
    private Context mContext;
    private List<WallpaperCardViewInfo> mList;

    public WallpaperCardViewAdapter(Context context, List<WallpaperCardViewInfo> data) {
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
        final WallpaperCardViewInfo info = mList.get(position);
        ImageView imageView = holder.getImageView();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(mContext, FullWallpaperActivity.class);
                intent.putExtra("url", info.imgUrl);
                mContext.startActivity(intent);
            }
        });
        Picasso.with(mContext).load(modifyResolution(info.imgUrl)).into(imageView);
        holder.setTitle(
                mContext.getString(R.string.title_time, MiscUtility.getDateInFormat(info.date)));
        holder.setSummary(mContext.getString(R.string.summary_location,info.location));
    }

    private String modifyResolution(String imgUrl) {
        return imgUrl;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
