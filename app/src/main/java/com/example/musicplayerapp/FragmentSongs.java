package com.example.musicplayerapp;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FragmentSongs extends androidx.fragment.app.Fragment {


    private static final int UPDATE_FREQUENCY = 500;
    private static final int STEP_VALUE = 5000;


    private RecyclerView Songlist;
    private List<Music> musicList;
    private MyAdapterForSongs myAdapterForSongs;


    public FragmentSongs() {
    }

    private void sortMusicList() {                              // how its sorted
        Collections.sort(musicList, new Comparator<Music>() {
            @Override
            public int compare(Music o1, Music o2) {
                return o1.getArtists().compareTo(o2.getArtists());
            }
        });

        if (myAdapterForSongs != null) {
            myAdapterForSongs.notifyDataSetChanged();

        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_songs, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //recycler
        Songlist = view.findViewById(R.id.songlist);
        Songlist.setLayoutManager(new LinearLayoutManager(getActivity()));
        musicList = new ArrayList<>();
        if (musicList.equals(Collections.emptyList()))


            // define an adapter
            myAdapterForSongs = new MyAdapterForSongs(musicList, getContext());
        Songlist.setAdapter(myAdapterForSongs);
    }


    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void setupUI() {
        musicList = new MusicManager().getMusic(getContext().getContentResolver());
        sortMusicList();
        myAdapterForSongs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }


}
