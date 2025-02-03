package pgdp.shuttle.computer;

import pgdp.shuttle.tasks.ErrorProneTaskGenerator;
import pgdp.shuttle.tasks.ErrorlessTaskGenerator;
import pgdp.shuttle.tasks.TaskGenerator;

import java.nio.channels.ScatteringByteChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * This class assembles all ShuttleComputerComponents together
 */
public class ShuttleComputer extends Thread {

    private final long tasksToGenerate;
    private final TaskGenerator generator;
    private final long sleepTime;

    public ShuttleComputer(long tasksToGenerate, TaskGenerator generator, long sleepTime) {
        this.tasksToGenerate = tasksToGenerate;
        this.generator = generator;
        this.sleepTime = sleepTime;
    }

    @Override
    public void run() {

        boolean scint = false;
        boolean tdint = false;

        System.out.println("ShuttleComputer booting up.");

        List<ShuttleProcessor> spList = new ArrayList<>();
        ShuttleOutput shuttleOutput = new ShuttleOutput();

        TaskChecker taskChecker = new TaskChecker(spList, shuttleOutput);
        for (int i = 0; i < 4; i++) {
            spList.add(new ShuttleProcessor(taskChecker));
        }
        TaskDistributer taskDistributer = new TaskDistributer(tasksToGenerate, spList, generator);

        taskDistributer.start();
        for (ShuttleProcessor sp : spList) {
            sp.start();
        }

        taskChecker.start();
        shuttleOutput.start();

        try {
            sleep(sleepTime);
        } catch (InterruptedException e) {
            tdint = true;
        }

        taskDistributer.shutDown();
        try {
            taskDistributer.join();
        } catch (InterruptedException e) {
            tdint = true;
        }

        taskChecker.shutDown();
        shuttleOutput.shutDown();
        for (ShuttleProcessor sp : spList) {
            sp.shutDown();
        }

        for (ShuttleProcessor sp : spList) {
            try {
                sp.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            taskChecker.join();
            shuttleOutput.join();
        } catch (InterruptedException ex) {
            scint = true;
        }

        if (tdint) {
            System.out.println("ShuttleComputer crashed (interrupted while waiting for TaskDistributer)!");
        } else if (scint) {
            System.out.println("ShuttleComputer crashed (interrupted while waiting for ShuttleComputerComponent)!");
        } else System.out.println("ShuttleComputer shutting down.");
    }

    public static void main(String[] args) {
    }
}

