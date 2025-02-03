package pgdp.saleuine;

public class AmountOrder extends TradeOrder {
    private final int targetAmountAnchovies;
    private final int targetAmountCrustaceans;
    private final int  targetAmountSardines;
    private int currentAmountAnchovies;
    private int currentAmountCrustaceans;
    private int currentAmountSardines;

    public AmountOrder(int targetAmountAnchovies, int targetAmountCrustaceans, int targetAmountSardines) {
        this.targetAmountAnchovies = targetAmountAnchovies;
        this.targetAmountCrustaceans = targetAmountCrustaceans;
        this.targetAmountSardines = targetAmountSardines;
        currentAmountAnchovies = 0;
        currentAmountCrustaceans = 0;
        currentAmountSardines = 0;
    }

    public int getTargetAmountAnchovies() {
        return targetAmountAnchovies;
    }

    public int getTargetAmountCrustaceans() {
        return targetAmountCrustaceans;
    }

    public int getTargetAmountSardines() {
        return targetAmountSardines;
    }

    public int getCurrentAmountAnchovies() {
        return currentAmountAnchovies;
    }

    public int getCurrentAmountCrustaceans() {
        return currentAmountCrustaceans;
    }

    public int getCurrentAmountSardines() {
        return currentAmountSardines;
    }

    @Override
    public String orderType() {
        return "Anzahl: [" + targetAmountAnchovies + "," + targetAmountCrustaceans + "," + targetAmountSardines + "]";
    }

    @Override
    public String toString() {
        return "Die Bestellung(" + orderType() +") hat ein Gesamtgewicht von " + getCurrentWeight() + "g und kostet " + getTotalCost() + "PD.";
    }

    @Override
    public boolean isOrderFulfilled(){
        return currentAmountAnchovies >= targetAmountAnchovies && currentAmountCrustaceans >= targetAmountCrustaceans && currentAmountSardines >= targetAmountSardines;}

        @Override
        public boolean isOrderFulfilledall(String type) {
            if (type.equals("Anchovie")) {
                return currentAmountAnchovies >= targetAmountAnchovies;
            } else if (type.equals("Sardine")) {
                return currentAmountSardines >= targetAmountSardines;

            } else if (type.equals("Crustacean")) {
                return currentAmountCrustaceans >= targetAmountCrustaceans;
            }
            return false;
        }
    @Override
    public void addAmount(int amount, String type){
        if (type.equals("Anchovie")) {
            currentAmountAnchovies += 1;
        } else if (type.equals("Sardine")) {
            currentAmountSardines += 1;
        } else if (type.equals("Crustacean")) {
            currentAmountCrustaceans += 1;
        }
    }
}
//
