package control;

import entity.Customer;
import entity.CustomerStatus;
import entity.Restaurant;
import entity.Table;

public class Host {
    private final Restaurant restaurant;

    public Host(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void seatCustomer(Customer c, Table t) {
        t.seatCustomer(c);
        c.setStatus(CustomerStatus.SEATED);
    }
}
