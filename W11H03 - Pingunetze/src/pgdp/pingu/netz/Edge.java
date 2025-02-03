package pgdp.pingu.netz;

import java.util.Optional;

public class Edge {
    private final Place from;
    private final Place to;
    private final int weight;

    private Edge(Place from, Place to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    public static Edge to(Place to, int weight) {
        return new Edge(null, to, weight);
    }
    public static Edge from(Place from, int weight) {
        return new Edge(from, null, weight);
    }

    public Optional<Place> getTo() {
        return Optional.ofNullable(to);
    }

    public Optional<Place> getFrom() {
        return Optional.ofNullable(from);
    }

    public int getWeight() {
        return weight;
    }
}
