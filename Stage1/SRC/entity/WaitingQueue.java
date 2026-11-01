package entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class WaitingQueue {
    private final Queue<Customer> customers = new LinkedList<>();

    public void enqueue(Customer c) {
        customers.add(c);
    }

    // returns null if nobody is waiting
    public Customer dequeueNext() {
        return customers.poll();
    }

    // lets the manager pick who to seat instead of always the first in line
    public boolean remove(Customer c) {
        return customers.remove(c);
    }

    public boolean isEmpty() {
        return customers.isEmpty();
    }

    public int getSize() {
        return customers.size();
    }

    // returns a copy so other classes can't change the queue by accident
    public List<Customer> getCustomers() {
        return Collections.unmodifiableList(new ArrayList<>(customers));
    }
}
