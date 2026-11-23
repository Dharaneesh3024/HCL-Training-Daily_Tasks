package rental;

import java.time.LocalDate;

public sealed interface BookingStatus {

    record Pending() implements BookingStatus {}

    record Confirmed(LocalDate confirmedOn) implements BookingStatus {}

    record Cancelled(String reason) implements BookingStatus {

        public Cancelled {
            if (reason == null || reason.isBlank()) {
                throw new IllegalArgumentException("a cancellation needs a reason");
            }
        }
    }

    static String label(BookingStatus status) {
        return switch (status) {
            case Pending p -> "Awaiting confirmation";
            case Confirmed c -> "Confirmed on " + c.confirmedOn();
            case Cancelled c -> "Cancelled: " + c.reason();
        };
    }

    static boolean canCancel(BookingStatus status) {
        return switch (status) {
            case Pending p -> true;
            case Confirmed c -> true;
            case Cancelled c -> false;
        };
    }
}
