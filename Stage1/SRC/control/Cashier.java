package control;

import entity.Customer;
import entity.CustomerStatus;
import entity.GameResult;
import entity.Order;

public class Cashier {

    // closes the order and adds the money and the customer's satisfaction to the score
    public void processPayment(Order o, GameResult r) {
        Customer c = o.getCustomer();
        o.markPaid();
        c.setStatus(CustomerStatus.PAID);
        r.recordPayment(o.getPrice(), c.getSatisfaction());
    }
}
