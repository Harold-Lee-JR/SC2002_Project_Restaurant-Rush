package control;

import entity.Customer;
import entity.MenuItem;
import entity.HappyHour;

public class PricingCalculation {
    public int calculateFinalPrice(Customer c, MenuItem item, boolean happyHourActive, int turn){
        if(happyHourActive){
            HappyHour hh = new HappyHour();
            return (int)((c.calculateBill(item)*hh.getDiscount(turn))/100);
        } else return c.calculateBill(item);
    }
}
