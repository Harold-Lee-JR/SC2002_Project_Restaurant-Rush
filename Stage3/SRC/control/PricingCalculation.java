package control;

import entity.Customer;
import entity.MenuItem;
import entity.HappyHour;

public class PricingCalculation {
    public int calculateFinalPrice(Customer c, MenuItem item, HappyHour happyHour, int turn){
        int bill = c.calculateBill(item);
        return bill * (100-happyHour.getDiscount(turn)) / 100;
    }
}
