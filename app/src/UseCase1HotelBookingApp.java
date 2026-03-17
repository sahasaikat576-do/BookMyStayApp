import java.util.Scanner;

class Hotel {
    String hotelName;
    int availableRooms;
    double pricePerRoom;

    Hotel(String hotelName, int availableRooms, double pricePerRoom) {
        this.hotelName = hotelName;
        this.availableRooms = availableRooms;
        this.pricePerRoom = pricePerRoom;
    }

    void displayDetails() {
        System.out.println("Hotel Name: " + hotelName);
        System.out.println("Available Rooms: " + availableRooms);
        System.out.println("Price per Room: " + pricePerRoom);
    }

    void bookRoom(int rooms) {
        if (rooms <= availableRooms) {
            double totalCost = rooms * pricePerRoom;
            availableRooms -= rooms;
            System.out.println("Booking Successful!");
            System.out.println("Rooms Booked: " + rooms);
            System.out.println("Total Cost: " + totalCost);
        } else {
            System.out.println("Sorry! Not enough rooms available.");
        }
    }
}

public class UseCase1HotelBookingApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Create hotel object
        Hotel h1 = new Hotel("Taj Hotel", 10, 2000);

        System.out.println("---- Hotel Details ----");
        h1.displayDetails();

        System.out.print("\nEnter number of rooms to book: ");
        int rooms = sc.nextInt();

        h1.bookRoom(rooms);

        sc.close();
    }
}