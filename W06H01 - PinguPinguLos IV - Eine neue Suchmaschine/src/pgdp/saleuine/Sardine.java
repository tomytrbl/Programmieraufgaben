package pgdp.saleuine;

public class Sardine extends PinguFood{
    private static int MIN_AGE = 1;
    private static int MIN_WEIGHT = 100;
    private static int MIN_LENGTH = 14;
    private int length;

    public Sardine(int age, int weight, int length){
        super(age, weight);
        this.length = length;
    }

    public int getLength() {
        return length;
    }

    @Override
    public boolean isEdible(){
        return getAge() >= MIN_AGE && getWeight() >= MIN_WEIGHT && getLength() >= MIN_LENGTH;
    }

    public String toString(){
        return "Sardine(Alter: "+getAge()+" Jahre, Gewicht: "+getWeight()+"g, Länge: "+length+")";
    }
}
