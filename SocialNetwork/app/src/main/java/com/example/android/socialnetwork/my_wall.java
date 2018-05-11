package com.example.android.socialnetwork;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.socialnetwork.packagefordb.PostHelper;
import com.example.android.socialnetwork.packagefordb.UserHelper;
import com.example.android.socialnetwork.packagefordb.UserHelper;
import com.example.android.socialnetwork.packagefordb.usercontract.userEntry;

import com.example.android.socialnetwork.packagefordb.usercontract.PostEntry;

import java.util.ArrayList;

public class my_wall extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wall);
        show_my_posts();
    }

    public void show_my_posts(){
        PostHelper postHelper = new PostHelper(this) ;
        SQLiteDatabase sql= postHelper.getReadableDatabase();

        String id = String.valueOf(personal_page.this_user_id);

        String [] projection = {PostEntry.TABLE_NAME , PostEntry.COLUMN_postid , PostEntry.COLUMN_likes , PostEntry.COULMN_POST} ;
        String selection = PostEntry.COULMN_userid+"=?";
        String [] selectionargs = {id} ;
        Cursor c = sql.query(userEntry.TABLE_NAME ,projection,selection,selectionargs,null,null,
                                PostEntry.COLUMN_postid + " DES" );

        // Get all posts of that user into an arraylist

        ArrayList<post>my_posts=new ArrayList<>();

        int n = c.getCount() ;

        c.moveToFirst();
        int i = 0 ;
        while(i<n) {
            post a = new post(c.getString(c.getColumnIndex(PostEntry.COULMN_POST)), c.getInt(c.getColumnIndex(PostEntry.COLUMN_likes)));
            my_posts.add(a);
            i++;
        }
    }

    // Pass them to arrayadapter then to screen

}
