package pgdp.shuttle.computer;

import java.util.concurrent.LinkedBlockingQueue;

import pgdp.shuttle.tasks.ShuttleTask;

/**
 *	This class outputs Task results until it's shut down.
 */
public class ShuttleOutput extends Thread implements ShuttleComputerComponent {
	private final LinkedBlockingQueue<ShuttleTask<?, ?>> taskQueue;
	private boolean shutDown = false;

	public ShuttleOutput() {
		taskQueue = new LinkedBlockingQueue<>();
	}

	public void addTask(ShuttleTask<?, ?> task) throws InterruptedException {
		taskQueue.put(task);
	}

	@Override
	public void run() {
		while(!this.isInterrupted() && !shutDown){
			ShuttleTask<?,?> task = taskQueue.poll();
			if(task == null){
                try {
                    this.sleep(50);
					continue;
                } catch (InterruptedException e) {
					this.interrupt();}
            }
			System.out.println("Result: "+task.getResult().toString());
		}
		if(!this.isInterrupted()){
			System.out.println("ShuttleOutput shutting down.");
		} else System.out.println("ShuttleOutput was interrupted. Shutting down.");
	}

	@Override
	public void shutDown() {
		shutDown = true;
	}
}
//