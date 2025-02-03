package pgdp.shuttle.computer;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import pgdp.shuttle.tasks.ShuttleTask;

/**
 * This class checks, whether a Task was evaluated successful or not and
 * either passes them to the ShuttleOutput or returns them to the ShuttleProcessors
 * until it is shutDown.
 */
public class TaskChecker extends Thread implements ShuttleComputerComponent {
    private final LinkedBlockingQueue<ShuttleTask<?, ?>> taskQueue;
    private final List<ShuttleProcessor> processors;
    private final ShuttleOutput output;
    private boolean shutDown = false;

    public TaskChecker(List<ShuttleProcessor> processors, ShuttleOutput output) {
        taskQueue = new LinkedBlockingQueue<>();
        this.processors = processors;
        this.output = output;
    }

    public void addTask(ShuttleTask<?, ?> task) throws InterruptedException {
        taskQueue.add(task);
    }

    @Override
    public void run() {
        // TODO

        while (!this.isInterrupted() && !shutDown) {

            ShuttleTask<?, ?> task = taskQueue.peek();
            if (task == null) {
                continue;
            }
            boolean s = task.computationSuccessfull();
            if (!s) {
                task.reset();
                for (ShuttleProcessor p : processors) {
                    try {
                        p.addPriorityTask(task);
                    } catch (InterruptedException e) {
                        this.interrupt();
                        break;
                    }
                }
            } else {
                try {
                    output.addTask(task);
                } catch (InterruptedException e) {
                    this.interrupt();
                    break;
                }
            }
            taskQueue.removeIf(i -> i.equals(task));

        }
        if (!this.isInterrupted()) {
            System.out.println("TaskChecker shutting down.");
        } else System.out.println("TaskChecker was interrupted. Shutting down.");
    }

    @Override
    public void shutDown() {
        // TODO
        shutDown = true;
    }

    public LinkedBlockingQueue<ShuttleTask<?, ?>> getTaskQueue() {
        return taskQueue;
    }
}
//
