import java.io.File;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Test {
    private static List<Rentable> vehicles = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    static {
        vehicles.add(new Car("Toyota Camry", "Sedan", 55.0));
        vehicles.add(new Car("Honda Civic", "Sedan", 50.0));
        vehicles.add(new Car("Ford Explorer", "SUV", 70.0));
        vehicles.add(new Car("Jeep Wrangler", "SUV", 75.0));
        vehicles.add(new Van("Suzuki Hiace", "Hiace", 80.0));
        vehicles.add(new Van("Toyota Hi-Roof", "Hi-Roof", 90.0));
        vehicles.add(new Van("Coaster Bus", "Coaster", 100.0));
    }

    public static void main(String[] args) {
        System.out.println("Welcome to the Vehicle Rental System!");
        System.out.print("Please enter your name: ");
        String name = scanner.nextLine();
        Customer customer = new Customer(name);

        boolean running = true;
        while (running) {
            try {
                showMenu();
                int choice = readIntInput("Select an option: ");

                switch (choice) {
                    case 1: listAvailableVehicles(); break;
                    case 2: rentToCustomer(customer); break;
                    case 3: customer.returnCar(); break;
                    case 4:
                        System.out.println("Goodbye, " + customer.getName() + "! Thanks for using our service.");
                        running = false;
                        break;
                    case 5: showFileInfo(); break;
                    default:
                        System.out.println("Invalid option, please select again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input, please enter a valid number.");
                scanner.nextLine();
            }
        }
        scanner.close();
    }

    private static void showMenu() {
        System.out.println("\n=== Menu ===");
        System.out.println("1. List Available Vehicles");
        System.out.println("2. Rent a Vehicle");
        System.out.println("3. Return Rented Vehicle");
        System.out.println("4. Exit");
        System.out.println("5. Show File Info");
    }

    private static void listAvailableVehicles() {
        System.out.println("\nAvailable Vehicles:");
        boolean anyAvailable = false;
        int index = 1;
        for (Rentable v : vehicles) {
            Vehicle vehicle = (Vehicle) v;
            if (vehicle.isAvailable()) {
                System.out.println(index + ". Model: " + vehicle.getModel() +
                        " | Type: " + (vehicle instanceof Car ? ((Car) vehicle).getType() : ((Van) vehicle).getType()) +
                        " | Rate per day: $" + vehicle.getRatePerDay());
                anyAvailable = true;
            }
            index++;
        }
        if (!anyAvailable) {
            System.out.println("Sorry, no vehicles are currently available.");
        }
    }

    private static void rentToCustomer(Customer customer) {
        listAvailableVehicles();
        int choice = readIntInput("Enter the number of the vehicle you want to rent: ");
        scanner.nextLine();

        if (choice < 1 || choice > vehicles.size()) {
            System.out.println("Invalid vehicle selection.");
            return;
        }

        Vehicle selectedVehicle = (Vehicle) vehicles.get(choice - 1);
        if (!selectedVehicle.isAvailable()) {
            System.out.println("Selected vehicle is not available.");
            return;
        }

        int days = readIntInput("Enter number of days to rent: ");
        scanner.nextLine();

        if (days <= 0) {
            System.out.println("Invalid number of days.");
            return;
        }

        System.out.printf("\n=== Rental Summary ===\nCustomer: %s\nVehicle: %s\nRate per Day: $%.2f\nRental Days: %d\nEstimated Total: $%.2f\n",
            customer.getName(),
            selectedVehicle.getModel(),
            selectedVehicle.getRatePerDay(),
            days,
            selectedVehicle.calculateRentalPrice(days));

        System.out.print("Do you want to confirm the rental? (Y/N): ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("Y")) {
            customer.rentCar(selectedVehicle, days);
        } else {
            System.out.println("Rental canceled.");
        }
    }

    private static int readIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a valid integer number.");
            scanner.next();
            System.out.print(prompt);
        }
        return scanner.nextInt();
    }

    private static void showFileInfo() {
        File f = new File("D:\\files\\LC.txt");
        try {
            if (!f.exists()) {
                f.getParentFile().mkdirs();
                if (f.createNewFile()) {
                    System.out.println("File created: " + f.getAbsolutePath());
                } else {
                    System.out.println("Failed to create file.");
                    return;
                }
            }
            System.out.println("File Name: " + f.getName());
            System.out.println("File Location: " + f.getAbsolutePath());
            System.out.println("File Writable: " + f.canWrite());
            System.out.println("File Readable: " + f.canRead());
            System.out.println("File Size: " + f.length() + " bytes");
        } catch (Exception e) {
            System.out.println("An error occurred while checking the file: " + e.getMessage());
        }
    }
}
