package com.example.android.socialnetwork;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.android.socialnetwork.packagefordb.PostHelper;
import com.example.android.socialnetwork.packagefordb.UserHelper;
import com.example.android.socialnetwork.packagefordb.usercontract;
import com.example.android.socialnetwork.packagefordb.usercontract.userEntry;
import com.example.android.socialnetwork.packagefordb.usercontract.PostEntry;

import java.util.ArrayList;

public class friends_posts extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_posts);
      // show_no();
        show_posts();
    }

    /* This is a function to check that info of user was transferred correctly
    public void show_no(){
        TextView t = (TextView)findViewById(R.id.show_number);
        t.setText(""+personal_page.this_user_id);
    }
    */



    public void show_posts(){
        UserHelper userHelper = new UserHelper(this) ;
        SQLiteDatabase ins = userHelper.getReadableDatabase();
        String[] proj ={userEntry._ID , userEntry.COULMN_UserName , userEntry.COULMN_friends, userEntry.COULMN_number_friends
                ,userEntry.COULMN_password};
        Cursor c = ins.query(usercontract.userEntry.TABLE_NAME , proj , null,null,null,null,null ) ;
        c.moveToLast() ;
        String friends = c.getString(c.getColumnIndex(userEntry.COULMN_friends)) ;

        String sel = "=?" ;
        String selargs[] ;
        // Parse friends strings to extract ids of user friends
        String []friends_list = friends.split(",");

        ArrayList<String>all_posts = new ArrayList<>();

        for(String s : friends_list){
            // loop where friends_list[i] will be in s and so on
            int id_of_user_friend = Integer.parseInt(s) ;
            selargs = new String[]{String.valueOf(id_of_user_friend)} ;
            // Then Search the post tablr for the id of that friend and get the post then push it into
            // the ArrayList all_posts then to arrayadapter then show on screen
            PostHelper postHelper = new PostHelper(this);
            SQLiteDatabase sqLiteDatabase = postHelper.getReadableDatabase() ;


        }
    }

}
