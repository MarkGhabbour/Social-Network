package com.example.android.socialnetwork;

import java.util.Vector;

/**
 * Created by Martin on 5/1/2018.
 */

public class post {
    String content;
    int no_likes;
    Vector<user>liked;

    public post(){
        content = "";
        no_likes = -1;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getNo_likes() {
        return no_likes;
    }

    public void setNo_likes(int no_likes) {
        this.no_likes = no_likes;
    }

    public Vector<user> getLiked() {
        return liked;
    }

    public void setLiked(Vector<user> liked) {
        this.liked = liked;
    }
}
