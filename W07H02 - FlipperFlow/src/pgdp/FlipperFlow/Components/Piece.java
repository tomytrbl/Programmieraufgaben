package pgdp.FlipperFlow.Components;

import pgdp.FlipperFlow.Helpers.Direction;

public abstract class Piece {
    private boolean inserted;
    private double resistance;
    private Direction inputDirection;
    private Direction outputDirection;

    public Piece(){
        inputDirection = null;
        outputDirection = null;
        resistance = 0;
    }

    public boolean isInserted() {
        return inserted;
    }

    public void setInputDirection(Direction inputDirection) {
        if(!isInserted()){
        this.inputDirection = inputDirection;
        inserted = true;}
    }

    public void setOutputDirection(Direction outputDirection) {
        if(this.outputDirection == null){
        this.outputDirection = outputDirection;}
    }

    public Direction getInputDirection(){
        return inputDirection;
    };

    public double getResistance(){
        return resistance;
    }

    public void setResistance(double amount){
        this.resistance = amount;
    }

    public Direction getOutputDirection() {
        return outputDirection;
    }
}
