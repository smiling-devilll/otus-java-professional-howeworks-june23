package homework;

import java.util.Map;
import java.util.TreeMap;

public class CustomerService {

    private TreeMap<Customer, String> container;

    public CustomerService() {
        this.container = new TreeMap<>((o1, o2) -> {
            long id1 = o1.getScores();
            long id2 = o2.getScores();
            return Long.compare(id1, id2);
        });
    }

    public Map.Entry<Customer, String> getSmallest() {
        Map.Entry<Customer, String> entry = this.container.firstEntry();
        return this.copyEntry(entry);
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        if (customer != null)
            return this.copyEntry(container.higherEntry(customer));
        else
            return null;
    }

    public void add(Customer customer, String data) {
        container.put(customer, data);
    }

    private Map.Entry<Customer, String> copyEntry(Map.Entry<Customer, String> entry) {
        if (entry != null) {
            Customer cust = new Customer(entry.getKey().getId(), entry.getKey().getName(), entry.getKey().getScores());
            return Map.entry(cust, entry.getValue());
        } else return null;
    }
}
