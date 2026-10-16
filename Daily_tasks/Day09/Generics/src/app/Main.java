package app;

import model.Customer;
import model.Product;
import repository.InMemoryRepository;
import toolkit.Box;
import toolkit.CsvLoader;
import toolkit.Pair;
import toolkit.TrackedResource;
import toolkit.Util;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        pairDemo();
        boxDemo();
        maxDemo();
        wildcardDemo();
        closeOrderDemo();
        csvAndRepositoryDemo();
    }

    static void pairDemo() {
        System.out.println("=== 1. Pair<K,V> ===");
        Pair<String, Integer> p = new Pair<>("age", 21);
        System.out.println("  " + p + " swapped -> " + p.swap());
    }

    static void boxDemo() {
        System.out.println("\n=== 2. Box<T extends Number> ===");
        Box<Integer> a = new Box<>(7);
        Box<Double> b = new Box<>(3.5);
        System.out.println("  " + a + " doubled = " + a.doubled());
        System.out.println("  " + b + " doubled = " + b.doubled());
        System.out.println("  a bigger than b? " + a.isBiggerThan(b));
    }

    static void maxDemo() {
        System.out.println("\n=== 3. max() with <T extends Comparable<T>> ===");
        System.out.println("  max int:    " + Util.max(List.of(4, 19, 7, 12)));
        System.out.println("  max String: " + Util.max(List.of("pen", "bag", "lamp")));
        System.out.println("  max Product: " + Util.max(List.of(
                new Product(1, "Pen", 10, 5),
                new Product(2, "Lamp", 420, 2),
                new Product(3, "Bag", 899, 1))));
    }

    static void wildcardDemo() {
        System.out.println("\n=== 4. Wildcards ===");
        List<Integer> ints = List.of(1, 2, 3);
        List<Double> doubles = List.of(1.5, 2.5);
        System.out.println("  sum of ints:    " + Util.sumAll(ints));
        System.out.println("  sum of doubles: " + Util.sumAll(doubles));

        List<Number> numbers = new ArrayList<>();
        Util.fillWithIntegers(numbers, 3);
        System.out.println("  List<Number> filled via ? super Integer: " + numbers);

        List<Object> objects = new ArrayList<>();
        Util.copy(ints, objects);
        System.out.println("  copied ints into List<Object>: " + objects);

        System.out.println("  printAll(List<?>):");
        Util.printAll(List.of("x", 2, 3.0));
    }

    static void closeOrderDemo() {
        System.out.println("\n=== 5. try-with-resources close order ===");
        try (TrackedResource first = new TrackedResource("A");
             TrackedResource second = new TrackedResource("B");
             TrackedResource third = new TrackedResource("C")) {
            first.use(false);
            second.use(false);
            third.use(false);
        }

        System.out.println("\n  with an exception inside:");
        try (TrackedResource x = new TrackedResource("X");
             TrackedResource y = new TrackedResource("Y")) {
            y.use(true);
            x.use(false);
        } catch (IllegalStateException e) {
            System.out.println("  caught: " + e.getMessage());
        }
    }

    static void csvAndRepositoryDemo() {
        System.out.println("\n=== 6. CSV + InMemoryRepository ===");
        InMemoryRepository<Product, Integer> products = new InMemoryRepository<>(Product::getId);
        InMemoryRepository<Customer, Integer> customers = new InMemoryRepository<>(Customer::getId);

        try {
            products.saveAll(CsvLoader.load(Paths.get("data", "products.csv"), Product::fromCsv));
            customers.saveAll(CsvLoader.load(Paths.get("data", "customers.csv"), Customer::fromCsv));
        } catch (IOException e) {
            System.out.println("  could not read data files: " + e.getMessage());
            return;
        }

        System.out.println("  products loaded:  " + products.count());
        products.findAll().forEach(p -> System.out.println("    " + p));
        System.out.println("  customers loaded: " + customers.count());
        customers.findAll().forEach(c -> System.out.println("    " + c));

        System.out.println("  product 3: " + products.findById(3).map(Product::getName).orElse("none"));
        System.out.println("  product 99: " + products.findById(99).map(Product::getName).orElse("none"));
        System.out.println("  delete product 2: " + products.deleteById(2));
        System.out.println("  products left: " + products.count());
    }
}
