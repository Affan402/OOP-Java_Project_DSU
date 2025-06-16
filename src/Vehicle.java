public abstract class Vehicle {
    private String model;
    private boolean available;
    protected double ratePerDay;

    public Vehicle(String model, double ratePerDay) {
        this.model = model;
        this.available = true;
        this.ratePerDay = ratePerDay;
    }

    public String getModel() {
        return model;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean status) {
        this.available = status;
    }

    public abstract double calculateRentalPrice(int days);

    public double getRatePerDay() {
        return ratePerDay;
    }
}
