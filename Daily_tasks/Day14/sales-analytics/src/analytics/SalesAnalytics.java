package analytics;

import model.Order;
import model.OrderLine;
import model.OrderStatus;

import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class SalesAnalytics {

    private final List<Order> orders;

    public SalesAnalytics(List<Order> orders) {
        this.orders = List.copyOf(orders);
    }

    public Map<String, Double> revenueByCategory() {
        return orders.stream()
                .flatMap(o -> o.getLines().stream())
                .collect(Collectors.groupingBy(OrderLine::getCategory, TreeMap::new,
                        Collectors.summingDouble(OrderLine::getTotal)));
    }

    public List<Map.Entry<String, Double>> topCustomers(int n) {
        return orders.stream()
                .collect(Collectors.toMap(Order::getCustomer, Order::getTotal, Double::sum))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .limit(n)
                .collect(Collectors.toList());
    }

    public Map<Boolean, List<Order>> deliveredVersusRest() {
        return orders.stream()
                .collect(Collectors.partitioningBy(o -> o.getStatus() == OrderStatus.DELIVERED));
    }

    public Map<OrderStatus, Long> countByStatus() {
        return orders.stream()
                .collect(Collectors.groupingBy(Order::getStatus,
                        () -> new EnumMap<>(OrderStatus.class), Collectors.counting()));
    }

    public double averageBasketSize() {
        return orders.stream().collect(Collectors.averagingInt(Order::getBasketSize));
    }

    public String productList() {
        return orders.stream()
                .flatMap(o -> o.getLines().stream())
                .map(OrderLine::getProduct)
                .distinct()
                .sorted()
                .collect(Collectors.joining(", "));
    }

    public DoubleSummaryStatistics orderValueStats() {
        return orders.stream().collect(Collectors.summarizingDouble(Order::getTotal));
    }

    public double totalRevenue() {
        return orders.stream().map(Order::getTotal).reduce(0.0, Double::sum);
    }

    public boolean hasCancelledAbove(double amount) {
        return orders.stream()
                .anyMatch(o -> o.getStatus() == OrderStatus.CANCELLED && o.getTotal() > amount);
    }

    public List<Order> largestOrders(int n) {
        return orders.stream()
                .sorted(Comparator.comparingDouble(Order::getTotal).reversed())
                .limit(n)
                .collect(Collectors.toList());
    }

    public List<Order> getOrders() {
        return orders;
    }
}
