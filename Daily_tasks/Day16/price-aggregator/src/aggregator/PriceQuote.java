package aggregator;

public class PriceQuote {

    private final String supplier;
    private final double price;
    private final boolean available;

    private PriceQuote(String supplier, double price, boolean available) {
        this.supplier = supplier;
        this.price = price;
        this.available = available;
    }

    public static PriceQuote of(String supplier, double price) {
        return new PriceQuote(supplier, price, true);
    }

    public static PriceQuote unavailable(String supplier) {
        return new PriceQuote(supplier, 0, false);
    }

    public String getSupplier() { return supplier; }
    public double getPrice() { return price; }
    public boolean isAvailable() { return available; }

    @Override
    public String toString() {
        return available ? String.format("%-7s %9.2f", supplier, price) : String.format("%-7s %9s", supplier, "DOWN");
    }
}
