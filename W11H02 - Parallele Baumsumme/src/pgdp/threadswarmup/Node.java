package pgdp.threadswarmup;

import java.util.Optional;

class Node {
    private int value;
    private Optional<Node> left;
    private Optional<Node> right;

    public Node(int value) {
        this.value = value;
        this.left = Optional.empty();
        this.right = Optional.empty();
    }


    public void setLeft(Node left) {
        this.left = Optional.of(left);
    }

    public void setRight(Node right) {
        this.right = Optional.of(right);
    }

    public Optional<Node> getLeft() {
        return left;
    }

    public Optional<Node> getRight() {
        return right;
    }

    public int getValue() {
        return value;
    }

    public int sum() {
        // TODO Exercise 1

        int result = 0;
        result += value;

        if(left.isPresent()){
            result += left.get().sum();
        }
        if(right.isPresent()){
            result += right.get().sum();
        }

        return result;
    }

    public int sumWithThreads(int numberOfThreads) {
        SumThread sumThread = new SumThread(this, numberOfThreads);
        sumThread.start();

        try {
            sumThread.join();
        } catch (InterruptedException e) {
            System.out.println("Thread has been interrupted:");
            e.printStackTrace();
        }
        return sumThread.getSum().get();
    }
}
