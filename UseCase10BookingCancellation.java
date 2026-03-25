import java.util.*;

// ---------------------- Reservation ----------------------
class Reservation {
    private int bookingId;
    private String customerName;
    private String roomType;
    private String roomId;
    private boolean isCancelled;

    public Reservation(int bookingId, String customerName, String roomType, String roomId) {
        this.bookingId = bookingId;
        this.customerName = customerName;
        this.roomType = roomType;
        this.roomId = roomId;
        this.isCancelled = false;
    }

    public int getBookingId() {
        return bookingId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void cancel() {
        isCancelled = true;
    }

    @Override
    public String toString() {
        return "BookingID: " + bookingId +
               ", Name: " + customerName +
               ", RoomType: " + roomType +
               ", RoomID: " + roomId +
               ", Status: " + (isCancelled ? "Cancelled" : "Confirmed");
    }
}

// ---------------------- Inventory ----------------------
class RoomInventory {
    private Map<String, Integer> availableRooms;

    public RoomInventory() {
        availableRooms = new HashMap<>();
        availableRooms.put("Standard", 2);
        availableRooms.put("Deluxe", 2);
        availableRooms.put("Suite", 1);
    }

    public boolean isAvailable(String roomType) {
        return availableRooms.getOrDefault(roomType, 0) > 0;
    }

    public void allocateRoom(String roomType) {
        availableRooms.put(roomType, availableRooms.get(roomType) - 1);
    }

    public void releaseRoom(String roomType) {
        availableRooms.put(roomType, availableRooms.get(roomType) + 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory: " + availableRooms);
    }
}

// ---------------------- Booking Service ----------------------
class BookingService {
    private RoomInventory inventory;
    private Map<Integer, Reservation> bookings;
    private int counter = 100;
    private int roomCounter = 1;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
        this.bookings = new HashMap<>();
    }

    public Reservation bookRoom(String name, String roomType) {
        if (!inventory.isAvailable(roomType)) {
            System.out.println("Booking Failed: No rooms available for " + roomType);
            return null;
        }

        inventory.allocateRoom(roomType);

        String roomId = roomType.substring(0, 1) + roomCounter++;
        Reservation r = new Reservation(++counter, name, roomType, roomId);
        bookings.put(r.getBookingId(), r);

        System.out.println("Booking Successful: " + r);
        return r;
    }

    public Reservation getReservation(int bookingId) {
        return bookings.get(bookingId);
    }
}

// ---------------------- Cancellation Service ----------------------
class CancellationService {
    private RoomInventory inventory;

    // Stack for rollback tracking (LIFO)
    private Stack<String> rollbackStack = new Stack<>();

    public CancellationService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void cancelReservation(Reservation r) {

        // Validation
        if (r == null) {
            System.out.println("Cancellation Failed: Booking does not exist");
            return;
        }

        if (r.isCancelled()) {
            System.out.println("Cancellation Failed: Already cancelled");
            return;
        }

        // Step 1: Push room ID into rollback stack
        rollbackStack.push(r.getRoomId());

        // Step 2: Restore inventory
        inventory.releaseRoom(r.getRoomType());

        // Step 3: Mark as cancelled
        r.cancel();

        System.out.println("Cancellation Successful: " + r);

        // Show rollback behavior
        System.out.println("Rollback Stack (LIFO): " + rollbackStack);
    }
}

// ---------------------- Main ----------------------
public class UseCase10BookingCancellation {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        BookingService bookingService = new BookingService(inventory);
        CancellationService cancellationService = new CancellationService(inventory);

        // Book rooms
        Reservation r1 = bookingService.bookRoom("Alice", "Deluxe");
        Reservation r2 = bookingService.bookRoom("Bob", "Suite");

        inventory.displayInventory();

        // Cancel valid booking
        cancellationService.cancelReservation(r2);

        inventory.displayInventory();

        // Try cancelling again (edge case)
        cancellationService.cancelReservation(r2);

        // Cancel non-existent booking
        cancellationService.cancelReservation(null);
    }
}