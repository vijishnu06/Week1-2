import java.util.*;

class week1 {

    // productId -> stock count
    private HashMap<String, Integer> inventory = new HashMap<>();

    // productId -> waiting list (FIFO)
    private HashMap<String, Queue<Integer>> waitingList = new HashMap<>();

    public FlashSaleInventory() {
        inventory.put("IPHONE15_256GB", 100);
        waitingList.put("IPHONE15_256GB", new LinkedList<>());
    }

    // Check stock
    public int checkStock(String productId) {
        return inventory.getOrDefault(productId, 0);
    }

    // Purchase item (thread-safe)
    public synchronized String purchaseItem(String productId, int userId) {

        int stock = inventory.getOrDefault(productId, 0);

        if (stock > 0) {
            inventory.put(productId, stock - 1);
            return "Success, " + (stock - 1) + " units remaining";
        }
        else {
            Queue<Integer> queue = waitingList.get(productId);
            queue.add(userId);
            return "Added to waiting list, position #" + queue.size();
        }
    }

    // Display waiting list
    public void showWaitingList(String productId) {
        System.out.println("Waiting List: " + waitingList.get(productId));
    }
}

public class week{

    public static void main(String[] args) {

        FlashSaleInventory system = new FlashSaleInventory();

        System.out.println("Stock: " + system.checkStock("IPHONE15_256GB"));

        System.out.println(system.purchaseItem("IPHONE15_256GB", 12345));
        System.out.println(system.purchaseItem("IPHONE15_256GB", 67890));

        // simulate stock exhaustion
        for(int i = 0; i < 100; i++) {
            system.purchaseItem("IPHONE15_256GB", i);
        }

        System.out.println(system.purchaseItem("IPHONE15_256GB", 99999));

        system.showWaitingList("IPHONE15_256GB");
    }
}