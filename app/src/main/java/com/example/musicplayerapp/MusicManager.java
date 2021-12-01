package com.example.musicplayerapp;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore;

import java.util.LinkedList;
import java.util.List;

public class MusicManager {

    public List<Music> getMusic(ContentResolver contentResolver) {

        List<Music> returnedList = new LinkedList<>();

        final Cursor cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();

            do {
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.TITLE));
                String file = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
                String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST));
                String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM));
                long durationInMs = Long.parseLong(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DURATION)));
                Music music = new Music(title, file, artist, durationInMs, album);
                returnedList.add(music);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return returnedList;
    }
}