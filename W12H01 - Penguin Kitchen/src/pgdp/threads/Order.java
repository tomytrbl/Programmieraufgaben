package pgdp.threads;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private final int orderId;
    private final long creationTime;
    private final long maxPrepTime;
    private final List<KitchenStation> requiredStations;

    public Order(int orderId, long maxPrepTime, List<KitchenStation> stations) {
        this.orderId = orderId;
        this.maxPrepTime = maxPrepTime;
        this.creationTime = System.currentTimeMillis();
        this.requiredStations = new ArrayList<>(stations);
    }

    public void cook() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public int getOrderId() {
        return orderId;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public long getMaxPrepTime() {
        return maxPrepTime;
    }

    public List<KitchenStation> getRequiredStations() {
        return requiredStations;
    }
}
