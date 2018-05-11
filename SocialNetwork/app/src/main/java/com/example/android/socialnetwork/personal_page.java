package com.example.android.socialnetwork;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.example.android.socialnetwork.packagefordb.UserHelper;
import com.example.android.socialnetwork.packagefordb.usercontract.userEntry;
public class personal_page extends AppCompatActivity {

    private UserHelper userDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_page);
        userDb = new UserHelper(this);
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

    public void viewInfo(View view)
    {
        SQLiteDatabase info = userDb.getReadableDatabase();
        Cursor res = info.rawQuery("Select * from "+ userEntry.TABLE_NAME,null);
        res.moveToLast();
        if(res.getCount()==0){
            showMessage("Error","Nothing found");
            return;
        }
        else{
            StringBuffer buffer = new StringBuffer();
            buffer.append("Id :"+ res.getString(0)+"\n");
            buffer.append("Name :"+ res.getString(1)+"\n");
            buffer.append("Number of friends :"+ res.getInt(2)+"\n");
            buffer.append("password :"+ res.getString(4)+"\n\n");
            showMessage("Your account information",buffer.toString());
        }


    }
    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}