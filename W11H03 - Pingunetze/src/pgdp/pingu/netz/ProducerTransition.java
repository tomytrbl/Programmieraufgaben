package pgdp.pingu.netz;

import java.util.stream.Stream;

public class ProducerTransition extends Transition {
    public ProducerTransition(Edge[] edges) {
        super(edges);
    }
    @Override
    public boolean tryFire() {
        // TODO: Do your programming magic! 🐧
        //Kanten filtern die zu Places führen
        //an dem Place lock.
        //dann add mit weight der Kante
        //unlock
        Edge[] filteredEdges = Stream.of(edges)
                .filter(edge -> edge.getFrom().isEmpty())
                .filter(edge -> edge.getTo().isPresent())
                .toArray(Edge[]::new);
        Place[] place = Stream.of(filteredEdges).map(edge -> edge.getTo().get()).toArray(Place[]::new);
        for (int i = 0; i < place.length; i++) {
            place[i].lockPlace();
            place[i].addToken(filteredEdges[i].getWeight());
            place[i].unlockPlace();
        }
        return true;
    }
}
