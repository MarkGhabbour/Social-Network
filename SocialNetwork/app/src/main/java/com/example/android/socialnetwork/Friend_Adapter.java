package com.example.android.socialnetwork;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.socialnetwork.packagefordb.UserHelper;
import com.example.android.socialnetwork.packagefordb.usercontract;
import com.example.android.socialnetwork.packagefordb.usercontract.userEntry;

import java.util.ArrayList;

import static com.example.android.socialnetwork.personal_page.this_user_id;


public class Friend_Adapter extends ArrayAdapter<user> {


    public Friend_Adapter(@NonNull Context context, ArrayList<user> userList) {
        super(context, R.layout.search_result, userList);

    }
    public void  Add(View view)
    {

    }


    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
        LayoutInflater myCustomInflater = LayoutInflater.from(getContext());
        View customView = myCustomInflater.inflate(R.layout.search_result, parent, false);
        // get references.
        user singleUser = getItem(position);
        TextView itemText = (TextView) customView.findViewById(R.id.item_text);
        TextView smallItemText = (TextView) customView.findViewById(R.id.item_small_text);
        TextView Frnd= (TextView) customView.findViewById(R.id.Friend) ;
        Button btn = (Button) customView.findViewById(R.id.btnFriend);
        ImageView buckysImage = (ImageView) customView.findViewById(R.id.my_profile_image);
        // dynamically update the text from the array
        itemText.setText(singleUser.name);

        UserHelper Helper=new UserHelper(getContext());
        SQLiteDatabase sqLiteDatabase = Helper.getReadableDatabase() ;

        String[]  projection = {userEntry.COULMN_friends };
        String selection = userEntry._ID+"=?" ;
        String[] selectionargs ={String.valueOf(this_user_id)} ;

        Cursor c = sqLiteDatabase.query(userEntry.TABLE_NAME , projection , selection , selectionargs , null , null, null  ) ;
   // Cursor c;
        c.moveToFirst() ;
        String Friends= c.getString(c.getColumnIndex(userEntry.COULMN_friends));
        if (Friends.contains(String.valueOf(singleUser._Id))) {

            btn.setVisibility(View.INVISIBLE);
            Frnd.setVisibility(View.VISIBLE);

        }
        else {

            Frnd.setVisibility(View.INVISIBLE);
            btn.setVisibility(View.VISIBLE);

        }
        smallItemText.setText("Has "+singleUser.no_of_friends+" friends");
        // using the same image every time
        buckysImage.setImageResource(R.drawable.icons8_account_96);
        // Now we can finally return our custom View or custom item
        return customView;
    }

}
