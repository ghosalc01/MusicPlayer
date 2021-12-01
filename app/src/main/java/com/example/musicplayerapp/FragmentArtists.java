package com.example.musicplayerapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SeekBar;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentArtists extends Fragment {

    private static final int UPDATE_FREQUENCY = 500;
    private static final int STEP_VALUE = 5000;
    private static final int CALL = 872;
    static MediaPlayer mp = null;
    private final Handler handler = new Handler();

    private SeekBar seekbar;
    private MediaPlayer player;
    private RecyclerView recyclerView;
    private MyAdapterForArtists adapter;
    private List<Music> musicList;


    public FragmentArtists() {
        // Required empty public constructor
    }

    private void sortMusicList() {                              // how the list is sorted
        Collections.sort(musicList, new Comparator<Music>() {
            @Override
            public int compare(Music o1, Music o2) {
                return o1.getArtists().compareTo(o2.getArtists());
            }
        });

        if (adapter != null) {
            adapter.notifyDataSetChanged();

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_artists, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public void onViewCreated() {

        recyclerView = recyclerView.findViewById(R.id.artistlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        int permissionCheck = ContextCompat.checkSelfPermission(requireActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            setupUI();
        } else {

            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    },
                    CALL);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void setupUI() {

        player = new MediaPlayer();
        musicList = new MusicManager().getMusic(getActivity().getContentResolver());
        sortMusicList();

        adapter = new MyAdapterForArtists(musicList);

        adapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        {

            recyclerView.setAdapter(adapter);

        }

    }
}
