package com.example.android.socialnetwork;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.socialnetwork.packagefordb.PostHelper;
import com.example.android.socialnetwork.packagefordb.UserHelper;
import com.example.android.socialnetwork.packagefordb.UserHelper;
import com.example.android.socialnetwork.packagefordb.usercontract.userEntry;

import com.example.android.socialnetwork.packagefordb.usercontract.PostEntry;

import java.util.ArrayList;

public class myWall extends AppCompatActivity {
    private PostHelper postHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wall);
        postHelper = new PostHelper(this);
        show_my_posts();
    }

    public void show_my_posts() {


        String id = String.valueOf(personal_page.this_user_id);

        String[] projection = {PostEntry.COLUMN_postid, PostEntry.COLUMN_likes, PostEntry.COULMN_POST};
        String selection = PostEntry.COULMN_userid + " = ?";
        String[] selectionargs = {id};
        SQLiteDatabase sql = postHelper.getReadableDatabase();
        Cursor c = sql.query(PostEntry.TABLE_NAME, projection, selection, selectionargs, null, null, PostEntry.COLUMN_postid + " DESC", null);
        // Get all posts of that user into an arraylist

        final ArrayList<post> my_posts = new ArrayList<>();

        int n = c.getCount();

        c.moveToFirst();
        int i = 0;
        while (i < n) {
            post a = new post(c.getString(c.getColumnIndex(PostEntry.COULMN_POST)), c.getInt(c.getColumnIndex(PostEntry.COLUMN_likes))
                    , c.getInt(c.getColumnIndex(PostEntry.COLUMN_postid)));
            my_posts.add(a);
            c.moveToNext();
            i++;
        }
        // Pass them to arrayadapter then to screen
        ListAdapter customListAdapter = new PostAdapter(this, my_posts);// Pass the array to the constructor.
        ListView customListView = (ListView) findViewById(R.id.wall_list_view);
        customListView.setAdapter(customListAdapter);
        customListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //       String postBody = String.valueOf(parent.getItemAtPosition(position));
                        //      Toast.makeText(myWall.this, postBody, Toast.LENGTH_LONG).show();
                        view_deletion_message(my_posts.get(position).getId_of_the_post());
                    }
                }
        );

    }

    public void view_deletion_message(final int position){
        AlertDialog.Builder make_sure = new AlertDialog.Builder(this) ;
        String title = "Post Deletion" ;
        String body = "Are you sure you want to delete this post ? ";
        make_sure.setTitle(title) ;
        make_sure.setMessage(body) ;
      make_sure.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
              position2 = position ;
              remove_post();
          }
      });

        make_sure.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dont_remove();
            }
        });
        make_sure.show() ;
    }

    public int position2 ;
    public void remove_post(){

        PostHelper postHelper = new PostHelper(this);
        SQLiteDatabase ins =postHelper.getWritableDatabase();
        ins.delete(PostEntry.TABLE_NAME , PostEntry.COLUMN_postid+"="+String.valueOf(position2),
                        null ) ;

        Toast.makeText(this , "Post has been deleted   " + String.valueOf(position2) , Toast.LENGTH_LONG).show();

     // Show posts again


        Intent intent = new Intent(this  , myWall.class);
        startActivity(intent);
        finish();

    }

    public void dont_remove(){
        //Toast.makeText(this , "Post has " , Toast.LENGTH_LONG).show();
        return;}


}






