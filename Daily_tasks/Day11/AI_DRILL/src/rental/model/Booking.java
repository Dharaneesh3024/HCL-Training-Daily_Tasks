package rental.model;

import java.time.LocalDate;

public class Booking {

    private final int id;
    private final int vehicleId;
    private final LocalDate start;
    private final LocalDate end;

    public Booking(int id, int vehicleId, LocalDate start, LocalDate end) {
        if (!end.isAfter(start)) {
            throw new IllegalArgumentException("end must be after start");
        }
        this.id = id;
        this.vehicleId = vehicleId;
        this.start = start;
        this.end = end;
    }

    public Integer getId() { return id; }
    public int getVehicleId() { return vehicleId; }
    public LocalDate getStart() { return start; }
    public LocalDate getEnd() { return end; }
}
