package com.example.musicplayerapp;


import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PageController extends FragmentPagerAdapter {
    int tabCounts;

    public PageController(FragmentManager fm, int tabCounts) {
        super(fm);
        this.tabCounts = tabCounts;
    }

    @Override
    public androidx.fragment.app.Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new FragmentNowPlaying();
            case 1:
                return new FragmentPlaylists();
            case 2:
                return new FragmentSongs();
            case 3:
                return new FragmentArtists();
            case 4:
                return new FragmentAlbums();
            case 5:
                return new FragmentGenre();
            default:
                return null;


        }
    }

    @Override
    public int getCount() {
        return tabCounts;
    }
}
