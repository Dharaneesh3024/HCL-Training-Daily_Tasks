package app;

import analytics.SalesAnalytics;
import data.OrderGenerator;
import model.Order;

import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        List<Order> orders = OrderGenerator.generate(1000, 42);
        SalesAnalytics sales = new SalesAnalytics(orders);

        creationAndLaziness();
        reports(sales);
        parallelTiming(orders);
    }

    static void creationAndLaziness() {
        System.out.println("=== 1. Creating streams ===");
        System.out.println("  Stream.of:          " + Stream.of("a", "b", "c").collect(Collectors.toList()));
        System.out.println("  Arrays.stream:      " + Arrays.stream(new int[]{3, 1, 2}).sum());
        System.out.println("  IntStream.range:    " + IntStream.rangeClosed(1, 5).boxed().collect(Collectors.toList()));
        System.out.println("  Stream.iterate:     " + Stream.iterate(1, x -> x * 2).limit(8).collect(Collectors.toList()));

        System.out.println("\n=== 2. Laziness ===");
        System.out.println("  nothing prints until the terminal operation runs:");
        Stream<Integer> pipeline = Stream.of(5, 8, 12, 3, 20)
                .peek(x -> System.out.println("    looking at " + x))
                .filter(x -> x > 10);
        System.out.println("  pipeline built, calling findFirst now");
        System.out.println("  first match: " + pipeline.findFirst().orElse(-1) + " (stopped early, 3 and 20 never visited)");
    }

    static void reports(SalesAnalytics sales) {
        System.out.println("\n=== 3. Sales reports (1,000 orders) ===");

        System.out.println("  revenue by category (groupingBy + summingDouble):");
        sales.revenueByCategory().forEach((category, revenue) ->
                System.out.printf("    %-12s %14.2f%n", category, revenue));

        System.out.println("  top 3 customers (toMap, sorted, limit):");
        sales.topCustomers(3).forEach(e ->
                System.out.printf("    %-12s %14.2f%n", e.getKey(), e.getValue()));

        Map<Boolean, List<Order>> split = sales.deliveredVersusRest();
        System.out.println("  partitioningBy delivered:");
        System.out.println("    delivered:     " + split.get(true).size());
        System.out.println("    not delivered: " + split.get(false).size());
        System.out.println("  count by status (groupingBy + counting): " + sales.countByStatus());

        System.out.printf("  average basket size: %.2f items%n", sales.averageBasketSize());
        System.out.println("  products sold (distinct, sorted, joining):");
        System.out.println("    " + sales.productList());

        DoubleSummaryStatistics stats = sales.orderValueStats();
        System.out.printf("  order value (summarizing): min %.2f, max %.2f, avg %.2f, count %d%n",
                stats.getMin(), stats.getMax(), stats.getAverage(), stats.getCount());
        System.out.printf("  total revenue (reduce): %.2f%n", sales.totalRevenue());
        System.out.println("  any cancelled order above 20000? " + sales.hasCancelledAbove(20000));
        System.out.println("  largest 3 order ids: "
                + sales.largestOrders(3).stream().map(o -> String.valueOf(o.getId())).collect(Collectors.joining(", ")));
    }

    static double heavyScore(Order order) {
        double x = order.getTotal();
        for (int i = 0; i < 100_000; i++) {
            x = Math.sqrt(x + i);
        }
        return x;
    }

    static double lightScore(Order order) {
        return order.getTotal() * 1.18;
    }

    static <T> long timeMs(Supplier<T> work) {
        long start = System.nanoTime();
        work.get();
        return (System.nanoTime() - start) / 1_000_000;
    }

    static void parallelTiming(List<Order> orders) {
        System.out.println("\n=== 4. Sequential vs parallel ===");
        System.out.println("  CPU cores available: " + Runtime.getRuntime().availableProcessors());

        for (int i = 0; i < 3; i++) {
            orders.stream().mapToDouble(Main::heavyScore).sum();
            orders.parallelStream().mapToDouble(Main::heavyScore).sum();
        }

        long heavySeq = timeMs(() -> orders.stream().mapToDouble(Main::heavyScore).sum());
        long heavyPar = timeMs(() -> orders.parallelStream().mapToDouble(Main::heavyScore).sum());

        List<Order> small = orders.subList(0, 50);
        int rounds = 20000;
        long lightSeq = timeMs(() -> IntStream.range(0, rounds)
                .mapToDouble(r -> small.stream().mapToDouble(Main::lightScore).sum()).sum());
        long lightPar = timeMs(() -> IntStream.range(0, rounds)
                .mapToDouble(r -> small.parallelStream().mapToDouble(Main::lightScore).sum()).sum());

        System.out.printf("  %-34s %10s %10s%n", "Workload (ms)", "sequential", "parallel");
        System.out.printf("  %-34s %10d %10d%n", "heavy map (100k sqrt per order)", heavySeq, heavyPar);
        System.out.printf("  %-34s %10d %10d%n", "light map on 50 orders, " + rounds + "x", lightSeq, lightPar);
        System.out.println("  same result from both? "
                + (orders.stream().mapToDouble(Main::heavyScore).sum()
                == orders.parallelStream().mapToDouble(Main::heavyScore).sum()));
    }
}
