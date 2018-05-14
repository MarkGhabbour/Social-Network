package com.example.android.socialnetwork;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.socialnetwork.packagefordb.UserHelper;
import java.util.ArrayList;

import com.example.android.socialnetwork.packagefordb.usercontract.userEntry;

public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView sign_up=(TextView)findViewById(R.id.sign_up); // Navigate to choose username , passwords
        TextView admin = (TextView)findViewById(R.id.admin);  // Navigate to show all users
        TextView log_in=(TextView)findViewById(R.id.log_in);  // Navigate to Enter id/username
    }

    public static ArrayList<user> users = new ArrayList<>();






    public void ne(View view) {
        Intent n = new Intent(this , signup.class);
        startActivity(n);
    }
    public void gotoLogin(View view){
        Intent n  = new Intent(this,Login.class);
        startActivity(n);
    }
    public void gotoAdmin(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Security Check");
        builder.setMessage("Enter Admin Password");

// Set up the input
        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String m_Text = input.getText().toString();
                if(m_Text.equals("admin"))
                    gotoAdmin2();
                else
                    Toaster();

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

    public void gotoAdmin2()
    {
        Intent n = new Intent(this,admin.class);
        startActivity(n);
    }
    public void Toaster()
    {
        Toast.makeText(this,"Wrong Password",Toast.LENGTH_SHORT).show();
    }

}
