package engine;

import model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class FilterEngine {

    private FilterEngine() {}

    public static final Predicate<Product> IN_STOCK = Product::isInStock;

    public static Predicate<Product> priceBetween(double min, double max) {
        return p -> p.getPrice() >= min && p.getPrice() <= max;
    }

    public static Predicate<Product> inCategory(String category) {
        return p -> p.getCategory().equalsIgnoreCase(category);
    }

    public static List<Product> filter(List<Product> products, Predicate<Product> rule) {
        List<Product> result = new ArrayList<>();
        products.forEach(p -> {
            if (rule.test(p)) {
                result.add(p);
            }
        });
        return result;
    }

    public static <R> List<R> transform(List<Product> products, Function<Product, R> mapper) {
        List<R> result = new ArrayList<>();
        products.forEach(p -> result.add(mapper.apply(p)));
        return result;
    }

    public static <T> void printAll(List<T> items, Consumer<T> printer) {
        items.forEach(printer);
    }

    public static Product orDefault(Product found, Supplier<Product> fallback) {
        return found != null ? found : fallback.get();
    }
}
