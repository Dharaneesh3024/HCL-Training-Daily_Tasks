package data;

import model.Order;
import model.OrderLine;
import model.OrderStatus;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class OrderGenerator {

    private static final String[] CUSTOMERS = {
        "Arun", "Divya", "Karthik", "Meena", "Ravi", "Sneha", "Vikram", "Priya",
        "Naveen", "Lakshmi", "Suresh", "Anitha", "Rahul", "Deepa", "Manoj"
    };

    private static final String[][] CATALOGUE = {
        {"Laptop", "Electronics", "55000"}, {"Headphones", "Electronics", "1800"},
        {"Mouse", "Electronics", "450"}, {"Keyboard", "Electronics", "900"},
        {"Notebook", "Stationery", "45"}, {"Pen Set", "Stationery", "120"},
        {"Marker", "Stationery", "60"}, {"Desk Lamp", "Home", "650"},
        {"Water Bottle", "Home", "300"}, {"Backpack", "Travel", "1500"},
        {"Luggage Tag", "Travel", "80"}, {"Neck Pillow", "Travel", "350"}
    };

    private OrderGenerator() {}

    public static List<Order> generate(int count, long seed) {
        Random random = new Random(seed);
        OrderStatus[] statuses = OrderStatus.values();

        return IntStream.rangeClosed(1, count)
                .mapToObj(id -> {
                    String customer = CUSTOMERS[random.nextInt(CUSTOMERS.length)];
                    OrderStatus status = statuses[random.nextInt(statuses.length)];
                    int lineCount = 1 + random.nextInt(4);
                    List<OrderLine> lines = IntStream.range(0, lineCount)
                            .mapToObj(i -> {
                                String[] item = CATALOGUE[random.nextInt(CATALOGUE.length)];
                                return new OrderLine(item[0], item[1], 1 + random.nextInt(3), Double.parseDouble(item[2]));
                            })
                            .collect(Collectors.toList());
                    return new Order(id, customer, status, lines);
                })
                .collect(Collectors.toList());
    }
}
