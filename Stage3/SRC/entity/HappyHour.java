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
        if(!activated){
            return activated;
        } else {
            if (activated && (startTurn+duration)>=turn){ //if it has been more than two turn since activation we return false
                return activated;
            } else {
                return false;
            }
        }
    }

    public boolean hasBeenActivated(){
        if(startTurn < 0){
            return false;
        } else return true;
    }

    public int getRemainingTurns(int turn){
        if(hasBeenActivated()){
            int th = turn - startTurn;
            switch (th){
                case 0:
                    return duration;
                case 1:
                    duration -=1;
                    return duration;
                case 2:
                    duration -=1;
                    return duration;
                default:
                    return 0;
            }
        } else return duration;
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
