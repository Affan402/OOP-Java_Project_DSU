public class Car extends Vehicle implements Rentable {
    private String type;

    public Car(String model, String type, double ratePerDay) {
        super(model, ratePerDay);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public double calculateRentalPrice(int days) {
        return days * ratePerDay;
    }

    public double calculateRentalPrice(int days, double discount) {
        return days * ratePerDay * (1 - discount);
    }

    @Override
    public void rent(int days) throws CarNotAvailableException {
        if (!this.isAvailable()) {
            throw new CarNotAvailableException("Car " + this.getModel() + " is currently not available.");
        }
        this.setAvailable(false);
        double cost = calculateRentalPrice(days);
        System.out.println("You rented " + getModel() + " for " + days + " days. Total cost: $" + cost);
    }

    @Override
    public void returnVehicle() {
        this.setAvailable(true);
        System.out.println("Thank you for returning the car: " + getModel());
    }
}

