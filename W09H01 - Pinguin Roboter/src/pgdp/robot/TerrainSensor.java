package pgdp.robot;

import pgdp.robot.MalfunctionException;
import pgdp.robot.Robot;
import pgdp.robot.Sensor;

public class TerrainSensor extends Sensor<Character> {

    public TerrainSensor(String name, double reliability){
        super(name, reliability);
    }


    @Override
    public Character getData() throws MalfunctionException {
        char c = owner.getWorld().getTerrain(owner.getPosition());
        if (owner.getFailureSimulator().getNextRandom() > reliability) {
            throw new MalfunctionException(this);
        }
        return c;
    }

    @Override
    public Sensor<Character> createNewSensor(){
        TerrainSensor neu = new TerrainSensor(this.name, this.reliability);
        neu.setProcessor(this.processor);
        return neu;
    }

    public String toString(){
        return "TerrainSensor "+name;
    }
}
