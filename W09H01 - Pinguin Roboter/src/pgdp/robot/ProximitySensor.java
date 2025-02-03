package pgdp.robot;

public class ProximitySensor extends Sensor<Double>{
    protected double directionOffset;
    protected double accuracy;
    protected double maxRange;

    public ProximitySensor(String name, double directionOffset, double accuracy, double maxRange, double reliability){
        super(name, reliability);
        this.directionOffset = directionOffset;
        this.accuracy = accuracy;
        this.maxRange = maxRange;
    }

//    @Override
//    public Character getData() throws MalfunctionException {
//        char c = owner.getWorld().getTerrain(owner.getPosition());
//        if (owner.getFailureSimulator().getNextRandom() > reliability) {
//            throw new MalfunctionException(this);
//        }
//        return c;
    @Override
    public Double getData() throws MalfunctionException{
        //get position gibt eine Kopie, die man frei verändern kann
        //verschieben bis #
        //oder maxRange wenn größter ist die distanz
        Position current = owner.getPosition();
        Position nextWall = owner.getPosition();
        double dir = owner.getDirection() +directionOffset;
        current.moveBy(owner.getSize()/2, dir); //jetzt sind wir am Rand in Fichtung wo wir hinschauen
        nextWall.moveBy(owner.getSize()/2, dir);
        while(owner.getWorld().getTerrain(nextWall) != '#'){
            nextWall.moveBy(accuracy, dir);
        }
        if (owner.getFailureSimulator().getNextRandom() > reliability) {
            throw new MalfunctionException(this);}

        return Math.min(maxRange, current.distanceTo(nextWall));
    }

    @Override
    public Sensor<Double> createNewSensor() {
            ProximitySensor neu = new ProximitySensor(this.name, this.directionOffset, this.accuracy, this.maxRange, this.reliability);
            neu.setProcessor(this.processor);
            return neu;}


    public String toString(){
        return "ProximitySensor "+name;
    }
}
