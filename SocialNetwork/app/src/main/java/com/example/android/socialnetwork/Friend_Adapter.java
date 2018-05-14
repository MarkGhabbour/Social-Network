package com.example.android.socialnetwork;

import android.content.ContentValues;
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

import com.example.android.socialnetwork.packagefordb.UserHelper;
import com.example.android.socialnetwork.packagefordb.usercontract;
import com.example.android.socialnetwork.packagefordb.usercontract.userEntry;

import java.util.ArrayList;

import static com.example.android.socialnetwork.personal_page.this_user_id;


public class Friend_Adapter extends ArrayAdapter<user> {


    public Friend_Adapter(@NonNull Context context, ArrayList<user> userList) {
        super(context, R.layout.search_result, userList);

    }

     public  void update(user singleUser,TextView txt)
     {

         UserHelper userHelper = new UserHelper(getContext()) ;
         UserHelper userHelper2 = new UserHelper(getContext()) ;
         SQLiteDatabase sql = userHelper.getWritableDatabase();
         SQLiteDatabase sql2 = userHelper2.getReadableDatabase() ;
         ContentValues contentValues = new ContentValues();
         String[] proj = {userEntry.COULMN_friends,userEntry.COULMN_number_friends };
         String select = userEntry._ID+"=?" ;
         String[] selectionarg ={String.valueOf(this_user_id)} ;

         //get the fridns string and friends number of the searching user

         Cursor C = sql2.query(userEntry.TABLE_NAME , proj , select , selectionarg , null , null, null  ) ;
         C.moveToFirst() ;
         String Friend= C.getString(C.getColumnIndex(userEntry.COULMN_friends));

         //Add the id of the user he added to his friend string
         if(Friend=="")
         {
             Friend=String.valueOf(singleUser._Id);
         }
         else
         {
             Friend=Friend+","+String.valueOf(singleUser._Id);
         }

         contentValues.put(userEntry.COULMN_friends,Friend);
         String Number= C.getString(C.getColumnIndex(userEntry.COULMN_number_friends));
         int result = Integer.parseInt(Number);
         result++;
         contentValues.put(userEntry.COULMN_number_friends,String.valueOf(result));
         sql .update(userEntry.TABLE_NAME, contentValues, userEntry._ID+"=" +String.valueOf(this_user_id) , null);



         //starting searching the added user strings
         UserHelper Help = new UserHelper(getContext()) ;
         UserHelper Help2 = new UserHelper(getContext()) ;
         SQLiteDatabase Data = Help.getReadableDatabase() ;
         SQLiteDatabase Data2 = Help2.getWritableDatabase();
         ContentValues Values = new ContentValues();
         String[] proj2= {userEntry.COULMN_friends,userEntry.COULMN_number_friends };
         String select2= userEntry._ID+"=?" ;
         String[] selectionarg2 ={String.valueOf(singleUser._Id)} ;


         //gets the friends string of the added user and the friends count
         Cursor C2 = Data.query(userEntry.TABLE_NAME , proj2 , select2 , selectionarg2 , null , null, null  ) ;
         C2.moveToFirst() ;
         String Friend2= C2.getString(C2.getColumnIndex(userEntry.COULMN_friends));

         //add the id of the user who added to the string of the added user
         if(Friend2=="")
         {
             Friend2=String.valueOf(singleUser._Id);
         }
         else
         {
             Friend2=Friend2+","+String.valueOf(this_user_id);
         }
         //update frinds count of the added user
         Values.put(userEntry.COULMN_friends,Friend2);
         String Number2= C2.getString(C2.getColumnIndex(userEntry.COULMN_number_friends));
         int result2 = Integer.parseInt(Number2);
         result2++;
         txt.setText("Has "+String.valueOf(result2)+" Friends");
         Values.put(userEntry.COULMN_number_friends,String.valueOf(result2));
         Data2.update(userEntry.TABLE_NAME, Values, userEntry._ID+"=" +String.valueOf(singleUser._Id) , null);
         //
     }


  ;

    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
        LayoutInflater myCustomInflater = LayoutInflater.from(getContext());
        View customView = myCustomInflater.inflate(R.layout.search_result, parent, false);
        // get references.
        final user singleUser = getItem(position);
        TextView itemText = (TextView) customView.findViewById(R.id.item_text);
        final TextView smallItemText = (TextView) customView.findViewById(R.id.item_small_text);
         final TextView Frnd= (TextView) customView.findViewById(R.id.Friend) ;
        Button btn = (Button) customView.findViewById(R.id.btnFriend);
        ImageView buckysImage = (ImageView) customView.findViewById(R.id.my_profile_image);
        // dynamically update the text from the array
        itemText.setText(singleUser.name);

        UserHelper Helper=new UserHelper(getContext());
        SQLiteDatabase sqLiteDatabase = Helper.getReadableDatabase() ;

        String[]  projection = {userEntry.COULMN_friends };
        String selection = userEntry._ID+"=?" ;
        String[] selectionargs ={String.valueOf(this_user_id)} ;

        //get string array of the user searching
        Cursor c = sqLiteDatabase.query(userEntry.TABLE_NAME , projection , selection , selectionargs , null , null, null  ) ;
   // Cursor c;
        c.moveToFirst() ;
        String Friends= c.getString(c.getColumnIndex(userEntry.COULMN_friends));
        //check if the found user(s) are friend with the searching user
        //if they are friend displays add as friend button else displays text view contains thw rod friend
        if (Friends.contains(String.valueOf(singleUser._Id))) {

            btn.setVisibility(View.INVISIBLE);
            Frnd.setVisibility(View.VISIBLE);

        }
        else {

            Frnd.setVisibility(View.INVISIBLE);
            btn.setVisibility(View.VISIBLE);

        }

        //add as friend button
     btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    update(singleUser,smallItemText);
                                    Frnd.setVisibility(View.VISIBLE);

                                    Button bt= (Button) v;
                                    bt.setVisibility(View.INVISIBLE);
                                }
     });


        smallItemText.setText("Has "+singleUser.no_of_friends+" Friends");

        // using the same image every time
        buckysImage.setImageResource(R.drawable.icons8_account_96);
        // Now we can finally return our custom View or custom item
        return customView;
    }

}
