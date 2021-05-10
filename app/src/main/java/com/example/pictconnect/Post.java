package com.example.pictconnect;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class Post implements Serializable {

    String post_id;
    int type;
    String content;
    String title;
    String subheading;
    String date;
    int likes;
    String user_id;

    public Post(String post_id, int type, String content, String title, String subheading, String date, int likes, String user_id) {
        this.post_id = post_id;
        this.type = type;
        this.content = content;
        this.title = title;
        this.subheading = subheading;
        this.date = date;
        this.likes = likes;
        this.user_id = user_id;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubheading() {
        return subheading;
    }

    public void setSubheading(String subheading) {
        this.subheading = subheading;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
