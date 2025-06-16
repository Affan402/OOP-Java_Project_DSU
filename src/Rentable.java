public interface Rentable {
    void rent(int days) throws CarNotAvailableException;
    void returnVehicle();
}
