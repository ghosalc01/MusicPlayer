package com.example.musicplayerapp;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toolbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity {

    private static final int UPDATE_FREQUENCY = 500;
    private static final int STEP_VALUE = 5000;
    private static final int CALL = 872;
    private final Handler handler = new Handler();
    private final Runnable updatePositionRunnable = new Runnable() {
        public void run() {
            updatePosition();
        }
    };
    TabLayout tabs;
    Toolbar toolbar;
    TabItem playlist;
    TabItem allsongs;
    TabItem artists;
    TabItem albums;
    TabItem genre;
    TabItem nowplaying;
    ViewPager viewPager;
    PageController pageController;
    private MediaPlayer player;

    private void updatePosition() {
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setActionBar(toolbar);
        getActionBar().setTitle("Music Player");

        tabs = findViewById(R.id.tabs);
        playlist = findViewById(R.id.playlist);
        artists = findViewById(R.id.artists);
        allsongs = findViewById(R.id.allsongs);
        albums = findViewById(R.id.albums);
        genre = findViewById(R.id.genre);
        viewPager = findViewById(R.id.view_pager);
        nowplaying = findViewById(R.id.nowplaying);

        pageController = new PageController((getSupportFragmentManager()), tabs.getTabCount());
        viewPager.setAdapter(pageController);

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
    }

}
