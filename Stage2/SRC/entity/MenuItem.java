package entity;

// One dish on the menu: name, base price (in cents) and the number of preparation units the Chef must spend on it
public class MenuItem {
    private final String name;
    private final int basePriceCents;
    private final int preparationUnits;

    public MenuItem(String name, int basePriceCents, int preparationUnits) {
        this.name = name;
        this.basePriceCents = basePriceCents;
        this.preparationUnits = preparationUnits;
    }

    public String getName() {
        return name;
    }

    // Base price in cents, e.g. 900 for $9.00
    public int getPrice() {
        return basePriceCents;
    }

    public int getPrepUnit() {
        return preparationUnits;
    }
}
