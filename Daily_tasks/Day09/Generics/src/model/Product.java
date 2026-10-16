package model;

public class Product implements Comparable<Product> {

    private final int id;
    private final String name;
    private final double price;
    private final int quantity;

    public Product(int id, String name, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public static Product fromCsv(String[] cols) {
        return new Product(
                Integer.parseInt(cols[0].trim()),
                cols[1].trim(),
                Double.parseDouble(cols[2].trim()),
                Integer.parseInt(cols[3].trim()));
    }

    public Integer getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }

    @Override
    public int compareTo(Product other) {
        return Double.compare(this.price, other.price);
    }

    @Override
    public String toString() {
        return String.format("#%d %-12s %8.2f x %d", id, name, price, quantity);
    }
}
