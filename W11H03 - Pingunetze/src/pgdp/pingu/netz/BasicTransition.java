package pgdp.pingu.netz;


import java.util.Collections;
import java.util.stream.Stream;

public class BasicTransition extends Transition {
    public BasicTransition(Edge[] edges) {
        super(edges);
    }

    @Override
    public boolean tryFire() {
        // TODO: Do your programming magic! 🐧
        //filter alle eingehende Places
        //einfach weights in einem array
        Edge[] filteredEdges = Stream.of(edges)
                .filter(edge -> edge.getFrom().isPresent() && edge.getTo().isEmpty())
                .toArray(Edge[]::new);

        Place[] place = Stream.of(filteredEdges)
                .map(edge -> edge.getFrom().get())
                .toArray(Place[]::new);

        Edge[] filteredEdgesToGive = Stream.of(edges)
                .filter(edge -> edge.getFrom().isEmpty() && edge.getTo().isPresent())
                .toArray(Edge[]::new);

        Place[] placeToGive = Stream.of(filteredEdgesToGive)
                .map(edge -> edge.getTo().get()).toArray(Place[]::new);

        for(Place p : placeToGive){
            p.lockPlace();}
        for(Place p : place){
                p.lockPlace();
        } // alle reciever locken

        boolean enoughTokens = true; //check ob alle genug haben
        for (int i = 0; i < place.length; i++) {
            if(!place[i].hasEnoughTokens(filteredEdges[i].getWeight())){
                enoughTokens = false;
                break;
            }
        }
        int ToGiveTokens = 0;
        if(!enoughTokens){
            for(Place p : place){
                p.unlockPlace();
            }
            for(Place p : placeToGive){
                p.unlockPlace();
            }
            return false; // wenn flag false; unlock all, return false
        }else {
            for (int i = 0; i < place.length; i++) {
                place[i].consumeToken(filteredEdges[i].getWeight());

                ToGiveTokens += filteredEdges[i].getWeight();
            }
            for (int j = 0; j < placeToGive.length; j++) {
                placeToGive[j].addToken(filteredEdgesToGive[j].getWeight());
            }
        }
        for(Place p: place){
            p.unlockPlace();
        }
        for(Place p : placeToGive){
            p.unlockPlace();
        }//und auf alle verteilen
        return true;

//        int recievingIndex = 0;

//        while(ToGiveTokens > 0 && recievingIndex < placeToGive.length){
//            Edge toGiveEdge = filteredEdgesToGive[recievingIndex];
//            Place toGivePlace = placeToGive[recievingIndex];
//            int toGiveEdgeWeight = toGiveEdge.getWeight();
//
//            int TokensBefore = ToGiveTokens;
//
//            if(ToGiveTokens >= toGiveEdgeWeight){ // normal case
//                toGivePlace.addToken(toGiveEdgeWeight);
//                ToGiveTokens -= toGiveEdgeWeight;
////            } else {
////                toGivePlace.addToken(ToGiveTokens);
////                ToGiveTokens = 0;
//
//            }
//            recievingIndex++;

//        }
//

    }
}
