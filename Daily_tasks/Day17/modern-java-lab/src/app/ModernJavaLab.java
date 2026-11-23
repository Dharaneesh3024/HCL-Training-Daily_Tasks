package app;

import rental.Booking;
import rental.BookingStatus;
import rental.NotificationTemplates;
import rental.Vehicle;
import shapes.Circle;
import shapes.Rectangle;
import shapes.Shape;
import shapes.ShapeMath;
import shapes.Triangle;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ModernJavaLab {

    record InvoiceLine(String item, int quantity, double unitPrice) {

        double amount() {
            return quantity * unitPrice;
        }
    }

    public static void main(String[] args) {
        records();
        sealedShapes();
        patternMatching();
        switchExpressions();
        textBlocks();
        immutableCollections();
        stringMethods();
        varGuidelines();
        refactoredProject();
    }

    static void records() {
        System.out.println("=== 1. Records ===");
        Circle a = new Circle(2.5);
        Circle b = new Circle(2.5);
        System.out.println("  toString:  " + a);
        System.out.println("  equals:    " + a.equals(b) + ", same hashCode: " + (a.hashCode() == b.hashCode()));
        System.out.println("  accessor:  radius() = " + a.radius());
        try {
            new Circle(-1);
        } catch (IllegalArgumentException e) {
            System.out.println("  compact constructor rejected radius -1: " + e.getMessage());
        }
    }

    static void sealedShapes() {
        System.out.println("\n=== 2. Sealed interface and exhaustive switch ===");
        List<Shape> shapes = List.of(new Circle(3), new Rectangle(4, 5), new Rectangle(6, 6),
                new Triangle(10, 4), new Circle(12));
        for (Shape shape : shapes) {
            System.out.printf("  %-34s area %8.2f%n", ShapeMath.describe(shape), ShapeMath.area(shape));
        }
        System.out.printf("  total area: %.2f%n", shapes.stream().mapToDouble(ShapeMath::area).sum());
    }

    static String classify(Object value) {
        return switch (value) {
            case null -> "null value";
            case Integer i when i > 100 -> "big integer " + i;
            case Integer i -> "integer " + i;
            case String s when s.isBlank() -> "blank text";
            case String s -> "text of length " + s.length();
            case List<?> list -> "list with " + list.size() + " items";
            default -> "something else: " + value.getClass().getSimpleName();
        };
    }

    static void patternMatching() {
        System.out.println("\n=== 3. Pattern matching ===");
        Object input = "invoice-2026";
        if (input instanceof String text && text.length() > 5) {
            System.out.println("  instanceof pattern: " + text.toUpperCase());
        }
        for (Object value : new Object[]{7, 250, "hello", "   ", List.of(1, 2), 3.5, null}) {
            System.out.println("  switch pattern: " + classify(value));
        }
    }

    static void switchExpressions() {
        System.out.println("\n=== 4. Switch expressions ===");
        for (DayOfWeek day : new DayOfWeek[]{DayOfWeek.MONDAY, DayOfWeek.SATURDAY, DayOfWeek.WEDNESDAY}) {
            String kind = switch (day) {
                case SATURDAY, SUNDAY -> "weekend";
                default -> "weekday";
            };
            int surchargePercent = switch (day) {
                case FRIDAY, SATURDAY, SUNDAY -> 20;
                default -> {
                    int base = 0;
                    yield base;
                }
            };
            System.out.println("  " + day + ": " + kind + ", rental surcharge " + surchargePercent + "%");
        }
    }

    static void textBlocks() {
        System.out.println("\n=== 5. Text block ===");
        List<InvoiceLine> lines = List.of(
                new InvoiceLine("Notebook", 4, 45.50),
                new InvoiceLine("Pen Set", 2, 120.00),
                new InvoiceLine("Backpack", 1, 1500.00));

        String body = lines.stream()
                .map(l -> "%-12s %3d %10.2f".formatted(l.item(), l.quantity(), l.amount()))
                .collect(Collectors.joining("\n"));
        double total = lines.stream().mapToDouble(InvoiceLine::amount).sum();

        String email = """
                Subject: Your invoice #4821

                Hello %s,

                Thank you for your order. Here is your invoice:

                Item         Qty     Amount
                %s
                %s
                Total        %14.2f

                Regards,
                Billing Team
                """.formatted("Arun", body, "-".repeat(28), total);
        System.out.println(email.indent(2));
    }

    static void immutableCollections() {
        System.out.println("=== 6. Immutable collections ===");
        List<String> fixed = List.of("A", "B", "C");
        Set<Integer> numbers = Set.of(1, 2, 3);
        Map<String, Integer> stock = Map.of("pen", 10, "book", 4);

        try {
            fixed.add("D");
        } catch (UnsupportedOperationException e) {
            System.out.println("  List.of add -> UnsupportedOperationException");
        }
        try {
            numbers.remove(1);
        } catch (UnsupportedOperationException e) {
            System.out.println("  Set.of remove -> UnsupportedOperationException");
        }
        try {
            stock.put("bag", 2);
        } catch (UnsupportedOperationException e) {
            System.out.println("  Map.of put -> UnsupportedOperationException");
        }
        try {
            List.of("A", null);
        } catch (NullPointerException e) {
            System.out.println("  List.of with null element -> NullPointerException");
        }

        List<String> source = new ArrayList<>(List.of("x", "y"));
        List<String> snapshot = List.copyOf(source);
        source.add("z");
        System.out.println("  List.copyOf is a snapshot: source " + source + ", copy " + snapshot);
    }

    static void stringMethods() {
        System.out.println("\n=== 7. New String methods ===");
        String[] inputs = {"  Arun Kumar  ", "   ", "", "\tDivya\n"};
        for (String raw : inputs) {
            String cleaned = raw.strip();
            System.out.println("  [" + cleaned + "] blank=" + raw.isBlank());
        }

        String pasted = "Notebook\n\n  Pen Set \n   \nBackpack";
        List<String> items = pasted.lines().map(String::strip).filter(s -> !s.isBlank()).toList();
        System.out.println("  lines(), cleaned and non-blank: " + items);
        System.out.println("  repeat: " + "=".repeat(20));
        System.out.println("  stripLeading/stripTrailing: [" + "  x  ".stripLeading() + "] [" + "  x  ".stripTrailing() + "]");
    }

    static void varGuidelines() {
        System.out.println("\n=== 8. var ===");
        var customers = new ArrayList<String>();
        customers.add("Arun");
        var count = customers.size();
        var message = "customers: " + customers + " (" + count + ")";
        System.out.println("  " + message);
        for (var customer : customers) {
            System.out.println("  loop variable inferred as String: " + customer.getClass().getSimpleName());
        }
    }

    static void refactoredProject() {
        System.out.println("\n=== 9. Refactored project: records, sealed status, text blocks ===");
        Vehicle swift = new Vehicle(1, "  tn01ab1234 ", " Chennai ", 1800);
        System.out.println("  " + swift);

        Booking booking = new Booking(101, swift.id(), LocalDate.of(2026, 12, 1), LocalDate.of(2026, 12, 4),
                new BookingStatus.Pending());
        System.out.println("  " + BookingStatus.label(booking.status()) + ", can cancel: "
                + BookingStatus.canCancel(booking.status()));

        Booking confirmed = booking.withStatus(new BookingStatus.Confirmed(LocalDate.of(2026, 11, 23)));
        System.out.println("  " + BookingStatus.label(confirmed.status()));
        System.out.println();
        System.out.print(NotificationTemplates.confirmation("Arun", confirmed, swift).indent(2));

        Booking cancelled = confirmed.withStatus(new BookingStatus.Cancelled("Customer changed plans"));
        System.out.println("  can cancel after cancellation: " + BookingStatus.canCancel(cancelled.status()));
        System.out.print(NotificationTemplates.statusUpdate("Arun", cancelled).indent(2));
    }
}
