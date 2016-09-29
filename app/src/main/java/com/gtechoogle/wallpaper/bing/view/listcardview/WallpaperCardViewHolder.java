package com.gtechoogle.wallpaper.bing.view.listcardview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gtechoogle.wallpaper.bing.R;


public class WallpaperCardViewHolder extends RecyclerView.ViewHolder {
    private ImageView mImage;
    private TextView mTitle;
    private TextView mSummary;

    public WallpaperCardViewHolder(View itemView) {
        super(itemView);
        mImage = (ImageView) itemView.findViewById(R.id.thumbnail);
        mTitle = (TextView) itemView.findViewById(R.id.cardview_title);
        mSummary = (TextView) itemView.findViewById(R.id.cardview_summary);
    }
    public ImageView getImageView() {
        return mImage;
    }

    public void setTitle(String title) {
        mTitle.setText(title);
    }

    public void setSummary(String summary) {
        mSummary.setText(summary);
    }
}
