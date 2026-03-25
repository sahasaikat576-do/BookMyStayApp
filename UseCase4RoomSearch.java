import java.util.*;

// Room Domain Model
class Room {
    private String type;
    private double price;
    private List<String> amenities;

    public Room(String type, double price, List<String> amenities) {
        this.type = type;
        this.price = price;
        this.amenities = amenities;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public List<String> getAmenities() {
        return amenities;
    }
}

// Inventory (State Holder)
class Inventory {
    private Map<String, Integer> roomAvailability = new HashMap<>();

    public void addRoom(String type, int count) {
        roomAvailability.put(type, count);
    }

    // Read-only access
    public int getAvailableRooms(String type) {
        return roomAvailability.getOrDefault(type, 0);
    }

    public Set<String> getAllRoomTypes() {
        return roomAvailability.keySet();
    }
}

// Search Service (Read-only logic)
class SearchService {
    private Inventory inventory;
    private Map<String, Room> roomDetails;

    public SearchService(Inventory inventory, Map<String, Room> roomDetails) {
        this.inventory = inventory;
        this.roomDetails = roomDetails;
    }

    public void searchAvailableRooms() {
        System.out.println("Available Rooms:\n");

        for (String type : inventory.getAllRoomTypes()) {
            int available = inventory.getAvailableRooms(type);

            // Validation: Only show rooms with availability > 0
            if (available > 0) {
                Room room = roomDetails.get(type);

                System.out.println("Room Type: " + room.getType());
                System.out.println("Price: ₹" + room.getPrice());
                System.out.println("Amenities: " + room.getAmenities());
                System.out.println("Available Count: " + available);
                System.out.println("--------------------------");
            }
        }
    }
}

// Main Class
public class UseCase4RoomSearch {
    public static void main(String[] args) {

        // Step 1: Create Inventory
        Inventory inventory = new Inventory();
        inventory.addRoom("Single", 5);
        inventory.addRoom("Double", 0); // unavailable
        inventory.addRoom("Suite", 2);

        // Step 2: Create Room Details (Domain Model)
        Map<String, Room> roomDetails = new HashMap<>();

        roomDetails.put("Single",
                new Room("Single", 2000, Arrays.asList("WiFi", "TV")));

        roomDetails.put("Double",
                new Room("Double", 3500, Arrays.asList("WiFi", "TV", "AC")));

        roomDetails.put("Suite",
                new Room("Suite", 6000, Arrays.asList("WiFi", "TV", "AC", "Mini Bar")));

        // Step 3: Search Service (Read-only)
        SearchService searchService = new SearchService(inventory, roomDetails);

        // Step 4: Guest initiates search
        searchService.searchAvailableRooms();
    }
}