package pgdp.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class KitchenManager {
    private final List<KitchenStation> stations;
    private final List<Chef> chefs;
    private final BlockingQueue<Order> orderQueue;
    private boolean running;
    private int activeOrders;

    public KitchenManager(List<KitchenStation> stations, int chefCount) {
        this.stations = stations;
        this.orderQueue = new LinkedBlockingQueue<>();
        this.chefs = new ArrayList<>();
        this.activeOrders = 0;

        for (int i = 0; i < chefCount; i++) {
            chefs.add(new Chef("Chef-" + i, orderQueue, this));
        }
    }

    public synchronized void start() {
        if (!running) {
            running = true;
            for (Chef c : chefs) {
                c.start();
                System.out.println("Thread: " + c.getName() + " got started");
            }
        }
        // TODO
    }

    public synchronized void stop() {
        // TODO
        if (running) {
            running = false;
            for (Chef c : chefs) {
                c.interrupt();
            }
            orderQueue.clear();
            activeOrders = 0;
        }
    }

    public synchronized void submitOrder(Order order) {
        // TODO
        if (running) {
            orderQueue.add(order);
            activeOrders++;
        }
    }

    public synchronized void removeOrder(Order order) {
        // TODO
        activeOrders--;
    }

    public synchronized int getActiveOrders() {
        return activeOrders;
    }

    public boolean isRunning() {
        return running;
    }
}
