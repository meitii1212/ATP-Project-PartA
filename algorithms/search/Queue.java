package algorithms.search;

import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.Iterator;

public class Queue extends AbstractQueue {

    private ArrayList<Object> QueueList;


    //CONSTRUCTOR
    public Queue() {
        QueueList = new ArrayList<Object>();

    }

    @Override
    public Iterator iterator() {
        return QueueList.iterator();
    }

    @Override
    public int size() {
        return QueueList.size();
    }

    @Override

    public boolean offer(Object o) {
        if (o==null){
            return false;
        }
        QueueList.add(o);
        return true;
    }

    @Override
    public Object poll() {
        return QueueList.remove(0);
    }


    @Override
    public Object peek() {
        if(QueueList.size() ==0){
            return null;
        }
        return QueueList.get(0);

    }
}
