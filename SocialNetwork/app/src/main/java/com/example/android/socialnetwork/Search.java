package com.example.android.socialnetwork;

import com.example.android.socialnetwork.queue;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.android.socialnetwork.packagefordb.UserHelper;
import com.example.android.socialnetwork.packagefordb.usercontract.userEntry;
import com.example.android.socialnetwork.packagefordb.usercontract.PostEntry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.Vector;

import static java.lang.Integer.parseInt;

public final class Search extends AppCompatActivity {





    public ArrayList<Integer> getPeopleUMayKnow(user me)
    {
        ArrayList<Integer> result = new ArrayList<Integer>();
        Vector<ArrayList<Integer>> adjList = new  Vector<ArrayList<Integer>>();

        String[] projection = {userEntry._ID, userEntry.COULMN_UserName, userEntry.COULMN_password, userEntry.COULMN_number_friends,
                userEntry.COULMN_friends};
        String selection = userEntry.COULMN_UserName + "=?" + " AND " + userEntry.COULMN_password + "=?";

        Context context = getApplicationContext();

        UserHelper userHelper = new UserHelper(context);
        SQLiteDatabase ins = userHelper.getReadableDatabase();

        Cursor c = ins.query(userEntry.TABLE_NAME, projection, null,
                null, null, null, "ASC", null);
        c.moveToFirst();



        Vector<Boolean> visited = new Vector<>(  c .getCount());
        for(int i=0; i<c.getCount(); i++)
        {
            visited.add(i,false);

        }

//        Vector<user> users = new Vector<>();
//
//        //this code needs each user to have an id attribue which i added
//        //added an id attribute to each user it needs to build users vector, in which users are added from cursor to vector
//        //and adding each user friends in it
//
//        for(int i=0; i<c.getCount(); i++)
//        {
//            user tmpUser = new user();
//            tmpUser = users.get(i);
//
//            for (int j = 0; j <tmpUser.getFriends().size(); j++)
//            {
//                user tmpUser2 = new user();
//                tmpUser2= tmpUser.getFriends().get(j);
//                int tmp_id = tmpUser2.get_Id();
//                adjList.get(j).add(tmp_id);
//
//            }
//
//        }

        getAdjList(c, adjList);

        for(int i=0; i<adjList.size(); i++){

            for(int j=0; j< adjList.get(i).size(); j++){
                Log.v("Search Class", ""+ adjList.get(i).get(j));
            }

        }

        queue  toBeVisited = new queue () ;


        toBeVisited.enqueue(me._Id);


        while(!toBeVisited.isEmpty())
        {
            int current_node= toBeVisited.dequeue() ;


            for (int i=0; i<  adjList.get(current_node).size();  i++)
            {
                int index =adjList.get(current_node).get(i);
                if (visited.get(index)==false)
                {
                    visited.set(index, true);
                    toBeVisited.enqueue(i);
                }
            }
        }


        return result;
    }

private void getAdjList(Cursor c,  Vector<ArrayList<Integer>> adjList )
{

       int count = c.getCount();

        String s = new String();

        for(int i=0; i<count; i++)
        {
            int id= c.getInt(c.getColumnIndex(userEntry._ID));
            s= getString(c.getColumnIndex(userEntry.COULMN_friends ));
            int no_friends = c.getInt(c.getColumnIndex(userEntry.COULMN_number_friends));
            String[] parts = s.split(",");

            for(int j=0; j<no_friends; j++)
            {
                int friend_Id = parseInt(parts[j]);
                adjList.get(i).add(friend_Id);
            }

            c.moveToNext();
        }
  }


}
