package com.example.android.socialnetwork;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.android.socialnetwork.packagefordb.UserHelper;
import com.example.android.socialnetwork.packagefordb.PostHelper;
import com.example.android.socialnetwork.packagefordb.usercontract;
import com.example.android.socialnetwork.packagefordb.usercontract.userEntry ;
import com.example.android.socialnetwork.packagefordb.usercontract.PostEntry ;


public class user_page_in_admin_mode extends AppCompatActivity {

    public static int id ;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__page_in_admin_mode);
    }


    public void delete_user(View view){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this) ;
        String title = "You are about to delete a user !" ;
        String message = "Are you sure you want to delete this user from network ?" ;
        alertDialog.setMessage(message) ;
        alertDialog.setTitle(title) ;
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                erase_user();
            }
        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                do_nothing();
            }
        });
        alertDialog.show();
    }

    public void erase_user(){
        String id_of_the_erased_user = String.valueOf(id);
        UserHelper userHelper = new UserHelper(this) ;
        SQLiteDatabase sql = userHelper.getWritableDatabase() ;
        sql.delete(userEntry.TABLE_NAME , userEntry._ID + " = " +id_of_the_erased_user , null ) ;

        // Delete all posts of that user
        PostHelper postHelper = new PostHelper(this) ;
        SQLiteDatabase sql2 = postHelper.getWritableDatabase();
        sql2.delete(usercontract.PostEntry.TABLE_NAME , usercontract.PostEntry.COULMN_userid+" = "+id_of_the_erased_user,null);

        Toast.makeText(this,"The user is deleted ",Toast.LENGTH_LONG).show();

        // go to previous page of all users again
        personal_page.this_user_id = id ;
        Intent intent = new Intent(this , admin.class) ;
        startActivity(intent);
        finish(); // this fn returns shuts current intent and return to its parent.
        //startActivity(intent);
    }

    public void do_nothing(){
        return;
    }

    public void delete_content(View view){
        PostHelper postHelper = new PostHelper(this);
        SQLiteDatabase sql = postHelper.getReadableDatabase() ;
        String id_of_the_erased_user = String.valueOf(id);

        String [] projection = {PostEntry.COLUMN_postid , PostEntry.COLUMN_likes , PostEntry.COULMN_POST} ;

        String selection = PostEntry.COULMN_userid+"=?";
        String [] selectionargs = {id_of_the_erased_user} ;

        Cursor c = sql.query(PostEntry.TABLE_NAME , projection , selection, selectionargs, null ,null , null);

        if(c.getCount()>0) {
            Intent intent = new Intent(this, user_wall_for_admin.class);
            startActivity(intent);
        }
        else
            Toast.makeText(this , "This user has no posts " ,Toast.LENGTH_LONG).show();
    }

}
