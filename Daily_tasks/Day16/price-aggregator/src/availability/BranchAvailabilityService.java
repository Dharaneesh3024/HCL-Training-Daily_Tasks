package availability;

import aggregator.SupplierUnavailableException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class BranchAvailabilityService implements AutoCloseable {

    private final Map<String, Integer> carsByBranch;
    private final Set<String> unreachable;
    private final ExecutorService pool;

    public BranchAvailabilityService(Map<String, Integer> carsByBranch, Set<String> unreachable) {
        this.carsByBranch = Map.copyOf(carsByBranch);
        this.unreachable = Set.copyOf(unreachable);
        this.pool = Executors.newFixedThreadPool(carsByBranch.size());
    }

    private BranchAvailability lookup(String branch) {
        try {
            Thread.sleep(300L + branch.length() * 50L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new SupplierUnavailableException(branch + " check interrupted");
        }
        if (unreachable.contains(branch)) {
            throw new SupplierUnavailableException(branch + " is not responding");
        }
        return BranchAvailability.of(branch, carsByBranch.getOrDefault(branch, 0));
    }

    public List<BranchAvailability> checkSequentially(List<String> branches) {
        List<BranchAvailability> results = new ArrayList<>();
        for (String branch : branches) {
            try {
                results.add(lookup(branch));
            } catch (SupplierUnavailableException e) {
                results.add(BranchAvailability.unknown(branch));
            }
        }
        return results;
    }

    public CompletableFuture<List<BranchAvailability>> checkAll(List<String> branches) {
        List<CompletableFuture<BranchAvailability>> futures = branches.stream()
                .map(b -> CompletableFuture
                        .supplyAsync(() -> lookup(b), pool)
                        .exceptionally(ex -> BranchAvailability.unknown(b)))
                .collect(Collectors.toList());

        return CompletableFuture.allOf(futures.toArray(new CompletableFuture<?>[0]))
                .thenApply(done -> futures.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList()));
    }

    public CompletableFuture<Integer> totalAvailable(List<String> branches) {
        return checkAll(branches).thenApply(results -> results.stream()
                .filter(BranchAvailability::isReachable)
                .mapToInt(BranchAvailability::getAvailableCars)
                .sum());
    }

    @Override
    public void close() {
        pool.shutdown();
    }
}
