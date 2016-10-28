package com.gtechoogle.wallpaper.bing.wallpaperlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gtechoogle.app.utility.NetworkManager;
import com.gtechoogle.wallpaper.bing.R;
import com.gtechoogle.wallpaper.bing.ad.AdManager;
import com.gtechoogle.wallpaper.bing.services.ChangeWallpaperService;
import com.gtechoogle.wallpaper.bing.view.listcardview.WallpaperCardViewAdapter;

public class WallpaperListActivity extends AppCompatActivity implements IWallpaperView{
    private static final String TAG = "WallpaperListActivity";
    private RecyclerView mRecyclerView;
    private AdManager mAdManager;
    private ProgressBar mLoading;
    private WallpaperListPresenter mPresent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initVariable();
        mPresent = new WallpaperListPresenter(this);
        mPresent.initAdapter(this);
        startService(new Intent(this, ChangeWallpaperService.class));
    }

    private void initVariable() {
        mLoading = (ProgressBar) findViewById(R.id.progress_loading);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        setupRecyclerView();
        mAdManager = new AdManager(this);
        mAdManager.initAd();
    }

    private void setupRecyclerView() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!NetworkManager.isNetworkAvailable(this)) {
            Toast toast = Toast.makeText(this,R.string.no_connection, Toast.LENGTH_LONG);
            toast.show();
        }
        mAdManager.showAd();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAdManager.cancelAd();
    }

    @Override
    public void showLoading(boolean show) {
        mLoading.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void bindAdapter(WallpaperCardViewAdapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.settings) {
//            Intent intent = new Intent(this, SettingsActivity.class);
//            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
