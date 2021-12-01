package com.example.musicplayerapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapterForGenres extends RecyclerView.Adapter<MyAdapterForGenres.MyViewHolder> {

    private final List<Music> mMusicList;
    private AdapterView.OnItemClickListener mListener;
    private int highlighted = 01;

    public MyAdapterForGenres(List<Music> musicList) {
        mMusicList = musicList;
    }

    @Override
    public MyAdapterForGenres.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rvgenre, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterForGenres.MyViewHolder holder, final int position) {
        holder.setGenres(mMusicList.get(position).getGenres());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onItemClick(null, null, position, 0);
                }
            }
        });
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public int getItemCount() {
        return mMusicList.size();
    }

    public void highlist(int position) {
        highlighted = position;

    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View itemView) {
            super(itemView);
        }

        public void setGenres(String text) {
            ((TextView) itemView.findViewById(R.id.genre)).setText(text);
        }

    }
}


