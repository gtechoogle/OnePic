package com.gtechoogle.app.utility;

import android.content.Context;
import android.content.Intent;

/**
 * Created by edy on 2016/9/10.
 */

public class ShareFunction {
    private Context mContext;
    public ShareFunction (Context context) {
        mContext = context;
    }
    //分享文字
    public void shareText() {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "This is my Share text.");
        shareIntent.setType("text/plain");
        //设置分享列表的标题，并且每次都显示分享列表
        mContext.startActivity(Intent.createChooser(shareIntent, "分享到"));
    }
}
