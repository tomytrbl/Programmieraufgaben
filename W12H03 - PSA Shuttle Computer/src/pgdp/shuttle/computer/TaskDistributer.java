package pgdp.shuttle.computer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import pgdp.shuttle.tasks.ErrorlessTaskGenerator;
import pgdp.shuttle.tasks.ShuttleTask;
import pgdp.shuttle.tasks.TaskGenerator;

/**
 * This class generates tasks using the provided TaskGenerator until it reaches tasksToGenerate or it's shut down,
 * and sends them to each ShuttleProcessors.
 */
public class TaskDistributer extends Thread implements ShuttleComputerComponent {
	private final long tasksToGenerate;
	private final List<ShuttleProcessor> processors;
	private final TaskGenerator generator;
	private boolean end = false;

	private long currentTaskCount;

	public TaskDistributer(long tasksToGenerate, List<ShuttleProcessor> processors, TaskGenerator generator) {
		this.tasksToGenerate = tasksToGenerate;
		this.processors = processors;
		this.generator = generator;
		currentTaskCount = 0;
	}

	@Override
	public void run() {
		System.out.println("TaskDistributer starting to generate tasks.");
		while (tasksToGenerate != currentTaskCount && !end && !this.isInterrupted()) {
			ShuttleTask<?, ?> Task = generator.generateTask();
			for (ShuttleProcessor sp : processors) {
				synchronized (sp) {
					try {
						sp.addTask(Task);
						sp.notify();
					} catch (InterruptedException e) {

						this.interrupt();
						break;
					}
				}
			}
			currentTaskCount++;
		}
		if (!this.isInterrupted()) {
			System.out.println("TaskDistributer finished generating " + currentTaskCount + "/" + tasksToGenerate + " tasks. Shutting down.");
		} else System.out.println("TaskDistributer was interrupted after " + currentTaskCount + " tasks!");
	}

	@Override
	public void shutDown() {
		end = true;
	}
}
