package pgdp.robot;

public class ControllerReceiver extends Sensor<World.Controller> {

    public ControllerReceiver(String name, double reliability){
        super(name, reliability);
    }

    @Override
    public World.Controller getData() throws  MalfunctionException{
        World.Controller cont = owner.getWorld().getController();
        if (owner.getFailureSimulator().getNextRandom() > reliability) {
            throw new MalfunctionException(this);
        }
        return cont;
    }

    @Override
    public Sensor<World.Controller> createNewSensor(){
        ControllerReceiver neu = new ControllerReceiver(this.name, this.reliability);
        neu.setProcessor(this.processor);
        return neu;
    }

    @Override
    public String toString(){
        return "ControllerReceiver "+name;
    }


}
