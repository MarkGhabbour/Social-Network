package com.example.android.socialnetwork;

import java.util.Vector;

/**
 * Created by Martin on 5/1/2018.
 */

public class user {
    String name;
    Vector<user>friends;
    Vector<post>posts;

    public String getName() {
        return name;
    }

    public post get_specific_post(int no){
        if(no>=posts.size()||no<0){
            post po = new post();
            return po;
        }
        else
            return posts.get(no);
    }


    public user get_specific_friend(int no){
        return friends.get(no);
    }

    public void setName(String name) {
        this.name = name;
    }

    public Vector<user> getFriends() {
        return friends;
    }

    public void setFriends(Vector<user> friends) {
        this.friends = friends;
    }

    public Vector<post> getPosts() {
        return posts;
    }

    public void setPosts(Vector<post> posts) {
        this.posts = posts;
    }

    public user(){
        name="";
    }

    public user(String name, Vector<user> friends, Vector<post> posts) {
        this.name = name;
        this.friends = friends;
        this.posts = posts;
    }
}