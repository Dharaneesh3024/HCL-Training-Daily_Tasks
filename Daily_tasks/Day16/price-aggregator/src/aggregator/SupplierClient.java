package aggregator;

public class SupplierClient {

    private final String name;
    private final long delayMs;
    private final double price;
    private final boolean failing;

    public SupplierClient(String name, long delayMs, double price, boolean failing) {
        this.name = name;
        this.delayMs = delayMs;
        this.price = price;
        this.failing = failing;
    }

    public SupplierClient failing() {
        return new SupplierClient(name, delayMs, price, true);
    }

    public String getName() { return name; }

    public PriceQuote fetchPrice(String product) {
        try {
            Thread.sleep(delayMs);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new SupplierUnavailableException(name + " was interrupted");
        }
        if (failing) {
            throw new SupplierUnavailableException(name + " could not price " + product);
        }
        return PriceQuote.of(name, price);
    }
}
