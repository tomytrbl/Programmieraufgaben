package pgdp.saleuine;

public class WeightOrder extends TradeOrder{
    private final int targetWeight;
    public WeightOrder(int targetWeight){
        this.targetWeight = targetWeight;
    }

    public int getTargetWeight() {
        return targetWeight;
    }

    @Override
    public String orderType(){
        return "Zielgewicht: "+targetWeight+"g";
    }
    @Override
    public String toString(){
        return "Die Bestellung("+orderType()+") hat ein Gesamtgewicht von "+getCurrentWeight()+"g und kostet "+getTotalCost()+"PD.";
    }
    @Override
    public boolean isOrderFulfilled(){
        return getCurrentWeight() >= targetWeight;
    }
}
