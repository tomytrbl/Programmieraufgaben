package pgdp.pingu.netz;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Graphviz {
    public static String toGraphviz(PinguNet pinguNet) {
        Transition[] transitions = pinguNet.getTransitions();
        StringBuilder graphviz = new StringBuilder();
        graphviz.append("digraph PinguNet {\n");
        graphviz.append("  rankdir=LR;\n"); // Layout: Left to Right

        Set<String> nodes = new HashSet<>();

        for (int i = 0; i < transitions.length; i++) {
            BasicTransition transition = (BasicTransition) transitions[i];
            String transitionName = "T" + (i + 1);

            // Add Transition node
            graphviz.append("  ").append(transitionName).append(" [shape=rectangle, label=\"")
                    .append(transitionName).append("\"];\n");

            for (Edge edge : transition.edges) {
                // Incoming edges
                edge.getFrom().ifPresent(place -> {
                    String placeLabel = place.getLabel();
                    if (!nodes.contains(placeLabel)) {
                        graphviz.append("  ").append(placeLabel).append(" [shape=circle, label=\"")
                                .append(placeLabel).append("\"];\n");
                        nodes.add(placeLabel);
                    }
                    graphviz.append("  ").append(placeLabel).append(" -> ").append(transitionName)
                            .append(" [label=\"").append(edge.getWeight()).append("\"];\n");
                });

                // Outgoing edges
                edge.getTo().ifPresent(place -> {
                    String placeLabel = place.getLabel();
                    if (!nodes.contains(placeLabel)) {
                        graphviz.append("  ").append(placeLabel).append(" [shape=circle, label=\"")
                                .append(placeLabel).append("\"];\n");
                        nodes.add(placeLabel);
                    }
                    graphviz.append("  ").append(transitionName).append(" -> ").append(placeLabel)
                            .append(" [label=\"").append(edge.getWeight()).append("\"];\n");
                });
            }
        }

        graphviz.append("}");
        return graphviz.toString();
    }

    public static void writeGraphizToFile(PinguNet pinguNet, String path){
        String g = toGraphviz(pinguNet);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))){
            writer.write(g);
        }catch (IOException e){
            System.err.println("Error writing to file.");
        }
    }
}
