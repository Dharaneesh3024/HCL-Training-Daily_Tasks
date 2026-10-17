package rental.model;

import java.time.LocalDate;
import java.util.Objects;

public class MaintenanceBlock {

    private final int id;
    private final int vehicleId;
    private final LocalDate from;
    private final LocalDate to;
    private final String reason;

    public MaintenanceBlock(int id, int vehicleId, LocalDate from, LocalDate to, String reason) {
        if (from == null || to == null || !to.isAfter(from)) {
            throw new IllegalArgumentException("'to' must be after 'from'");
        }
        if (reason == null || reason.isBlank()) {
            throw new IllegalArgumentException("reason must not be blank");
        }
        this.id = id;
        this.vehicleId = vehicleId;
        this.from = from;
        this.to = to;
        this.reason = reason.trim();
    }

    public Integer getId() { return id; }
    public int getVehicleId() { return vehicleId; }
    public LocalDate getFrom() { return from; }
    public LocalDate getTo() { return to; }
    public String getReason() { return reason; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MaintenanceBlock)) return false;
        return id == ((MaintenanceBlock) o).id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Block#" + id + " vehicle " + vehicleId + " " + from + " to " + to + " (" + reason + ")";
    }
}
