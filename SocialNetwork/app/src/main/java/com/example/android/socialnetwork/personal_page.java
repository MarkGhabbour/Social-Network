package com.example.android.socialnetwork;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class personal_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_page);
    }


    /* WE will rake an integer which is the id of the user to determine
        which personal page we will open   */



    public static int this_user_id = signup.id ;
    public static String user2 = signup.user ;

    public void view_friends_posts(View view){

        // open new page -> friends_posts
        Intent intent = new Intent(this , friends_posts.class);
        startActivity(intent);
    }


    public void WritePost (View view){
        Intent intent = new Intent(this , writepost.class) ;
        startActivity(intent);
    }


}