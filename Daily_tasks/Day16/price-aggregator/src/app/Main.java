package app;

import aggregator.PriceAggregator;
import aggregator.PriceQuote;
import aggregator.SupplierClient;
import availability.BranchAvailability;
import availability.BranchAvailabilityService;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class Main {

    private static long lastMs;

    public static void main(String[] args) {
        priceAggregator();
        branchAvailability();
    }

    static <T> T timed(Supplier<T> work) {
        long start = System.nanoTime();
        T result = work.get();
        lastMs = (System.nanoTime() - start) / 1_000_000;
        return result;
    }

    static void show(String title, List<PriceQuote> quotes, long ms) {
        System.out.println("  " + title + " (" + ms + " ms)");
        quotes.forEach(q -> System.out.println("    " + q));
        System.out.println("    cheapest: " + PriceAggregator.cheapest(quotes).map(PriceQuote::toString).orElse("none available"));
    }

    static void priceAggregator() {
        List<SupplierClient> suppliers = Arrays.asList(
                new SupplierClient("ShopA", 1000, 1499.00, false),
                new SupplierClient("ShopB", 1500, 1399.00, false),
                new SupplierClient("ShopC", 2000, 1449.00, false));

        System.out.println("=== 1. Price Aggregator: all suppliers healthy ===");
        try (PriceAggregator aggregator = new PriceAggregator(suppliers)) {
            List<PriceQuote> sequential = timed(() -> aggregator.fetchSequentially("Headphones"));
            long sequentialMs = lastMs;
            show("Sequential", sequential, sequentialMs);

            List<PriceQuote> executor = timed(() -> aggregator.fetchWithExecutor("Headphones"));
            long executorMs = lastMs;
            show("ExecutorService + Callable", executor, executorMs);

            List<PriceQuote> async = timed(() -> aggregator.fetchAsync("Headphones").join());
            long asyncMs = lastMs;
            show("CompletableFuture.allOf", async, asyncMs);

            PriceQuote combined = timed(() -> aggregator.cheaperOfFirstTwo("Headphones").join());
            System.out.println("\n  thenCombine, cheaper of ShopA and ShopB: " + combined + " (" + lastMs + " ms)");

            System.out.println("\n  Timing comparison");
            System.out.printf("    %-30s %6d ms%n", "Sequential", sequentialMs);
            System.out.printf("    %-30s %6d ms%n", "ExecutorService + Callable", executorMs);
            System.out.printf("    %-30s %6d ms%n", "CompletableFuture.allOf", asyncMs);
        }

        System.out.println("\n=== 2. One supplier fails (ShopB) ===");
        List<SupplierClient> withFailure = Arrays.asList(suppliers.get(0), suppliers.get(1).failing(), suppliers.get(2));
        try (PriceAggregator aggregator = new PriceAggregator(withFailure)) {
            List<PriceQuote> executor = timed(() -> aggregator.fetchWithExecutor("Headphones"));
            show("ExecutorService, ExecutionException caught", executor, lastMs);

            List<PriceQuote> async = timed(() -> aggregator.fetchAsync("Headphones").join());
            show("CompletableFuture, exceptionally fallback", async, lastMs);

            String best = timed(() -> aggregator.cheapestAsync("Headphones")
                    .thenApply(q -> q.map(PriceQuote::toString).orElse("none"))
                    .join());
            System.out.println("  thenApply on the combined future: " + best + " (" + lastMs + " ms)");
        }
    }

    static void branchAvailability() {
        List<String> branches = Arrays.asList("Chennai", "Coimbatore", "Madurai", "Trichy");
        Map<String, Integer> cars = Map.of("Chennai", 12, "Coimbatore", 7, "Madurai", 4, "Trichy", 9);

        System.out.println("\n=== 3. Parallel availability check across branches ===");
        try (BranchAvailabilityService service = new BranchAvailabilityService(cars, Set.of("Madurai"))) {
            List<BranchAvailability> sequential = timed(() -> service.checkSequentially(branches));
            System.out.println("  Sequential (" + lastMs + " ms)");
            sequential.forEach(r -> System.out.println("    " + r));

            List<BranchAvailability> parallel = timed(() -> service.checkAll(branches).join());
            System.out.println("  CompletableFuture (" + lastMs + " ms)");
            parallel.forEach(r -> System.out.println("    " + r));

            int total = service.totalAvailable(branches).join();
            System.out.println("  total cars available across reachable branches: " + total);
        }
    }
}
