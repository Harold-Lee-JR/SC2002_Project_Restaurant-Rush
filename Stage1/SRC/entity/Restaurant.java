package entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Restaurant {
    private final List<Table> tables = new ArrayList<>();
    private final WaitingQueue waitingQueue = new WaitingQueue();
    private final List<MenuItem> menu = new ArrayList<>();

    public Restaurant(int tableCount) {
        for (int i = 1; i <= tableCount; i++) {
            tables.add(new Table(i));
        }
        // the three dishes: name, price, number of chef actions to cook
        menu.add(new MenuItem("Salad", 9.00, 1));
        menu.add(new MenuItem("Burger", 15.00, 1));
        menu.add(new MenuItem("Pasta", 18.00, 2));
    }

    // first free table, or null if all are taken
    public Table getAvailableTable() {
        for (Table t : tables) {
            if (t.isAvailable()) {
                return t;
            }
        }
        return null;
    }

    public WaitingQueue getWaitingQueue() {
        return waitingQueue;
    }

    public List<Table> getOccupiedTables() {
        List<Table> occupied = new ArrayList<>();
        for (Table t : tables) {
            if (!t.isAvailable()) {
                occupied.add(t);
            }
        }
        return occupied;
    }

    // needed when a customer pays or leaves and their table has to be freed
    public Table findTableOf(Customer c) {
        for (Table t : tables) {
            if (t.getOccupant() == c) {
                return t;
            }
        }
        return null;
    }

    public List<Table> getTables() {
        return Collections.unmodifiableList(tables);
    }

    public List<MenuItem> getMenu() {
        return Collections.unmodifiableList(menu);
    }
}
