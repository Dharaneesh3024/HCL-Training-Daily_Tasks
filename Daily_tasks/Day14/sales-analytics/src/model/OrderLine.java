package model;

public class OrderLine {

    private final String product;
    private final String category;
    private final int quantity;
    private final double unitPrice;

    public OrderLine(String product, String category, int quantity, double unitPrice) {
        this.product = product;
        this.category = category;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public String getProduct() { return product; }
    public String getCategory() { return category; }
    public int getQuantity() { return quantity; }
    public double getUnitPrice() { return unitPrice; }

    public double getTotal() {
        return quantity * unitPrice;
    }
}
