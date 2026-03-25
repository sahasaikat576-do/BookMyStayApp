import java.util.*;

// Reservation (Booking Request)
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// Inventory Service (State Holder)
class Inventory {
    private Map<String, Integer> availability;

    public Inventory() {
        availability = new HashMap<>();
    }

    public void addRoom(String type, int count) {
        availability.put(type, count);
    }

    public int getAvailable(String type) {
        return availability.getOrDefault(type, 0);
    }

    public void decrement(String type) {
        availability.put(type, availability.get(type) - 1);
    }
}

// Booking Request Queue (FIFO)
class BookingRequestQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.offer(r);
    }

    public Reservation getNextRequest() {
        return queue.poll(); // removes from queue (FIFO)
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

// Booking Service (Core Logic)
class BookingService {
    private Inventory inventory;

    // To prevent duplicate room IDs
    private Set<String> allocatedRoomIds = new HashSet<>();

    // Map roomType → assigned room IDs
    private Map<String, Set<String>> roomAllocations = new HashMap<>();

    public BookingService(Inventory inventory) {
        this.inventory = inventory;
    }

    public void processRequest(Reservation reservation) {
        String type = reservation.getRoomType();

        // Step 1: Check availability
        if (inventory.getAvailable(type) <= 0) {
            System.out.println("Booking Failed for " + reservation.getGuestName() +
                    " (No rooms available for " + type + ")");
            return;
        }

        // Step 2: Generate unique Room ID
        String roomId;
        do {
            roomId = type.substring(0, 1).toUpperCase() + new Random().nextInt(1000);
        } while (allocatedRoomIds.contains(roomId));

        // Step 3: Allocate room
        allocatedRoomIds.add(roomId);

        roomAllocations.putIfAbsent(type, new HashSet<>());
        roomAllocations.get(type).add(roomId);

        // Step 4: Update inventory (IMPORTANT)
        inventory.decrement(type);

        // Step 5: Confirm booking
        System.out.println("Booking Confirmed!");
        System.out.println("Guest: " + reservation.getGuestName());
        System.out.println("Room Type: " + type);
        System.out.println("Allocated Room ID: " + roomId);
        System.out.println("-----------------------------");
    }
}

// Main Class
public class UseCase6RoomAllocationService {
    public static void main(String[] args) {

        // Step 1: Setup Inventory
        Inventory inventory = new Inventory();
        inventory.addRoom("Single", 2);
        inventory.addRoom("Suite", 1);

        // Step 2: Setup Queue
        BookingRequestQueue queue = new BookingRequestQueue();
        queue.addRequest(new Reservation("Alice", "Single"));
        queue.addRequest(new Reservation("Bob", "Single"));
        queue.addRequest(new Reservation("Charlie", "Single")); // Should fail
        queue.addRequest(new Reservation("David", "Suite"));
        queue.addRequest(new Reservation("Eve", "Suite")); // Should fail

        // Step 3: Booking Service
        BookingService bookingService = new BookingService(inventory);

        // Step 4: Process Queue (FIFO)
        while (!queue.isEmpty()) {
            Reservation r = queue.getNextRequest();
            bookingService.processRequest(r);
        }
    }
}