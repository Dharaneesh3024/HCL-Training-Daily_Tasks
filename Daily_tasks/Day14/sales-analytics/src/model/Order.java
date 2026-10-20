package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Order {

    private final int id;
    private final String customer;
    private final OrderStatus status;
    private final List<OrderLine> lines;

    public Order(int id, String customer, OrderStatus status, List<OrderLine> lines) {
        this.id = id;
        this.customer = customer;
        this.status = status;
        this.lines = Collections.unmodifiableList(new ArrayList<>(lines));
    }

    public int getId() { return id; }
    public String getCustomer() { return customer; }
    public OrderStatus getStatus() { return status; }
    public List<OrderLine> getLines() { return lines; }

    public double getTotal() {
        return lines.stream().mapToDouble(OrderLine::getTotal).sum();
    }

    public int getBasketSize() {
        return lines.stream().mapToInt(OrderLine::getQuantity).sum();
    }
}
