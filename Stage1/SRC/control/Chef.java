package control;

import entity.Order;

public class Chef {

    // cook one unit, the order is READY once no units are left
    public void prepareOrder(Order o, int turn) {
        o.advancePreparation();
        if (o.isFullyPrepared()) {
            o.markReady(turn);
        }
    }
}
