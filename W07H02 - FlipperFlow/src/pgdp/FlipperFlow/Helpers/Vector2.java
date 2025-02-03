package pgdp.FlipperFlow.Helpers;

import pgdp.FlipperFlow.Components.PowerSource;
import pgdp.FlipperFlow.Components.Resistor;

import java.util.Objects;

import static pgdp.FlipperFlow.Helpers.Direction.*;

public class Vector2 {
    private Pair<Integer, Integer> xy;

    public Vector2(int x, int y) {
        xy = new Pair<Integer, Integer>(x, y);
    }

    public void setX(int newx) {
        xy.setFirst(newx);
    }

    public void setY(int newy) {
        xy.setSecond(newy);
    }

    public int getX() {
        return xy.getFirst();
    }

    public int getY() {
        return xy.getSecond();
    }

    public Vector2 add(Vector2 other) {
        Vector2 newVector2 = new Vector2(getX(),getY());
        newVector2.xy.setFirst(getX() + other.getX());
        newVector2.xy.setSecond(getY() + other.getY());
        return newVector2;
    }

    @Override
    public String toString() {
        Integer x = getX();
        Integer y = getY();
        if(x == null || y == null){
            return null;
        }
        return "(" +x+ ", " + y + ")";
    }

    @Override
    public boolean equals(Object object) {
        Vector2 vector2 = (Vector2) object;
        return getX() == vector2.getX() && getY() == vector2.getY();
    }


}