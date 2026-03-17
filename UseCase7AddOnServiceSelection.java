import java.util.*;

// Add-On Service Class
class Service {
    private String serviceName;
    private double cost;

    public Service(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }
}

// Add-On Service Manager
class AddOnServiceManager {

    // Map: Reservation ID -> List of Services
    private Map<String, List<Service>> serviceMap;

    public AddOnServiceManager() {
        serviceMap = new HashMap<>();
    }

    // Add service to a reservation
    public void addService(String reservationId, Service service) {
        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);
    }

    // Get services for a reservation
    public List<Service> getServices(String reservationId) {
        return serviceMap.getOrDefault(reservationId, new ArrayList<>());
    }

    // Calculate total cost of services
    public double calculateTotalCost(String reservationId) {
        double total = 0;
        List<Service> services = getServices(reservationId);

        for (Service s : services) {
            total += s.getCost();
        }
        return total;
    }

    // Display services for a reservation
    public void displayServices(String reservationId) {
        List<Service> services = getServices(reservationId);

        if (services.isEmpty()) {
            System.out.println("No add-on services selected.");
            return;
        }

        System.out.println("Add-On Services for Reservation ID: " + reservationId);
        for (Service s : services) {
            System.out.println("- " + s.getServiceName() + " : " + s.getCost());
        }

        System.out.println("Total Add-On Cost: " + calculateTotalCost(reservationId));
    }
}

// Main Class
public class UseCase7AddOnServiceSelection {
    public static void main(String[] args) {

        // Example reservation ID
        String reservationId = "RES123";

        // Create service manager
        AddOnServiceManager manager = new AddOnServiceManager();

        // Create services
        Service wifi = new Service("WiFi", 200);
        Service breakfast = new Service("Breakfast", 500);
        Service spa = new Service("Spa", 1500);

        // Guest selects services
        manager.addService(reservationId, wifi);
        manager.addService(reservationId, breakfast);
        manager.addService(reservationId, spa);

        // Display selected services
        manager.displayServices(reservationId);
    }
}