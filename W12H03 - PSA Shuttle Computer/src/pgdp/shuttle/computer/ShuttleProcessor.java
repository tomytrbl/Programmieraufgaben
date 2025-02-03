package pgdp.shuttle.computer;

import java.util.concurrent.LinkedBlockingQueue;

import pgdp.shuttle.tasks.ShuttleTask;

/**
 * This class evaluates tasks and then passes them to the TaskChecker until it is shut down.
 * Tasks in priorityTaskQueue are preferred over Tasks in taskQueue.
 */
public class ShuttleProcessor extends Thread implements ShuttleComputerComponent {
    private final LinkedBlockingQueue<ShuttleTask<?, ?>> taskQueue;
    private final LinkedBlockingQueue<ShuttleTask<?, ?>> priorityTaskQueue;
    private final TaskChecker checker;
    private boolean shutDown = false;

    public ShuttleProcessor(TaskChecker checker) {
        taskQueue = new LinkedBlockingQueue<>();
        priorityTaskQueue = new LinkedBlockingQueue<>();
        this.checker = checker;
    }

    public void addTask(ShuttleTask<?, ?> task) throws InterruptedException {
        taskQueue.put(task);
    }

    public void addPriorityTask(ShuttleTask<?, ?> task) throws InterruptedException {
        priorityTaskQueue.put(task);
    }

    @Override
    public void run() {
        // TODO
        synchronized (this) {
            while (!this.isInterrupted() && !shutDown) {
                ShuttleTask<?, ?> nextTask = null;
                if (!priorityTaskQueue.isEmpty()) {
                    nextTask = priorityTaskQueue.poll();
                } else if (!taskQueue.isEmpty()) {
                    nextTask = taskQueue.poll();
                }
                synchronized (nextTask) {
                    nextTask.evaluate();
                }
                try {
                    if (!checker.getTaskQueue().contains(nextTask)) {
                        checker.addTask(nextTask);

                    }
                } catch (InterruptedException e) {
                    this.isInterrupted();
                }
            }
        }
        if (!this.isInterrupted()) {
            System.out.println("ShuttleProcessor shutting down.");
        } else System.out.println("ShuttleProcessor was interrupted. Shutting down.");
    }

    @Override
    public void shutDown() {
        // TODO
        shutDown = true;
    }
}
//