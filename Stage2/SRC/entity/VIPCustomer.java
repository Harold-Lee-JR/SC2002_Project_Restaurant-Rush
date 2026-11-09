package entity;

//VIP customer: more patient than a Regular (loses 5 satisfaction per waiting turn)
//and gets 10% off the bill (rounded to the nearest cent). Orders the same way as a Regular.
 
public class VIPCustomer extends Customer {
    private static final int VIP_SATISFACTION_LOSS = 5;
    private static final double VIP_DISCOUNT_RATE = 0.10;

    public VIPCustomer(int id) {
        super(id);
    }

    @Override
    public int getSatisfactionLoss() {
        return VIP_SATISFACTION_LOSS;
    }

    @Override
    public int calculateBill(MenuItem item) {
        return (int) Math.round(item.getPrice() * (1 - VIP_DISCOUNT_RATE));
    }

    @Override
    protected String getTypeLabel() {
        return "VIP Customer";
    }
}
