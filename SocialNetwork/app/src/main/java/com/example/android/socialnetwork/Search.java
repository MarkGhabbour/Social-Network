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
import java.util.Stack;
import java.util.Vector;

import static java.lang.Integer.parseInt;

public final class Search extends AppCompatActivity {





    public ArrayList<Integer> getPeopleUMayKnow(int _Id)
    {
        ArrayList<Integer> result = new ArrayList<Integer>();
        Vector<ArrayList<Integer>> adjList = new  Vector<ArrayList<Integer>>();

        String[] projection = {userEntry._ID, userEntry.COULMN_friends};

        UserHelper userHelper = new UserHelper(this);
        SQLiteDatabase ins = userHelper.getReadableDatabase();
        Cursor c = ins.query(userEntry.TABLE_NAME, projection, null,
                null, null, null, null);

        if(c.getCount()>0) {
            c.moveToFirst();

            Vector<Boolean> visited = new Vector<>(c.getCount());
            for (int i = 0; i < c.getCount(); i++)
                visited.add(i, false);

           adjList= getAdjList(c );

            queue toBeVisited = new queue();


            toBeVisited.enqueue(_Id);


            while (!toBeVisited.isEmpty()) {
                int current_node = toBeVisited.dequeue();


                for (int i = 0; i < adjList.get(current_node).size(); i++) {
                    int index = adjList.get(current_node).get(i);
                    if (visited.get(index) == false) {
                        visited.set(index, true);
                        toBeVisited.enqueue(i );
                    }
                }
            }

        }
        return result;

    }

    public   Vector<ArrayList<Integer>> getAdjList(Cursor c )
    {

        int count = c.getCount();
        c.moveToFirst();
        Vector<ArrayList<Integer>> adjList = new Vector<ArrayList<Integer>>();

        for(int i=0; i<count; i++)
        {
            adjList.add( new ArrayList<Integer>());
        }

        String s = new String();

        for(int i=0; i<count; i++)
        {
            int id= c.getInt(c.getColumnIndex(userEntry._ID));
            id--;
            s= c.getString(c.getColumnIndex(userEntry.COULMN_friends ));
            int no_friends= c.getInt(c.getColumnIndex(userEntry.COULMN_number_friends));
            String[] parts = s.split(",");

            for(int j=0; j<no_friends; j++)
            {
                int friend_Id = parseInt(parts[j]);

                friend_Id--;
                adjList.get(i).add(friend_Id);
            }

            c.moveToNext();
        }

        return adjList;
    }

    Stack<Integer> findShortestPathInSocialNetwork(Vector<ArrayList<Integer> > adjList, int source, int end, int size)
    {

        Vector<Boolean> isVisited = new Vector<> ( );
        Vector<ArrayList<Integer>>  parent = new Vector<ArrayList<Integer>>();
        Vector<Integer> myDistance= new Vector<Integer>();
        queue toBeVisited = new queue();
        boolean isFound = false;

        for(int i=0; i<size; i++)
        {
            isVisited.add(i, false);
            myDistance.add(i, 0);
            parent.add( new ArrayList<Integer>());

        }

        toBeVisited.enqueue(source);
        isVisited.set(source, true) ;


        while (!toBeVisited.isEmpty())
        {
            int current_node = toBeVisited.dequeue();


            if (current_node == end)
            {
                isFound = true;
                break;
            }

            for (int i=0; i<  adjList.get(current_node).size();  i++)
            {
                int index =adjList.get(current_node).get(i);

                if (!isVisited.get(index))
                {
                    isVisited.set(index, true);
                    toBeVisited.enqueue(index);
                    int tmpDist = myDistance.get(current_node)+1;
                    myDistance.set(index, tmpDist) ;
                    parent.get(index).add(current_node);
                }
            }
        }
        Stack<Integer> path = new Stack<>();

        if (isFound)
        {

            path.push(new Integer(end));
            int distance = myDistance.get(end);
            int current_node = end;

            while (distance!=0)
            {
                int tmp = parent.get(current_node).get(0);
                path.push(tmp);
                current_node = path.peek();
                distance--;
            }

        }

        return path;
    }



}
