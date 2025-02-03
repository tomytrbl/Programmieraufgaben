package pgdp.FlipperFlow.Components;

public class Cable extends Piece implements  Conductive{
    private double currentInput;
    private double voltageInput;

    @Override
    public double getCurrentInput() {
        return currentInput;
    }

    @Override
    public double getCurrentOutput() {
        return this.getCurrentInput();
    }

    @Override
    public double getVoltageInput() {
        return voltageInput;
    }

    @Override
    public double getVoltageOutput() {
        return this.getVoltageInput();
    }

    public void setCurrentInput(double amount){
        currentInput = amount;
    }

    public void setVoltageInput(double amount){
        voltageInput = amount;
    }

}
