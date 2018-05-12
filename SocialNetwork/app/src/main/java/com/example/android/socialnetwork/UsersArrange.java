package com.example.android.socialnetwork;

import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Martin on 5/12/2018.
 */

public class UsersArrange implements Comparator<user> {

    public int compare (user a , user b){
        String name1 = a.name ;
        String name2 = b.name ;
        if(name1.compareTo(name2)==1) return -1 ;
        else return 1 ;
    }
}
