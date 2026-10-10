package service;

import exception.InsufficientStockException;
import exception.InvalidQuantityException;
import exception.OrderProcessingException;
import java.util.ArrayList;
import java.util.List;

public class OrderProcessor {

    private final Inventory inventory = new Inventory();
    private final List<String> auditLog = new ArrayList<>();

    // throws (in the signature) = "I might pass this on", throw (statement) = actually raising one
    public void process(String item, int qty) throws OrderProcessingException {
        String result = "FAILED";
        try {
            inventory.reserve(item, qty);
            result = "OK";
            System.out.println("Order placed: " + qty + " x " + item);
        } catch (InsufficientStockException e) {
            // wrap it but keep the original as the cause
            throw new OrderProcessingException("Could not process order for " + item, e);
        } finally {
            // runs on success, on checked failure and on unchecked failure
            auditLog.add(item + " x" + qty + " -> " + result);
            System.out.println("  [audit] " + item + " x" + qty + " -> " + result);
        }
    }

    // input like "pen:3"
    public void processText(String line) throws OrderProcessingException {
        String item;
        int qty;
        try {
            String[] parts = line.split(":");
            item = parts[0].trim();
            qty = Integer.parseInt(parts[1].trim());
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            // multi-catch: same handling for both
            throw new InvalidQuantityException("Bad order line '" + line + "', expected item:qty", e);
        }
        process(item, qty);
    }

    public List<String> getAuditLog() {
        return auditLog;
    }
}
