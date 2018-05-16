package com.example.android.socialnetwork;

import android.content.Context;

import java.io.FileOutputStream;
import java.io.IOException;

public class fileStream {
    String to;
    public void writeToFile(Context context, String file_name,String result )
    {
        try {
            FileOutputStream fos = context.openFileOutput(file_name, Context.MODE_PRIVATE);
            fos.write(result.getBytes(), 0, result.length());
            fos.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }
    public void set_string(String s)
    {
        to=s;
    }
    public String get_string()
    {
        return to;
    }
}
