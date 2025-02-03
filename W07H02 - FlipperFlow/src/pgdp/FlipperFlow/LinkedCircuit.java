package pgdp.FlipperFlow;

import pgdp.FlipperFlow.Components.Cable;
import pgdp.FlipperFlow.Components.PowerSource;
import pgdp.FlipperFlow.Components.Resistor;

public class LinkedCircuit<T extends Cable, R extends PowerSource> {
    private CableLink start;
    private CableLink end;
    private R powerSource;

    public class CableLink {
        private T cable;
        private CableLink next;

        public CableLink(T cable) {
            this.cable = cable;
            next = null;
        }
        public void propagate() {
            if (next != null) {
                next.cable.setCurrentInput(this.cable.getCurrentOutput());
                next.cable.setVoltageInput(this.cable.getVoltageOutput());
            }
        }
        public CableLink getNext() {
            return next;
        }
        public T getCable() {
            return cable;
        }
    }
    //Vllt brauchen wir keinen Konstruktor
//    public LinkedCircuit(T cable, R powerSource) {
//        this.start= new CableLink(cable);
//        this.powerSource = powerSource;
//    }

    public void link(T t){
        if(start == null){
            start = end = new CableLink(t);
            return;
        }
        if (end == null) {
            end = new CableLink(t);
            start.next = end;
        } else {
            end.next = new CableLink(t);
            end = end.next;
        }
    }
    public boolean isClosed() {
        return powerSource != null;
    }
    public CableLink getEnd() {
        return end;
    }
    public R getPowerSource() {
        return powerSource;
    }
    public CableLink getStart() {
        return start;
    }
    public void setPowerSource(R powerSource) {
        this.powerSource = powerSource;
    }
    public double getTotalResistance(){
        double ret = 0;
        CableLink current = start;
        while (current != null){
            ret += current.getCable().getResistance();
            current = current.next;
        }
        return ret;
    }
    public String printStatusReport(){
        String ret = "";
        CableLink current = start;
        while (current!= null){
            if(current.getCable() instanceof Resistor){
                ret += current.getCable().toString();
            }
            current = current.next;
        }
        return ret;
    }
    public void setStart(T start) {
        this.start = new CableLink(start);
    }
}

