import java.util.*;

class Car {
    private String carId;
    private String brand;
    private String model;
    private double pricePerDay;
    private double pricePerKm = 5;
    private boolean available = true;

    public Car(String carId, String brand, String model, double pricePerDay) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.pricePerDay = pricePerDay;
    }

    public String getCarId() { return carId; }
    public String getBrand() { return brand; }
    public String getModel() { return model; }
    public double getPricePerDay() { return pricePerDay; }
    public double getPricePerHour() { return pricePerDay / 24; }
    public double getPricePerKm() { return pricePerKm; }
    public boolean isAvailable() { return available; }

    public void rent() { available = false; }
    public void returnCar() { available = true; }

    public void displayCar() {
        System.out.printf("| %-5s | %-10s | %-10s | %10.2f | %10.2f | %7.2f | %-9s |\n",
                carId, brand, model, pricePerDay, getPricePerHour(), pricePerKm, available ? "True" : "False");
    }
}

class User {
    private String userId;
    private String name;
    private String contact;
    private String currentLocation;

    public User(String userId, String name, String contact, String currentLocation) {
        this.userId = userId;
        this.name = name;
        this.contact = contact;
        this.currentLocation = currentLocation;
    }

    public String getUserId() { return userId; }
    public String getName() { return name; }
    public String getContact() { return contact; }
    public String getCurrentLocation() { return currentLocation; }

    public void displayUser() {
        System.out.printf("| %-5s | %-15s | %-12s | %-15s |\n",
                userId, name, contact, currentLocation);
    }
}

class Rental {
    private User user;
    private Car car;
    private String pickup;
    private String destination;
    private double distance;
    private int hours;
    private double totalPrice;

    public Rental(User user, Car car, String pickup, String destination, double distance, int hours) {
        this.user = user;
        this.car = car;
        this.pickup = pickup;
        this.destination = destination;
        this.distance = distance;
        this.hours = hours;
        this.totalPrice = car.getPricePerHour() * hours + car.getPricePerKm() * distance;
        car.rent();
    }

    public double getTotalPrice() { return totalPrice; }

    public void displaySlip() {
        System.out.println("\n================ RENTAL SLIP ================");
        System.out.println(" User Name      : " + user.getName());
        System.out.println(" Car            : " + car.getBrand() + " " + car.getModel());
        System.out.println(" Pickup         : " + pickup);
        System.out.println(" Destination    : " + destination);
        System.out.println(" Distance       : " + distance + " km");
        System.out.println(" Hours Rented   : " + hours);
        System.out.printf(" Price/Hour     : %.2f\n", car.getPricePerHour());
        System.out.printf(" Price/Km       : %.2f\n", car.getPricePerKm());
        System.out.printf(" Total Price    : %.2f\n", totalPrice);
        System.out.println("=============================================\n");
    }
}

public class CarRentalSystem {
    static Scanner sc = new Scanner(System.in);
    static List<Car> cars = new ArrayList<>();
    static List<User> users = new ArrayList<>();
    static List<Rental> rentals = new ArrayList<>();

    // Credentials
    static String adminUser = "admin", adminPass = "1234";
    static String ownerUser = "owner", ownerPass = "1234";

    public static void main(String[] args) {
        cars.add(new Car("C101", "Maruti", "Swift", 1000));
        cars.add(new Car("C102", "Toyota", "Innova", 2000));

        while (true) {
            System.out.println("\n================ CAR RENTAL SYSTEM ================");
            System.out.println(" Login as:");
            System.out.println(" 1. User");
            System.out.println(" 2. Admin");
            System.out.println(" 3. Owner");
            System.out.println(" 4. Exit");
            System.out.println("===================================================");
            System.out.print(" Enter choice: ");
            int role = sc.nextInt();
            sc.nextLine();

            switch (role) {
                case 1 -> userMenu();
                case 2 -> adminLogin();
                case 3 -> ownerLogin();
                case 4 -> {
                    System.out.println("✅ Exiting system. Goodbye!");
                    return;
                }
                default -> System.out.println("❌ Invalid Choice!");
            }
        }
    }

    // ========== USER MENU ==========
    static void userMenu() {
        while (true) {
            System.out.println("\n================ USER MENU ================");
            System.out.println(" 1. Add User Info");
            System.out.println(" 2. View Cars");
            System.out.println(" 3. Rent a Car");
            System.out.println(" 4. Exit to Main Menu");
            System.out.println("==========================================");
            System.out.print(" Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> addUser();
                case 2 -> viewCars();
                case 3 -> rentCar();
                case 4 -> {
                    System.out.println("↩ Returning to Main Menu...");
                    return;
                }
                default -> System.out.println("❌ Invalid Choice!");
            }
        }
    }

    static void addUser() {
        System.out.print(" Enter User ID: ");
        String id = sc.nextLine();
        System.out.print(" Enter Name: ");
        String name = sc.nextLine();
        System.out.print(" Enter Contact: ");
        String contact = sc.nextLine();
        System.out.print("Enter Current Location: ");
        String loc = sc.nextLine();

        users.add(new User(id, name, contact, loc));
        System.out.println("✅ User Added Successfully!");
    }

    static void viewCars() {
        System.out.println("\n================ AVAILABLE CARS ================");
        System.out.printf("| %-5s | %-10s | %-10s | %-10s | %-10s | %-7s | %-9s |\n",
                "ID", "Brand", "Model", "Price/Day", "Price/Hour", "Price/Km", "Available");
        System.out.println("----------------------------------------------------------------------------");
        for (Car c : cars) c.displayCar();
        System.out.println("=============================================================\n");
    }

    static void rentCar() {
        if (users.isEmpty()) {
            System.out.println("❌ Please add User Info first!");
            return;
        }

        System.out.print(" Enter User ID: ");
        String uid = sc.nextLine();
        User user = null;
        for (User u : users) if (u.getUserId().equals(uid)) user = u;

        if (user == null) {
            System.out.println("❌ User not found!");
            return;
        }

        System.out.print(" Enter Pickup Location: ");
        String pickup = sc.nextLine();
        System.out.print(" Enter Destination: ");
        String dest = sc.nextLine();
        System.out.print(" Enter Distance (km): ");
        double dist = sc.nextDouble();
        System.out.print(" Enter Hours to Rent: ");
        int hrs = sc.nextInt();
        sc.nextLine();

        viewCars();
        System.out.print(" Enter Car ID to rent: ");
        String cid = sc.nextLine();
        Car car = null;
        for (Car c : cars) if (c.getCarId().equals(cid) && c.isAvailable()) car = c;

        if (car == null) {
            System.out.println("❌ Car not available!");
            return;
        }

        Rental rental = new Rental(user, car, pickup, dest, dist, hrs);
        rentals.add(rental);
        rental.displaySlip();
    }

    // ========== ADMIN MENU ==========
    static void adminLogin() {
        System.out.print("👤 Enter Admin Username: ");
        String uname = sc.nextLine();
        System.out.print("🔑 Enter Password: ");
        String pass = sc.nextLine();

        if (uname.equals(adminUser) && pass.equals(adminPass)) {
            System.out.println("✅ Login Successful! Welcome Admin.");
            adminMenu();
        } else System.out.println("❌ Invalid Credentials!");
    }

    static void adminMenu() {
        while (true) {
            System.out.println("\n================ ADMIN MENU ================");
            System.out.println(" 1. View All Users");
            System.out.println(" 2. View All Rentals");
            System.out.println(" 3. Add Car");
            System.out.println(" 4. Exit to Main Menu");
            System.out.println("============================================");
            System.out.print(" Enter choice: ");
            int ch = sc.nextInt();
            sc.nextLine();

            switch (ch) {
                case 1 -> viewUsers();
                case 2 -> viewRentals();
                case 3 -> addCar();
                case 4 -> {
                    System.out.println("↩ Returning to Main Menu...");
                    return;
                }
                default -> System.out.println("❌ Invalid Choice!");
            }
        }
    }

    // ========== OWNER MENU ==========
    static void ownerLogin() {
        System.out.print("👑 Enter Owner Username:");
        String uname = sc.nextLine();
        System.out.print("🔑 Enter Password: ");
        String pass = sc.nextLine();

        if (uname.equals(ownerUser) && pass.equals(ownerPass)) {
            System.out.println("👑 Login Successful! Welcome Owner.");
            ownerMenu();
        } else System.out.println("❌ Invalid Owner Credentials!");
    }

    static void ownerMenu() {
        while (true) {
            System.out.println("\n================ OWNER MENU ================");
            System.out.println(" 1. View All Cars");
            System.out.println(" 2. View All Users");
            System.out.println(" 3. View All Rentals");
            System.out.println(" 4. View Profit Report");
            System.out.println(" 5. Add Car");
            System.out.println(" 6. Exit to Main Menu");
            System.out.println("============================================");
            System.out.print(" Enter choice: ");
            int ch = sc.nextInt();
            sc.nextLine();

            switch (ch) {
                case 1 -> viewCars();
                case 2 -> viewUsers();
                case 3 -> viewRentals();
                case 4 -> viewProfit();
                case 5 -> addCar();
                case 6 -> {
                    System.out.println("↩ Returning to Main Menu...");
                    return;
                }
                default -> System.out.println("❌ Invalid Choice!");
            }
        }
    }

    // Common Utility Functions
    static void viewUsers() {
        if (users.isEmpty()) {
            System.out.println("❌ No Users Found!");
            return;
        }
        System.out.println("\n================ REGISTERED USERS ================");
        System.out.printf("| %-5s | %-15s | %-12s | %-15s |\n", "ID", "Name", "Contact", "Location");
        System.out.println("-------------------------------------------------------------");
        for (User u : users) u.displayUser();
        System.out.println("=============================================================");
    }

    static void viewRentals() {
        if (rentals.isEmpty()) {
            System.out.println("❌ No Rentals Found!");
            return;
        }
        for (Rental r : rentals) r.displaySlip();
    }

    static void addCar() {
        System.out.print(" Enter Car ID: ");
        String id = sc.nextLine();
        System.out.print(" Enter Brand: ");
        String brand = sc.nextLine();
        System.out.print(" Enter Model: ");
        String model = sc.nextLine();
        System.out.print(" Enter Price/Day: ");
        double price = sc.nextDouble();
        sc.nextLine();

        cars.add(new Car(id, brand, model, price));
        System.out.println("✅ Car Added Successfully!");
    }

    static void viewProfit() {
        double total = 0;
        for (Rental r : rentals) total += r.getTotalPrice();
        System.out.println("\n💰 Total Profit Earned: " + total);
    }
}
