package pgdp.threads;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        KitchenStation grill = new KitchenStation("Grill", 1);
        KitchenStation fryer = new KitchenStation("Fryer", 1);
        KitchenStation prep = new KitchenStation("Prep", 1);
        KitchenStation oven = new KitchenStation("Oven", 1);
        List<KitchenStation> stations = Arrays.asList(grill, fryer, prep, oven);

        int numberOfChefs = 3;
        KitchenManager manager = new KitchenManager(stations, numberOfChefs);

        manager.start();
        System.out.println("Starting Kitchen Manager with " + numberOfChefs + " chefs...");

        // Create and submit orders
        Order order1 = new Order(1, 5000, Arrays.asList(grill, prep));
        manager.submitOrder(order1);
        System.out.println("Submitted Order " + order1.getOrderId());

        Order order2 = new Order(2, 7000, Arrays.asList(fryer, prep));
        manager.submitOrder(order2);
        System.out.println("Submitted Order " + order2.getOrderId());

        Order order3 = new Order(3, 10000, Arrays.asList(grill));
        manager.submitOrder(order3);
        System.out.println("Submitted Order " + order3.getOrderId());

        Order order4 = new Order(4, 200, Arrays.asList(fryer, prep));
        manager.submitOrder(order4);
        System.out.println("Submitted Order " + order4.getOrderId());

        Order order5 = new Order(5, 8000, Arrays.asList(grill, oven, prep));
        manager.submitOrder(order5);
        System.out.println("Submitted Order " + order5.getOrderId());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Main thread interrupted while waiting.");
        }

        System.out.println("Active Orders before shutdown: " + manager.getActiveOrders());

        System.out.println("Stopping Kitchen Manager...");
        manager.stop();

        System.out.println("Active Orders after shutdown: " + manager.getActiveOrders());
    }
}