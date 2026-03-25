import java.util.*;

// Reservation (Represents booking intent)
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

    @Override
    public String toString() {
        return "Guest: " + guestName + ", Room Type: " + roomType;
    }
}

// Booking Request Queue (FIFO)
class BookingRequestQueue {
    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    // Add request to queue
    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
        System.out.println("Request added: " + reservation);
    }

    // View all requests (without removing)
    public void viewRequests() {
        System.out.println("\nCurrent Booking Requests in Queue:");
        for (Reservation r : queue) {
            System.out.println(r);
        }
    }

    // Get next request (for future processing)
    public Reservation getNextRequest() {
        return queue.peek(); // does not remove
    }
}

// Main Class
public class UseCase5BookingRequestQueue {
    public static void main(String[] args) {

        // Step 1: Create Booking Queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Step 2: Guests submit booking requests
        bookingQueue.addRequest(new Reservation("Alice", "Single"));
        bookingQueue.addRequest(new Reservation("Bob", "Suite"));
        bookingQueue.addRequest(new Reservation("Charlie", "Double"));
        bookingQueue.addRequest(new Reservation("David", "Single"));

        // Step 3: View all queued requests (FIFO order)
        bookingQueue.viewRequests();

        // Step 4: Show next request to be processed
        Reservation next = bookingQueue.getNextRequest();
        System.out.println("\nNext request to process:");
        System.out.println(next);
    }
}