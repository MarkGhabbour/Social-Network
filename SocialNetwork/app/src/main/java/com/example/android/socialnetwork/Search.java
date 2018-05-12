package com.example.android.socialnetwork;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.example.android.socialnetwork.packagefordb.UserHelper;
import com.example.android.socialnetwork.packagefordb.usercontract.userEntry;
import com.example.android.socialnetwork.packagefordb.usercontract.PostEntry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.Vector;

public class Search extends AppCompatActivity {





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
            visited.add(i,false);

        Vector<user> users = new Vector<>();

        //this code needs each user to have an id attribue which i added
        //added an id attribute to each user it needs to build users vector, in which users are added from cursor to vector
        //and adding each user friends in it

        for(int i=0; i<c.getCount(); i++)
        {
            user tmpUser = new user();
            tmpUser = users.get(i);

            for (int j = 0; j <tmpUser.getFriends().size(); j++)
            {
                user tmpUser2 = new user();
                tmpUser2= tmpUser.getFriends().get(j);
                int tmp_id = tmpUser2.get_Id();
                adjList.get(j).add(tmp_id);

            }

        }
        Queue<Integer> toBeVisited = new Queue<Integer>() {
            @Override
            public boolean add(Integer integer) {
                return false;
            }

            @Override
            public boolean offer(Integer integer) {
                return false;
            }

            @Override
            public Integer remove() {
                return null;
            }

            @Override
            public Integer poll() {
                return null;
            }

            @Override
            public Integer element() {
                return null;
            }

            @Override
            public Integer peek() {
                return null;
            }

            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @NonNull
            @Override
            public Iterator<Integer> iterator() {
                return null;
            }

            @NonNull
            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @NonNull
            @Override
            public <T> T[] toArray(@NonNull T[] ts) {
                return null;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(@NonNull Collection<?> collection) {
                return false;
            }

            @Override
            public boolean addAll(@NonNull Collection<? extends Integer> collection) {
                return false;
            }

            @Override
            public boolean removeAll(@NonNull Collection<?> collection) {
                return false;
            }

            @Override
            public boolean retainAll(@NonNull Collection<?> collection) {
                return false;
            }

            @Override
            public void clear() {

            }
        };

        toBeVisited.add(me._Id);


        while(!toBeVisited.isEmpty())
        {
            int current_node= toBeVisited.remove() ;


            for (int i=0; i<  adjList.get(current_node).size();  i++)
            {
                int index =adjList.get(current_node).get(i);
                if (visited.get(index)==false)
                {
                    visited.set(index, true);
                    toBeVisited.add(i);
                }

            }



        }




        return result;
    }

}
