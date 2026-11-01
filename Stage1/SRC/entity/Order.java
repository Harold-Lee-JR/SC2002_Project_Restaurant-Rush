package entity;

// status only moves forward, the price is fixed when the order is made
public class Order {
    private final Customer customer;
    private final MenuItem item;
    private final double price;
    private OrderStatus status;
    private int preparationRemaining;
    private int turnReady;
    private int turnServed;

    public Order(Customer customer, MenuItem item) {
        this.customer = customer;
        this.item = item;
        this.price = item.getPrice();
        this.preparationRemaining = item.getPrepUnit();
        this.status = OrderStatus.PREPARING;
        this.turnReady = -1;
        this.turnServed = -1;
    }

    // one chef action cooks one unit
    public void advancePreparation() {
        if (status == OrderStatus.PREPARING && preparationRemaining > 0) {
            preparationRemaining--;
        }
    }

    public boolean isFullyPrepared() {
        return preparationRemaining == 0;
    }

    public void markReady(int turn) {
        this.status = OrderStatus.READY;
        this.turnReady = turn;
    }

    public void markServed(int turn) {
        this.status = OrderStatus.SERVED;
        this.turnServed = turn;
    }

    // paid and cancelled are the two end states
    public void markPaid() {
        this.status = OrderStatus.PAID;
    }

    public void markCancelled() {
        this.status = OrderStatus.CANCELLED;
    }

    public Customer getCustomer() {
        return customer;
    }

    public MenuItem getItem() {
        return item;
    }

    public double getPrice() {
        return price;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public int getPreparationRemaining() {
        return preparationRemaining;
    }

    public int getTurnReady() {
        return turnReady;
    }

    public int getTurnServed() {
        return turnServed;
    }
}
