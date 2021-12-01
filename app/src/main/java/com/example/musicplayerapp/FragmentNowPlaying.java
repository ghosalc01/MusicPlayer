package com.example.musicplayerapp;

import static android.R.drawable.ic_media_play;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class FragmentNowPlaying extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int UPDATE_FREQUENCY = 500;
    private static final int STEP_VALUE = 5000;
    private static final int CALL = 872;
    static MediaPlayer mp = null;
    private final Handler handler = new Handler();
    private final MediaPlayer.OnErrorListener onError = new MediaPlayer.OnErrorListener() {

        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {

            return false;
        }
    };
    View v;
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private List<Music> musicList;
    private MediaPlayer player;
    private TextView selectedFile;
    private TextView selectedArtist;
    private SeekBar seekbar;
    private final Runnable updatePositionRunnable = new Runnable() {
        public void run() {
            updatePosition();
        }
    };
    private ImageButton playButton;
    private ImageButton prevButton;
    private ImageButton nextButton;
    private ImageButton repeat_oneButton;
    private ImageButton shuffleButton;
    private boolean isStarted = true;
    private int currentPosition;
    private final MediaPlayer.OnCompletionListener onCompletion = new MediaPlayer.OnCompletionListener() {

        @Override
        public void onCompletion(MediaPlayer mp) { // what to do when a song is finished

            stopPlay();
            startPlay(currentPosition + 1);
        }

    };
    private boolean isMovingSeekBar = false;
    private final SeekBar.OnSeekBarChangeListener seekBarChanged = new SeekBar.OnSeekBarChangeListener() {        //seekbar code
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            isMovingSeekBar = false;
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            isMovingSeekBar = true;
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (isMovingSeekBar) {
                player.seekTo(progress);

                Log.i("OnSeekBarChangeListener", "onProgressChanged");
            } else {
                TextView duration = getView().findViewById(R.id.song_duration);
                duration.setText(String.valueOf(progress));
                long totalSeconds = TimeUnit.MILLISECONDS.toSeconds(progress);
                long minss = totalSeconds / 60;
                long seconds = totalSeconds % 60;

                duration.setText(String.format(Locale.UK, "%02d:%02d", minss, seconds));
            }
        }
    };
    private boolean isShuffle = false;                      // main controls
    private final View.OnClickListener onButtonClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            switch (v.getId()) {

                case R.id.play:     // How the user can control how they listen to their playlist
                    if (player.isPlaying()) {
                        handler.removeCallbacks(updatePositionRunnable);
                        player.pause();
                        playButton.setImageResource(android.R.drawable.ic_media_play);
                    } else {
                        if (isStarted) {
                            player.start();
                            playButton.setImageResource(android.R.drawable.ic_media_pause);

                            updatePosition();
                        } else {
                            startPlay(currentPosition);
                        }
                    }

                    break;

                case R.id.prev:
                    startPlay(currentPosition - 1);


                    break;


                case R.id.next:
                    startPlay(currentPosition + 1);

                    break;


                case R.id.shuffle:
                    if (isShuffle) {
                        Collections.shuffle(musicList);
                        adapter.notifyDataSetChanged();
                        shuffleButton.setColorFilter(-16776961);
                    } else {
                        sortMusicList();
                        shuffleButton.setColorFilter(-16777216);
                    }

                    isShuffle = !isShuffle;
                    break;

                case R.id.repeat_one: {
                    player.setLooping(true);
                    repeat_oneButton.setImageResource(R.drawable.ic_repeat_one_black_24dp);
                    repeat_oneButton.setColorFilter(-16776961);

                }

            }

        }
    };

    public FragmentNowPlaying() {
        // Required empty public constructor
    }

    public static FragmentNowPlaying newInstance(String param1, String param2) {
        FragmentNowPlaying fragment = new FragmentNowPlaying();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private void sortMusicList() {                              // how its sorted
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nowplaying, container, false);


    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        int permissionCheck = ContextCompat.checkSelfPermission(requireActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                setupUI();
            }
        } else {

            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    },
                    CALL);
        }
    }

    private void startPlay(int position) {
        if (position < 0) {
            position = 0;
        }
        if (position >= musicList.size()) {
            position = musicList.size() - 1;
        }
        String file = musicList.get(position).getFile();
        String title = musicList.get(position).getTitle();
        String artists = musicList.get(position).getArtists();

        currentPosition = position;
        if (artists != null) {
            selectedArtist.setText(artists);
        }
        if (title != null) {
            selectedFile.setText(title);
        }


        seekbar.setProgress(0);
        updatePosition();

        player.stop();
        player.reset();

        try {
            player.setDataSource(file);
            player.prepare();
            player.start();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        seekbar.setMax(player.getDuration());
        playButton.setImageResource(android.R.drawable.ic_media_pause);


        isStarted = true;
    }

    private void stopPlay() {
        player.stop();
        player.reset();
        playButton.setImageResource(ic_media_play);
        handler.removeCallbacks(updatePositionRunnable);

        isStarted = false;
    }

    private void updatePosition() {
        handler.removeCallbacks(updatePositionRunnable);

        seekbar.setProgress(player.getCurrentPosition());

        handler.postDelayed(updatePositionRunnable, UPDATE_FREQUENCY);

    }


    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void setupUI() {        // what to do when the program starts
        player = new MediaPlayer();


        musicList = new MusicManager().getMusic(Objects.requireNonNull(getContext()).getContentResolver());
        sortMusicList();

        adapter = new MyRecyclerViewAdapter(musicList);

        recyclerView.setAdapter(adapter);


    }

}
