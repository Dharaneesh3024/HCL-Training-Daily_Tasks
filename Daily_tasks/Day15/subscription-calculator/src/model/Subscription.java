package model;

import java.time.LocalDate;
import java.util.Objects;

public class Subscription {

    private final int id;
    private final String customer;
    private final Plan plan;
    private final LocalDate startDate;

    public Subscription(int id, String customer, Plan plan, LocalDate startDate) {
        this.id = id;
        this.customer = Objects.requireNonNull(customer, "customer");
        this.plan = Objects.requireNonNull(plan, "plan");
        this.startDate = Objects.requireNonNull(startDate, "startDate");
    }

    public Integer getId() { return id; }
    public String getCustomer() { return customer; }
    public Plan getPlan() { return plan; }
    public LocalDate getStartDate() { return startDate; }

    @Override
    public String toString() {
        return "Subscription#" + id + " " + customer + " " + plan + " from " + startDate;
    }
}
