package entity;

public class HappyHour {
    static final int DISCOUNT = 20;
    static final int RECOVERY = 15;
    int duration = 2;
    int startTurn = -1;
    boolean activated = false;

    public void activate(int turn){
        startTurn = turn;
        activated = true;
    }

    public boolean isActive(int turn) {
        return activated && turn >= startTurn && turn < startTurn + duration;
    }

    public boolean hasBeenActivated(){
        if(startTurn < 0){
            return false;
        } else return true;
    }

    public int getRemainingTurns(int turn){
        if (!hasBeenActivated()){
            return duration;
        } return Math.max(0, Math.min(duration, startTurn + duration - turn));
    }

    public int getDiscount(int turn){
        if (isActive((turn))){
            return DISCOUNT;
        }
        else return 0;
    }

    public int getRecovery(){
        return RECOVERY;
    }
}
