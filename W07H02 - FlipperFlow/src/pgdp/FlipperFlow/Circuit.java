package pgdp.FlipperFlow;

import pgdp.FlipperFlow.Components.Cable;
import pgdp.FlipperFlow.Components.Piece;
import pgdp.FlipperFlow.Components.PowerSource;
import pgdp.FlipperFlow.Components.Resistor;
import pgdp.FlipperFlow.Helpers.Direction;
import pgdp.FlipperFlow.Helpers.Vector2;

import static pgdp.FlipperFlow.Helpers.Direction.*;


public class Circuit {
    private Piece[][] grid;
    private Vector2 powerSourcePosition;
    private Vector2 openEnd;
    private int height;
    private int width;
    private final Vector2 closed;

    public Circuit(int width, int height, int sourceX, int sourceY){
        this.grid = new Piece[height][width];
        this.powerSourcePosition = new Vector2(sourceX,sourceY);

        PowerSource neu = null;
        if(sourceX == (width-1) && sourceY == (height-1)){
            neu = new PowerSource(LEFT, DOWN);
        } else if (sourceX == (width-1)) {
            neu = new PowerSource(LEFT, UP);
        } else if (sourceX == 0 && sourceY == 0) {
            neu = new PowerSource(UP, RIGHT);
        } else if (sourceX == 0) {
            neu = new PowerSource(DOWN, RIGHT);
        } else neu = new PowerSource(LEFT, RIGHT);

        grid[sourceY][sourceX] = neu;
        openEnd = powerSourcePosition;
        this.height = height;
        this.width = width;
        closed = powerSourcePosition.add(neu.getInputDirection().getVector());
        System.out.println(closed.getX() +"+"+ closed.getY());
    }

    public PowerSource getPowerSource(){
        return (PowerSource) grid[powerSourcePosition.getY()][powerSourcePosition.getX()];
    }

    public Vector2 getOpenEnd() {
        return openEnd;
    }

    public void setSourceVoltage(double amount){
        PowerSource currentPower = getPowerSource();
        currentPower.setVoltage(amount);

    }

    private void setSourceCurrent(double amount){
        PowerSource currentPower = getPowerSource();
        currentPower.setCurrent(amount);
    }

    public Piece getAt(int x, int y){
        if ((x > width || x < 0) || (y > height || y < 0) )
            return null;
        else if (grid[y][x] == null) {
            return null;
        }
        else return grid[y][x];
    }
    
    public boolean insertAt(Piece piece, int x, int y){
        if(openEnd == null){
            return false;}
        Vector2 neuerPiece = new Vector2(x,y);
        if(!positionIsEmpty(neuerPiece)){
            return false;}
        if (piece instanceof PowerSource){
            return false;
        }
        if(piece.isInserted()){
            return false;
        }
        boolean vorhanden = false;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (grid[j][i] != null) {
                    if (grid[j][i].equals(piece)) {
                        vorhanden = true;
                        break;
                    }
                }
            }
            if(vorhanden){
                break;
            }
        }
        if (vorhanden){
            return false;
        }
            //Ist die der Punkt an einem der drei Punkte wo es erlaubt ist von Open End aus
        Vector2[] erlaubteVektoren = getPossibleLinkPositions();
        for (int i = 0; i < erlaubteVektoren.length; i++) {
            if(erlaubteVektoren[i].equals(neuerPiece)){
                grid[y][x] = piece;
                grid[openEnd.getY()][openEnd.getX()].setOutputDirection(getDirectionFromCoordinates(openEnd, neuerPiece));
                grid[y][x].setInputDirection(getDirectionFromCoordinates(neuerPiece, openEnd));
                openEnd = neuerPiece;

                if(piece instanceof Resistor){
                    Resistor resistornew = (Resistor) grid[y][x];
                    resistornew.setPositionInGrid(neuerPiece);
                }
                if(neuerPiece.equals(closed)){
                    grid[y][x].setOutputDirection(getDirectionFromCoordinates(neuerPiece,powerSourcePosition));
                    openEnd = null;
                    break;
                }
            }
        }

    return true;}

    private boolean positionIsEmpty(Vector2 vector){
        int x = vector.getX();
        int y = vector.getY();
        if((x >= width || x < 0) || (y >= height || y < 0) ){
            return false;}
        if (grid[y][x] != null){
            return false;}
        else return true;
    }

    private Vector2[] getPossibleLinkPositions(){
        boolean onlyPower = true;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (grid[j][i] != null && !(j == powerSourcePosition.getY() && i == powerSourcePosition.getX())) {
                    onlyPower = false;
                    break;
                }
            }
            if(!onlyPower){
                break;
            }
        }

        if(onlyPower){
            Vector2 only = getCoordinatesFromDirection(getPowerSource().getOutputDirection(),powerSourcePosition);
            Vector2[] one = new Vector2[1];
            one[0] = only;
            return one;
        }

        Direction openEnddir = grid[openEnd.getY()][openEnd.getX()].getInputDirection();
        Vector2 erlaubt1 = null;
        Vector2 erlaubt2 = null;
        Vector2 erlaubt3 = null;

        if(openEnddir == UP){
            erlaubt1 = openEnd.add(DOWN.getVector());
            erlaubt2 = openEnd.add(LEFT.getVector());
            erlaubt3 = openEnd.add(RIGHT.getVector());
        } else if (openEnddir == DOWN) {
            erlaubt1 = openEnd.add(UP.getVector());
            erlaubt2 = openEnd.add(LEFT.getVector());
            erlaubt3 = openEnd.add(RIGHT.getVector());
        } else if (openEnddir == LEFT) {
            erlaubt1 = openEnd.add(UP.getVector());
            erlaubt2 = openEnd.add(DOWN.getVector());
            erlaubt3 = openEnd.add(RIGHT.getVector());
        }else if (openEnddir == RIGHT){
            erlaubt1 = openEnd.add(UP.getVector());
            erlaubt2 = openEnd.add(DOWN.getVector());
            erlaubt3 = openEnd.add(LEFT.getVector());
        }

        Vector2[] erlaubteVektoren = new Vector2[3];
        erlaubteVektoren[0] = erlaubt1;
        erlaubteVektoren[1] = erlaubt2;
        erlaubteVektoren[2] = erlaubt3;

        int richtigeVektoren = 0;
        for (int i = 0; i < erlaubteVektoren.length; i++) {
            int x = erlaubteVektoren[i].getX();
            int y = erlaubteVektoren[i]. getY();
            if(!(x > width || x < 0) || (y > height || y < 0)){
                richtigeVektoren++;
            }
        }

        Vector2[] ret = new Vector2[richtigeVektoren];
        int index = 0;

        for (int i = 0; i < erlaubteVektoren.length; i++) {
            int x = erlaubteVektoren[i].getX();
            int y = erlaubteVektoren[i]. getY();
            if(!(x > width || x < 0) || (y > height || y < 0)){
                ret[index] = erlaubteVektoren[i];
                index++;
            }
        }
        //prüfen ob außerhalb width oder height, dann beide auf null setzen
        return ret;
    }

    public LinkedCircuit<Cable, PowerSource> generateLinked() {
        if (openEnd != null) {
            return null;
        }
        PowerSource power = getPowerSource();

        Vector2 fristcable = powerSourcePosition.add(power.getOutputDirection().getVector());
        Cable cable = (Cable) grid[fristcable.getY()][fristcable.getX()];
        LinkedCircuit<Cable, PowerSource> newcircuit = new LinkedCircuit();

        newcircuit.setStart(cable);
        newcircuit.setPowerSource(power);
        Vector2 end = powerSourcePosition;

        LinkedCircuit<Cable, PowerSource>.CableLink current = newcircuit.getStart();
        Vector2 currposition = fristcable;

        while (!currposition.add(current.getCable().getOutputDirection().getVector()).equals(powerSourcePosition)) {
            currposition = currposition.add(current.getCable().getOutputDirection().getVector());
            newcircuit.link((Cable) getAt(currposition.getX(), currposition.getY()));
            current = current.getNext();
        }
        if (newcircuit.getStart().getNext() == null) {
            newcircuit.link(newcircuit.getStart().getCable());
        }
        return newcircuit;
    }

    public void propagate(){
        LinkedCircuit<Cable, PowerSource> newcircuit = generateLinked();
        if (newcircuit == null) {
            return;
        }

        double strom = getPowerSource().getVoltage() / newcircuit.getTotalResistance();
        setSourceCurrent(strom);

        LinkedCircuit<Cable, PowerSource>.CableLink currentLink = newcircuit.getStart();
        currentLink.getCable().setCurrentInput(strom);
        currentLink.getCable().setVoltageInput(getPowerSource().getVoltage());

        while(currentLink != null){
            currentLink.propagate();
            currentLink = currentLink.getNext();
        }
    }

    public String printStatusReport(){
        LinkedCircuit<Cable, PowerSource> newcircuit = generateLinked();
        if (newcircuit != null) {
            return newcircuit.printStatusReport();
        }
        return null;
    }

}
