package com.example.android.socialnetwork;

import java.util.Vector;

public class queue {
    private Vector<Integer> data;
    private int head, tail;

    queue()
    {
        data= new Vector<Integer>(100);
        head=tail=0;
    }

    public void enqueue(int d)
    {

        data.insertElementAt(d, tail);
        tail++;

    }
    public int dequeue(){

        //vector returns Integer but receiver is int

        int d= data.get(head) ;
        head++;

        return d;

    }

    public boolean isEmpty(){

        return (head==tail);
    }



}
