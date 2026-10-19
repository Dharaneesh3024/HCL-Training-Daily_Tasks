package model;

public class Product {

    private final int id;
    private final String name;
    private final String category;
    private final double price;
    private final boolean inStock;

    public Product(int id, String name, String category, double price, boolean inStock) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.inStock = inStock;
    }

    public static Product fromLine(String line) {
        String[] p = line.split(",");
        return new Product(Integer.parseInt(p[0].trim()), p[1].trim(), p[2].trim(),
                Double.parseDouble(p[3].trim()), Boolean.parseBoolean(p[4].trim()));
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }
    public boolean isInStock() { return inStock; }

    @Override
    public String toString() {
        return String.format("#%d %-12s %-12s %8.2f %s", id, name, category, price, inStock ? "in stock" : "sold out");
    }
}
