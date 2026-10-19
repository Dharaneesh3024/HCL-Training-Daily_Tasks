package app;

import engine.FilterEngine;
import functional.PriceRule;
import model.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        List<Product> products = loadProducts();

        predicates(products);
        functions(products);
        consumers(products);
        suppliers();
        methodReferenceKinds();
        strategies();
    }

    static List<Product> loadProducts() {
        return Arrays.asList(
                "1,Laptop,Electronics,55000,true",
                "2,Headphones,Electronics,1800,true",
                "3,Mouse,Electronics,450,true",
                "4,Keyboard,Electronics,900,false",
                "5,Notebook,Stationery,45,true",
                "6,Pen Set,Stationery,120,true",
                "7,Desk Lamp,Home,650,false",
                "8,Backpack,Travel,1500,true")
                .stream()
                .map(Product::fromLine)
                .collect(Collectors.toList());
    }

    static void predicates(List<Product> products) {
        System.out.println("=== 1. Composed Predicates ===");
        Predicate<Product> affordable = FilterEngine.priceBetween(0, 1000);
        Predicate<Product> electronics = FilterEngine.inCategory("electronics");

        System.out.println("  affordable AND electronics AND in stock:");
        FilterEngine.printAll(
                FilterEngine.filter(products, affordable.and(electronics).and(FilterEngine.IN_STOCK)),
                p -> System.out.println("    " + p));

        System.out.println("  stationery OR travel:");
        FilterEngine.printAll(
                FilterEngine.filter(products, FilterEngine.inCategory("Stationery").or(FilterEngine.inCategory("Travel"))),
                System.out::println);

        System.out.println("  NOT in stock (negate):");
        FilterEngine.printAll(FilterEngine.filter(products, FilterEngine.IN_STOCK.negate()), System.out::println);
    }

    static void functions(List<Product> products) {
        System.out.println("\n=== 2. Function chains ===");
        Function<Product, String> nameOf = Product::getName;
        Function<Product, String> shout = nameOf.andThen(String::toUpperCase);
        System.out.println("  names, upper case: " + FilterEngine.transform(products, shout));

        double gstRate = 0.18;
        Function<Double, Double> discount = price -> price * 0.90;
        Function<Double, Double> addGst = price -> price * (1 + gstRate);
        Function<Double, String> label = price -> String.format("Rs %.2f", price);

        Function<Double, String> finalPrice = discount.andThen(addGst).andThen(label);
        Function<Double, String> composedOrder = label.compose(addGst).compose(discount);

        System.out.println("  Laptop final price (andThen):  " + finalPrice.apply(products.get(0).getPrice()));
        System.out.println("  Laptop final price (compose):  " + composedOrder.apply(products.get(0).getPrice()));

        List<String> priceTags = FilterEngine.transform(products,
                p -> p.getName() + " -> " + finalPrice.apply(p.getPrice()));
        System.out.println("  first two price tags: " + priceTags.subList(0, 2));
    }

    static void consumers(List<Product> products) {
        System.out.println("\n=== 3. Consumer ===");
        List<String> audit = new ArrayList<>();
        Consumer<Product> print = p -> System.out.println("    " + p.getName());
        Consumer<Product> record = p -> audit.add(p.getName());

        System.out.println("  printing, then recording (andThen):");
        FilterEngine.printAll(FilterEngine.filter(products, FilterEngine.inCategory("Stationery")), print.andThen(record));
        System.out.println("  audit list: " + audit);

        List<Product> sorted = new ArrayList<>(products);
        sorted.sort(Comparator.comparing(Product::getPrice).reversed());
        System.out.println("  most expensive: " + sorted.get(0).getName());
    }

    static void suppliers() {
        System.out.println("\n=== 4. Supplier ===");
        Supplier<Product> placeholder = () -> new Product(0, "Unknown", "None", 0.0, false);
        Supplier<List<Product>> emptyList = ArrayList::new;

        Product found = null;
        System.out.println("  default product: " + FilterEngine.orDefault(found, placeholder));
        System.out.println("  new empty list from Supplier: " + emptyList.get());
    }

    static void methodReferenceKinds() {
        System.out.println("\n=== 5. Four kinds of method reference ===");
        Function<String, Integer> staticRef = Integer::parseInt;
        System.out.println("  static:              Integer::parseInt        -> " + staticRef.apply("42"));

        Consumer<String> boundRef = System.out::println;
        System.out.print("  bound instance:      System.out::println      -> ");
        boundRef.accept("printed");

        Function<Product, String> unboundRef = Product::getName;
        System.out.println("  unbound instance:    Product::getName         -> "
                + unboundRef.apply(new Product(9, "Router", "Electronics", 2200, true)));

        Supplier<ArrayList<String>> constructorRef = ArrayList::new;
        System.out.println("  constructor:         ArrayList::new          -> " + constructorRef.get());
    }

    static void strategies() {
        System.out.println("\n=== 6. Custom @FunctionalInterface as Strategy ===");
        int bulkLimit = 10;

        Map<String, PriceRule> rules = new LinkedHashMap<>();
        rules.put("none", (unitPrice, qty) -> unitPrice * qty);
        rules.put("bulk10", (unitPrice, qty) -> qty >= bulkLimit ? unitPrice * qty * 0.90 : unitPrice * qty);
        rules.put("flat50", (unitPrice, qty) -> Math.max(0, unitPrice * qty - 50));
        rules.put("bulk10 rounded", rules.get("bulk10").thenRound());

        rules.forEach((name, rule) ->
                System.out.printf("  %-15s 12 x 45.50 = %8.2f   2 x 45.50 = %8.2f%n",
                        name, rule.apply(45.50, 12), rule.apply(45.50, 2)));
    }
}
