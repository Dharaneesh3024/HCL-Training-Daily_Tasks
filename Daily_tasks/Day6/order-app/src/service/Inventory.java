package service;

import exception.InsufficientStockException;
import exception.InvalidQuantityException;
import java.util.HashMap;
import java.util.Map;

public class Inventory {

    private final Map<String, Integer> stock = new HashMap<>();

    public Inventory() {
        stock.put("pen", 10);
        stock.put("notebook", 4);
        stock.put("bag", 1);
    }

    public void reserve(String item, int qty) throws InsufficientStockException {
        if (qty <= 0) {
            throw new InvalidQuantityException("Quantity must be positive, got " + qty);
        }
        int available = stock.getOrDefault(item, 0);
        if (qty > available) {
            throw new InsufficientStockException(item, qty, available);
        }
        stock.put(item, available - qty);
    }

    public int left(String item) {
        return stock.getOrDefault(item, 0);
    }
}
