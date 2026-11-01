package entity;

public class GameResult {
    // revenue only goes up through recordPayment
    private double revenue;
    private int paidCount;
    private int satisfactionTotal;
    private GameOutcome outcome = GameOutcome.IN_PROGRESS;
    private int servedCount;
    private int abandonedCount;

    public void recordPayment(double price, int satisfaction) {
        revenue += price;
        paidCount++;
        satisfactionTotal += satisfaction;
    }

    public void recordServed() {
        servedCount++;
    }

    public void recordAbandonment() {
        abandonedCount++;
    }

    // only counts customers who paid
    public double calculateAverageSatisfaction() {
        if (paidCount == 0) {
            return 0;
        }
        return (double) satisfactionTotal / paidCount;
    }

    // all three goals must be met to win
    public GameOutcome evaluate(GameConfig cfg) {
        boolean enoughCustomers = paidCount >= cfg.getVictoryPaidCustomers();
        boolean enoughRevenue = revenue >= cfg.getVictoryRevenue();
        boolean happyEnough = paidCount > 0 && calculateAverageSatisfaction() >= cfg.getVictorySatisfaction();
        outcome = (enoughCustomers && enoughRevenue && happyEnough) ? GameOutcome.VICTORY : GameOutcome.DEFEAT;
        return outcome;
    }

    public boolean isVictory() {
        return outcome == GameOutcome.VICTORY;
    }

    public double getRevenue() {
        return revenue;
    }

    public int getPaidCount() {
        return paidCount;
    }

    public int getSatisfactionTotal() {
        return satisfactionTotal;
    }

    public GameOutcome getOutcome() {
        return outcome;
    }

    public int getServedCount() {
        return servedCount;
    }

    public int getAbandonedCount() {
        return abandonedCount;
    }
}
