import java.util.*;

// ---------------------- Custom Exception ----------------------
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// ---------------------- Reservation ----------------------
class Reservation {
    private int bookingId;
    private String customerName;
    private String roomType;
    private int nights;

    public Reservation(int bookingId, String customerName, String roomType, int nights) {
        this.bookingId = bookingId;
        this.customerName = customerName;
        this.roomType = roomType;
        this.nights = nights;
    }

    @Override
    public String toString() {
        return "BookingID: " + bookingId +
               ", Name: " + customerName +
               ", Room: " + roomType +
               ", Nights: " + nights;
    }
}

// ---------------------- Inventory ----------------------
class RoomInventory {
    private Map<String, Integer> rooms;

    public RoomInventory() {
        rooms = new HashMap<>();
        rooms.put("Standard", 2);
        rooms.put("Deluxe", 2);
        rooms.put("Suite", 1);
    }

    public boolean isValidRoomType(String roomType) {
        return rooms.containsKey(roomType);
    }

    public int getAvailableRooms(String roomType) {
        return rooms.getOrDefault(roomType, 0);
    }

    public void bookRoom(String roomType) {
        rooms.put(roomType, rooms.get(roomType) - 1);
    }
}

// ---------------------- Validator ----------------------
class InvalidBookingValidator {

    public void validate(String roomType, int nights, RoomInventory inventory)
            throws InvalidBookingException {

        // Validate room type
        if (!inventory.isValidRoomType(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }

        // Validate nights
        if (nights <= 0) {
            throw new InvalidBookingException("Nights must be greater than 0");
        }

        // Validate availability
        if (inventory.getAvailableRooms(roomType) <= 0) {
            throw new InvalidBookingException("No rooms available for: " + roomType);
        }
    }
}

// ---------------------- Booking Service ----------------------
class BookingService {
    private RoomInventory inventory;
    private InvalidBookingValidator validator;
    private int bookingCounter = 100;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
        this.validator = new InvalidBookingValidator();
    }

    public Reservation bookRoom(String name, String roomType, int nights)
            throws InvalidBookingException {

        // FAIL-FAST validation
        validator.validate(roomType, nights, inventory);

        // Only after validation → update state
        inventory.bookRoom(roomType);

        return new Reservation(++bookingCounter, name, roomType, nights);
    }
}

// ---------------------- Main ----------------------
public class UseCase9ErrorHandlingValidation {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        BookingService bookingService = new BookingService(inventory);

        // Test cases (valid + invalid)
        String[][] testInputs = {
            {"Alice", "Deluxe", "2"},     // valid
            {"Bob", "Suite", "1"},        // valid
            {"Charlie", "Luxury", "2"},   // invalid room
            {"David", "Standard", "0"},   // invalid nights
            {"Eve", "Suite", "1"}         // no availability (after booking once)
        };

        for (String[] input : testInputs) {
            String name = input[0];
            String roomType = input[1];
            int nights = Integer.parseInt(input[2]);

            try {
                System.out.println("\nProcessing booking for " + name);

                Reservation r = bookingService.bookRoom(name, roomType, nights);

                System.out.println("Booking Successful: " + r);

            } catch (InvalidBookingException e) {
                // Graceful failure handling
                System.out.println("Booking Failed: " + e.getMessage());
            }
        }

        System.out.println("\nSystem continues running safely after errors.");
    }
}