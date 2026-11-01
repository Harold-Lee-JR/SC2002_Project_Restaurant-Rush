package entity;

public class Table {
    private final int tableNumber;
    private Customer occupant;

    public Table(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public boolean isAvailable() {
        return occupant == null;
    }

    public void seatCustomer(Customer c) {
        // double check so two customers can never share a table
        if (!isAvailable()) {
            throw new IllegalStateException("Table " + tableNumber + " is already occupied");
        }
        this.occupant = c;
    }

    // called when the customer pays or leaves
    public void release() {
        this.occupant = null;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public Customer getOccupant() {
        return occupant;
    }
}
