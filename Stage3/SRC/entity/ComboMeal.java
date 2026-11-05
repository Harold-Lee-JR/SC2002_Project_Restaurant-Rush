package entity;
 
public class ComboMeal extends MenuItem{
    private final MenuItem itemOne;
    private final MenuItem itemTwo;
    private static final int COMBO_DISCOUNT = 10;

    public ComboMeal(MenuItem itemOne, MenuItem itemTwo){
        super(itemOne.getName() + " + " + itemTwo.getName() + " Combo", 0, 0);
        this.itemOne = itemOne;
        this.itemTwo = itemTwo;
    }

    @Override 
    public int getPrice(){
        return (int) (((itemOne.getPrice()+itemTwo.getPrice()) *(100 - COMBO_DISCOUNT)/100));
    }

    @Override 
    public int getPrepUnit(){
        return (itemOne.getPrepUnit()+itemTwo.getPrepUnit());
    }
}
