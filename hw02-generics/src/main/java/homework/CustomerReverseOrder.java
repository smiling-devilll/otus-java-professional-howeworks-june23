package homework;

import java.util.ArrayDeque;
import java.util.Deque;

public class CustomerReverseOrder {

    private Deque<Customer> queue;

    public CustomerReverseOrder() {
        this.queue = new ArrayDeque<>();
    }

    public void add(Customer customer) {
        queue.addFirst(customer);
    }

    public Customer take() {
        return queue.removeFirst();
    }
}
