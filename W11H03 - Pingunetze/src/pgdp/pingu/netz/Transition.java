package pgdp.pingu.netz;

public abstract class Transition implements Runnable {
    protected Edge[] edges;
    protected Transition(Edge[] edges) {
        this.edges = edges;
    }
    abstract boolean tryFire();
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            tryFire();
        }
    }
}
