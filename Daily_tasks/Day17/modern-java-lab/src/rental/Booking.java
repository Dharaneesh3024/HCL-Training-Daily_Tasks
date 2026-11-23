package rental;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public record Booking(int id, int vehicleId, LocalDate start, LocalDate end, BookingStatus status) {

    public Booking {
        if (!end.isAfter(start)) {
            throw new IllegalArgumentException("end must be after start");
        }
    }

    public long days() {
        return ChronoUnit.DAYS.between(start, end);
    }

    public Booking withStatus(BookingStatus newStatus) {
        return new Booking(id, vehicleId, start, end, newStatus);
    }
}
