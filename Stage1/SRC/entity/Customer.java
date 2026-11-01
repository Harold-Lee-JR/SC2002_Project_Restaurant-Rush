package entity;

import java.util.List;

public class Customer {
    // satisfaction starts at 100 and drops 8 every turn the customer is still waiting for something
    private static final int STARTING_SATISFACTION = 100;
    private static final int REGULAR_SATISFACTION_LOSS = 8;

    private final int id;
    private int satisfaction;
    private CustomerStatus status;
    private int turnServed;
    private Order order;

    public Customer(int id) {
        this.id = id;
        this.satisfaction = STARTING_SATISFACTION;
        this.status = CustomerStatus.WAITING;
        this.turnServed = -1;
    }

    public int getSatisfactionLoss() {
        return REGULAR_SATISFACTION_LOSS;
    }

    // never goes below 0
    public void reduceSatisfaction() {
        satisfaction = Math.max(0, satisfaction - getSatisfactionLoss());
    }

    // food depends on the id: 1 Salad, 2 Burger, 3 Pasta, then it repeats
    public MenuItem chooseMenuItem(List<MenuItem> menu) {
        return menu.get((id - 1) % menu.size());
    }

    // getting served means they start eating, we also remember the turn so the cashier knows who was first
    public void setServed(int turn) {
        this.status = CustomerStatus.EATING;
        this.turnServed = turn;
    }

    public int getTurnServed() {
        return turnServed;
    }

    // true while the customer is still waiting on the restaurant, these are the ones who get more annoyed
    public boolean isWaitingForService() {
        return status == CustomerStatus.WAITING
                || status == CustomerStatus.SEATED
                || status == CustomerStatus.ORDERED;
    }

    public int getId() {
        return id;
    }

    public int getSatisfaction() {
        return satisfaction;
    }

    public CustomerStatus getStatus() {
        return status;
    }

    public void setStatus(CustomerStatus status) {
        this.status = status;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "Customer #" + id;
    }
}
