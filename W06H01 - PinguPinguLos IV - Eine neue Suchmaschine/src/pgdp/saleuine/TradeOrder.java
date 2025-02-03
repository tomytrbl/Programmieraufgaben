package pgdp.saleuine;

import java.math.BigDecimal;

public class TradeOrder {
    private BigDecimal totalCost;
    private int currentWeight;

    public TradeOrder(){
        currentWeight = 0;
        totalCost = new BigDecimal(0);
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public int getCurrentWeight() {
        return currentWeight;
    }

    public String orderType(){
        return "Einzeln";
    }

    public String toString(){
        return "Die Bestellung(Einzeln) hat ein Gesamtgewicht von " + currentWeight + "g und kostet " + totalCost + "PD.";
    }

    public boolean supplyOrder(PinguFood supply, BigDecimal cost) {
        if (!supply.isEdible() || isOrderFulfilled()) {
            return false;
        } else {
            if (supply instanceof Anchovie) {
                if (isOrderFulfilledall("Anchovie")){
                    return false;
                }
                else{addAmount(supply.getWeight(), "Anchovie");}
            } else if (supply instanceof Sardine) {
                if (isOrderFulfilledall("Sardine")){
                    return false;
                }
                else{addAmount(supply.getWeight(), "Sardine");}
            } else if (supply instanceof Crustacean) {
                if (isOrderFulfilledall("Crustacean")){
                    return false;
                }
                else{addAmount(supply.getWeight(), "Crustacean");}
            }
            currentWeight += supply.getWeight();
            totalCost = totalCost.add(cost);
            return true;
        }
    }
    public void addAmount(int amount, String type){
    }

    public boolean isOrderFulfilledall(String type) {
        return false;}

        public boolean isOrderFulfilled () {
            return currentWeight > 0;
        }

	// TODO
}
