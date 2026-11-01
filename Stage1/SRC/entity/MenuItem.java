package entity;

public class MenuItem {
    private final String name;
    private final double basePrice;
    private final int preparationUnits;

    public MenuItem(String name, double basePrice, int preparationUnits) {
        this.name = name;
        this.basePrice = basePrice;
        this.preparationUnits = preparationUnits;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return basePrice;
    }

    public int getPrepUnit() {
        return preparationUnits;
    }
}
