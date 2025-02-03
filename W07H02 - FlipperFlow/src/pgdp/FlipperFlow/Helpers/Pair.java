package pgdp.FlipperFlow.Helpers;

import java.util.Objects;

public class Pair<T, R> {
    private T first;
    private R second;

    public Pair(T first, R second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst(){
        return first;}
    public R getSecond(){
        return second;}
    public void setFirst(T first){
        this.first = first;
    }
    public void setSecond(R second){
        this.second = second;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Pair<?, ?> pair = (Pair<?, ?>) object;

        return Objects.equals(pair.first, first) && Objects.equals(pair.second, second);
    }


}
