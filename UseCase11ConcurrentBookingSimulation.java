import java.util.*;

// ---------------------- Booking Request ----------------------
class BookingRequest {
    String customerName;
    String roomType;

    public BookingRequest(String customerName, String roomType) {
        this.customerName = customerName;
        this.roomType = roomType;
    }
}

// ---------------------- Inventory ----------------------
class RoomInventory {
    private Map<String, Integer> rooms = new HashMap<>();

    public RoomInventory() {
        rooms.put("Standard", 2);
        rooms.put("Deluxe", 1);
        rooms.put("Suite", 1);
    }

    // Critical section (synchronized)
    public synchronized boolean allocateRoom(String roomType, String customerName) {
        int available = rooms.getOrDefault(roomType, 0);

        if (available > 0) {
            System.out.println(Thread.currentThread().getName() +
                    " allocating " + roomType + " to " + customerName);

            rooms.put(roomType, available - 1);

            try {
                Thread.sleep(100); // simulate delay
            } catch (InterruptedException e) {}

            return true;
        } else {
            System.out.println(Thread.currentThread().getName() +
                    " FAILED for " + customerName + " (No " + roomType + " left)");
            return false;
        }
    }

    public void display() {
        System.out.println("\nFinal Inventory: " + rooms);
    }
}

// ---------------------- Shared Queue ----------------------
class BookingQueue {
    private Queue<BookingRequest> queue = new LinkedList<>();

    public synchronized void addRequest(BookingRequest req) {
        queue.add(req);
    }

    public synchronized BookingRequest getRequest() {
        return queue.poll();
    }
}

// ---------------------- Worker Thread ----------------------
class BookingProcessor extends Thread {
    private BookingQueue queue;
    private RoomInventory inventory;

    public BookingProcessor(BookingQueue queue, RoomInventory inventory, String name) {
        super(name);
        this.queue = queue;
        this.inventory = inventory;
    }

    @Override
    public void run() {
        while (true) {

            BookingRequest req;

            // synchronized queue access
            synchronized (queue) {
                req = queue.getRequest();
            }

            if (req == null) break;

            // Critical section: inventory update
            inventory.allocateRoom(req.roomType, req.customerName);
        }
    }
}

// ---------------------- Main ----------------------
public class UseCase11ConcurrentBookingSimulation {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        BookingQueue queue = new BookingQueue();

        // Simulating multiple guest requests
        queue.addRequest(new BookingRequest("Alice", "Deluxe"));
        queue.addRequest(new BookingRequest("Bob", "Deluxe"));
        queue.addRequest(new BookingRequest("Charlie", "Suite"));
        queue.addRequest(new BookingRequest("David", "Suite"));
        queue.addRequest(new BookingRequest("Eve", "Standard"));
        queue.addRequest(new BookingRequest("Frank", "Standard"));

        // Multiple threads (guests)
        BookingProcessor t1 = new BookingProcessor(queue, inventory, "Thread-1");
        BookingProcessor t2 = new BookingProcessor(queue, inventory, "Thread-2");
        BookingProcessor t3 = new BookingProcessor(queue, inventory, "Thread-3");

        // Start threads
        t1.start();
        t2.start();
        t3.start();

        // Wait for completion
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {}

        // Final state
        inventory.display();
    }
}