package model;

import java.time.Period;

public enum Plan {

    MONTHLY(Period.ofMonths(1), 299),
    QUARTERLY(Period.ofMonths(3), 799),
    YEARLY(Period.ofYears(1), 2899);

    private final Period length;
    private final double price;

    Plan(Period length, double price) {
        this.length = length;
        this.price = price;
    }

    public Period getLength() { return length; }
    public double getPrice() { return price; }
}
