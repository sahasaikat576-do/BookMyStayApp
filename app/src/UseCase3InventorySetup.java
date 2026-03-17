import java.util.HashMap;
import java.util.Map;

// Inventory Class (Centralized Management)
class RoomInventory {
    private Map<String, Integer> inventory;

    // Constructor → Initialize inventory
    public RoomInventory() {
        inventory = new HashMap<>();

        // Register room types with availability
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    // Get availability of a specific room type
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update availability (controlled update)
    public void updateAvailability(String roomType, int count) {
        if (inventory.containsKey(roomType)) {
            inventory.put(roomType, count);
        } else {
            System.out.println("Room type not found!");
        }
    }

    // Display full inventory
    public void displayInventory() {
        System.out.println("---- Room Inventory ----");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

// Main Class
public class UseCase3InventorySetup {
    public static void main(String[] args) {

        // Initialize Inventory
        RoomInventory inventory = new RoomInventory();

        // Display initial inventory
        inventory.displayInventory();

        // Check availability
        System.out.println("\nChecking availability for Single Room:");
        System.out.println("Available: " + inventory.getAvailability("Single Room"));

        // Update availability
        System.out.println("\nUpdating availability for Single Room...");
        inventory.updateAvailability("Single Room", 4);

        // Display updated inventory
        System.out.println("\nAfter Update:");
        inventory.displayInventory();
    }
}