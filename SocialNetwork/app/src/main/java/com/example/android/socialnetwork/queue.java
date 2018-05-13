package com.example.android.socialnetwork;

import java.util.Vector;

public class queue {
    Vector<Integer> data;
    int head, tail;

    queue()
    {
        head=tail=-1;
    }
 public void enqueue(int d)
 {
     tail++;
     data.add(tail, d);


 }
public int dequeue(){

        //vector returns Integer but receiver is int
        head++;
        int d= data.get(head);

        return d;

}

public boolean isEmpty(){

        return (head==tail);
}



}
