package com.example.musicplayerapp;

/**
 * Created by sky on 19/07/2017.
 */

public class Music {

    private String title;
    private String file;
    private String artists;
    private long durationInMs;
    private String albums;
    private String genres;


    public Music(String title, String file, String artists, long durationInMs, String albums) {
        this.title = title;
        this.file = file;
        this.artists = artists;
        this.durationInMs = durationInMs;
        this.albums = albums;
        this.genres = genres;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getArtists() {
        return artists;
    }

    public void setArtists(String artists) {
        this.artists = artists;
    }

    public long getDurationInMs() {
        return durationInMs;
    }

    public void setDurationInMs(long durationInMs) {
        this.durationInMs = durationInMs;
    }

    public String getAlbums() {
        return albums;
    }

    public void setAlbums(String albums) {
        this.albums = albums;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }
}
