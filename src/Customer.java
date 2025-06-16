import java.io.FileWriter;
import java.io.IOException;

public class Customer {
    private String name;
    private Rental currentRental;

    public Customer(String name) {
        this.name = name;
        this.currentRental = null;
    }

    public String getName() {
        return name;
    }

    public void rentCar(Rentable vehicle, int days) {
        if (this.currentRental != null) {
            System.out.println("You already have a rented vehicle, please return it first.");
            return;
        }

        try {
            vehicle.rent(days);
            this.currentRental = new Rental(vehicle, days);
            System.out.println("Rental for " + name + " confirmed.");

            Vehicle v = (Vehicle) vehicle;
            double total = v.calculateRentalPrice(days);

            FileWriter writer = new FileWriter("D:\\files\\LC.txt", true);
            writer.write("Customer: " + name + ", Vehicle: " + v.getModel() + ", Days: " + days +
                         ", Total: $" + total + "\n");
            writer.close();

            System.out.println("\n========== RENTAL SLIP ==========");
            System.out.println("Customer Name : " + name);
            System.out.println("Vehicle Model : " + v.getModel());
            System.out.println("Rate Per Day  : $" + v.getRatePerDay());
            System.out.println("Rental Days   : " + days);
            System.out.printf("Total Price   : $%.2f\n", total);
            System.out.println("=================================\n");

        } catch (CarNotAvailableException e) {
            System.out.println("Sorry, " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public void returnCar() {
        if (currentRental == null) {
            System.out.println("No vehicle rented currently.");
            return;
        }

        Vehicle v = (Vehicle) currentRental.getVehicle();
        int days = currentRental.getDays();

        currentRental.getVehicle().returnVehicle();
        currentRental = null;

        try {
            FileWriter writer = new FileWriter("D:\\files\\LC.txt", true);
            writer.write("Returned: " + v.getModel() + " by " + name + ", Duration: " + days + " day(s)\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Error writing return info to file: " + e.getMessage());
        }
    }
}
