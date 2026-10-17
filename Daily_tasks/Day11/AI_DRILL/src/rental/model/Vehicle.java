package rental.model;

public class Vehicle {

    private final int id;
    private final String plate;
    private final String branch;
    private final double dailyRate;

    public Vehicle(int id, String plate, String branch, double dailyRate) {
        this.id = id;
        this.plate = plate;
        this.branch = branch;
        this.dailyRate = dailyRate;
    }

    public Integer getId() { return id; }
    public String getPlate() { return plate; }
    public String getBranch() { return branch; }
    public double getDailyRate() { return dailyRate; }

    @Override
    public String toString() {
        return "Vehicle#" + id + " " + plate + " (" + branch + ")";
    }
}
