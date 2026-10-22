package aggregator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

public class PriceAggregator implements AutoCloseable {

    private final List<SupplierClient> suppliers;
    private final ExecutorService pool;

    public PriceAggregator(List<SupplierClient> suppliers) {
        this.suppliers = List.copyOf(suppliers);
        this.pool = Executors.newFixedThreadPool(suppliers.size());
    }

    public List<PriceQuote> fetchSequentially(String product) {
        List<PriceQuote> quotes = new ArrayList<>();
        for (SupplierClient supplier : suppliers) {
            try {
                quotes.add(supplier.fetchPrice(product));
            } catch (SupplierUnavailableException e) {
                quotes.add(PriceQuote.unavailable(supplier.getName()));
            }
        }
        return quotes;
    }

    public List<PriceQuote> fetchWithExecutor(String product) {
        List<Future<PriceQuote>> futures = new ArrayList<>();
        for (SupplierClient supplier : suppliers) {
            Callable<PriceQuote> task = () -> supplier.fetchPrice(product);
            futures.add(pool.submit(task));
        }

        List<PriceQuote> quotes = new ArrayList<>();
        for (int i = 0; i < futures.size(); i++) {
            String name = suppliers.get(i).getName();
            try {
                quotes.add(futures.get(i).get(5, TimeUnit.SECONDS));
            } catch (ExecutionException | TimeoutException e) {
                quotes.add(PriceQuote.unavailable(name));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                quotes.add(PriceQuote.unavailable(name));
            }
        }
        return quotes;
    }

    public CompletableFuture<List<PriceQuote>> fetchAsync(String product) {
        List<CompletableFuture<PriceQuote>> futures = suppliers.stream()
                .map(s -> quoteAsync(s, product))
                .collect(Collectors.toList());

        return CompletableFuture.allOf(futures.toArray(new CompletableFuture<?>[0]))
                .thenApply(done -> futures.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList()));
    }

    private CompletableFuture<PriceQuote> quoteAsync(SupplierClient supplier, String product) {
        return CompletableFuture
                .supplyAsync(() -> supplier.fetchPrice(product), pool)
                .exceptionally(ex -> PriceQuote.unavailable(supplier.getName()));
    }

    public CompletableFuture<PriceQuote> cheaperOfFirstTwo(String product) {
        CompletableFuture<PriceQuote> first = quoteAsync(suppliers.get(0), product);
        CompletableFuture<PriceQuote> second = quoteAsync(suppliers.get(1), product);
        return first.thenCombine(second,
                (a, b) -> cheapest(List.of(a, b)).orElse(PriceQuote.unavailable("none")));
    }

    public CompletableFuture<Optional<PriceQuote>> cheapestAsync(String product) {
        return fetchAsync(product).thenApply(PriceAggregator::cheapest);
    }

    public static Optional<PriceQuote> cheapest(List<PriceQuote> quotes) {
        return quotes.stream()
                .filter(PriceQuote::isAvailable)
                .min(Comparator.comparingDouble(PriceQuote::getPrice));
    }

    @Override
    public void close() {
        pool.shutdown();
    }
}
