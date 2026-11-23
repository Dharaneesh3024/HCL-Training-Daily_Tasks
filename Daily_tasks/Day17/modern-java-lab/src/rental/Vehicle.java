package rental;

public record Vehicle(int id, String plate, String branch, double dailyRate) {

    public Vehicle {
        if (plate == null || plate.isBlank()) {
            throw new IllegalArgumentException("plate must not be blank");
        }
        if (dailyRate <= 0) {
            throw new IllegalArgumentException("dailyRate must be positive");
        }
        plate = plate.strip().toUpperCase();
        branch = branch.strip();
    }
}
