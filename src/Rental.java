public class Rental {
    private Rentable vehicle;
    private int days;

    public Rental(Rentable vehicle, int days) {
        this.vehicle = vehicle;
        this.days = days;
    }

    public Rentable getVehicle() {
        return vehicle;
    }

    public int getDays() {
        return days;
    }
}
