package control;

import entity.Customer;
import entity.CustomerStatus;
import entity.MenuItem;
import entity.Order;
import entity.Restaurant;
import entity.Table;

public class Waiter {
    private final Restaurant restaurant;

    public Waiter(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void seatCustomer(Customer c, Table t) {
        t.seatCustomer(c);
        c.setStatus(CustomerStatus.SEATED);
    }

    // the customer picks the dish, the order starts as PREPARING
    public Order takeOrder(Customer c) {
        MenuItem item = c.chooseMenuItem(restaurant.getMenu());
        Order order = new Order(c, item);
        c.setOrder(order);
        c.setStatus(CustomerStatus.ORDERED);
        return order;
    }

    // marks the order as served and the customer starts eating
    public void serve(Order o, int turn) {
        o.markServed(turn);
        o.getCustomer().setServed(turn);
    }
}
