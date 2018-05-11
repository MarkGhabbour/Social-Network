package com.example.android.socialnetwork;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.socialnetwork.packagefordb.UserHelper;
import com.example.android.socialnetwork.packagefordb.usercontract.userEntry;

public class Login extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void log_in2 (View view){

        // We will take username and password and search the users table for this id and then change
        //global variable id in signup.java as it is passed to the user's page (personal_page)
        EditText username = (EditText)findViewById(R.id.username);
        EditText password = (EditText)findViewById(R.id.password);

        String name = username.getText().toString() ;
        String pass = password.getText().toString() ;

        String [] projection = { userEntry._ID , userEntry.COULMN_UserName , userEntry.COULMN_password , userEntry.COULMN_number_friends,
                userEntry.COULMN_friends};
        String selection = userEntry.COULMN_UserName+"=?" + " AND " + userEntry.COULMN_password+"=?" ;
        String [] selectionargs = {name , pass} ;

        UserHelper userHelper = new UserHelper(this) ; ;
        SQLiteDatabase ins = userHelper.getReadableDatabase();
        Cursor c = ins.query(userEntry.TABLE_NAME , projection , selection , selectionargs , null , null , null , null);
        c.moveToFirst();

        // update signup.id global variable
        signup.id = c.getInt(c.getColumnIndex(userEntry._ID)) ;

        Intent intent = new Intent(this , personal_page.class) ;
        startActivity(intent);

    }
}
