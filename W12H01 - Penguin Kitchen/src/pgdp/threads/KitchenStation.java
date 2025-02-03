package pgdp.threads;

import java.util.concurrent.Semaphore;

public class KitchenStation {

    private final String name;
    private Semaphore capacity;


    public KitchenStation(String name, int capacity) {
        this.name = name;
        this.capacity = new Semaphore(capacity);
        // TODO
    }

    public boolean validateOrder(Order order) {
        return System.currentTimeMillis() - order.getCreationTime() <= order.getMaxPrepTime();
    }

    public void processOrder(Order order) throws InterruptedException {
            capacity.acquire();
            order.cook();
            capacity.release();
        // TODO
    }

    public String getName() {
        return name;
    }

}