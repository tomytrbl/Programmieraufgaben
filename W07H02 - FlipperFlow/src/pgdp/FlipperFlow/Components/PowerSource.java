package pgdp.FlipperFlow.Components;

import pgdp.FlipperFlow.Helpers.Direction;

public class PowerSource extends Piece {
    private double voltage;
    private double current;

    public PowerSource(Direction inputDirection, Direction outputDirection){
        voltage = 10;
        current = 0;
        super.setInputDirection(inputDirection);
        super.setOutputDirection(outputDirection);

    }

    public void setVoltage(double voltage) {
        this.voltage = voltage;
    }

    public void setCurrent(double current) {
        this.current = current;
    }

    public double getVoltage() {
        return voltage;
    }

    public double getCurrent() {
        return current;
    }


}
