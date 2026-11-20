package com.rachapung.phonemod.client;

public class ShortsVideo {
    public final String title;
    public final String channel;
    public final String url;
    public final int themeColorTop;
    public final int themeColorBottom;
    public int likes;
    public boolean liked;

    public ShortsVideo(String title, String channel, String url, int themeColorTop, int themeColorBottom, int likes) {
        this.title = title;
        this.channel = channel;
        this.url = url;
        this.themeColorTop = themeColorTop;
        this.themeColorBottom = themeColorBottom;
        this.likes = likes;
        this.liked = false;
    }
}
