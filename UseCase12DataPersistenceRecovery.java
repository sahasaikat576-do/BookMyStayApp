import java.io.*;
import java.util.*;

// ---------------------- Reservation ----------------------
class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;

    int bookingId;
    String customerName;
    String roomType;

    public Reservation(int bookingId, String customerName, String roomType) {
        this.bookingId = bookingId;
        this.customerName = customerName;
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return "BookingID: " + bookingId +
               ", Name: " + customerName +
               ", RoomType: " + roomType;
    }
}

// ---------------------- System State ----------------------
class SystemState implements Serializable {
    private static final long serialVersionUID = 1L;

    Map<String, Integer> inventory;
    List<Reservation> bookings;

    public SystemState(Map<String, Integer> inventory, List<Reservation> bookings) {
        this.inventory = inventory;
        this.bookings = bookings;
    }
}

// ---------------------- Persistence Service ----------------------
class PersistenceService {

    private static final String FILE_NAME = "booking_data.ser";

    // Save state
    public static void save(SystemState state) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(state);
            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    // Load state
    public static SystemState load() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            SystemState state = (SystemState) in.readObject();
            System.out.println("Data loaded successfully.");
            return state;
        } catch (FileNotFoundException e) {
            System.out.println("No previous data found. Starting fresh.");
        } catch (Exception e) {
            System.out.println("Error loading data. Starting fresh.");
        }
        return null;
    }
}

// ---------------------- Main ----------------------
public class UseCase12DataPersistenceRecovery {

    public static void main(String[] args) {

        Map<String, Integer> inventory;
        List<Reservation> bookings;

        // Step 1: Try loading existing data
        SystemState loadedState = PersistenceService.load();

        if (loadedState != null) {
            inventory = loadedState.inventory;
            bookings = loadedState.bookings;
        } else {
            // Fresh start
            inventory = new HashMap<>();
            inventory.put("Standard", 2);
            inventory.put("Deluxe", 2);
            inventory.put("Suite", 1);

            bookings = new ArrayList<>();
        }

        // Step 2: Simulate booking
        Reservation r1 = new Reservation(101, "Alice", "Deluxe");
        bookings.add(r1);
        inventory.put("Deluxe", inventory.get("Deluxe") - 1);

        System.out.println("\nCurrent Bookings:");
        for (Reservation r : bookings) {
            System.out.println(r);
        }

        System.out.println("\nCurrent Inventory: " + inventory);

        // Step 3: Save state before shutdown
        SystemState currentState = new SystemState(inventory, bookings);
        PersistenceService.save(currentState);

        System.out.println("\nSystem shutdown... Restart to see recovery.");
    }
}