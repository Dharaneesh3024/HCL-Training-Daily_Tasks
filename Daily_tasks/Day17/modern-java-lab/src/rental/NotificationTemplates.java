package rental;

public final class NotificationTemplates {

    private NotificationTemplates() {}

    public static String confirmation(String customer, Booking booking, Vehicle vehicle) {
        return """
                Hello %s,

                Your booking #%d is confirmed.

                Vehicle : %s (%s branch)
                Pickup  : %s
                Return  : %s
                Days    : %d
                Total   : Rs %.2f

                Thank you for choosing us.
                """.formatted(customer, booking.id(), vehicle.plate(), vehicle.branch(),
                booking.start(), booking.end(), booking.days(), booking.days() * vehicle.dailyRate());
    }

    public static String statusUpdate(String customer, Booking booking) {
        return """
                Hello %s,

                The status of booking #%d is now: %s
                """.formatted(customer, booking.id(), BookingStatus.label(booking.status()));
    }
}
