package pgdp.pingu.netz;

import java.util.ArrayList;

public class PinguNet {
    private Transition[] transitions;
    Thread[] transitionThreads;

    public PinguNet(Transition[] transitions) {
        this.transitions = transitions;

        // TODO: Do your programming magic! 🐧
        Thread[] res = new Thread[transitions.length];
        for (int i = 0; i < transitions.length; i++) {
                res[i] = new Thread(transitions[i]);
        }
        this.transitionThreads = res;
    }

    public void start() {
        // TODO: Do your programming magic! 🐧
        for (Thread t : transitionThreads) {
            t.start();
        }
    }

    public void stop() {
        // TODO: Do your programming magic! 🐧
        for (Thread t : transitionThreads) {
            t.interrupt();
        }
    }

    public Transition[] getTransitions() {
        return transitions;
    }

    public static void main(String[] args) throws InterruptedException {
        Place p1 = new Place("ClientsInWaitQueue", 0);
        Place p2 = new Place("VerbindungIstAufgebaut",0);
        Place p3 = new Place("ClientIstAuthentifiziert",0);
        Place p4 = new Place("MainThread",0);
        Place p5 = new Place("SecondaryThread",0);
        Place p6 = new Place("DatenbankIstAktualisiert",0);
        Place p7 = new Place("EMailsSindVersendet",0);
        Place p8 = new Place("StatusaenderungIstHinterlegt",0);
        Place p9 = new Place("ServerIstEmpfangsbereit",1);
        Edge e1 = Edge.to(p1, 1);
        Edge e2 = Edge.from(p1, 1);
        Edge e3 = Edge.to(p2, 1);
        Edge e4 = Edge.from(p2, 1);
        Edge e5 = Edge.to(p3, 1);
        Edge e6 = Edge.from(p3, 1);
        Edge e7 = Edge.to(p4, 1);
        Edge e8 = Edge.from(p4, 1);
        Edge e9 = Edge.to(p5, 1);
        Edge e10 = Edge.from(p5, 1);
        Edge e11 = Edge.to(p6, 1);
        Edge e12 = Edge.from(p6, 1);
        Edge e13 = Edge.to(p7, 1);
        Edge e14 = Edge.from(p7, 1);
        Edge e15 = Edge.to(p8, 1);
        Edge e16 = Edge.from(p8, 1);
        Edge e17 = Edge.to(p9, 1);
        Edge e18 = Edge.from(p9, 1);
        Edge e19 = Edge.to(p9, 1);
        Edge e20 = Edge.from(p2, 1);
        Transition t1 = new BasicTransition(new Edge[] { e1 });
        Transition t2 = new BasicTransition(new Edge[] { e2, e18, e3 });
        Transition t3 = new BasicTransition(new Edge[] { e4, e5 });
        Transition t4 = new BasicTransition(new Edge[] { e6, e7, e9 });
        Transition t5 = new BasicTransition(new Edge[] { e8, e11 });
        Transition t6 = new BasicTransition(new Edge[] { e12, e14, e15 });
        Transition t7 = new BasicTransition(new Edge[] { e10, e13 });
        Transition t8 = new BasicTransition(new Edge[] { e16, e17 });
        Transition t9 = new BasicTransition(new Edge[] { e19, e20 });
        PinguNet pinguNet = new PinguNet(new Transition[] { t1, t2, t3, t4, t5, t6, t7, t8, t9 });
        Graphviz.writeGraphizToFile(pinguNet, "pingunetz.txt");
        pinguNet.start();
        Thread.sleep(1000);
        pinguNet.stop();
    }
}
