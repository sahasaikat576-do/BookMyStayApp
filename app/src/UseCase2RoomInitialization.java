// Abstract Class
abstract class Room {
    protected int beds;
    protected double size;
    protected double price;

    // Constructor
    Room(int beds, double size, double price) {
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    // Method to display room details
    public void displayDetails() {
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sq.ft");
        System.out.println("Price: " + price);
    }

    // Abstract method
    public abstract String getRoomType();
}

// Single Room Class
class SingleRoom extends Room {

    SingleRoom() {
        super(1, 200, 1000);
    }

    @Override
    public String getRoomType() {
        return "Single Room";
    }
}

// Double Room Class
class DoubleRoom extends Room {

    DoubleRoom() {
        super(2, 350, 2000);
    }

    @Override
    public String getRoomType() {
        return "Double Room";
    }
}

// Suite Room Class
class SuiteRoom extends Room {

    SuiteRoom() {
        super(3, 500, 4000);
    }

    @Override
    public String getRoomType() {
        return "Suite Room";
    }
}

// Main Class
public class UseCase2RoomInitialization {
    public static void main(String[] args) {

        // Create Room Objects (Polymorphism)
        Room r1 = new SingleRoom();
        Room r2 = new DoubleRoom();
        Room r3 = new SuiteRoom();

        // Static Availability Variables
        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        // Display Details
        System.out.println("---- Room Details ----\n");

        System.out.println(r1.getRoomType());
        r1.displayDetails();
        System.out.println("Available: " + singleAvailable + "\n");

        System.out.println(r2.getRoomType());
        r2.displayDetails();
        System.out.println("Available: " + doubleAvailable + "\n");

        System.out.println(r3.getRoomType());
        r3.displayDetails();
        System.out.println("Available: " + suiteAvailable + "\n");
    }
}