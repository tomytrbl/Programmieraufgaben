package pgdp.saleuine;

public class Anchovie extends PinguFood{
    private static int MIN_AGE = 1;
    private static int MIN_WEIGHT = 5;

    public Anchovie(int age, int weight){
        super(age, weight);
    }
    @Override
    public boolean isEdible(){
        return getAge() >= MIN_AGE && getWeight() >= MIN_WEIGHT;
    }

    @Override
    public String toString(){
        return "Sardelle(Alter: "+getAge()+" Jahre, Gewicht: "+getWeight()+"g)";
    }
}
