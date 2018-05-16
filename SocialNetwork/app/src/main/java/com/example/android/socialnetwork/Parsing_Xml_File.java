
package com.example.android.socialnetwork;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Parsing_Xml_File extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xml_file  );
    }
    TextView txt= (TextView) findViewById(R.id.textView);
    public void txt()
    {
        fileStream fs=new fileStream();
        txt.setText(fs.get_string());
    }
}