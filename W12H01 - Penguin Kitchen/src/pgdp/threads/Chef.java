
package pgdp.threads;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public class Chef extends Thread {
    private final BlockingQueue<Order> orderQueue;
    private final KitchenManager kitchen;

    public Chef(String name, BlockingQueue<Order> orderQueue, KitchenManager kitchen) {
        super(name);
        this.orderQueue = orderQueue;
        this.kitchen = kitchen;
    }

    @Override
    public void run() {
        // TODO
        while(!Thread.currentThread().isInterrupted() && kitchen.isRunning()){
            Order current;
            synchronized (kitchen){
            current = orderQueue.poll();}
            if(current == null) continue;
            System.out.println(Thread.currentThread().getName() + "just got " + current.getOrderId());


            boolean validated = true;

            for (KitchenStation ks : current.getRequiredStations()) {
                validated = ks.validateOrder(current);
                if(!validated) {
                    break;
                }
                try {
                    processAtStation(current, ks);
                    System.out.println(Thread.currentThread().getName() + " just processed Order " +current.getOrderId() + "at the KitchenStation: " + ks.getName() );
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            if(!validated){
                System.out.println("The Order: "+current.getOrderId() + " was not finished due to time");
            } System.out.println("The Order: "+current.getOrderId() + " was just succesfully finished by " + Thread.currentThread().getName());
            kitchen.removeOrder(current);
        }
    }

    private boolean processAtStation(Order order, KitchenStation station) throws InterruptedException {
        // TODO
        try {
            station.processOrder(order);
        } catch (InterruptedException e) {
            System.out.println("Order " + order.getOrderId() + " expired");
            Thread.currentThread().interrupt();
            return false;
        }

        return true;
    }
}