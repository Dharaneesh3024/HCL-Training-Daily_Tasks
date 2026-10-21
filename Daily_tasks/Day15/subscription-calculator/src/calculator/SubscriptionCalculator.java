package calculator;

import model.Plan;
import model.SubscriptionStatus;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public final class SubscriptionCalculator {

    public static final ZoneId IST = ZoneId.of("Asia/Kolkata");
    public static final ZoneId UTC = ZoneOffset.UTC;

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd MMM yyyy");
    private static final DateTimeFormatter ZONED_FORMAT = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm z");

    private SubscriptionCalculator() {}

    public static LocalDate expiryDate(LocalDate start, Plan plan) {
        return start.plus(plan.getLength());
    }

    public static LocalDate graceEndDate(LocalDate expiry, int graceDays) {
        return expiry.plusDays(graceDays);
    }

    public static long daysRemaining(LocalDate today, LocalDate expiry) {
        return Math.max(0, ChronoUnit.DAYS.between(today, expiry));
    }

    public static Period timeRemaining(LocalDate today, LocalDate expiry) {
        return today.isAfter(expiry) ? Period.ZERO : Period.between(today, expiry);
    }

    public static SubscriptionStatus statusOn(LocalDate today, LocalDate expiry, int graceDays) {
        if (!today.isAfter(expiry)) {
            return SubscriptionStatus.ACTIVE;
        }
        if (!today.isAfter(graceEndDate(expiry, graceDays))) {
            return SubscriptionStatus.IN_GRACE_PERIOD;
        }
        return SubscriptionStatus.EXPIRED;
    }

    public static ZonedDateTime expiryMomentIst(LocalDate expiry) {
        return expiry.atTime(LocalTime.of(23, 59)).atZone(IST);
    }

    public static ZonedDateTime inZone(ZonedDateTime moment, ZoneId zone) {
        return moment.withZoneSameInstant(zone);
    }

    public static Duration timeUntil(ZonedDateTime now, ZonedDateTime moment) {
        Duration d = Duration.between(now, moment);
        return d.isNegative() ? Duration.ZERO : d;
    }

    public static LocalDateTime reminderTime(LocalDate expiry, int daysBefore) {
        return expiry.minusDays(daysBefore).atTime(9, 0);
    }

    public static String formatDate(LocalDate date) {
        return date.format(DATE_FORMAT);
    }

    public static String formatMoment(ZonedDateTime moment) {
        return moment.format(ZONED_FORMAT);
    }

    public static String describe(Period period) {
        return period.getYears() + "y " + period.getMonths() + "m " + period.getDays() + "d";
    }

    public static String describe(Duration duration) {
        return duration.toDays() + "d " + duration.toHoursPart() + "h " + duration.toMinutesPart() + "m";
    }
}
