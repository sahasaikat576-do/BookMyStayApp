import java.util.*;

// Reservation class
class Reservation {
    private int bookingId;
    private String customerName;
    private String roomType;
    private int nights;
    private double pricePerNight;

    public Reservation(int bookingId, String customerName, String roomType, int nights, double pricePerNight) {
        this.bookingId = bookingId;
        this.customerName = customerName;
        this.roomType = roomType;
        this.nights = nights;
        this.pricePerNight = pricePerNight;
    }

    public int getBookingId() {
        return bookingId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getNights() {
        return nights;
    }

    public double getTotalAmount() {
        return nights * pricePerNight;
    }

    @Override
    public String toString() {
        return "BookingID: " + bookingId +
               ", Name: " + customerName +
               ", Room: " + roomType +
               ", Nights: " + nights +
               ", Total: ₹" + getTotalAmount();
    }
}

// Booking History class
class BookingHistory {
    private List<Reservation> reservations;

    public BookingHistory() {
        reservations = new ArrayList<>();
    }

    // Store confirmed booking
    public void addReservation(Reservation r) {
        reservations.add(r); // maintains insertion order
    }

    // Retrieve all bookings
    public List<Reservation> getAllReservations() {
        return Collections.unmodifiableList(reservations);
    }
}

// Reporting Service
class BookingReportService {

    // Display all bookings
    public void showAllBookings(List<Reservation> list) {
        System.out.println("\n--- Booking History ---");
        for (Reservation r : list) {
            System.out.println(r);
        }
    }

    // Generate summary report
    public void generateSummary(List<Reservation> list) {
        int totalBookings = list.size();
        double totalRevenue = 0;

        for (Reservation r : list) {
            totalRevenue += r.getTotalAmount();
        }

        System.out.println("\n--- Summary Report ---");
        System.out.println("Total Bookings: " + totalBookings);
        System.out.println("Total Revenue: ₹" + totalRevenue);
    }
}

// Main Class
public class UseCase8BookingHistoryReport {

    public static void main(String[] args) {

        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        // Simulating confirmed bookings
        Reservation r1 = new Reservation(101, "Alice", "Deluxe", 2, 2000);
        Reservation r2 = new Reservation(102, "Bob", "Suite", 3, 3500);
        Reservation r3 = new Reservation(103, "Charlie", "Standard", 1, 1500);

        // Add to booking history
        history.addReservation(r1);
        history.addReservation(r2);
        history.addReservation(r3);

        // Admin views booking history
        List<Reservation> storedBookings = history.getAllReservations();

        reportService.showAllBookings(storedBookings);

        // Generate report
        reportService.generateSummary(storedBookings);
    }
}