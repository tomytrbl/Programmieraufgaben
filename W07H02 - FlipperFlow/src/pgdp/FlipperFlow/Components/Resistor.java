package pgdp.FlipperFlow.Components;

import pgdp.FlipperFlow.Helpers.Vector2;

public class Resistor extends Cable{
    private double minCurrent;
    private double maxCurrent;
    private double minVoltage;
    private double maxVoltage;
    private Vector2 positionInGrid;

    public Resistor(double resistance, double minCurrent, double minVoltage, double maxCurrent, double maxVoltage){
        this.minCurrent = minCurrent;
        this.minVoltage = minVoltage;
        this.maxCurrent = maxCurrent;
        this.maxVoltage = maxVoltage;
        setResistance(resistance);

    }

    public String getStatus(){
        if((getCurrentInput() <= maxCurrent && getCurrentInput() >= minCurrent) && ( minVoltage <= getVoltageInput() && getVoltageInput() <= maxVoltage)){
            return "WORKING";
        }else return "NOT WORKING";
    }

    @Override
    public double getVoltageOutput(){
        return getVoltageInput() - getResistance() * getCurrentInput();
    }

    public void setPositionInGrid(Vector2 vector){
        positionInGrid = vector;
    }
//
    @Override
    public String toString(){
        String position = positionInGrid != null ? positionInGrid.toString() :  "null";
        return "HI I AM RESISTOR AT: "+position+", CURRENTLY "+getStatus()+"\n" +
                "MY STATS ARE: RESISTANCE "+String.format("%.2f",getResistance())+", CURRENT "+String.format("%.2f",getCurrentInput())+", VOLTAGE_INPUT "+String.format("%.2f",getVoltageInput())+", "+
                "VOLTAGE_OUTPUT "+String.format("%.2f", getVoltageOutput())+"\n"+
                "MY SPECIFICATIONS ARE: MIN_CURRENT "+String.format("%.2f",minCurrent)+", MAX_CURRENT "+String.format("%.2f", maxCurrent)+", MIN_VOLTAGE "+String.format("%.2f", minVoltage)+", MAX_VOLTAGE "+String.format("%.2f", maxVoltage)+"\n";
    }


}
