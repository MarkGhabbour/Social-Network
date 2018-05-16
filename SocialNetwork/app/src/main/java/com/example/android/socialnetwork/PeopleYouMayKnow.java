package com.example.android.socialnetwork;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.android.socialnetwork.packagefordb.UserHelper;
import com.example.android.socialnetwork.packagefordb.usercontract.userEntry;

import java.util.ArrayList;
import java.util.Stack;
import java.util.Vector;

public class PeopleYouMayKnow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_not_friends);


        showPeopleYouMayKnow();
    }

    public void showPeopleYouMayKnow() {

        int _Id = personal_page.this_user_id;
        ArrayList<Integer> result = new ArrayList<Integer>();
        Vector<ArrayList<Integer>> adjList = new Vector<ArrayList<Integer>>();
        Search search = new Search();


        String[] projection = {userEntry._ID, userEntry.COULMN_friends, userEntry.COULMN_number_friends};
        UserHelper userHelper = new UserHelper(this);
        SQLiteDatabase ins = userHelper.getReadableDatabase();
        Cursor c = ins.query(userEntry.TABLE_NAME, projection, null,
                null, null, null, null);


        Vector<Boolean> visited = new Vector<>(c.getCount());

        for (int i = 0; i < c.getCount(); i++)
            visited.add(i, false);

         adjList=search.getAdjList(c);

        queue toBeVisited = new queue();
        Vector<Integer> dataa= new Vector<>();

        toBeVisited.enqueue(--_Id);
        visited.set(_Id, true);


        while (!toBeVisited.isEmpty())
        {
            int current_node = toBeVisited.dequeue();




            for (int i = 0; i < adjList.get(current_node).size(); i++)
            {
                int index = adjList.get(current_node).get(i);

                if (visited.get(index) == false)
                {
                    visited.set(index, true);
                    toBeVisited.enqueue(index);
                    if(current_node!=_Id)
                    result.add(index);
                }
            }
        }


        ArrayList<user> users = new ArrayList<>();



        SQLiteDatabase sql = userHelper.getReadableDatabase();
        String[] projectionn = {userEntry.COULMN_UserName, userEntry.COULMN_number_friends, userEntry._ID};
        String selection = userEntry._ID + "=?";
        String[] selectionargs = {String.valueOf(personal_page.this_user_id)};
       // c = sql.query(userEntry.TABLE_NAME, projectionn, selection, selectionargs, null, null, null);
        //c.moveToFirst();

        for (int i = 0; i < result.size(); i++)
        {
            String[] tmpselectionargs = {String.valueOf(result.get(i)+1)};


            String tmpPath = new String();
            tmpPath="";
            Stack<Integer> path = new Stack<>();

            path = search.findShortestPathInSocialNetwork(adjList, _Id, result.get(i), visited.size());


            while(! path.empty())
            {
                int tmpId = path.pop()+1;
                //char tmpChar = ( char) (tmpId + 'A');

                Log.v("PeopleYouMayKnow", ""+tmpId);


                String select = userEntry._ID + "=?";
                String[] selecArgs = {String.valueOf(tmpId)};

                 c= sql.query(userEntry.TABLE_NAME, projectionn, select,
                        selecArgs, null, null, null );
                c.moveToFirst();


                if(!path.empty())
                {
                    //tmpPath += tmpChar + "->";
                    tmpPath +=c.getString(c.getColumnIndex(userEntry.COULMN_UserName))+"->";
                }
                else
                {
                    //tmpPath+= tmpChar;
                    tmpPath +=c.getString(c.getColumnIndex(userEntry.COULMN_UserName));
                }
                c.close();
            }
            tmpPath =  "\n"+ tmpPath;

             c = sql.query(userEntry.TABLE_NAME, projectionn, selection, tmpselectionargs, null, null, null);
            c.moveToFirst();
            user us = new user(c.getString(c.getColumnIndex(userEntry.COULMN_UserName)) + tmpPath,
                    c.getInt(c.getColumnIndex(userEntry.COULMN_number_friends)),
                    c.getInt(c.getColumnIndex(userEntry._ID)));
            users.add(us);


        }


        // List them on screen
        final ListAdapter customListAdapter = new UserAdapterWithAddFriend(this, users);// Pass the array to the constructor.
        final ListView customListView = (ListView) findViewById(R.id.list_of_non_friends);
        customListView.setAdapter(customListAdapter);


    }

}