package com.example.android.socialnetwork;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.socialnetwork.packagefordb.PostHelper ;
import com.example.android.socialnetwork.packagefordb.usercontract.PostEntry ;


public class writepost extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writepost);
    }

    public  void AddPost (View view){
        EditText post = (EditText)findViewById(R.id.new_post) ;
        String post_body = post.getText().toString() ;

        //Add post to posts table
        PostHelper postHelper = new PostHelper(this) ;
        SQLiteDatabase sql = postHelper.getWritableDatabase() ;

        ContentValues c = new ContentValues();
        c.put(PostEntry.COULMN_POST , post_body);
        c.put(PostEntry.COULMN_userid ,personal_page.this_user_id);
        c.put(PostEntry.COLUMN_likes , 0);

        sql.insert(PostEntry.TABLE_NAME , null , c) ;

        //Now display a toast message and return automatically to personal_page file

        Context context = getApplicationContext();
        CharSequence text = "The post is added to your Timeline";
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        // Now return back to his personal page
        Intent intent = new Intent(this , personal_page.class);
        startActivity(intent);

    }


}