

import java.util.*;

class InventoryManager {

    private HashMap<String, Integer> stock;
    private LinkedHashMap<String, Queue<Integer>> waitingList;

    public InventoryManager() {
        stock = new HashMap<>();
        waitingList = new LinkedHashMap<>();
        stock.put("IPHONE15_256GB", 100);
    }

    public synchronized void checkStock(String productId) {
        int count = stock.getOrDefault(productId, 0);
        System.out.println(productId + " → " + count + " units available");
    }

    public synchronized void purchaseItem(String productId, int userId) {

        int available = stock.getOrDefault(productId, 0);

        if (available > 0) {
            stock.put(productId, available - 1);
            System.out.println("purchaseItem(\"" + productId + "\", userId=" + userId + ") → Success, " + (available - 1) + " units remaining");
        } else {

            waitingList.putIfAbsent(productId, new LinkedList<>());
            Queue<Integer> queue = waitingList.get(productId);
            queue.offer(userId);

            System.out.println("purchaseItem(\"" + productId + "\", userId=" + userId + ") → Added to waiting list, position #" + queue.size());
        }
    }
}

public class FlashSaleInventoryManager {

    public static void main(String[] args) {

        InventoryManager manager = new InventoryManager();

        manager.checkStock("IPHONE15_256GB");

        manager.purchaseItem("IPHONE15_256GB", 12345);
        manager.purchaseItem("IPHONE15_256GB", 67890);

        for (int i = 0; i < 100; i++) {
            manager.purchaseItem("IPHONE15_256GB", 10000 + i);
        }

        manager.purchaseItem("IPHONE15_256GB", 99999);
    }
}
