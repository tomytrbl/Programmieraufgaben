package pgdp.threadswarmup;

import java.util.Optional;

public class SumThread extends Thread {
    private Optional<Integer> sum;

    private Node node;

    private int remainingThreads;

    SumThread leftThread;
    SumThread rightThread;


    public SumThread(Node node, int remainingThreads) {
        this.node = node;
        this.remainingThreads = remainingThreads;
        this.sum = Optional.empty();
        // TODO Exercise 2
    }

    public Optional<Integer> getSum() {
        return sum;
    }

    public void run() {
        // TODO Exercise 5
        int sum = node.getValue();

        startChildThreads();
        if(leftThread != null) {
            try {
                leftThread.join();
                sum += leftThread.getSum().orElse(0);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }else if(node.getLeft().isPresent()) sum += node.getLeft().get().sum();

        if(rightThread != null) {
            try {
                rightThread.join();
                sum += rightThread.getSum().orElse(0);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }else if(node.getRight().isPresent()) sum += node.getRight().get().sum();

        this.sum = Optional.of(sum);
    }


    protected void startChildThreads() {
        // TODO Exercise 4
        int rightTheadCount = remainingThreads - leftThreadCount() -1;

        if(leftThreadCount() != 0 && node.getLeft().isPresent()){
            leftThread = new SumThread(node.getLeft().orElse(null), leftThreadCount());
            leftThread.start();}
        if(rightTheadCount != 0 && node.getRight().isPresent()){
            rightThread = new SumThread(node.getRight().orElse(null), rightTheadCount);
        rightThread.start();}
    }

    protected int leftThreadCount() {
        // TODO Exercise 3
        return (node.getLeft().isEmpty()) ? 0 : (node.getRight().isEmpty()) ? remainingThreads-1 : (remainingThreads-1)/2;
    }
}
