package app;

import calculator.SubscriptionCalculator;
import exception.SubscriptionNotFoundException;
import model.Plan;
import model.Subscription;
import service.SubscriptionService;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

public class Main {

    private static final LocalDate TODAY = LocalDate.of(2026, 10, 21);

    public static void main(String[] args) {
        SubscriptionService service = new SubscriptionService();
        service.add(new Subscription(1, "Arun", Plan.MONTHLY, LocalDate.of(2026, 10, 5)));
        service.add(new Subscription(2, "Divya", Plan.QUARTERLY, LocalDate.of(2026, 8, 15)));
        service.add(new Subscription(3, "Karthik", Plan.YEARLY, LocalDate.of(2025, 11, 1)));
        service.registerEmail("Arun", "arun@example.com");

        optionalBasics(service);
        dates(service);
        zones(service);
    }

    static void optionalBasics(SubscriptionService service) {
        System.out.println("=== 1. Optional ===");
        System.out.println("  Optional.of:        " + Optional.of("value"));
        System.out.println("  Optional.empty:     " + Optional.empty());
        System.out.println("  Optional.ofNullable (missing env var): "
                + Optional.ofNullable(System.getenv("NO_SUCH_VARIABLE_X")));

        System.out.println("  findById(1):        " + service.findById(1));
        System.out.println("  findById(99):       " + service.findById(99));

        System.out.println("  map to customer:    " + service.findById(2).map(Subscription::getCustomer).orElse("unknown"));
        System.out.println("  filter yearly plan: " + service.findById(1).filter(s -> s.getPlan() == Plan.YEARLY).isPresent());

        System.out.println("  orElse evaluates its default every time:");
        service.findById(1).map(Subscription::getCustomer).orElse(describeDefault("orElse"));
        System.out.println("  orElseGet on a present value computes nothing:");
        service.findById(1).map(Subscription::getCustomer).orElseGet(() -> describeDefault("orElseGet"));
        System.out.println("  orElseGet on an empty value computes the default:");
        service.findById(99).map(Subscription::getCustomer).orElseGet(() -> describeDefault("orElseGet"));

        System.out.println("  flatMap email (Arun):    " + service.findEmailBySubscription(1).orElse("no email"));
        System.out.println("  flatMap email (Divya):   " + service.findEmailBySubscription(2).orElse("no email"));
        System.out.println("  flatMap email (id 99):   " + service.findEmailBySubscription(99).orElse("no email"));

        service.findById(3).ifPresentOrElse(
                s -> System.out.println("  ifPresentOrElse found: " + s.getCustomer()),
                () -> System.out.println("  ifPresentOrElse: nothing found"));

        try {
            service.getById(99);
        } catch (SubscriptionNotFoundException e) {
            System.out.println("  orElseThrow -> " + e.getMessage());
        }
    }

    static String describeDefault(String source) {
        System.out.println("    default computed by " + source);
        return "default";
    }

    static void dates(SubscriptionService service) {
        System.out.println("\n=== 2. Dates, expiry and grace period (today = "
                + SubscriptionCalculator.formatDate(TODAY) + ") ===");

        int graceDays = 7;
        for (int id = 1; id <= 3; id++) {
            Subscription s = service.getById(id);
            LocalDate expiry = SubscriptionCalculator.expiryDate(s.getStartDate(), s.getPlan());
            System.out.printf("  %-8s %-10s start %s  expiry %s  left %3d days (%s)  grace ends %s  status %s%n",
                    s.getCustomer(), s.getPlan(),
                    SubscriptionCalculator.formatDate(s.getStartDate()),
                    SubscriptionCalculator.formatDate(expiry),
                    SubscriptionCalculator.daysRemaining(TODAY, expiry),
                    SubscriptionCalculator.describe(SubscriptionCalculator.timeRemaining(TODAY, expiry)),
                    SubscriptionCalculator.formatDate(SubscriptionCalculator.graceEndDate(expiry, graceDays)),
                    SubscriptionCalculator.statusOn(TODAY, expiry, graceDays));
        }

        System.out.println("  month-end edge cases:");
        System.out.println("    31 Jan 2026 + 1 month = "
                + SubscriptionCalculator.formatDate(SubscriptionCalculator.expiryDate(LocalDate.of(2026, 1, 31), Plan.MONTHLY)));
        System.out.println("    29 Feb 2028 + 1 year  = "
                + SubscriptionCalculator.formatDate(SubscriptionCalculator.expiryDate(LocalDate.of(2028, 2, 29), Plan.YEARLY)));

        System.out.println("  status of one expiry (30 Nov 2026) across dates, grace " + graceDays + " days:");
        LocalDate expiry = LocalDate.of(2026, 11, 30);
        for (LocalDate day : new LocalDate[]{LocalDate.of(2026, 11, 30), LocalDate.of(2026, 12, 1),
                LocalDate.of(2026, 12, 7), LocalDate.of(2026, 12, 8)}) {
            System.out.println("    " + SubscriptionCalculator.formatDate(day) + " -> "
                    + SubscriptionCalculator.statusOn(day, expiry, graceDays));
        }
    }

    static void zones(SubscriptionService service) {
        System.out.println("\n=== 3. Time zones, Duration, reminder ===");
        LocalDate expiry = service.findExpiry(1).orElseThrow(() -> new SubscriptionNotFoundException(1));

        ZonedDateTime expiryIst = SubscriptionCalculator.expiryMomentIst(expiry);
        ZonedDateTime expiryUtc = SubscriptionCalculator.inZone(expiryIst, SubscriptionCalculator.UTC);
        ZonedDateTime expiryNy = SubscriptionCalculator.inZone(expiryIst, ZoneId.of("America/New_York"));

        System.out.println("  expiry in IST:       " + SubscriptionCalculator.formatMoment(expiryIst));
        System.out.println("  expiry in UTC:       " + SubscriptionCalculator.formatMoment(expiryUtc));
        System.out.println("  expiry in New York:  " + SubscriptionCalculator.formatMoment(expiryNy));
        System.out.println("  same instant? " + expiryIst.toInstant().equals(expiryUtc.toInstant()));

        ZonedDateTime nowIst = TODAY.atTime(20, 30).atZone(SubscriptionCalculator.IST);
        Duration left = SubscriptionCalculator.timeUntil(nowIst, expiryIst);
        System.out.println("  time left from " + SubscriptionCalculator.formatMoment(nowIst) + ": "
                + SubscriptionCalculator.describe(left));
        System.out.println("  after expiry the duration is: "
                + SubscriptionCalculator.describe(SubscriptionCalculator.timeUntil(expiryIst.plusDays(1), expiryIst)));

        System.out.println("  renewal reminder 3 days before expiry: " + SubscriptionCalculator.reminderTime(expiry, 3));
    }
}
